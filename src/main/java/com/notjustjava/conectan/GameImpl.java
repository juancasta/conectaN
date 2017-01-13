package com.notjustjava.conectan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.notjustjava.conectan.beans.Edge;
import com.notjustjava.conectan.beans.Node;

public class GameImpl {

	private int maxScore;

	List<List<Chip>> board;
	
	List<Node> listChipR = new ArrayList<>();
	List<Node> listChipY = new ArrayList<>();;
	
	
	/**
	 * @param rows the number of rows in the board
	 * @param columns the number of columns in the board
	 * @param n the number of connected chips required to win the game
	 */
	public GameImpl(final int rows, final int columns, final int n) {
		
		this.maxScore = n;
		
		board = new ArrayList<>(columns);
		 
		for (int i = 0; i < columns; i++) {
			  board.add(new LinkedList<Chip>());
			}
		
	}

	/**
	 * @return the game result if there is a winner, together with the winning chips, or null otherwise
	 * <p>
	 * Note that when the winning streak is longer than the required number (for example in connect-4 when a new chip joins two lines of 2, resulting
	 * in a streak of 5) this method may return any of the smaller 4-chip streaks, or the whole 5-chip streak.
	 */

	public GameResult getGameResult() {
		
		//RED
		GameResult resp = evaluate(Chip.RED, this.listChipR, TypeStrike.HORIZONTAL);
		if (resp != null) return resp;
		resp = evaluate(Chip.RED, this.listChipR, TypeStrike.VERTICAL);
		if (resp != null) return resp;
		resp = evaluate(Chip.RED, this.listChipR, TypeStrike.DIAGONAL_RIGHT);
		if (resp != null) return resp;
		resp = evaluate(Chip.RED, this.listChipR, TypeStrike.DIAGONAL_LEFT);
		if (resp != null) return resp;
		
		//YELLOW
		resp = evaluate(Chip.YELLOW, this.listChipY, TypeStrike.HORIZONTAL);
		if (resp != null) return resp;
		resp = evaluate(Chip.YELLOW, this.listChipY, TypeStrike.VERTICAL);
		if (resp != null) return resp;
		resp = evaluate(Chip.YELLOW, this.listChipY, TypeStrike.DIAGONAL_RIGHT);
		if (resp != null) return resp;
		resp = evaluate(Chip.YELLOW, this.listChipY, TypeStrike.DIAGONAL_LEFT);
		if (resp != null) return resp;
		
		return null;

	}

	private GameResult evaluate(Chip chip, List<Node> listChip, TypeStrike strike) {
		
		for (Node node: listChip) {
		
			List<Node> winnerList = new ArrayList<>();
			winnerList.add(node);
			Boolean result = evaluateStrike(node, 1, winnerList, strike);
			
			if (result) {
				System.out.println("Ha ganado el " + chip);
				return buildResponse(chip, winnerList);
			}
				
		}
		
		return null;
		
	}
	
	private Boolean evaluateStrike(Node source, int score, List<Node> winnerList, TypeStrike strike) {
		
		for (Edge edge: source.getEdges()) {
			
			if (evaluateProperCondition(source, edge.getNodeTarget(), strike)) {
				
				score = score + 1;
				winnerList.add(edge.getNodeTarget());
				if (score == this.maxScore) {
					return true;
				}
				Boolean resp = evaluateStrike(edge.getNodeTarget(), score, winnerList, strike);
				if (resp) {
					//salida de recursividad. Hemos acabado
					return resp;
				}
			}
			
		}
		return false;
		
	}
	
	private boolean evaluateProperCondition(Node source, Node nodeTarget, TypeStrike typeStreak) {
		
		switch (typeStreak) {
			case HORIZONTAL:
				return 	
						source.getPosition().getRow() == nodeTarget.getPosition().getRow() &&
						source.getPosition().getColumn() + 1 == nodeTarget.getPosition().getColumn();
			case VERTICAL:
				return
						source.getPosition().getColumn() == nodeTarget.getPosition().getColumn() &&
						source.getPosition().getRow() + 1 == nodeTarget.getPosition().getRow();
			case DIAGONAL_RIGHT:
				return
						source.getPosition().getColumn() + 1 == nodeTarget.getPosition().getColumn() &&
						source.getPosition().getRow() + 1 == nodeTarget.getPosition().getRow();
			case DIAGONAL_LEFT:
				return
						source.getPosition().getColumn() - 1 == nodeTarget.getPosition().getColumn() &&
						source.getPosition().getRow() + 1 == nodeTarget.getPosition().getRow();
		
		}
		return false;
	}

	private GameResult buildResponse(Chip chip, List<Node> winnerList) {

		List<ChipPosition> l = winnerList.stream().map(s -> s.getPosition()).collect(Collectors.toList());
		ChipPosition[] acp = new ChipPosition[l.size()];
		GameResult gr = new GameResult(chip, l.toArray(acp));
			
		return gr;
	}
	
	
	/**
	 * Deja una nueva ficha en el ta
	 * Drops a new chip into the board
	 *
	 * @param chip the type of chip
	 * @param column the column where the player drops the chip
	 */
	public void putChip(final Chip chip, final int column) {        
		
		int row = this.board.get(column).size();
		
		this.board.get(column).add(chip);
		
		System.out.println("Metiendo ficha en fila | columna: " + row + column);
		
		switch (chip) {
			case RED: 	listChipR.add(new Node(new ChipPosition(row, column)));
						buildEdges(listChipR, chip);
						break;
			case YELLOW: 	listChipY.add(new Node(new ChipPosition(row, column)));
							buildEdges(listChipY, chip);
							break;
		}
		
		
		
	}

	/**
	 * Builds edges of all vortex
	 * @param chip
	 */
	private void buildEdges(List<Node> listChipR2, Chip chip) {
		
		for (Node source: listChipR2) {
			source.getEdges().clear();
			
			for (Node target: listChipR2) {
				if (!source.equals(target)) {
					
//					System.out.println("Estudiando viabilidad de: " + source.getPosition() + " " + target.getPosition());
					
					if ((target.getPosition().getColumn() >= source.getPosition().getColumn() -1) &&
						(target.getPosition().getColumn() <= source.getPosition().getColumn() + 1) &&
						(target.getPosition().getRow() >= source.getPosition().getRow() - 1) &&
						(target.getPosition().getRow() <= source.getPosition().getRow() + 1)) {
						
//						System.out.println("Estan comunidados");
						source.getEdges().add(new Edge(source, target));
						
					}
					
				}
				
			}
			
			
		}
		System.out.println("Impriendo aristas de chip: " + chip);
		printAllEdgesList(listChipR2);
		
	}

	/**
	 * Prints all edges, just for debubbing
	 * @param listChipR2
	 */
	private void printAllEdgesList(List<Node> listChipR2) {
		
		for (Node node: listChipR2) {
			System.out.println("Imprimiendo todos los destino de: " + node.getPosition() + " -> " + node.getEdgesString());
		}
		
	}

}

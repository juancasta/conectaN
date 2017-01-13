package com.notjustjava.conectan;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.notjustjava.conectan.Chip;
import com.notjustjava.conectan.ChipPosition;
import com.notjustjava.conectan.GameImpl;
import com.notjustjava.conectan.GameResult;

public class GameTest {

	private static GameResult run(final int rows, final int columns, final int connectN, final Player playerA, final Player playerB) {
		final GameEngine gameEngine = new GameEngine();
		final GameImpl game = new GameImpl(rows, columns, connectN);
		return gameEngine.play(game, playerA, playerB);
	}

	private static ChipPosition[] buildStreak(final ChipPosition start, final ChipPosition end) {
		final int rowIncrement = Integer.signum(end.getRow() - start.getRow()); // doesn't work properly on int overflow
		final int columnIncrement = Integer.signum(end.getColumn() - start.getColumn()); // doesn't work properly on int overflow
		final int length = Math.max(Math.abs(end.getRow() - start.getRow()), Math.abs(end.getColumn() - start.getColumn())) + 1;
		final ChipPosition[] result = new ChipPosition[length];

		int row = start.getRow();
		int column = start.getColumn();
		for (int i = 0; i < length; i++) {
			result[i] = new ChipPosition(row, column);
			row += rowIncrement;
			column += columnIncrement;
		}

		return result;
	}

	@Test
	public void testHorizontal() {
		final GameResult gameResult = run(6, 7, 2, new Player(Chip.RED, 1, 2, 5), new Player(Chip.YELLOW, 0, 0, 0));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(Chip.RED));
	}
	
	@Test
	public void testVertical() {
		final GameResult gameResult = run(6, 7, 2, new Player(Chip.RED, 1, 4, 5), new Player(Chip.YELLOW, 0, 0, 0));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(Chip.YELLOW));
	}
	
	@Test
	public void testDiagonalRight() {
		final GameResult gameResult = run(6, 7, 2, new Player(Chip.RED, 2, 3, 5), new Player(Chip.YELLOW, 3, 0, 0));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(Chip.RED));
	}
	
}

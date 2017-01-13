package com.notjustjava.conectan.beans;

import java.util.ArrayList;
import java.util.List;

import com.notjustjava.conectan.ChipPosition;

public class Node {

	private List<Edge> edges;
	private ChipPosition position;
	private Boolean visited;
	
	public Node(ChipPosition position) {
		super();
		this.edges = new ArrayList<>();
		this.position = position;
	}

	public String getEdgesString() {
		StringBuilder str = new StringBuilder();
		if (this.edges != null && this.edges.size() > 0) {
			for (Edge edge : this.edges) {
				str.append(edge.getNodeTarget().getPosition());
			}
		}
		return str.toString();
		
	}
	
	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public ChipPosition getPosition() {
		return position;
	}

	public void setPosition(ChipPosition position) {
		this.position = position;
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}
	
	
	
}

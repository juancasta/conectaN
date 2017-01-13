package com.notjustjava.conectan.beans;

public class Edge {

	private Node nodeSource;
	private Node nodeTarget;
	
	public Edge(Node nodeSource, Node nodeTarget) {
		super();
		this.nodeSource = nodeSource;
		this.nodeTarget = nodeTarget;
	}
	
	public Node getNodeSource() {
		return nodeSource;
	}

	public void setNodeSource(Node nodeSource) {
		this.nodeSource = nodeSource;
	}

	public Node getNodeTarget() {
		return nodeTarget;
	}

	public void setNodeTarget(Node nodeTarget) {
		this.nodeTarget = nodeTarget;
	}
	
}

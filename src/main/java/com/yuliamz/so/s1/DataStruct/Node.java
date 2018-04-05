package com.yuliamz.so.s1.DataStruct;

public class Node <T> {
	
	protected T info;
	protected Node<T> next;

	public Node(T info) {
		this.info = info;
		this.next = null;
	}
	
	public Node(T info, Node<T> next) {
		this.info = info;
		this.next = next;
	}
	
	public T getInfo() {
		return info;
	}
	public Node<T> getNext() {
		return next;
	}

}

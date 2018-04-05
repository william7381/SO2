package com.yuliamz.so.s1.DataStruct;

public class Queue <T> {
	private Node <T> first;
	private Node <T> last;
	
	public Queue() {
		this.first = null;
		this.last = null;
	}
	
	public void push(T info) {
		if (isEmpty()) {
			this.last = this.first = new Node<T>(info);
		}else {
			this.last.next = new Node<T>(info);
			this.last = this.last.next;
		}
	}
	
	public T pop() {
		Node<T> aux = this.first;
		this.first = this.first.next;
		if (isEmpty()) {
			this.last = null;
		}
		return aux.info;
	}
        
        public T peek() {
            return this.first.info;
        }
        
        public boolean isEmpty() {
		return first == null;
	}
}

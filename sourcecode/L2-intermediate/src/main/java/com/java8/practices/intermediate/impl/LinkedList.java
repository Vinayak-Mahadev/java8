package com.java8.practices.intermediate.impl;

public class LinkedList<E> 
{
	private Node<E> first;
	private Node<E> last;
	private int size;

	@SuppressWarnings("hiding")
	private class Node<E>
	{
		private E data;
		private Node<E> next;
		private Node<E> prev;

		protected Node(E data) 
		{
			this.data = data;
		}

		E getData()
		{
			return data;
		}

		@Override
		public String toString() {
			if(data != null && next != null && prev != null)
				return "Node [data=" + data + ", next=" + next.data + ", prev=" + prev.data + "]";
			return "Node [data=" + data + ", next=" + next + ", prev=" + prev + "]";
		}

	}

	public void insert(E data) 
	{
		Node<E> newNode = new Node<>(data);
		if(this.first == null)
		{
			this.first = newNode;
			//			newNode.prev = this.first;
		}
		else
		{
			Node<E> node = this.first;
			while (node.next != null) 
				node = node.next;
			newNode.prev = node;
			this.last = node.next = newNode;
		}
		size++;
	}

	public void print() 
	{
		Node<E> node = this.first;
		if(node != null)
			System.out.print("LinkedList :: " + node.getData());
		while (node != null && node.next != null) 
		{
			node = node.next;
			System.out.print(" " + node.getData());
		}
		System.out.println();
	}

	public E get(int i) 
	{
		Node<E> node = getNode(i);
		return (node != null) ? node.getData() : null;
	}

	public E getFirst() 
	{
		Node<E> node = this.first;
		return (node != null) ? node.getData() : null;
	}

	public E getLast() 
	{
		Node<E> node = this.last;
		return (node != null) ? node.getData() : null;
	}

	public Node<E> getNode(int i) 
	{
		Node<E> node = null;
		if(first != null)
			node = this.first;
		if (i > size) return null;
		else if(node != null && i <= 0)
			return node;

		for (int j = 0; j < i; j++) 
			node = node.next;



		if(node != null) return node;
		return null;			
	}


	public int getSize() 
	{
		return size;
	}

	public void removeAll() 
	{
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	public void remove(int index) 
	{

	}



}

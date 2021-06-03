package de.fhbielefeld.pmdungeon.quibble.util;

import java.util.ArrayList;

public class HashTable<K, V>
{
	private static class Node<K, V>
	{
		K key;
		V value;
		final int hashCode;
		Node<K, V> next;
		
		private Node(K key, V value, int hashCode)
		{
			this.key = key;
			this.value = value;
			this.hashCode = hashCode;
		}
		
	}
	
	private ArrayList<Node<K, V>> buckets;
	private int numBuckets;
	private int size;
	
	public HashTable()
	{
		buckets = new ArrayList<>();
		numBuckets = 16;
		size = 0;
		
		for(int i = 0; i < numBuckets; i++)
		{
			buckets.add(null);
		}
	}
	
	private int getBucketIndex(K key)
	{
		int hashCode = key.hashCode();
		int index = hashCode % numBuckets;
		
		index = index < 0 ? index * -1 : index;
		return index;
	}
	
	public V remove(K key)
	{
		int bucketIndex = getBucketIndex(key);
		int hashCode = key.hashCode();
		Node<K, V> head = buckets.get(bucketIndex);
		
		Node<K, V> prev = null;
		while(head != null)
		{
			if(head.key.equals(key) && hashCode == head.hashCode)
			{
				break;
			}
			prev = head;
			head = head.next;
		}
		
		if(head == null)
		{
			return null;
		}
		
		size--;
		
		
		if(prev != null)
		{
			prev.next = head.next;
		}
		else
		{
			buckets.set(bucketIndex, head.next);
		}
		return head.value;
	}
	
	public V get(K key)
	{
		int bucketIndex = getBucketIndex(key);
		int hashCode = key.hashCode();
		
		Node<K, V> head = buckets.get(bucketIndex);
		while(head != null)
		{
			if(head.key.equals(key) && head.hashCode == hashCode)
				return head.value;
			head = head.next;
		}
		return null;
	}
	
	public void put(K key, V value)
	{
		int bucketIndex = getBucketIndex(key);
		int hashCode = key.hashCode();
		Node<K, V> head = buckets.get(bucketIndex);
		
		while(head != null)
		{
			if(head.key.equals(key) && head.hashCode == hashCode)
			{
				head.value = value;
				return;
			}
			head = head.next;
		}
		
		size++;
		head = buckets.get(bucketIndex);
		Node<K, V> newNode = new Node<K, V>(key, value, hashCode);
		newNode.next = head;
		buckets.set(bucketIndex, newNode);
		
		if((1.0 * size) / numBuckets >= 0.7)
		{
			ArrayList<Node<K, V>> tmp = buckets;
			buckets = new ArrayList<Node<K, V>>();
			numBuckets = 2 * numBuckets;
			size = 0;
			for(int i = 0; i < numBuckets; i++)
			{
				buckets.add(null);
			}
			
			for(Node<K, V> node : tmp)
			{
				while(node != null)
				{
					put(node.key, node.value);
					node = node.next;
				}
			}
		}
	}
}
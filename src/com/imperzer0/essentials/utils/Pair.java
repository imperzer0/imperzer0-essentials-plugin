package com.imperzer0.essentials.utils;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V>
{
	private final K key;
	private V val;
	
	public Pair(K key, V val)
	{
		this.val = val;
		this.key = key;
	}
	
	@Override
	public K getKey() { return key; }
	
	@Override
	public V getValue() { return val; }
	
	@Override
	public V setValue(V v)
	{
		V tmp = val;
		val = v;
		return tmp;
	}
}

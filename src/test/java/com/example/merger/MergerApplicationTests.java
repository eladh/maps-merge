package com.example.merger;

import com.example.merger.core.MergingEngine;
import com.example.merger.core.handlers.ConcatStringHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MergerApplicationTests {

	@Test
	public void tesMapsMerge() throws ExecutionException, InterruptedException {
		MergingEngine<Integer,String > mergingEngine = new MergingEngine<>();
		List<Map<Integer, String>> list = generateMapsList();

		Map<Integer, String> mergedMap = mergingEngine.merge(list.get(0) ,list.get(2) , new ConcatStringHandler());
		assertEquals("boopookoo", mergedMap.get(1));
		assertEquals("bik", mergedMap.get(11));
	}

	@Test
	public void tesMapsListMerge() throws ExecutionException, InterruptedException {
		MergingEngine<Integer,String > mergingEngine = new MergingEngine<>();
		List<Map<Integer, String>> list = generateMapsList();

		Map<Integer, String> mergedMap = mergingEngine.merge(list, new ConcatStringHandler());

		assertEquals("foobarboopookoo", mergedMap.get(1));
		assertEquals("foo2baz", mergedMap.get(2));
		assertEquals("pookootvv", mergedMap.get(3));
		assertEquals("bddd", mergedMap.get(4));
		assertEquals("bik", mergedMap.get(11));
	}

	private List<Map<Integer, String>> generateMapsList() {
		Map<Integer ,String> data4 = new HashMap<>(3);
		data4.put(1 ,"foo");
		data4.put(2 ,"foo2");
		data4.put(3 ,"pookoo");
		data4.put(4 ,"bddd");

		Map<Integer, String> data3 = new HashMap<>(2);
		data3.put(1 ,"bar");
		data3.put(2 ,"baz");
		data3.put(3 ,"tvv");

		Map<Integer, String> data2 = new HashMap<>(2);
		data2.put(1 ,"boo");
		data2.put(11 ,"bik");

		Map<Integer, String> data1 = new HashMap<>(2);
		data1.put(1 ,"pookoo");

		List<Map<Integer, String>> list = new ArrayList<>(4);
		list.add(data2);
		list.add(data3);
		list.add(data1);
		list.add(data4);

		return list;
	}


}

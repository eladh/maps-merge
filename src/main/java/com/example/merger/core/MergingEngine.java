package com.example.merger.core;

import com.example.merger.core.handlers.Handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class MergingEngine<K,V> {

	public Map<K,V> merge(Map<K, V> map1, Map<K, V> map2 , Handler<V> handler) {
		Map<K,V> mergeFromMap = map1.size() > map2.size() ? map2 : map1;
		Map<K,V> mergeToMap = map1.size() > map2.size() ? map1 : map2;

		mergeFromMap.forEach((key, value) -> {
			if (mergeToMap.containsKey(key)) {
				mergeToMap.replace(key, handler.handle(mergeToMap.get(key), value));
			} else {
				mergeToMap.put(key, value);
			}
		});

		return mergeToMap;
	}

	public Map<K,V> merge(List<Map<K, V>> maps, Handler<V> handler) {
		int numberOfMaps = maps.size();

		if (numberOfMaps == 1) {
			return maps.get(0);
		}

		List<Map<K, V>> listMaps = maps.stream()
				.sorted(Comparator.comparingInt(Map::size))
				.collect(Collectors.toList());

		int nThreads = numberOfMaps / 2;

		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		List<Callable<Map<K,V>>> tasks = new ArrayList<>(nThreads);

		for (int i =0;i<nThreads;i++) {
			int index = i *2;
			tasks.add(() -> merge(listMaps.get(index) ,listMaps.get(index +1) , handler));
		}
		List<Map<K, V>> sortedMapsList = executeMerge(executor, tasks);

		if (numberOfMaps % 2 != 0) {
			sortedMapsList.add(maps.get(numberOfMaps-1));
		}

		return merge(sortedMapsList , handler);
	}

	private List<Map<K, V>> executeMerge(ExecutorService executor, List<Callable<Map<K, V>>> tasks) {
		List<Map<K, V>> sortedMapsList = new ArrayList<>(tasks.size());

		try {
			List<Future<Map<K, V>>> futures = executor.invokeAll(tasks);
			executor.shutdown();
			for (final Future<Map<K, V>> future : futures) {
				sortedMapsList.add(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executor.shutdownNow();
		}

		return sortedMapsList;
	}
}
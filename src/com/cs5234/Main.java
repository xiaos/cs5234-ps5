package com.cs5234;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String... args) {
		new Main().test();
	}

	private static final int N = 10000;
	private static final int M = 200;
	private static final int A = 50;
	private static final int B = 100;

	private DataGenerator dataGenerator;
	private HashGenerator hashGenerator;

	public Main() {

		dataGenerator = new DataGenerator(N, M);
		hashGenerator = new HashGenerator(A, B, M);
	}

	public void test() {
//		int[] data = dataGenerator.uniform();
		int[] data = dataGenerator.exponential();

		Map<Integer, Integer> algo1Results = algo1(data);
		Map<Integer, Integer> algo2Results = algo2(data);

		Map<Integer, Integer> results = count(data);

		int error1 = 0;
		for (Map.Entry<Integer, Integer> entrySet : algo1Results.entrySet()) {
			error1 += Math.abs(entrySet.getValue() - results.get(entrySet.getKey()));

			// System.out
			// .println(entrySet.getKey() + " : " + entrySet.getValue() + " : "
			// + results.get(entrySet.getKey()));
		}

		int error2 = 0;
		for (Map.Entry<Integer, Integer> entrySet : algo2Results.entrySet()) {
			error2 += Math.abs(entrySet.getValue() - results.get(entrySet.getKey()));

			System.out
					.println(entrySet.getKey() + " : " + entrySet.getValue() + " : " + results.get(entrySet.getKey()));
		}

		System.out.println("Error1: " + error1);
		System.out.println("Error2: " + error2);

	}

	private Map<Integer, Integer> count(int[] data) {
		Map<Integer, Integer> results = new HashMap<>();

		for (int i = 0; i < data.length; i++) {
			int count = 0;

			if (results.get(data[i]) != null) {
				count = results.get(data[i]);
			}

			results.put(data[i], ++count);
		}

		return results;
	}

	private Map<Integer, Integer> algo2(int[] data) {
		Map<Integer, Integer> results = new HashMap<>();

		//
		int[][] counters = process(data);

		for (int i = 0; i < data.length; i++) {
			if (results.get(data[i]) == null) {
				// query result.
				int[] tempResults = new int[A];
				for (int j = 0; j < tempResults.length; j++) {
					int hashValue = hashGenerator.hash(j, data[i]);
					if (hashValue % 2 == 0) {
						if (hashValue + 1 < B) {
							tempResults[j] = counters[j][hashValue + 1];
						}
					} else {
						tempResults[j] = counters[j][hashValue - 1];
					}

				}

				// get median value.
				int result = median(tempResults);

				results.put(data[i], result);
			}
		}

		return results;
	}

	private Map<Integer, Integer> algo1(int[] data) {
		Map<Integer, Integer> results = new HashMap<>();

		//
		int[][] counters = process(data);

		for (int i = 0; i < data.length; i++) {
			if (results.get(data[i]) == null) {
				// query result.
				int[] tempResults = new int[A];
				for (int j = 0; j < tempResults.length; j++) {
					int hashValue = hashGenerator.hash(j, data[i]);
					tempResults[j] = counters[j][hashValue];
				}

				// get median value.
				int result = median(tempResults);

				results.put(data[i], result);
			}
		}

		return results;
	}

	private int[][] process(int[] data) {
		int[][] counters = new int[A][B];
		for (int i = 0; i < data.length; i++) {
			int hashIndex = (int) (Math.random() * A);
			int hashValue = hashGenerator.hash(hashIndex, data[i]);

			counters[hashIndex][hashValue]++;
		}

		return counters;
	}

	private int median(int[] results) {
		int result;
		Arrays.sort(results);
		if (results.length % 2 == 0) {
			result = (results[results.length / 2] + results[results.length - 1]) / 2;
		} else {
			result = results[results.length / 2];
		}

		for (int i = 0; i < results.length; i++) {
			System.out.println("\t " + results[i]);
		}

		return result;
	}

}

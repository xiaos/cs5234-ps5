package com.cs5234;

import java.util.Arrays;

public class Main {

	public static void main(String... args) {
		new Main().test();
	}

	private static final int N = 1000000;
	private static final int M = 300;

	private static final int A = 100;
	private static final int B = 400;

	private DataGenerator dataGenerator;
	private HashGenerator hashGenerator;

	public Main() {

		dataGenerator = new DataGenerator(N, M);
		hashGenerator = new HashGenerator(A, B, M);
	}

	public void test() {
		//int[] data = dataGenerator.uniform();
		 int[] data = dataGenerator.exponential();

		int[][] counters = process(data);

		int[] algo1Results = algo1(counters);
		int[] algo2Results = algo2(counters);

		int[] results = count(data);

		compareResults(algo1Results, results);
		compareResults(algo2Results, results);
	}

	private int[][] process(int[] data) {
		int[][] counters = new int[A][B];

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < A; j++) {
				int hashValue = hashGenerator.hash(j, data[i]);
				counters[j][hashValue]++;
			}
		}

		return counters;
	}

	private int[] count(int[] data) {
		int[] results = new int[M];

		for (int i = 0; i < data.length; i++) {
			results[data[i]]++;
		}

		return results;
	}

	private int[] algo1(int[][] counters) {
		int[] results = new int[M];

		for (int i = 0; i < M; i++) {
			// query result.
			int[] tempResults = new int[A];
			for (int j = 0; j < tempResults.length; j++) {
				int hashValue = hashGenerator.hash(j, i);
				tempResults[j] = counters[j][hashValue];
			}

			// get median value.
			results[i] = median(tempResults);
		}

		return results;
	}

	private int[] algo2(int[][] counters) {
		int[] results = new int[M];

		for (int i = 0; i < M; i++) {
			// query result.
			int[] tempResults = new int[A];
			for (int j = 0; j < tempResults.length; j++) {
				int hashValue = hashGenerator.hash(j, i);
				if (hashValue % 2 == 0) {
					if (hashValue + 1 < B) {
						tempResults[j] = counters[j][hashValue] - counters[j][hashValue + 1];
					}
				} else {
					tempResults[j] = counters[j][hashValue] - counters[j][hashValue - 1];
				}

			}

			// get median value.
			results[i] = median(tempResults);
		}

		return results;
	}

	private int median(int[] results) {
		int result;
		Arrays.sort(results);
		if (results.length % 2 == 0) {
			result = (results[results.length / 2] + results[results.length / 2 - 1]) / 2;
		} else {
			result = results[results.length / 2];
		}

		// System.out.println("------------------------------------");
		for (int i = 0; i < results.length; i++) {
			// System.out.println("\t " + results[i]);
		}

		return result;
	}

	private void compareResults(int[] algoResults, int[] results) {
		System.out.println("====================================================");
		int error = 0;

		for (int i = 0; i < M; i++) {
			// System.out.println(i + " : " + algoResults[i] + " : " +
			// results[i]);
			error += Math.abs(algoResults[i] - results[i]);
		}

		System.out.println("Error:" + error);
	}

}

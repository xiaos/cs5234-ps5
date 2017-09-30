package com.cs5234;

import java.util.Arrays;

public class Main {

	public static void main(String... args) {
		new Main().test();
	}

	private static int N = 1000000;
	private static int M = 200;

	private static final int A = 4;
	private static final int B = 40;

	private static final float DELTA = 0.05f;

	private DataGenerator dataGenerator;
	private HashGenerator hashGenerator;

	public Main() {
		// 13238

	}

	public void test() {
		// int[] data = dataGenerator.book();
		// int[] data = dataGenerator.exponential();
		N = 105197;
		M = 13739;

		dataGenerator = new DataGenerator(N, M);
		hashGenerator = new HashGenerator(A, B, M);

		int[] data = dataGenerator.book();
		int[][] counters = process(data);
		int[] algo1Results = algo1(counters);
		int[] results = count(data);
		compareResults("Algo1", algo1Results, results);

		data = dataGenerator.book();
		counters = process(data);
		int[] algo2Results = algo2(counters);
		results = count(data);
		compareResults("Algo2", algo2Results, results);

	}

	private int[][] process(int[] data) {
		int[][] counters = new int[A][B];

		// i -< [0, A-1], j -< [0, B-1]
		// When we see element x in the stream, then for all i -< [0, A-1], we
		// will increment the counter C(i; hi(x))
		for (int indexJ = 0; indexJ < data.length; indexJ++) {
			int x = data[indexJ];
			for (int i = 0; i < A; i++) {
				int j = hashGenerator.hash(i, x);
				counters[i][j]++;
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

	private void compareResults(String algo, int[] algoResults, int[] results) {
		System.out.println("======================== " + algo + " ==========================");
		System.out.println("N:" + N + " M:" + M + " DELTA:" + DELTA);
		System.out.println("A:" + A + " B:" + B);
		int error = 0;

		for (int i = 0; i < M; i++) {
			if (Math.abs(algoResults[i] - results[i]) > N * DELTA) {
				error += 1;
			}
		}

		System.out.println("Error:" + error);
		System.out.println();
	}

}

package com.cs5234;

import java.util.Random;

public class DataGenerator {

	public static void main(String... args) {
		new DataGenerator(10000, 500).exponential();
	}

	private int N;
	private int M;

	private Random random;

	public DataGenerator(int N, int M) {
		this.N = N;
		this.M = M;
		random = new Random();
	}

	public int[] uniform() {
		int[] values = new int[N];

		for (int i = 0; i < values.length; i++) {
			values[i] = (int) (random.nextDouble() * M);
		}

		return values;
	}

	public int[] exponential() {
		int[] values = new int[N];

		double max = 1 - Math.pow(0.5, M + 1);

		for (int i = 0; i < values.length; i++) {
			double u = Math.random() * max;
			values[i] = (int) (M * Math.log(1 - u) / (-2));

			System.out.println(values[i]);
		}

		return values;
	}
}

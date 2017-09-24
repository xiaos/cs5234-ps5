package com.cs5234;

import java.util.Random;

public class DataGenerator {

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

		for (int i = 0; i < values.length; i++) {

		}

		return values;
	}
}

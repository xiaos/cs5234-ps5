package com.cs5234;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DataGenerator {

	public static void main(String... args) {
		new DataGenerator(10000, 500).book();
	}

	private int N;
	private int M;

	private Random random;

	public DataGenerator(int N, int M) {
		this.N = N;
		this.M = M;
		random = new Random();
	}

	public int[] book() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		List<Integer> numList = new ArrayList<>();

		Scanner in;
		try {
			in = new Scanner(new InputStreamReader(new FileInputStream("pg1112.txt"), StandardCharsets.UTF_8));

			while (in.hasNext()) {
				String word = in.next();

				if (word != null) {
					word = word.trim();

					Integer index = map.get(word);
					if (index == null) {
						index = map.size();
						map.put(word, index);
					}

					numList.add(index);
				}
			}
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int[] nums = new int[numList.size()];
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			nums[i] = numList.get(i);
			if (nums[i] > max) {
				max = nums[i];
			}
			// System.out.println(nums[i]);
		}
		System.out.println("N:" + nums.length);
		System.out.println("M:" + max);

		return nums;
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

			values[i] = (int) (-1 - log2(1 - u));
		}

		return values;
	}

	public static double log2(double n) {
		return (Math.log(n) / Math.log(2));
	}

}

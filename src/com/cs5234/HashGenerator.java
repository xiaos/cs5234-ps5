package com.cs5234;

public class HashGenerator {

	private int A;
	private int B;
	private int p;

	private int[] a;
	private int[] b;

	public HashGenerator(int A, int B, int M) {
		this.A = A;
		this.B = B;

		// prime p larger than M
		p = 2 * M;

		a = new int[A];
		b = new int[A];

		for (int i = 0; i < A; i++) {
			a[i] = (int) (Math.random() * (p - 1) + 1);
			b[i] = (int) (Math.random() * p);
		}
	}

	public int hash(int hashIndex, int x) {
		return ((a[hashIndex] * x + b[hashIndex]) % p) % B;
	}

}

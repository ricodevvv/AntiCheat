/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.tuple;

public class Tuple<A, B> {
	private A a;
	private B b;

	public Tuple(A var1, B var2) {
		this.a = var1;
		this.b = var2;
	}

	public A a() {
		return this.a;
	}

	public B b() {
		return this.b;
	}
}

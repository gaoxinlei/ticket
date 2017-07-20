package net.ykrd.tick;

public class Sale {

	public static void main(String[] args) {
		Saler saler = new Saler();
		Thread t1 = new Thread(saler,"售票员1号");
		Thread t2 = new Thread(saler,"售票员2号");
		Thread t3 = new Thread(saler,"售票员3号");
		t1.start();
		t2.start();
		t3.start();
	}
}

package net.ykrd.tick;

public class Saler implements Runnable{

	private int total = 40;
	
	@Override
	public void run() {
		while(total>0) {
			synchronized (this) {
				if(total>0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					total--;
					System.out.println(Thread.currentThread().getName()+"售出一张票,余票"+total+"张");
				}
			}
		}
	}

}

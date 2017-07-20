package net.ykrd.sale;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket {

	private Lock lock = new ReentrantLock();
	private AtomicInteger total;
	
	
	
	public Ticket(AtomicInteger total) {
		super();
		this.total = total;
	}



	public void saleTicket() {
		//用try finally块锁与加锁.
		lock.lock();
		try {
			int i = total.get();
			if(i>0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				total.decrementAndGet();
				System.out.println(Thread.currentThread().getName()+"售出票号"+i+",还剩"+total.get()+"张票.");
			}
			
		} finally {
			lock.unlock();
		}
	}
}

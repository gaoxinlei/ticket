package net.ykrd.sale;

import java.util.concurrent.atomic.AtomicInteger;

public class SaleTicket {

	public static void main(String[] args) {
		//资源,票
		final Ticket ticket = new Ticket(new AtomicInteger(40));
		//线程三个
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<40;i++) {
					ticket.saleTicket();
				}
			}
		},"售票员1号").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<40;i++) {
					ticket.saleTicket();
				}
			}
		},"售票员2号").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<40;i++) {
					ticket.saleTicket();
				}
			}
		},"售票员3号").start();
		
		Runnable r1 = () ->{for(int i=0;i<40;i++) {ticket.saleTicket();}};
		Runnable r2 = () ->{for(int i=0;i<40;i++) {ticket.saleTicket();}};
		new Thread(r1, "售票员4号").start();
		new Thread(r2, "售票员5号").start();
	}
}

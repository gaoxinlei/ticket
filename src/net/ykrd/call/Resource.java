package net.ykrd.call;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Resource {

	private AtomicInteger number = new AtomicInteger(0);
	private Lock lock = new ReentrantLock();
	/**
	 * 1.交替增减例题使用的两个锁。
	 */
	private Condition add = lock.newCondition(); 
	private Condition sub = lock.newCondition(); 
	/**
	 * 2.三个锁依次唤醒需要的标记和condition.
	 * a锁打5次，b锁打10次，c锁打15次。
	 */
	private int flag = 1;
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	private Condition c3 = lock.newCondition();
	
	/**
	 * 3.读写锁测试需要的锁和变量
	 */
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private int stock;
	/**
	 * 读写锁测试代码的方法组
	 * 排他锁,写锁:write方法
	 * 共享锁:read方法.
	 * @param stock
	 */
	public void write(int stock) {
		//写锁,排他.
		rwLock.writeLock().lock();
		try {
			System.out.println("线程:"+Thread.currentThread().getName()+"写入值:"+stock);
			this.stock = stock;
		} catch (Exception e) {
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public int read() {
		System.out.println("线程:"+Thread.currentThread().getName()+"读取了值:"+this.stock);
		return this.stock;
	}
	
	
	/**
	 * 三个线程交替执行打印的方法组
	 * @throws InterruptedException 
	 */
	public void fun1(int loop) throws InterruptedException {
		//判断标记
		lock.lock();
		try {
			while(flag!=1) {
				c1.await();
			}
			for(int i=1;i<=5;i++) {
				System.out.println(Thread.currentThread().getName()+"第"+loop+"轮打印第"+i+"次");
			}
			flag = 2;
			c2.signal();

		} finally {
			lock.unlock();
		}
	}
	public void fun2(int loop) throws InterruptedException {
		//判断标记
		lock.lock();
		try {
			while(flag!=2) {
				c2.await();
			}
			for(int i=1;i<=10;i++) {
				System.out.println(Thread.currentThread().getName()+"第"+loop+"轮打印第"+i+"次");
			}
			flag = 3;
			c3.signal();
			
		} finally {
			lock.unlock();
		}
	}
	public void fun3(int loop) throws InterruptedException {
		//判断标记
		lock.lock();
		try {
			while(flag!=3) {
				c3.await();
			}
			for(int i=1;i<=15;i++) {
				System.out.println(Thread.currentThread().getName()+"第"+loop+"轮打印第"+i+"次");
			}
			flag = 1;
			c1.signal();
			
		} finally {
			lock.unlock();
		}
	}
	
	
	
	/**
	 * 交替增减的方法组
	 * @return
	 */
	public int selfAdd() {
		lock.lock();
		try {
			
			while(number.get()>0) {
				add.await();
			}
			number.incrementAndGet();
			sub.signalAll();
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
		return number.get();
	}
	
	public int selfSub() {
		lock.lock();
		try {
			while(number.get()==0) {
				sub.await();
			}
			number.decrementAndGet();
			add.signalAll();
		} catch (Exception e) {
		}
		return number.get();
	}
}

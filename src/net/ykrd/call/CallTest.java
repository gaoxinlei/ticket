package net.ykrd.call;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.sun.org.apache.regexp.internal.recompile;

public class CallTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//用callable+futuretask的方式获得线程
		//testCall();
		//测试线程按序指定次数交替
		//testSwap();
		//线程池方式获取线程
		//testPool();
		//读写锁.
		testReadWriteLock();
	}

	private static void testReadWriteLock() throws InterruptedException {
		//读写锁测试,保证先写后读.
		Resource resource = new Resource();
		Random random = new Random();
		Callable<Integer> cw = () ->{
			int next = random.nextInt(88888);
			resource.write(next);
			return next;
		};
		Callable<Integer> cr = () ->{
			int result = resource.read();
			return result;
		};
		
		//建立一个写,十个读.
		ExecutorService writePool = Executors.newSingleThreadExecutor();
		writePool.submit(cw);
		TimeUnit.SECONDS.sleep(2);
		ExecutorService readPool = Executors.newFixedThreadPool(10);
		for(int i=1;i<=10;i++) {
			readPool.submit(cr);
		}
		System.out.println("===================分隔线================");
		//再建立三个写的线程.10个读的线程,交替执行.
		for(int i=1;i<=3;i++) {
			writePool.submit(cw);
		}
		for(int i=1;i<=10;i++) {
			readPool.submit(cr);
		}
		//关池
		writePool.shutdown();
		readPool.shutdown();
	}

	private static void testPool() throws InterruptedException, ExecutionException {
		Resource resource = new Resource();
		//准备callable
		Callable<Integer> c1 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun1(i);
			}
			return 1;
		};
		Callable<Integer> c2 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun2(i);
			}
			return 2;
		};
		Callable<Integer> c3 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun3(i);
			}
			return 3;
		};
		
		//
		//缓存线程池
		ExecutorService cachePool = Executors.newCachedThreadPool();
		Future<Integer> f1 = cachePool.submit(c1);
		Future<Integer> f2 = cachePool.submit(c2);
		Future<Integer> f3 = cachePool.submit(c3);
		System.out.println(f1.get());
		System.out.println(f2.get());
		System.out.println(f3.get());
		cachePool.shutdown();
		System.out.println("===============缓存线程池方式结束=============");
		System.out.println("===============可调度线程方式开始=============");
		ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);
		f1 = scheduledPool.schedule(c1, 1, TimeUnit.SECONDS);
		f2 = scheduledPool.schedule(c2, 1, TimeUnit.SECONDS);
		f3 = scheduledPool.schedule(c3, 1, TimeUnit.SECONDS);
		scheduledPool.shutdown();
		System.out.println("over");
		
	}
	
	private static void testSwap() {
		Resource resource = new Resource();
		
		Callable<Integer> c1 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun1(i);
			}
			return 1;
		};
		Callable<Integer> c2 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun2(i);
			}
			return 2;
		};
		Callable<Integer> c3 = () ->{
			for(int i=1;i<=10;i++) {
				resource.fun3(i);
			}
			return 3;
		};
		FutureTask<Integer> f1 = new FutureTask<>(c1);
		FutureTask<Integer> f2 = new FutureTask<>(c2);
		FutureTask<Integer> f3 = new FutureTask<>(c3);
		new Thread(f1,"1线程").start();
		new Thread(f2,"2线程").start();
		new Thread(f3,"3线程").start();
	}

	private static void testCall() {
		Resource r = new Resource();
		//建两个futuretask
		Callable<Integer> c1 = () -> {
			for(int i=1;i<=20;i++) {
				System.out.println(Thread.currentThread().getName()+"第"+i+"次操作，结果为"+r.selfAdd());
			}
			return 0;
			};
		Callable<Integer> c2 = () -> {
			for(int i=1;i<=20;i++) {
				System.out.println(Thread.currentThread().getName()+"第"+i+"次操作，结果为"+r.selfSub());
			}
			return 0;
			};
		FutureTask<Integer> f1 = new FutureTask<Integer>(c1);
		FutureTask<Integer> f2 = new FutureTask<Integer>(c2);
		new Thread(f1,"增线程").start();
		new Thread(f2,"减线程").start();
	}
}

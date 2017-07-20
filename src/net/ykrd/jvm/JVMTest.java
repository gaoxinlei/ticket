package net.ykrd.jvm;

import java.util.Random;

public class JVMTest {

	public static void main(String[] args) {
		long totalMemory = Runtime.getRuntime().totalMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();
		System.out.println("maxMemory:"+maxMemory/(double)1024/1024+"Mb");
		System.out.println("totalMemory:"+totalMemory/(double)1024/1024+"Mb");
		
		String str = "ykrd.net";
		while(true) {
			str+= str+ new Random().nextInt(888888)+new Random().nextInt(999999);
		}
	}
}

package net.ykrd.jdk;

@FunctionalInterface
public interface Person {

	void sayHello(String name);
	
	default String discribe(String name,int age) {
		return "name:"+name+",age:"+age;
	}
	
	static void eat(String food) {
		System.out.println("吃:"+food);
	}
}

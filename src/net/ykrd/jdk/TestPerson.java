package net.ykrd.jdk;

public class TestPerson {

	public static void main(String[] args) {
		Person person = (name) ->{System.out.println("hello:"+name);};
		person.sayHello("gao");
		System.out.println(person.discribe("gxl", 28));
		Person.eat("地三鲜");
	}
}

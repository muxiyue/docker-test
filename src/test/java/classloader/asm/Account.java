package classloader.asm;

public class Account {
    public void operation() {
		 System.out.println("operation...."); 
	 }

	public static void main(String[] args) {
		new Account().operation();
	}
}

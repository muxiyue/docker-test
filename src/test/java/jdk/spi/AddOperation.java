package jdk.spi;

public class AddOperation implements Operation {
    @Override  
    public int operation(int numberA, int numberB) {  
        return numberA + numberB;  
    }  
}  
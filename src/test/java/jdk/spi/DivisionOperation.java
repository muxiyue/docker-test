package jdk.spi;

public class DivisionOperation implements Operation {
    @Override  
    public int operation(int numberA, int numberB) {  
        if (numberB == 0) {  
            throw new IllegalArgumentException("can not be 0.");  
        }  
        return numberA / numberB;  
    }  
}  
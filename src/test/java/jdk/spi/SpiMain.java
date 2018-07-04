package jdk.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

// spi 机制
// ServiceLoader.load 加载 META-INF/services/jdk.spi.Operation中的具体接口实现。
public class SpiMain {
    public static void main(String[] args) {  
        Operation operation = new AddOperation();
        System.out.println(operation.operation(6, 3));  
  
        ServiceLoader<Operation> operations = ServiceLoader.load(Operation.class);
        System.out.println(System.getProperty("java.class.path"));  
  
        Iterator<Operation> iterator = operations.iterator();
        while (iterator.hasNext()) {  
            operation = iterator.next();  
            System.out.println(operation.operation(6, 0));  
        }  
    }  
}  
package classloader.jit;


//-XX:CompileThreshold=1000 -XX:+PrintCompilation

public class JITTest {

    public static void met(){
        int a=0,b=0;
        b=a+b;
    }
    
    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            met();
        }
    }
}

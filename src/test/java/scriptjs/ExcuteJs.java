package scriptjs;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ExcuteJs {

    /**
     * 执行js函数，得到需要的值的值
     * @param paras
     * @return
     * @throws ScriptException
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public static String excuteJs(String paras) throws ScriptException, FileNotFoundException, NoSuchMethodException
    {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript"); //得到脚本引擎
        FileReader reader1 = new java.io.FileReader(ExcuteJs.class.getResource("/").getPath() + "static/testDepend.js");
        engine.eval(reader1);

        FileReader reader = new java.io.FileReader(ExcuteJs.class.getResource("/").getPath() + "static/test.js");
        engine.eval(reader);
        Invocable inv = (Invocable)engine;
        Object a = inv.invokeFunction("test", paras );
        System.out.println(a.toString());
        return a.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        excuteJs("csp");
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(ExcuteJs.class.getResource("/").getPath() + "static/testDepend.js");
    }
}

package script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by chenshengpeng on 2017/10/29.
 */
public class TestScriptEngine {
    public static void main(String[] args) {
        ScriptEngineManager _mgr = new ScriptEngineManager();
        //这里只调用javascript执行器，可以执行javascript脚本，除此之外还可以调用其他类型的脚本
        ScriptEngine _enginer = _mgr.getEngineByName("javascript");
        try {
            Object result = _enginer.eval("if((1+2)*3 > 10) { 1;} else { 2;}");
            System.out.print(result);
        }
        catch (

            ScriptException e)

        {
            e.printStackTrace();
        }
    }
}

package script;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.junit.Test;

import javax.script.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenshengpeng on 2017/10/29.
 */
public class TestGroovy {
    public static void main(String[] args) {
        Binding binding=new Binding();

        binding.setVariable("param1",10);
        binding.setVariable("param2",10);
        GroovyShell shell=new GroovyShell(binding);

        String str1 ="if(param1<34.2) " + "{ "
            + " result1=Math.floor((3*(1+2))/param2) "
            + "} "
            + "else "
            + "{ "
            + " result1=2 "
            + "};";
        String str2 = "if(param1==10) " + "{ "
            + " result2=100; "
            + "};";
        String str = str1 + str2 + " result = result1 + result2;";
        Object b=shell.evaluate(str);//此句为关键代码，利用java执行了code.txt中的动态计算公式的代码
        Object result=(Object)binding.getVariable("result");
        Object result1=(Object)binding.getVariable("result1");
        System.out.println("result:"+result + "    result1:" + result1);
    }



    @Test
    public void groovyClassLoader() throws IllegalAccessException, InstantiationException, IOException {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class groovyClass = loader.parseClass(new File(TestGroovy.class.getResource("/").getPath() + "hello.groovy"));
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        Object[] args = new Object[2];
        args[0] = "args1";
        args[1] = "args2";
        System.out.print(groovyObject.invokeMethod("hello", args));
    }

    @Test
    public void groovyShell() throws IOException {
        GroovyShell shell = new GroovyShell();
        Script groovyScript = shell.parse(new File(TestGroovy.class.getResource("/").getPath() + "hello.groovy"));
        Object[] args = new Object[2];
        args[0] = "args1";
        args[1] = "args2";
        System.out.print(groovyScript.invokeMethod("hello", args));
    }






    /**
     * @author hpym365
     * @创建时间 2017年3月2日 下午10:17:42
     * @desc 描述
     * @param filename
     *            groovy脚本文件名字
     * @param funname
     *            执行指定的方法名称
     * @param params
     *            执行脚本的参数
     * @return 返回执行结果
     */
    public Object runGroovyScriptByFile( String filename, String funname, Object[] params) {

        try {
            // String[]{".\\src\\main\\java\\com\\senyint\\util\\"}
            GroovyScriptEngine engine = new GroovyScriptEngine(TestGroovy.class.getResource("/").getPath());
            Script script = engine.createScript(filename, new Binding());
            return script.invokeMethod(funname, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author hpym365
     * @创建时间 2017年3月2日 下午9:43:08
     * @desc 执行groovy脚本(不指定方法)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param params
     *            执行grovvy需要传入的参数
     * @return 脚本执行结果
     */
    public Object runGroovyScript(String script, Map<String, Object> params) {
        if (script == null || "".equals(script))
            throw new RuntimeException("方法runGroovyScript无法执行，传入的脚本为空");

        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            Bindings bindings = engine.createBindings();
            bindings.putAll(params);
            return engine.eval(script, bindings);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author hpym365
     * @创建时间 2017年3月2日 下午9:43:08
     * @desc 执行groovy脚本(需要指定方法名)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName
     *            要执行的方法名
     * @param params
     *            执行grovvy需要传入的参数
     * @return
     */
    public Object runGroovyScript(String script, String funName, Object[] params) {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            // String HelloLanguage = "def hello(language) {return \"Hello
            // $language\"}";
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            return inv.invokeFunction(funName, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void getScriptEngineFactoryList() {
        ScriptEngineManager manager = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = manager.getEngineFactories();

        for (ScriptEngineFactory factory : factories) {

            System.out.printf(
                "Name: %s%n" + "Version: %s%n" + "Language name: %s%n" + "Language version: %s%n"
                    + "Extensions: %s%n" + "Mime types: %s%n" + "Names: %s%n",
                factory.getEngineName(), factory.getEngineVersion(), factory.getLanguageName(),
                factory.getLanguageVersion(), factory.getExtensions(), factory.getMimeTypes(), factory.getNames());
            // 得到当前的脚本引擎

            ScriptEngine engine = factory.getScriptEngine();
            System.out.println(engine);
        }
    }

    @Test
    public void groovyScriptEngine() {
//        getScriptEngineFactoryList();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("language", "groovy test");
        Object res = runGroovyScript("return \"Hello $language\"", params);
        String script = "def hello(param1,param2) {return \"the params is $param1 and $param2\"}";
        Object res1 = runGroovyScript(script, "hello", new String[] { "param1", "param2" });
        System.out.println(res);
        System.out.println(res1);

        Object res3 = runGroovyScriptByFile("hello.groovy", "hello", new String[] { "param3", "param4" });
        System.out.println(res3);

    }





    @Test
    public void testMemory() {


        for (int i = 0; i < 1000; i++) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("language", "groovy test");
            Object res = runGroovyScript("return \"Hello $language\"", params);
            System.out.println("========" + i);
//            System.gc();
        }

    }


    @Test
    public void testMemory2() {


        for (int i = 0; i < 1000; i++) {
            Object res3 = runGroovyScriptByFile("hello.groovy", "hello", new String[] { "param3", "param4" });
            System.out.println(res3);
            System.out.println("========" + i);
//            System.gc();
        }

    }


    @Test
    public void testMemory3() throws IllegalAccessException, IOException, InstantiationException {


        for (int i = 0; i < 1000; i++) {
            groovyClassLoader();
            System.out.println("========" + i);
//            System.gc();
        }

    }


    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();
    @Test
    public void test() {
        String script = "if(param1==10) " + "{ "
            + " result2=100; "
            + "};";
        String cacheKey = "test";
        Object scriptObject = null;
        try {
            Binding binding = new Binding();
            binding.setVariable("param1", 10);

            Script shell = null;
            if (scriptCache.containsKey(cacheKey)) {
                shell = (Script) scriptCache.get(cacheKey);
            } else {
                GroovyShell groovyShell = new GroovyShell();
                shell = groovyShell.parse(script);
                scriptCache.put(cacheKey, shell);
            }

            // GroovyClassLoader
//            shell.setBinding(binding);
//            scriptObject = (Object) shell.run();
//
//            // clear
//            binding.getVariables().clear();
//            binding = null;

            scriptObject = (Object) InvokerHelper.createScript(shell.getClass(), binding).run();

            System.out.println(scriptObject);
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }





}

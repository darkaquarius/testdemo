package scriptdemo;

/**
 * Created by huishen on 17/12/17.
 * java运行js
 */

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class RunJs {

    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("google_tk.js")));

        engine.eval(reader);

        Invocable invocable = (Invocable)engine;
        //这里参数 第一个是js文件中的js function名字，第二个是funciton接受的参数
        Object result = invocable.invokeFunction("VL", "happy new year!");
        System.out.println(result);
    }

    /**
     * java调用js
     * @param filePath
     * @param menthod
     * @param parm
     * @return
     */
    public static String invokeJs(String filePath, String menthod, String parm) throws Exception{

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByExtension("js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

        engine.eval(reader);

        Invocable invocable = (Invocable)engine;
        Object result = invocable.invokeFunction(menthod,parm);

        return result.toString();
    }

}

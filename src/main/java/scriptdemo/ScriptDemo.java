package scriptdemo;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by huishen on 17/6/26.
 *
 */
public class ScriptDemo {

    @Test
    public void test1() {
        ScriptEngineManager manager = new ScriptEngineManager();
        // @NotNull
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Reader scriptReader = new InputStreamReader(ScriptDemo.class.getClassLoader().getResourceAsStream("test/TestAdd.js"));

        if (engine != null) {
            try {
                engine.eval(scriptReader);
                if (engine instanceof Invocable) {
                    Invocable invocable = (Invocable) engine;
                    Object result = invocable.invokeFunction("add", 1, 2);
                    System.out.println("The result is: " + result);
                }
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                try {
                    scriptReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("ScriptEngine create error!");
        }

    }

}

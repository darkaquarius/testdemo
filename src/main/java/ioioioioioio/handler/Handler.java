package ioioioioioio.handler;

/**
 * @author huishen
 * @date 2019-08-20 22:46
 */
public abstract class Handler {
    protected Handler nextHandler;

    public Handler setNextHandler(Handler handler) {
        this.nextHandler = handler;
        return this;
    }

    abstract void exec(String str);

}

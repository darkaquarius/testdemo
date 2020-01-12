package ioioioioioio.handler.copy;

/**
 * @author huishen
 * @date 2019-08-21 09:45
 */
public abstract class Handler {
    public Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void exec(String data);

}

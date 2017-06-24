package httpClientdemo;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/22.
 * 回收
 */
public class IdleConnectionMonitorThread extends Thread {

    private final PoolingHttpClientConnectionManager cm;
    private volatile boolean shutdown;

    IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connMgr) {
        super("http-client-idle-connection-monitor-thread");
        this.cm = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    cm.closeExpiredConnections();
                    cm.closeIdleConnections(1, TimeUnit.MINUTES);
                    // logger.info(cm.getTotalStats().toString());
                }
            }
        } catch (InterruptedException ex) {
            shutdown();
        }
    }

    void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}

package scalableIODemo.multiple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author Lzyer
 * @ClassName: Reactor
 * @Description: 根据 Scalable IO in Java写的实例。
 * @date 2017年3月10日
 */
public class Reactor extends Thread {
    final Selector selector;
    // 主从的标志
    final boolean isMain;

    public Reactor(int port, boolean isMain) throws IOException {
        this.isMain = isMain;
        selector = Selector.open();
        System.out.println(selector + " isMainReactor = " + isMain);
        if (isMain) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new Acceptor(selector, serverSocketChannel));
            selector.wakeup();
            System.out.println(getClass().getSimpleName() + " start on " + port + " ...\n");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 会阻塞导致不能register，设置阻塞时间
                int n = selector.select(500);
                if (n == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable runnable = (Runnable) k.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }

}

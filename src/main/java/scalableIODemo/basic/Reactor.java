package scalableIODemo.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Lzyer
 * @ClassName: Reactor
 * @Description: 根据 Scalable IO in Java写的实例。
 * @date 2017年3月7日
 */
public class Reactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        // 分步处理,第一步,接收accept事件
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 等待事件
                int n = selector.select(2000);
                if (n == 0) {
                    continue;
                }
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    System.out.println("SelectionKey-next-start");
                    // Reactor负责dispatch收到的事件
                    dispatch(iterator.next());
                    System.out.println("SelectionKey-next-end");
                    System.out.println();
                }
                set.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey k) {
        Thread runnable = (Thread) k.attachment();
        if (runnable != null) {
            // runnable.run();
            runnable.start();
        }
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 10000; j++) {
                for (int z = 0; z < 10000; z++) {

                }
            }
        }
    }

    class Acceptor extends Thread {
        @Override
        public void run() {
            System.out.println("Acceptor-run-start");
            try {
                // 接收请求
                SocketChannel c = serverSocketChannel.accept();
                if (c != null) {
                    System.out.println("New Connection ...");
                    new Handler(selector, c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Acceptor-run-end");
        }
    }

    final class Handler extends Thread {

        final SocketChannel socket;
        final SelectionKey key;
        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteBuffer output = ByteBuffer.allocate(1024);
        static final int READING = 0, SENDING = 1;
        int state = READING;

        public Handler(Selector selector, SocketChannel c) throws IOException {
            socket = c;
            socket.configureBlocking(false);
            // Optionally try first read now
            key = socket.register(selector, 0);
            key.attach(this);
            // 第二步,接收Read事件
            key.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        @Override
        public void run() {
            System.out.println("Handler-run-start");
            try {
                if (state == READING) {
                    read();
                } else if (state == SENDING) {
                    send();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Handler-run-end");
        }

        boolean inputIsComplete() {
            return input.hasRemaining();
        }

        boolean outputIsComplete() {
            return output.hasRemaining();
        }

        void process() {
            // 读数据
            StringBuilder reader = new StringBuilder();
            input.flip();
            while (input.hasRemaining()) {
                reader.append((char) input.get());
            }
            System.out.println("[Client-INFO]");
            System.out.println(reader.toString());
            String str = "HTTP/1.1 200 OK\r\nDate: Fir, 10 March 2017 21:20:01 GMT\r\n" +
                "Content-Type: text/html;charset=UTF-8\r\nContent-Length: 23\r\nConnection:close" +
                "\r\n\r\nHelloWorld" + System.currentTimeMillis();
            output.put(str.getBytes());
            System.out.println("process over.... ");
        }

        void read() throws IOException {
            socket.read(input);
            if (inputIsComplete()) {
                process();
                state = SENDING;
                // 第三步,接收write事件
                key.interestOps(SelectionKey.OP_WRITE);
            }
        }

        void send() throws IOException {
            output.flip();
            socket.write(output);
            if (outputIsComplete()) {
                key.cancel();
            }
            state = READING;
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(9001)).start();
        System.out.println("Server start...");
    }
}

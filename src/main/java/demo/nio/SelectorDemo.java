package demo.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by huishen on 17/11/2.
 *
 */

public class SelectorDemo {

    /**
     * Selector的各个方法
     */
    public void test2() throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8090));

        channel.configureBlocking(false);
        // 注册
        // register()方法的第二个参数，这是一个"interest集合"
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);

        // 通过Selector选取已经ready的Channels
        // 会阻塞
        int select = selector.select();
        int select1 = selector.select(1000);
        // 不会阻塞，立刻返回
        int selectNow = selector.selectNow();

        // Set<SelectionKey> keys = selector.keys();
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
    }

    /**
     * SelectionKey的各个方法
     * register()方法返回一个SelectinKey对象，这个对象包含一些你感兴趣的属性：
     * interest集合,ready集合，Channel，Selector，附加的对象
     */
    @Test
    public void test1() throws Exception {
        // 创建一个Selector
        Selector selector = Selector.open();

        // 创建ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8090));


        // 与Selector一起使用时，Channel必须处于非阻塞模式下
        // 这意味着FileChannel与Selector不能一起使用
        channel.configureBlocking(false);

        // Selector注册到Channel
        // 当register()方法执行时，新建一个SelectioKey，并把它加入Selector的all-keys集合中
        // 第二个参数：Connect, Accept, Read, Write, 这是interest集合
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        int interestOps = key.interestOps();
        boolean isInterestedInAccept = (interestOps & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
        boolean isInterestedInConnect = (interestOps & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
        boolean isInterestedInRead = (interestOps & SelectionKey.OP_READ) == SelectionKey.OP_READ;
        boolean isInterestedInWrite = (interestOps & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;

        // 通道已经准备就绪的操作的集合
        int readyOps = key.readyOps();
        boolean acceptable = key.isAcceptable();
        boolean connectable = key.isConnectable();
        boolean readable = key.isReadable();
        boolean writable = key.isWritable();

        // 通过SelectKey访问Channel
        SelectableChannel channel1 = key.channel();
        // 通过SelectKey访问Selector
        Selector selector1 = key.selector();

        // SelectionKey上附加一个对象来储存更多的信息
        // key.attach(theObject);
        Object attachObj = key.attachment();

        // 通过调用某个SelectionKey的cancel()方法，关闭其通道，或者通过关闭其选择器来取消该Key之前，它一直保持有效。
        // 取消某个Key之后不会立即从Selector中移除它，相反，会将该Key添加到Selector的已取消key set，以便在下一次进行选择操作的时候移除它。
        key.cancel();
    }

    /**
     * 一个完整的Selector的工作示例
     */
    @Test
    public void test3() throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8090));

        channel.configureBlocking(false);
        // Channel注册Selector
        SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyChannels = selector.select(1000);
            if (0 == readyChannels) {
                continue;
            }

            // Set<SelectionKey> keys = selector.keys();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey k = keyIterator.next();
                if(k.isAcceptable()) {
                    System.out.println("a connection was accepted by a ServerSocketChannel.");
                    k.cancel();
                } else if (k.isConnectable()) {
                    System.out.println("a connection was established with a remote server.");
                    k.cancel();
                } else if (k.isReadable()) {
                    System.out.println("a channel is ready for reading.");
                    k.cancel();
                } else if (k.isWritable()) {
                    System.out.println("a channel is ready for writing.");
                    k.cancel();
                }

                // 注意每次迭代末尾的remove()调用，Selector不会自己从已选择集中移除SelectioKey实例，必须在处理完通道时自己移除
                // 直接调用selected-keys集合的remove()方法，或者调用它的iterator的remove()方法
                // 都可以从selected-keys集合中删除一个SelectionKey对象
                keyIterator.remove();
            }

        }
    }

    /**
     * OP_ACCEPT == 16
     * OP_CONNECT == 8
     * OP_READ == 1
     * OP_WRITE == 4
     */
    @Test
    public void test4() {
        // 16
        System.out.println(SelectionKey.OP_ACCEPT);
        // 8
        System.out.println(SelectionKey.OP_CONNECT);
        // 1
        System.out.println(SelectionKey.OP_READ);
        // 4
        System.out.println(SelectionKey.OP_WRITE);
    }


}

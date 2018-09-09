package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@SuppressWarnings("all")
/**
 * 管道输入流和管道输出流的交互程序
 */
public class PipedStreamTest {

    public static void main(String[] args) {
        testPipedStream();
    }

    private static void testPipedStream(){
        SenderThread st = new SenderThread();
        ReceiverThread rt = new ReceiverThread();
        PipedInputStream pis = rt.getPipedInputStream();
        PipedOutputStream pos = st.getPipedOutputStream();

        try {
            pos.connect(pis);

            new Thread(st).start();
            rt.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // private static void exampleFromIE() {
    //     Sender t1 = new Sender();
    //
    //     Receiver t2 = new Receiver();
    //
    //     PipedOutputStream out = t1.getOutputStream();
    //
    //     PipedInputStream in = t2.getInputStream();
    //
    //     try {
    //         //管道连接。下面2句话的本质是一样。
    //         //out.connect(in);
    //         in.connect(out);
    //
    //         /**
    //          * Thread类的START方法：
    //          * 使该线程开始执行；Java 虚拟机调用该线程的 run 方法。
    //          * 结果是两个线程并发地运行；当前线程（从调用返回给 start 方法）和另一个线程（执行其 run 方法）。
    //          * 多次启动一个线程是非法的。特别是当线程已经结束执行后，不能再重新启动。
    //          */
    //         t1.start();
    //         t2.start();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static class SenderThread implements Runnable {
        private PipedOutputStream pos = new PipedOutputStream();

        public PipedOutputStream getPipedOutputStream(){
            return pos;
        }
        @Override
        public void run() {
            //sendOneByte();
            //sendShortMessage();
            sendLongMessage();
        }

        private void sendOneByte(){
            try {
                pos.write(0x61);
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendShortMessage() {
            try {
                pos.write("this is a short message from senderThread !".getBytes());
                pos.flush();
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendLongMessage(){
            try {
                byte[] b = new byte[1028];
                //生成一个长度为1028的字节数组、前1020个是1、后8个是2。
                for(int i=0; i<1020; i++){
                    b[i] = 1;
                }
                for (int i = 1020; i <1028; i++) {
                    b[i] = 2;
                }
                pos.write(b);
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ReceiverThread extends Thread {

        private PipedInputStream pis = new PipedInputStream();

        public PipedInputStream getPipedInputStream(){
            return pis;
        }
        @Override
        public void run() {
            //receiveOneByte();
            //receiveShortMessage();
            receiverLongMessage();
        }

        private void receiveOneByte(){
            try {
                int n = pis.read();
                System.out.println(n);
                pis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void receiveShortMessage() {
            try {
                byte[] b = new byte[1024];
                int n = pis.read(b);
                System.out.println(new String(b, 0, n));
                pis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void receiverLongMessage(){
            try {
                byte[] b = new byte[2048];
                int count = 0;
                while(true){
                    count = pis.read(b);
                    for (int i = 0; i < count; i++) {
                        System.out.print(b[i]);
                    }
                    if(count == -1) {
                        break;
                    }
                }
                pis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


package com.arthur;

import java.util.TreeMap;
import java.util.concurrent.Semaphore;

/**
 * 下面对上面说的三个辅助类进行一个总结：

 　　1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

 　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

 　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

 　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

 　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。

 * Semaphore方式
 * 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * Created by gaopan on 17/12/25.
 */
public class SemaphoreTest {


    public static void main(String[] args) {
        int N = 8;            //工人数
        Semaphore semaphore = new Semaphore(5); //参数permits表示许可数目，即同时可以允许多少线程进行访问
        //Semaphore semaphore = new Semaphore(5,true); //这个多了一个参数fair表示是否是公平的，即等待时间越久的越先获取许可
        for(int i=0;i<N;i++)
            new Woker(""+i,semaphore).start();
    }


    static class  Woker extends Thread{
        private String name;
        private Semaphore semaphore;

        public Woker(String name, Semaphore semaphore) {
            this.name = name;
            this.semaphore = semaphore;
        }

        public void run() {

            try {
                semaphore.acquire(2);//acquire()用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
                System.out.println("工人"+this.name+"占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人"+this.name+"释放出机器");
                semaphore.release(2);//release()用来释放许可。注意，在释放许可之前，必须先获获得许可。

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 这4个方法都会被阻塞，如果想立即得到执行结果，可以使用下面几个方法：

     public boolean tryAcquire() { };    //尝试获取一个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
     public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException { };  //尝试获取一个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
     public boolean tryAcquire(int permits) { }; //尝试获取permits个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
     public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException { }; //尝试获取permits个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false

     */

}

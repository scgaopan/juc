package com.arthur;

import java.io.Writer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CyclicBarrier用法
 * 字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。叫做回环是因为当所有等待线程都被释放以后，
 * CyclicBarrier可以被重用。我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了
 * Created by gaopan on 17/12/25.
 */
public class CyclicBarrierTest {

        public static void main(String[] args) {
            //cycleBanier1();
            caleBanier2();
        }
        static class Writer extends Thread{
            private CyclicBarrier cyclicBarrier;
            public Writer(CyclicBarrier cyclicBarrier) {
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
                try {
                   // if("Thread-3".equals(Thread.currentThread().getName())){
                   //     Thread.sleep(5000);      //故意设置其中一个线程去实现 cyclicBarrier.await(1, TimeUnit.SECONDS);
                    //}else
                    Thread.sleep(2000);      //以睡眠来模拟写入数据操作
                    System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                    cyclicBarrier.await();//这个是无限等
                    //让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。
                    //cyclicBarrier.await(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch(BrokenBarrierException e){
                    e.printStackTrace();
                }
//                catch (TimeoutException e){
//                    e.printStackTrace();
//                }
                System.out.println("所有线程写入完毕，继续处理其他任务...");
            }
        }

        private  static  void cycleBanier1(){
            int N = 4;
            //参数parties指让多少个线程或者任务等待至barrier状态
            CyclicBarrier barrier  = new CyclicBarrier(N);
            for(int i=0;i<N;i++)
                new Writer(barrier).start();
        }


        //从结果可以看出，当四个线程都到达barrier状态后，会从四个线程中选择一个线程去执行Runnable。
        private static void caleBanier2(){
            int N = 4;
            CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable() {
                public void run() {//这里是所有banier都执行完了就会执行的任务......该线程的执行都是从到达barrier状态的线程中长一个线程去执行
                    System.out.println("当前线程"+Thread.currentThread().getName()+"等待所有banier线程都执行完了再执行......");
                }
            });

            for(int i=0;i<N;i++)
                new Writer(barrier).start();
        }
}

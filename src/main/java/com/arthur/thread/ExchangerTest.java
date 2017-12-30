package com.arthur.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**Exchanger
 *
 * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交换。它提供一个同步点，在这个同步点两个线程可以交换彼此的数据。
 * 这两个线程通过exchange方法交换数据， 如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange，当两个线程都到达同步点时，
 * 这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
 *
 * Exchanger的应用场景
 Exchanger可以用于遗传算法，遗传算法里需要选出两个人作为交配对象，这时候会交换两人的数据，并使用交叉规则得出2个交配结果。
 Exchanger也可以用于校对工作。比如我们需要将纸制银流通过人工的方式录入成电子银行流水，为了避免错误，采用AB岗两人进行录入，录入到Excel之后，系统需要加载这两个Excel，并对这两个Excel数据进行校对，看看是否录入的一致。代码如下：
 * Created by gaopan on 17/12/25.
 */
public class ExchangerTest {

    private static  final Exchanger<String> excharger=new Exchanger<String>();

    private static final ExecutorService service= Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        service.execute(new Runnable() {
            public void run() {
                try {
                    String a="银行流水A";
                    String b=excharger.exchange(a);
                    System.out.println("=========>"+b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.execute(new Runnable() {
            public void run() {
                try {
                    String b="银行流水B";
                    //如果两个线程有一个没有到达exchange方法，则会一直等待,如果担心有特殊情况发生，避免一直等待，可以使用exchange(V x, long timeout, TimeUnit unit)设置最大等待时长。
                    String a=excharger.exchange(b);
                    System.out.println("A和B数据是否一致：" + a.equals(b) + ",A录入的是："

                                    + a + ",B录入是：" + b);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }
}

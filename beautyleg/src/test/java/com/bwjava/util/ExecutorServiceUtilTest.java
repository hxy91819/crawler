package com.bwjava.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author chenjing
 * @date 2019-03-04 09:28
 */
public class ExecutorServiceUtilTest {
    ThreadPoolExecutor service = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    @Test
    public void testBlock() {
        Runnable runnableOuter = () -> {
            try {
                Runnable runnableInner1 = () -> {
                    try {
                        TimeUnit.SECONDS.sleep(3); // 模拟比较耗时的爬虫操作
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                Future<?> submit = service.submit(runnableInner1);

                submit.get(); // 实际业务中，runnableInner2需要用到此处返回的参数，所以必须get

                Runnable runnableInner2 = () -> {
                    try {
                        TimeUnit.SECONDS.sleep(5); // 模拟比较耗时的爬虫操作
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                Future<?> submit2 = service.submit(runnableInner2);
                submit2.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 20; i++) {
            service.execute(runnableOuter);
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) service);

        while (true) {
            System.out.println();

            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = tpe.getTaskCount();
            System.out.println("总线程数：" + taskCount);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() {
        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 1225; i++) {
            service.execute(runnable);
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) service);

        while (true) {
            System.out.println();

            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = tpe.getTaskCount();
            System.out.println("总线程数：" + taskCount);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1() {
        ExecutorService service = Executors.newFixedThreadPool(10);
    }
}
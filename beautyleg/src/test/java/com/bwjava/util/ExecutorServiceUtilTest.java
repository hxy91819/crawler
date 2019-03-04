package com.bwjava.util;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjing
 * @date 2019-03-04 09:28
 */
public class ExecutorServiceUtilTest {
    @Test
    public void test() {
        ExecutorService service = ExecutorServiceUtil.getInstance();

        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 1000; i++) {
            service.submit(runnable);
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
    public void test1(){
        ExecutorService service = Executors.newFixedThreadPool(10);
    }
}
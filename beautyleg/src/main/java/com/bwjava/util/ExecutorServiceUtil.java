package com.bwjava.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池单例
 *
 * @author chenjing
 * @date 2019-02-24 15:08
 */
public class ExecutorServiceUtil {
    private ExecutorServiceUtil() {
    }

    private volatile static ExecutorService instance;

    @SuppressWarnings("AlibabaThreadPoolCreation")
    public static ExecutorService getInstance() {
        if (instance == null) {
            synchronized (ExecutorServiceUtil.class) {
                if (instance == null) {
                    return Executors.newFixedThreadPool(10);
                }
            }
        }
        return instance;
    }
}

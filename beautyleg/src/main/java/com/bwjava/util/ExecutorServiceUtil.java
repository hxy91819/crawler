package com.bwjava.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

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

    public static ExecutorService getInstance() {
        if (instance == null) {
            synchronized (ExecutorServiceUtil.class) {
                if (instance == null) {
                    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                            .setNameFormat("demo-pool-%d").build();
                    instance = new ThreadPoolExecutor(5, 200,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
                    return instance;
                }
            }
        }
        return instance;
    }
}

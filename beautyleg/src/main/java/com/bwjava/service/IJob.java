package com.bwjava.service;

/**
 * @author chenjing
 * @date 2019-02-21 15:33
 */
public interface IJob extends Runnable {
    /**
     * 在job执行之前回调的方法
     */
    void beforeRun();


    /**
     * 在job执行完毕之后回调的方法
     */
    void afterRun();
}

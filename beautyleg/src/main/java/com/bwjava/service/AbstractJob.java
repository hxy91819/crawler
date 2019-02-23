package com.bwjava.service;

/**
 * @author chenjing
 * @date 2019-02-21 15:34
 */
public abstract class AbstractJob implements IJob {
    @Override
    public void beforeRun() {
    }

    @Override
    public void afterRun() {
    }


    @Override
    public void run() {
        this.beforeRun();

        try {
            this.doFetchPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.afterRun();
    }


    /**
     * 具体的抓去网页的方法， 需要子类来补全实现逻辑
     *
     * @throws Exception
     */
    public abstract void doFetchPage() throws Exception;
}

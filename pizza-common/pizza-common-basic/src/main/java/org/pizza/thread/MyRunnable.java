package org.pizza.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 高巍
 * @since 2020/12/31 1:07 下午
 */
public abstract class MyRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MyRunnable.class);
    protected Thread currentThread;
    protected AtomicBoolean stopped;
    protected String name;
    protected long lastActiveTime;

    public MyRunnable(String name) {
        this.name = name;
        this.stopped = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        this.currentThread = Thread.currentThread();
        String oldName = this.currentThread.getName();

        try {
            this.currentThread.setName(this.name);
            while (!this.stopped.get()) {
                try {
                    this.doIt();
                    lastActiveTime = System.currentTimeMillis();
                } catch (Throwable var10) {
                    logger.error("[" + this.name + "] meets:" + var10.getMessage(), var10);
                }
            }
        } catch (Exception var11) {
            logger.error(var11.getMessage(), var11);
        } finally {
            logger.info("Thread[" + this.currentThread.getName() + "] quited.");
            this.currentThread.setName(oldName);
        }
    }

    public void close() {
        this.stopped.set(true);
        if (this.currentThread != null) {
            logger.info("Aborting thread[" + this.currentThread.getName() + "].......");

            try {
                this.currentThread.interrupt();
            } catch (Exception var2) {
            }
        }
    }

    protected abstract void doIt();
}

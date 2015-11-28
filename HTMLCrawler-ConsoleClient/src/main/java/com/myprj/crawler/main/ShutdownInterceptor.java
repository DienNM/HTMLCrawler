package com.myprj.crawler.main;


/**
 * @author DienNM (DEE)
 **/

public class ShutdownInterceptor extends Thread {

    private CrawlerRunner app;

    public ShutdownInterceptor(CrawlerRunner app) {
        this.app = app;
    }

    public void run() {
        app.shutDown();
    }
}

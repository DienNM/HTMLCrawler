package com.myprj.crawler.domain;

/**
 * @author DienNM (DEE)
 */

public class ProxyTracer {

    private int maxDownloadTimes = 20;
    private int countDownloaded = 0;
    private int start = -1;

    public int getMaxDownloadTimes() {
        return maxDownloadTimes;
    }

    public void setMaxDownloadTimes(int maxDownloadTimes) {
        this.maxDownloadTimes = maxDownloadTimes;
    }

    public int getCountDownloaded() {
        return countDownloaded;
    }

    public void setCountDownloaded(int countDownloaded) {
        this.countDownloaded = countDownloaded;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}

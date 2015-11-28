package com.myprj.crawler.model;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class ProxyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;
    private int port;
    private String type;
    private boolean reachable = false;

    public ProxyModel() {
    }

    public ProxyModel(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

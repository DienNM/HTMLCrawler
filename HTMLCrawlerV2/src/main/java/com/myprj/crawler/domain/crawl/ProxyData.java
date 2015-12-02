package com.myprj.crawler.domain.crawl;

import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.ProxyModel;

/**
 * @author DienNM (DEE)
 */

public class ProxyData extends AuditData {

    private static final long serialVersionUID = 1L;

    private long id;

    private String ip;

    private int port;

    private boolean reachable = false;

    public ProxyData() {
    }

    public ProxyData(String text) {
        String[] ipAndPort = text.split(":");
        ip = ipAndPort[0];
        port = Integer.valueOf(ipAndPort[1]);
    }

    public ProxyData(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void toDatas(List<ProxyModel> sources, List<ProxyData> dests) {
        for (ProxyModel source : sources) {
            ProxyData dest = new ProxyData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ProxyData> sources, List<ProxyModel> dests) {
        for (ProxyData source : sources) {
            ProxyModel dest = new ProxyModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ProxyModel source, ProxyData dest) {
        dest.setId(source.getId());
        dest.setPort(source.getPort());
        dest.setReachable(source.isReachable());
        toAuditData(source, dest);
    }

    public static void toModel(ProxyData source, ProxyModel dest) {
        dest.setId(source.getId());
        dest.setPort(source.getPort());
        dest.setReachable(source.isReachable());
        toAuditModel(source, dest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

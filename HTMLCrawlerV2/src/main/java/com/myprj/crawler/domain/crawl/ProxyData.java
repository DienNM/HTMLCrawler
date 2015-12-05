package com.myprj.crawler.domain.crawl;

import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.ProxyModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class ProxyData extends AuditData {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("id")
    private long id;

    @EntityTransfer("ip")
    private String ip;

    @EntityTransfer("port")
    private int port;

    @EntityTransfer("reachable")
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
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ProxyData source, ProxyModel dest) {
        EntityConverter.convert2Entity(source, dest);
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

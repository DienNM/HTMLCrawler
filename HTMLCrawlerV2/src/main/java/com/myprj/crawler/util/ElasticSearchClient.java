package com.myprj.crawler.util;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author DienNM (DEE)
 */

public class ElasticSearchClient {

    private static Client instance;

    public synchronized static final Client getInstance() {
        if (instance == null) {
            instance = getClient();
        }
        return instance;
    }

    @SuppressWarnings("resource")
    private static Client getClient() {
        String host = Config.get("elasticsearch.host");
        int port = Integer.parseInt(Config.get("elasticsearch.port"));
        String clustername = Config.get("elasticsearch.cluster.name");

        ImmutableSettings.Builder settings = ImmutableSettings.settingsBuilder();
        if (!StringUtils.isEmpty(clustername)) {
            settings.put("cluster.name", clustername);
        }
        return new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port));
    }

}

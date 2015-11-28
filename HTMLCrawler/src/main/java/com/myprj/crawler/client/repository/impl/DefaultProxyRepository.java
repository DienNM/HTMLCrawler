package com.myprj.crawler.client.repository.impl;

import static java.io.File.separator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.client.util.ProjectType;
import com.myprj.crawler.model.ProxyModel;
import com.myprj.crawler.repository.ProxyRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultProxyRepository implements ProxyRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultProxyRepository.class);
    
    static final String ROOT = "data".intern() + separator;
    
    private static final Map<String, List<ProxyModel>> proxies = new HashMap<String, List<ProxyModel>>();
    
    static {
        loadProxies();
    }
    
    private static void loadProxies() {
        for(ProjectType type : ProjectType.values()) {
            BufferedReader br = null;
            try {
                File file = new File(ROOT + type.name() + "-proxy.txt");
                if(!file.exists()) {
                    continue;
                }
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if(StringUtils.isEmpty(line)) {
                        continue;
                    }
                    List<ProxyModel> proxiesByType = proxies.get(type);
                    if(proxiesByType == null) {
                        proxiesByType = new ArrayList<ProxyModel>();
                        proxies.put(type.name(), proxiesByType);
                    }
                    String[] ip_port = line.split(":");
                    ProxyModel proxyModel = new ProxyModel(ip_port[0], Integer.valueOf(ip_port[1]));
                    proxyModel.setReachable(true);
                    proxyModel.setType(type.name());
                    
                    proxiesByType.add(proxyModel);
                }
            } catch (Exception e) {
                logger.error("Error during load proxies: {}. Error: {}", type.name(), e);
            } finally {
                IOUtils.closeQuietly(br);
                logger.info("Loaded proxies");
            }
        }
        
    }

    @Override
    public List<ProxyModel> findByType(String type) {
        return proxies.get(type);
    }

}

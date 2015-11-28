package com.myprj.crawler.client.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.myprj.crawler.client.domain.PhoneData;
import com.myprj.crawler.domain.Item;
import com.myprj.crawler.domain.Site;
import com.myprj.crawler.service.AbstractCrawler;

/**
 * @author DienNM
 **/
public class ChototCrawlerService extends AbstractCrawler<PhoneData> {
    
    @Override
    public void init() {
        executorService = Executors.newFixedThreadPool(3);
    }
    
    @Override
    protected void persistResult(Site site, List<Item<PhoneData>> items) {
        // TODO
    }

    @Override
    protected String updateUrlBeforeCrawling(String url) {
        return url.replace("www.chotot.vn", "m.chotot.vn");
    }

    @Override
    protected String updateUrlFormat(String url) {
        if(url.endsWith("#")) {
            url = url.substring(0, url.lastIndexOf("#"));
        } 
        if(url.endsWith("f=p")) {
            url = url + "&o=%s";
        } else if(url.endsWith("f=c")) {
            url = url + "&o=%s";
        } else {
            url = url + "?o=%s";
        }
        return url;
    }

    @Override
    protected boolean isDownloadSuccess(Document document) {
        return document != null && document.body() != null;
    }

    @Override
    protected Set<String> getLinkForDetailInfo(Element body) {
        Set<String> urls = new HashSet<String>();
        Elements div = body.select("div#ad_list a.item_link");
        if (div == null) {
            return urls;
        }

        Iterator<Element> elements = div.iterator();
        while (elements.hasNext()) {
            Element element = elements.next();
            urls.add(element.attr("href"));
        }
        return urls;
    }

    @Override
    protected Item<PhoneData> getCrawledResult(String url, Document document) {
        String prefix = "tel:";
        int length = prefix.length();
        
        Elements phones = document.body().select("a#phonebtn");
        if (phones == null) {
            return null;
        }
        String newUrl = updateUrlBeforeCrawling(url);
        
        Element phone = phones.first();
        if(phone == null) {
            System.out.println("Cannot find the phone of: " + newUrl);
            return null;
        }
        String phoneTel = phone.attr("href");

        Item<PhoneData> item = new Item<PhoneData>();
        if(phoneTel != null && phoneTel.startsWith(prefix)) {
            phoneTel = phoneTel.substring(length);
        }
        
        item.setLink(newUrl);
        PhoneData phoneData = new PhoneData();
        phoneData.setPhone(phoneTel);
        
        item.setContent(phoneData);
        return item;
    }
    
}

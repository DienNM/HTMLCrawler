package com.myprj.crawler.client.service.impl;

import static com.myprj.crawler.util.PathConstants.PROJECTS;
import static java.io.File.separator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.myprj.crawler.client.domain.ProductData;
import com.myprj.crawler.domain.Item;
import com.myprj.crawler.domain.Site;
import com.myprj.crawler.service.AbstractCrawler;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM
 **/
public class LazadaCrawlerService extends AbstractCrawler<ProductData> {

    @Override
    public void init() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    protected void persistResult(Site site, List<Item<ProductData>> items) {
        List<String> detail = new ArrayList<String>();
        for (Item<ProductData> item : items) {
            ProductData productData = item.getContent();
            detail.add(Serialization.serialize(productData));
        }
        StreamUtil.write(new File(PROJECTS + site.getProject().getName() + separator + "lazada-product-info.json"),
                detail, true);
    }

    @Override
    protected String updateUrlBeforeCrawling(String url) {
        return url;
    }

    @Override
    protected String updateUrlFormat(String url) {
        return url;
    }

    @Override
    protected boolean isDownloadSuccess(Document document) {
        return document != null && document.body() != null;
    }

    @Override
    protected Set<String> getLinkForDetailInfo(Element body) {
        Set<String> urls = new HashSet<String>();
        Elements div = body.select("div.product-description a");
        if (div == null) {
            return urls;
        }
        Iterator<Element> elements = div.iterator();
        while (elements.hasNext()) {
            Element a = elements.next();
            String href = a.attr("href");
            if (href == null || !href.startsWith("http://www.lazada.vn/")) {
                continue;
            }
            urls.add(a.attr("href"));
        }
        return urls;
    }

    @Override
    protected Item<ProductData> getCrawledResult(String url, Document document) {
        Item<ProductData> item = new Item<ProductData>();
        ProductData product = new ProductData();
        item.setContent(product);
        item.setLink(updateUrlBeforeCrawling(url));
        
        Element body = document.body();

        // Get Product Name
        Elements productNameElements = body.select("div.product-info-name");
        if (productNameElements != null && !productNameElements.isEmpty()) {
            product.setName(productNameElements.first().text());
        }

        // Get Price
        Elements productPriceElements = body.select("div.product-prices span.product-price");
        if (productPriceElements != null && !productPriceElements.isEmpty()) {
            product.setPrice(productPriceElements.first().text());
        }

        // Get Past Price
        Elements productPriceOrgElements = body.select("div.product-prices span.product-price-past");
        if (productPriceOrgElements != null && !productPriceOrgElements.isEmpty()) {
            product.setOriginalPrice(productPriceOrgElements.first().text());
        }
        
        Elements specificationsElements = body.select("div.catWrapper.specifications.specifications-detail div.description-detail ul li span");
        if(specificationsElements != null && specificationsElements.first() != null) {
            for(Element element :specificationsElements) {
                product.getSpecifications().add(element.text());
            }
        }
        
        Elements includedInBoxsElements = body.select("div.catWrapper.whatisinbox div.description-detail ul li span");
        if(includedInBoxsElements != null && includedInBoxsElements.first() != null) {
            for(Element element :includedInBoxsElements) {
                product.getIncludedInBox().add(element.text());
            }
        }
        
        Elements descriptionDetailsElements = body.select("div.catWrapper div.description-detail ul.simpleList.uip li");
        if(descriptionDetailsElements != null) {
            for(Element element :descriptionDetailsElements) {
                product.getDescriptionDetails().add(element.text());
            }
        }
        
        return item;
    }
}

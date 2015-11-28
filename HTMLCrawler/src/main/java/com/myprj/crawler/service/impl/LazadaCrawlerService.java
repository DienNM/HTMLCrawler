package com.myprj.crawler.service.impl;

import static com.myprj.crawler.client.util.PathConstants.PROJECTS;
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
            detail.add(productData.toString());
        }
        StreamUtil.write(new File(PROJECTS + site.getProject().getName() + separator + "lazada-product-info.csv"),
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

        Elements productNameElements = document.body().select("div.product-info-name");
        if (productNameElements != null && !productNameElements.isEmpty()) {
            product.setName(productNameElements.first().text());
        }

        Element descriptionDetailElement = document.body().select("div.description-detail").first();
        if(descriptionDetailElement != null) {
            Elements detailElements = descriptionDetailElement.select("ul li");
            if (detailElements != null && !detailElements.isEmpty()) {
                for (Element e : detailElements) {
                    product.getDescriptionDetails().add(e.text());
                }
            }
        }

        Elements productPriceElements = document.body().select("div.product-prices span.product-price");
        if (productPriceElements != null && !productPriceElements.isEmpty()) {
            product.setPrice(productPriceElements.first().text());
        }

        Elements productPriceOrgElements = document.body().select("div.product-prices span.product-price-past");
        if (productPriceOrgElements != null && !productPriceOrgElements.isEmpty()) {
            product.setOriginalPrice(productPriceOrgElements.first().text());
        }
        return item;
    }
}

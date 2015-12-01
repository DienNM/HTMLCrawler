package com.myprj.crawler.client;

import static com.myprj.crawler.client.util.IdGenerator.generateId;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.client.util.IdGenerator;
import com.myprj.crawler.domain.worker.ListWorkerTargetParameter;
import com.myprj.crawler.domain.worker.WorkerItemConfig;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.WorkerItemTargetType;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.AttributeRepository;
import com.myprj.crawler.repository.CategoryRepository;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.repository.ItemRepository;
import com.myprj.crawler.repository.WorkerItemRepository;
import com.myprj.crawler.repository.WorkerRepository;
import com.myprj.crawler.repository.impl.DefaultAttributeRepository;
import com.myprj.crawler.repository.impl.DefaultCategoryRepository;
import com.myprj.crawler.repository.impl.DefaultCrawlHistoryRepository;
import com.myprj.crawler.repository.impl.DefaultItemRepository;
import com.myprj.crawler.repository.impl.DefaultWorkerItemRepository;
import com.myprj.crawler.repository.impl.DefaultWorkerRepository;
import com.myprj.crawler.service.CrawlerService;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.service.cache.AttributeCacheService;
import com.myprj.crawler.service.cache.impl.InMemoryAttributeCacheService;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.CrawlDetailCompletedEventListener;
import com.myprj.crawler.service.event.impl.DefaultCrawlEventPublisher;
import com.myprj.crawler.service.handler.impl.HtmlAttributeHandler;
import com.myprj.crawler.service.handler.impl.LinkAttributeHandler;
import com.myprj.crawler.service.handler.impl.ListAttributeHandler;
import com.myprj.crawler.service.handler.impl.TextAttributeHandler;
import com.myprj.crawler.service.impl.DefaultCrawlerService;
import com.myprj.crawler.service.impl.LazadaWorker;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class LazadaTest {

    private CrawlerService crawlerService;
    private WorkerService workerService;
    private AttributeCacheService attributeCacheService = new InMemoryAttributeCacheService();;

    private CategoryRepository categoryRepository = new DefaultCategoryRepository();
    private ItemRepository itemRepository = new DefaultItemRepository();
    private AttributeRepository attributeRepository = new DefaultAttributeRepository();

    private WorkerItemRepository workerItemRepository = new DefaultWorkerItemRepository();
    private WorkerRepository workerRepository = new DefaultWorkerRepository();

    private CrawlEventPublisher crawlEventPublisher = new DefaultCrawlEventPublisher();

    private CrawlHistoryRepository crawlHistoryRepository = new DefaultCrawlHistoryRepository();

    public LazadaTest() {
        // Initialize Handlers
        new ListAttributeHandler();
        new LinkAttributeHandler();
        new TextAttributeHandler();
        new HtmlAttributeHandler();

        workerService = new LazadaWorker();
        workerService.setAttributeCacheService(attributeCacheService);
        workerService.setCrawlEventPublisher(crawlEventPublisher);

        crawlEventPublisher.register(new CrawlDetailCompletedEventListener());

        crawlerService = new DefaultCrawlerService();
        crawlerService.setWorkerService(workerService);
        crawlerService.setWorkerRepository(workerRepository);
        crawlerService.setCrawlHistoryRepository(crawlHistoryRepository);
    }

    public CategoryModel createCategory() {
        CategoryModel category = new CategoryModel();
        category.setId(generateId());
        category.setName("Lazada");
        category.setDescription("Crawler for Lazada website");
        category.setParentCategoryId(-1);
        category.setStatus(GlobalStatus.ONLINE);
        category.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        categoryRepository.save(category);

        return category;
    }

    public ItemModel createItem(CategoryModel category) {
        ItemModel item = new ItemModel();

        item.setId(IdGenerator.generateId());
        item.setCategoryId(category.getId());
        item.setName("Product");
        item.setStatus(GlobalStatus.ONLINE);

        itemRepository.save(item);

        return item;
    }

    public List<AttributeModel> createAttributes(ItemModel item) {
        AttributeModel attribute1 = new AttributeModel();
        attribute1.setId(IdGenerator.generateId());
        attribute1.setItemId(item.getId());
        attribute1.setName("productName");
        attribute1.setType(AttributeType.TEXT);

        AttributeModel attribute2 = new AttributeModel();
        attribute2.setId(IdGenerator.generateId());
        attribute2.setItemId(item.getId());
        attribute2.setName("productPrice");
        attribute2.setType(AttributeType.TEXT);

        AttributeModel attribute3 = new AttributeModel();
        attribute3.setId(IdGenerator.generateId());
        attribute3.setItemId(item.getId());
        attribute3.setName("productOldPrice");
        attribute3.setType(AttributeType.TEXT);

        AttributeModel attribute4 = new AttributeModel();
        attribute4.setId(IdGenerator.generateId());
        attribute4.setItemId(item.getId());
        attribute4.setName("specifications");
        attribute4.setType(AttributeType.LIST);

        AttributeModel attribute5 = new AttributeModel();
        attribute5.setId(IdGenerator.generateId());
        attribute5.setItemId(item.getId());
        attribute5.setName("includedInBoxs");
        attribute5.setType(AttributeType.LIST);

        AttributeModel attribute6 = new AttributeModel();
        attribute6.setId(IdGenerator.generateId());
        attribute6.setItemId(item.getId());
        attribute6.setName("descriptionDetails");
        attribute6.setType(AttributeType.LIST);

        attributeRepository.save(attribute1);
        attributeRepository.save(attribute2);
        attributeRepository.save(attribute3);
        attributeRepository.save(attribute4);
        attributeRepository.save(attribute5);
        attributeRepository.save(attribute6);

        attributeCacheService.updateCache(attribute1);
        attributeCacheService.updateCache(attribute2);
        attributeCacheService.updateCache(attribute3);
        attributeCacheService.updateCache(attribute4);
        attributeCacheService.updateCache(attribute5);
        attributeCacheService.updateCache(attribute6);

        return Arrays.asList(attribute1, attribute2, attribute3, attribute4, attribute5, attribute6);
    }

    public WorkerModel createWorker(ItemModel item, List<AttributeModel> attributes) {
        WorkerModel worker = new WorkerModel();
        worker.setId(IdGenerator.generateId());
        worker.setAttemptTimes(3);
        worker.setDelayTime(300);
        worker.setDescription("Lazada Worker 1: Sample Worker");
        worker.setThreads(3);
        worker.setName("Lazada Worker 1");

        createWorkerItem(worker, item, attributes);

        workerRepository.save(worker);
        return worker;
    }

    public void createWorkerItem(WorkerModel worker, ItemModel item, List<AttributeModel> attributes) {
        ListWorkerTargetParameter listParam = new ListWorkerTargetParameter();
        listParam.setStart("1");
        listParam.setEnd("1");

        // Level 0
        WorkerItemModel workerItem1 = new WorkerItemModel();
        workerItem1.setId(IdGenerator.generateId());
        workerItem1.setWorkerId(worker.getId());
        workerItem1.setLevel(Level.Level0);
        workerItem1.setUrl("http://www.lazada.vn/trang-diem/?page=%s");
        workerItem1.setTargetType(WorkerItemTargetType.LIST);
        workerItem1.setPagingConfig(Serialization.serialize(listParam));

        Map<String, String> cssSelectors1 = new HashMap<String, String>();
        cssSelectors1.put(AttributeType.LINK.name(), "div.product-description a.product-image-url");
        workerItem1.setCssSelectors(Serialization.serialize(cssSelectors1));

        workerItemRepository.save(workerItem1);

        // Level 1
        WorkerItemModel workerItem2 = new WorkerItemModel();
        workerItem2.setId(IdGenerator.generateId());
        workerItem2.setWorkerId(worker.getId());
        workerItem2.setLevel(Level.Level1);
        workerItem2.setTargetType(WorkerItemTargetType.DETAIL);

        WorkerItemConfig workerItemConfig = new WorkerItemConfig();
        workerItemConfig.setCategoryId(item.getCategoryId());
        workerItemConfig.setItemId(item.getId());

        for (AttributeModel att : attributes) {
            if (att.getName().equals("productName")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.product-info-name");
            } else if (att.getName().equals("productPrice")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.product-prices span.product-price");
            } else if (att.getName().equals("productOldPrice")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(),
                        "div.product-prices span.product-price-past");
            } else if (att.getName().equals("specifications")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(),
                        "div.catWrapper.specifications.specifications-detail div.description-detail ul li span");
            } else if (att.getName().equals("includedInBoxs")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(),
                        "div.catWrapper.whatisinbox div.description-detail ul li span");
            } else if (att.getName().equals("descriptionDetails")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(),
                        "div.catWrapper div.description-detail ul.simpleList.uip li");
            }
        }

        // workerItemConfig.getAttributesCssSelectors().put(AttributeType.LINK.name(),
        // value);

        workerItem2.setCssSelectors(Serialization.serialize(workerItemConfig));
        workerItemRepository.save(workerItem2);

        worker.getWorkerItems().add(workerItem1);
        worker.getWorkerItems().add(workerItem2);
    }

    public static void main(String[] args) {

        LazadaTest main = new LazadaTest();

        CategoryModel category = main.createCategory();
        ItemModel item = main.createItem(category);
        List<AttributeModel> attributes = main.createAttributes(item);

        WorkerModel worker = main.createWorker(item, attributes);

        try {
            main.getCrawlerService().init(worker);
            main.getCrawlerService().crawl(worker.getId());
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
        main.getCrawlerService().destroy(worker);
    }

    public CrawlerService getCrawlerService() {
        return crawlerService;
    }
}

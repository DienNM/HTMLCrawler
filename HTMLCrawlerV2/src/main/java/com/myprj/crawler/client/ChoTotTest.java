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
import com.myprj.crawler.service.impl.ChototWorker;
import com.myprj.crawler.service.impl.DefaultCrawlerService;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class ChoTotTest {
    
    private CrawlerService crawlerService;
    private WorkerService workerService ;
    private AttributeCacheService attributeCacheService = new InMemoryAttributeCacheService();;
    
    private CategoryRepository categoryRepository = new DefaultCategoryRepository();
    private ItemRepository itemRepository = new DefaultItemRepository();
    private AttributeRepository attributeRepository = new DefaultAttributeRepository();
    
    private WorkerItemRepository workerItemRepository = new  DefaultWorkerItemRepository();
    private WorkerRepository workerRepository = new  DefaultWorkerRepository();
    
    private CrawlEventPublisher crawlEventPublisher = new DefaultCrawlEventPublisher();
    
    private CrawlHistoryRepository crawlHistoryRepository = new DefaultCrawlHistoryRepository();
    
    public ChoTotTest() {
        // Initialize Handlers
        new ListAttributeHandler();
        new LinkAttributeHandler();
        new TextAttributeHandler();
        new HtmlAttributeHandler();
        
        workerService = new ChototWorker();
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
        category.setName("Cho Tot");
        category.setDescription("Crawler for chotot website");
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
        attribute1.setName("phoneNumber");
        attribute1.setType(AttributeType.TEXT);
        
        AttributeModel attribute2 = new AttributeModel();
        attribute2.setId(IdGenerator.generateId());
        attribute2.setItemId(item.getId());
        attribute2.setName("customerName");
        attribute2.setType(AttributeType.TEXT);
        
        attributeRepository.save(attribute1);
        attributeRepository.save(attribute2);
        
        
        attributeCacheService.updateCache(attribute1);
        attributeCacheService.updateCache(attribute2);
        
        return Arrays.asList(attribute1, attribute2);
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
        listParam.setEnd("3");
        
        // Level 0
        WorkerItemModel workerItem1 = new WorkerItemModel();
        workerItem1.setId(IdGenerator.generateId());
        workerItem1.setWorkerId(worker.getId());
        workerItem1.setLevel(Level.Level0);
        workerItem1.setUrl("http://www.chotot.vn/tp-ho-chi-minh/mua-ban");
        workerItem1.setTargetType(WorkerItemTargetType.LIST);
        workerItem1.setPagingConfig(Serialization.serialize(listParam));
        
        Map<String, String> cssSelectors1 = new HashMap<String, String>();
        cssSelectors1.put(AttributeType.LINK.name(), "div#ad_list a.item_link");
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
        
        for(AttributeModel att : attributes) {
            if(att.getName().equals("phoneNumber")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "a#phonebtn{{href}}");
            } else if(att.getName().equals("customerName")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.contact_title");
            } 
        }
        
        //workerItemConfig.getAttributesCssSelectors().put(AttributeType.LINK.name(), value);
        
        workerItem2.setCssSelectors(Serialization.serialize(workerItemConfig));
        workerItemRepository.save(workerItem2);

        worker.getWorkerItems().add(workerItem1);
        worker.getWorkerItems().add(workerItem2);
    }
    
    public static void main(String[] args) {
        
        ChoTotTest main = new ChoTotTest();
        
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

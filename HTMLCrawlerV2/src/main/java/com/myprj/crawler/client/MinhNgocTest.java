package com.myprj.crawler.client;

import static com.myprj.crawler.client.util.IdGenerator.generateId;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import com.myprj.crawler.service.impl.DefaultNavigationWorkerService;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class MinhNgocTest {
    
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
    
    public MinhNgocTest() {
        // Initialize Handlers
        new ListAttributeHandler();
        new LinkAttributeHandler();
        new TextAttributeHandler();
        new HtmlAttributeHandler();
        
        workerService = new DefaultNavigationWorkerService();
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
        category.setName("Xo So Minh Ngoc");
        category.setDescription("Crawler for Minh Ngoc website");
        category.setParentCategoryId(-1);
        category.setStatus(GlobalStatus.ONLINE);
        category.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        return categoryRepository.save(category);
    }
    
    public ItemModel createItem(CategoryModel category) {
        ItemModel item = new ItemModel();
        
        item.setId(IdGenerator.generateId());
        item.setCategoryId(category.getId());
        item.setName("Product");
        item.setStatus(GlobalStatus.ONLINE);
        
        return itemRepository.save(item);
    }
    
    public List<AttributeModel> createAttributes(ItemModel item) {
        AttributeModel attribute1 = new AttributeModel();
        attribute1.setId(IdGenerator.generateId());
        attribute1.setItemId(item.getId());
        attribute1.setName("title");
        attribute1.setType(AttributeType.TEXT);
        
        AttributeModel attribute2 = new AttributeModel();
        attribute2.setId(IdGenerator.generateId());
        attribute2.setItemId(item.getId());
        attribute2.setName("loaiVe");
        attribute2.setType(AttributeType.TEXT);
        
        AttributeModel attribute3 = new AttributeModel();
        attribute3.setId(IdGenerator.generateId());
        attribute3.setItemId(item.getId());
        attribute3.setName("giaiDacBiet");
        attribute3.setType(AttributeType.TEXT);
        
        AttributeModel attribute4 = new AttributeModel();
        attribute4.setId(IdGenerator.generateId());
        attribute4.setItemId(item.getId());
        attribute4.setName("giaiNhat");
        attribute4.setType(AttributeType.TEXT);
        
        AttributeModel attribute5 = new AttributeModel();
        attribute5.setId(IdGenerator.generateId());
        attribute5.setItemId(item.getId());
        attribute5.setName("giaiNhi");
        attribute5.setType(AttributeType.TEXT);
        
        AttributeModel attribute6 = new AttributeModel();
        attribute6.setId(IdGenerator.generateId());
        attribute6.setItemId(item.getId());
        attribute6.setName("giaiBa");
        attribute6.setType(AttributeType.LIST);
        
        AttributeModel attribute7 = new AttributeModel();
        attribute7.setId(IdGenerator.generateId());
        attribute7.setItemId(item.getId());
        attribute7.setName("giaiTu");
        attribute7.setType(AttributeType.LIST);
        
        AttributeModel attribute8 = new AttributeModel();
        attribute8.setId(IdGenerator.generateId());
        attribute8.setItemId(item.getId());
        attribute8.setName("giaiNam");
        attribute8.setType(AttributeType.LIST);
        
        AttributeModel attribute9 = new AttributeModel();
        attribute9.setId(IdGenerator.generateId());
        attribute9.setItemId(item.getId());
        attribute9.setName("giaiSau");
        attribute9.setType(AttributeType.LIST);
        
        AttributeModel attribute10 = new AttributeModel();
        attribute10.setId(IdGenerator.generateId());
        attribute10.setItemId(item.getId());
        attribute10.setName("giaiBay");
        attribute10.setType(AttributeType.LIST);
        
        AttributeModel attribute11 = new AttributeModel();
        attribute11.setId(IdGenerator.generateId());
        attribute11.setItemId(item.getId());
        attribute11.setName("giaiTam");
        attribute11.setType(AttributeType.LIST);
        
        attributeRepository.save(attribute1);
        attributeRepository.save(attribute2);
        attributeRepository.save(attribute3);
        attributeRepository.save(attribute4);
        attributeRepository.save(attribute5);
        attributeRepository.save(attribute6);
        attributeRepository.save(attribute7);
        attributeRepository.save(attribute8);
        attributeRepository.save(attribute9);
        attributeRepository.save(attribute10);
        attributeRepository.save(attribute11);
        
        
        attributeCacheService.updateCache(attribute1);
        attributeCacheService.updateCache(attribute2);
        attributeCacheService.updateCache(attribute3);
        attributeCacheService.updateCache(attribute4);
        attributeCacheService.updateCache(attribute5);
        attributeCacheService.updateCache(attribute6);
        attributeCacheService.updateCache(attribute7);
        attributeCacheService.updateCache(attribute8);
        attributeCacheService.updateCache(attribute9);
        attributeCacheService.updateCache(attribute10);
        attributeCacheService.updateCache(attribute11);
        
        return Arrays.asList(attribute1, attribute2, attribute3, attribute4, attribute5, attribute6,
                attribute7, attribute8, attribute9, attribute10, attribute11);
    }
    
    public WorkerModel createWorker(ItemModel item, List<AttributeModel> attributes) {
        WorkerModel worker = new WorkerModel();
        worker.setId(IdGenerator.generateId());
        worker.setAttemptTimes(3);
        worker.setDelayTime(300);
        worker.setDescription("MinhNgoc Worker 1: Sample Worker");
        worker.setThreads(3);
        worker.setName("MinhNgoc Worker 1");
        
        createWorkerItem(worker, item, attributes);
        
        return workerRepository.save(worker);
    }
    
    public void createWorkerItem(WorkerModel worker, ItemModel item, List<AttributeModel> attributes) {
        ListWorkerTargetParameter listParam = new ListWorkerTargetParameter();
        listParam.setStart("01-09-2015");
        listParam.setEnd("19-11-2015");
        
        // Level 0
        WorkerItemModel workerItem1 = new WorkerItemModel();
        workerItem1.setId(IdGenerator.generateId());
        workerItem1.setWorkerId(worker.getId());
        workerItem1.setLevel(Level.Level0);
        workerItem1.setUrl("http://www.minhngoc.net.vn/ket-qua-xo-so/mien-nam/an-giang/%s.html");
        workerItem1.setTargetType(WorkerItemTargetType.NAVIGATION);
        workerItem1.setPagingConfig(Serialization.serialize(listParam));
        
        WorkerItemConfig workerItemConfig = new WorkerItemConfig();
        workerItemConfig.setCategoryId(item.getCategoryId());
        workerItemConfig.setItemId(item.getId());
        
        for(AttributeModel att : attributes) {
            if(att.getName().equals("title")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.box_kqxs div.title");
            } if(att.getName().equals("loaiVe")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content span.loaive");
            } if(att.getName().equals("giaiDacBiet")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giaidb div");
            } if(att.getName().equals("giaiNhat")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai1 div");
            } if(att.getName().equals("giaiNhi")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai2 div");
            } if(att.getName().equals("giaiBa")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai3 div");
            } if(att.getName().equals("giaiTu")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai4 div");
            } else if(att.getName().equals("giaiNam")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai5 div");
            }  else if(att.getName().equals("giaiSau")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai6 div");
            }  else if(att.getName().equals("giaiBay")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai7 div");
            }  else if(att.getName().equals("giaiTam")) {
                workerItemConfig.getAttributesCssSelectors().put(att.getId(), "div.content td.giai8 div");
            } 
        }
        
        //workerItemConfig.getAttributesCssSelectors().put(AttributeType.LINK.name(), value);
        
        workerItem1.setCssSelectors(Serialization.serialize(workerItemConfig));
        workerItemRepository.save(workerItem1);

        worker.getWorkerItems().add(workerItem1);
    }
    
    public static void main(String[] args) {
        
        MinhNgocTest main = new MinhNgocTest();
        
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

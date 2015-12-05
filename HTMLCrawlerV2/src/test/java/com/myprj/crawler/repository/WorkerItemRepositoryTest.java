package com.myprj.crawler.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemRepositoryTest extends AbstractTest {
    
    @Autowired
    private WorkerItemRepository workerItemRepository;
    
    @Transactional
    @Before
    public void startUp() {
        WorkerItemModel workerItem1 = new WorkerItemModel();
        workerItem1.setWorkerId(10);
        workerItem1.setLevel(Level.Level0);
        
        WorkerItemModel workerItem2 = new WorkerItemModel();
        workerItem2.setWorkerId(10);
        workerItem2.setLevel(Level.Level1);
        
        workerItemRepository.save(workerItem1);
        workerItemRepository.save(workerItem2);
    }
    
    @After
    @Transactional
    public void tearDown() {
        workerItemRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testFindByWorkerId() {
        List<WorkerItemModel> workerItems = workerItemRepository.findByWorkerId(10);
        Assert.assertEquals(2, workerItems.size());
        
        workerItems = workerItemRepository.findByWorkerId(11);
        Assert.assertEquals(0, workerItems.size());
    }
    
    @Test
    @Transactional
    public void testFindByWorkerIdAndStatus() {
        WorkerItemModel workerItem = workerItemRepository.findByWorkerIdAndLevel(10, Level.Level0);
        Assert.assertNotNull(workerItem);
        
        workerItem = workerItemRepository.findByWorkerIdAndLevel(10, Level.Level1);
        Assert.assertNotNull(workerItem);
        

        workerItem = workerItemRepository.findByWorkerIdAndLevel(10, Level.Level2);
        Assert.assertNull(workerItem);
    }
}

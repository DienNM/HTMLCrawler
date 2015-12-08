package com.myprj.crawler.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.model.crawl.WorkerItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemAttributeRepositoryTest extends AbstractTest {
    
    @Autowired
    private WorkerItemAttributeRepository workerItemAttributeRepository;
    
    @Before
    @Transactional
    public void startUp() {
        WorkerItemAttributeModel workerItemAttribute1 = new WorkerItemAttributeModel();
        workerItemAttribute1.setId("1|1|content");
        workerItemAttribute1.setAttributeId("1|content");
        

        WorkerItemAttributeModel workerItemAttribute2 = new WorkerItemAttributeModel();
        workerItemAttribute2.setId("1|1|content|name");
        workerItemAttribute2.setAttributeId("1|content|name");
        workerItemAttribute2.setParentId("1|1|content");
        

        WorkerItemAttributeModel workerItemAttribute3 = new WorkerItemAttributeModel();
        workerItemAttribute3.setId("1|1|content|price");
        workerItemAttribute3.setAttributeId("1|content|price");
        workerItemAttribute3.setParentId("1|1|content");
        
        workerItemAttributeRepository.save(workerItemAttribute1);
        workerItemAttributeRepository.save(workerItemAttribute2);
        workerItemAttributeRepository.save(workerItemAttribute3);
    }
    
    @After
    @Transactional
    public void tearDown() {
        workerItemAttributeRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testFindChildren() {
        List<WorkerItemAttributeModel> workerItemAttributes = workerItemAttributeRepository.findChildren("1|1|content");
        Assert.assertEquals(2, workerItemAttributes.size());
        
        workerItemAttributes = workerItemAttributeRepository.findChildren("2|1|content");
        Assert.assertEquals(0, workerItemAttributes.size());
    }
}

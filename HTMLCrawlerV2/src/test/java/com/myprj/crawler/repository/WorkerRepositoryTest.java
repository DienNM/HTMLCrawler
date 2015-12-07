package com.myprj.crawler.repository;

import static com.myprj.crawler.enumeration.WorkerStatus.Completed;
import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerRepositoryTest extends AbstractTest {
    
    @Autowired
    private WorkerRepository workerRepository;
    
    @Test
    @Transactional
    public void testFindByStatus() {
        WorkerModel worker1 = new WorkerModel();
        worker1.setName("Worker 1");
        worker1.setKey("key 1");
        worker1.setStatus(WorkerStatus.Completed);

        WorkerModel worker2 = new WorkerModel();
        worker2.setName("Worker 2");
        worker2.setKey("key 2");
        worker2.setStatus(WorkerStatus.Completed);
        
        workerRepository.save(worker1);
        workerRepository.save(worker2);
        
        List<WorkerModel> workerModels = workerRepository.findByStatus(Completed);
        Assert.assertEquals(2, workerModels.size());
        
        workerModels = workerRepository.findByStatus(Created);
        Assert.assertEquals(0, workerModels.size());
    }
    
}

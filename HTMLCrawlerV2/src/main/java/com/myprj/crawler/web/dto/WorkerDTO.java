package com.myprj.crawler.web.dto;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerDTO implements Serializable{
    
    private static final long serialVersionUID = -8527850233910942023L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("site")
    private String site;

    @DataTransfer("description")
    private String description;

    @DataTransfer("threads")
    private int threads = 1;

    @DataTransfer("attemptTimes")
    private int attemptTimes = 3;

    @DataTransfer("delayTime")
    private int delayTime = 1000;

    @DataTransfer("status")
    private WorkerStatus status = Created;
    
    private List<WorkerItemDTO> workerItems = new ArrayList<WorkerItemDTO>();
    
    public WorkerDTO() {
    }

    public static List<WorkerDTO> toDTOs(List<WorkerData> sources) {
        List<WorkerDTO> dests = new ArrayList<WorkerDTO>();
        for(WorkerData source : sources) {
            dests.add(toDTO(source));
        }
        return dests;
    }
    
    public static WorkerDTO toDTO(WorkerData source) {
        WorkerDTO dest = new WorkerDTO();
        DomainConverter.convert(source, dest, new ObjectConverter<WorkerData, WorkerDTO>() {
            @Override
            public void convert(WorkerData src, WorkerDTO dest) {
                dest.setWorkerItems(WorkerItemDTO.toDTOs(src.getWorkerItems()));
            }
        });
        return dest;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(int attemptTimes) {
        this.attemptTimes = attemptTimes;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public WorkerStatus getStatus() {
        return status;
    }

    public void setStatus(WorkerStatus status) {
        this.status = status;
    }

    public List<WorkerItemDTO> getWorkerItems() {
        return workerItems;
    }

    public void setWorkerItems(List<WorkerItemDTO> workerItems) {
        this.workerItems = workerItems;
    }
    
}

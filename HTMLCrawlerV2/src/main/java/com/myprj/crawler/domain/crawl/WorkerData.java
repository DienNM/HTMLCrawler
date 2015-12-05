package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer(value = "id")
    @EntityTransfer("id")
    private long id;

    @DataTransfer(value = "name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer(value = "description")
    @EntityTransfer("description")
    private String description;

    @DataTransfer(value = "site")
    @EntityTransfer("site")
    private String site;

    @DataTransfer(value = "threads")
    @EntityTransfer("threads")
    private int threads = 1;

    @DataTransfer(value = "attemptTimes")
    @EntityTransfer("attempt_times")
    private int attemptTimes = 3;

    @DataTransfer(value = "delayTime")
    @EntityTransfer("delay_time")
    private int delayTime = 1000;

    @DataTransfer(value = "status")
    @EntityTransfer("status")
    private WorkerStatus status = Created;

    @DataTransfer(value = "requestId")
    @EntityTransfer("request_id")
    private String requestId;

    private List<ProxyData> proxies = new ArrayList<ProxyData>();

    private List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();

    public WorkerData() {
    }

    public static void toDatas(List<WorkerModel> sources, List<WorkerData> dests) {
        for (WorkerModel source : sources) {
            WorkerData dest = new WorkerData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<WorkerData> sources, List<WorkerModel> dests) {
        for (WorkerData source : sources) {
            WorkerModel dest = new WorkerModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(WorkerModel source, WorkerData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(WorkerData source, WorkerModel dest) {
        EntityConverter.convert2Entity(source, dest);
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

    public List<WorkerItemData> getWorkerItems() {
        return workerItems;
    }

    public void setWorkerItems(List<WorkerItemData> workerItems) {
        this.workerItems = workerItems;
    }

    public List<ProxyData> getProxies() {
        return proxies;
    }

    public void setProxies(List<ProxyData> proxies) {
        this.proxies = proxies;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}

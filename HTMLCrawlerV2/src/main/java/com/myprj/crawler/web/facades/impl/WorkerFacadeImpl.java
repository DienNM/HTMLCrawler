package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.web.dto.WorkerItemDTO;
import com.myprj.crawler.web.facades.WorkerFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerFacadeImpl implements WorkerFacade {

    @Override
    public List<WorkerData> loadWorkersFromSource(InputStream inputStream) {
        List<WorkerData> workers = new ArrayList<WorkerData>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            List<String> lines = new ArrayList<String>();
            boolean loadingWorkerItem = false;

            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith(">") || line.startsWith("<END")) {
                    if (!lines.isEmpty()) {
                        WorkerData workerData = workers.get(workers.size() - 1);
                        List<WorkerItemDTO> workerItemDTOs = Serialization.deserialize(lines, WorkerItemDTO.class);
                        List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
                        WorkerItemDTO.toDatas(workerItemDTOs, workerItems);
                        workerData.setWorkerItems(workerItems);

                        loadingWorkerItem = false;
                    }
                }

                if (loadingWorkerItem) {
                    lines.add(line);
                }

                if (line.startsWith(">")) {
                    line = line.substring(1);
                    WorkerData workerData = parseWorker(line);
                    workers.add(workerData);
                    loadingWorkerItem = true;
                    lines = new ArrayList<String>();
                }
            }
        } catch (Exception ex) {
            return new ArrayList<WorkerData>();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return workers;
    }

    private WorkerData parseWorker(String line) {
        String[] elements = line.split(Pattern.quote("|"));
        WorkerData workerData = new WorkerData();
        workerData.setSite(elements[1]);
        workerData.setThreads(Integer.valueOf(elements[2]));
        workerData.setAttemptTimes(Integer.valueOf(elements[3]));
        workerData.setDelayTime(Integer.valueOf(elements[4]));
        workerData.setName(elements[5]);
        workerData.setCategory(elements[6]);
        workerData.setDescription(elements[7]);
        return workerData;
    }

}

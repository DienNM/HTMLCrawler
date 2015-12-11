package com.myprj.crawler.service.target.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;
import com.myprj.crawler.service.target.ConsolidationAttributeService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ConsolidationAttributeServiceImpl implements ConsolidationAttributeService {

    @Autowired
    private ConsolidationAttributeRepository consolidationAttributeRepository;
    
    @Override
    public List<ConsolidationAttributeData> getByConsolidationId(long consolidationId) {
        List<ConsolidationAttributeModel> consolidationAttributeModels = consolidationAttributeRepository.findByConsolidationId(consolidationId);
        List<ConsolidationAttributeData> consolidationAttributeDatas = new ArrayList<ConsolidationAttributeData>();
        ConsolidationAttributeData.toDatas(consolidationAttributeModels, consolidationAttributeDatas);
        return consolidationAttributeDatas;
    }

    @Override
    public ConsolidationAttributeData getById(long id) {
        ConsolidationAttributeModel consolidationAttributeModel = consolidationAttributeRepository.find(id);
        if(consolidationAttributeModel == null) {
            return null;
        }
        ConsolidationAttributeData consolidationAttributeData = new ConsolidationAttributeData();
        ConsolidationAttributeData.toData(consolidationAttributeModel, consolidationAttributeData);
        
        return consolidationAttributeData;
    }

    @Override
    public ConsolidationAttributeData get(String name, long consolidationId) {
        ConsolidationAttributeModel consolidationAttributeModel = consolidationAttributeRepository.findByNameAndConsolidationId(name, consolidationId);
        if(consolidationAttributeModel == null) {
            return null;
        }
        ConsolidationAttributeData consolidationAttributeData = new ConsolidationAttributeData();
        ConsolidationAttributeData.toData(consolidationAttributeModel, consolidationAttributeData);
        
        return consolidationAttributeData;
    }

}

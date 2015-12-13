package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.model.RuleScriptModel;
import com.myprj.crawler.repository.RuleScriptRepository;
import com.myprj.crawler.service.RuleScriptService;

/**
 * @author DienNM (DEE)
 */
@Service
public class RuleScriptServiceImpl implements RuleScriptService {

    @Autowired
    private RuleScriptRepository ruleScriptRepository;

    @Override
    public RuleScriptData get(String code) {
        RuleScriptModel ruleScriptModel = ruleScriptRepository.find(code);
        if (ruleScriptModel == null) {
            return null;
        }
        RuleScriptData ruleScriptData = new RuleScriptData();
        RuleScriptData.toData(ruleScriptModel, ruleScriptData);
        return ruleScriptData;
    }

    @Override
    public List<RuleScriptData> get(List<String> codes) {
        List<RuleScriptModel> ruleScriptModels = ruleScriptRepository.find(codes);
        List<RuleScriptData> ruleScriptDatas = new ArrayList<RuleScriptData>();
        RuleScriptData.toDatas(ruleScriptModels, ruleScriptDatas);
        return ruleScriptDatas;
    }

    @Override
    public List<RuleScriptData> getAll() {
        List<RuleScriptModel> ruleScriptModels = ruleScriptRepository.findAll();
        List<RuleScriptData> ruleScriptDatas = new ArrayList<RuleScriptData>();
        RuleScriptData.toDatas(ruleScriptModels, ruleScriptDatas);
        return ruleScriptDatas;
    }

    @Override
    @Transactional
    public void saveOrUpdate(RuleScriptData ruleScriptData) {
        RuleScriptModel newObject = new RuleScriptModel();
        RuleScriptData.toModel(ruleScriptData, newObject);

        RuleScriptModel ruleScriptModel = ruleScriptRepository.find(ruleScriptData.getCode());
        if (ruleScriptModel == null) {
            ruleScriptRepository.save(newObject);
        } else {
            ruleScriptModel.setEnabled(newObject.isEnabled());
            ruleScriptModel.setFile(newObject.getFile());
            ruleScriptModel.setScript(newObject.getScript());
            ruleScriptRepository.update(ruleScriptModel);
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(List<RuleScriptData> ruleScriptDatas) {
        for (RuleScriptData ruleScriptData : ruleScriptDatas) {
            RuleScriptModel newObject = new RuleScriptModel();
            RuleScriptData.toModel(ruleScriptData, newObject);

            RuleScriptModel ruleScriptModel = ruleScriptRepository.find(ruleScriptData.getCode());
            if (ruleScriptModel == null) {
                ruleScriptRepository.save(newObject);
            } else {
                ruleScriptModel.setEnabled(newObject.isEnabled());
                ruleScriptModel.setFile(newObject.getFile());
                ruleScriptModel.setScript(newObject.getScript());
                ruleScriptRepository.update(ruleScriptModel);
            }
        }
    }

}

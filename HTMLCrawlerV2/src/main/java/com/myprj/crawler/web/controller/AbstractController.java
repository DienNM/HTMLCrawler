package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.util.ReflectionUtil;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.mapping.DTOMappingHandler;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractController {
    
    protected <S, D> List<Map<String, Object>> getListMapResult(List<S> sources, Class<D> targetClass, DTOLevel level) {
        List<D> dtos = new ArrayList<D>();
        DomainConverter.convertList(sources, dtos, targetClass);
        List<Map<String, Object>> listDatas = new ArrayList<Map<String, Object>>();
        DTOMappingHandler.mapList(dtos, listDatas, level);
        return listDatas;
    }
    
    protected <DTO> List<Map<String, Object>> getListMapResult(List<DTO> sources, DTOLevel level) {
        List<Map<String, Object>> listDatas = new ArrayList<Map<String, Object>>();
        DTOMappingHandler.mapList(sources, listDatas, level);
        return listDatas;
    }
    
    protected <S, D> Map<String, Object> getMapResult(S source, Class<D> targetClass, DTOLevel level) {
        D dto = ReflectionUtil.createInstance(targetClass);
        DomainConverter.convert(source, dto);
        return getMapResult(dto, level);
    }
    
    protected Map<String, Object> getMapResult(Object object, DTOLevel level) {
        Map<String, Object> datas = new HashMap<String, Object>();
        DTOMappingHandler.map(object, datas, level);
        
        return datas;
    }
    
    protected String readLinesFile2String(MultipartFile file) {
        try {
            String content = StreamUtil.readFile2String(file.getInputStream());
            if (content == null) {
                return "";
            }
            return content.trim();
        } catch (Exception e) {
            return "";
        }
    }

    protected <T> List<T>  readLinesFile2List(MultipartFile file, Class<T> clazz) {
        List<T> targets = new ArrayList<T>();
        try {
            List<String> lines = StreamUtil.readFile2Strings(file.getInputStream());
            for(String line : lines) {
                targets.add(Serialization.deserialize(line, clazz));
            }
            return targets;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }
    
    protected <T> List<T>  convertStrings2Objects(List<String> lines, Class<T> clazz) {
        List<T> targets = new ArrayList<T>();
        try {
            for(String line : lines) {
                targets.add(Serialization.deserialize(line, clazz));
            }
            return targets;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

}

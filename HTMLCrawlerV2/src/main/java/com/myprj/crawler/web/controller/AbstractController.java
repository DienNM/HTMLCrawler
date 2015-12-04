package com.myprj.crawler.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.TargetDTOLevel;
import com.myprj.crawler.web.mapping.DTOHandler;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractController {

    protected JsonResponse returnResponses(List<?> objs, TargetDTOLevel target) {
        List<Map<String, Object>> json = DTOHandler.convert(objs, target);
        return new JsonResponse(json);
    }

    protected JsonResponse returnResponse(Object objs, TargetDTOLevel target) {
        Map<String, Object> json = DTOHandler.convert(objs, target);
        return new JsonResponse(json);
    }

    protected JsonResponse returnDataPaging(PageResult<?> pageResult) {
        JsonResponse response = new JsonResponse(true);
        response.put("pageSize", pageResult.getPageable().getPageSize());
        response.put("currentPage", pageResult.getPageable().getPageIndex());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("totalRecords", pageResult.getTotalRecords());
        return response;
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

}

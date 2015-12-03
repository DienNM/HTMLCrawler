package com.myprj.crawler.web.controller;

import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.web.dto.JsonResponse;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractController {
    
    protected JsonResponse returnDataPaging(PageResult<?> pageResult) {
        JsonResponse response = new JsonResponse(true);
        response.putData(pageResult.getContent());
        response.put("pageSize", pageResult.getPageable().getPageSize());
        response.put("currentPage", pageResult.getPageable().getPageIndex());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("totalRecords", pageResult.getTotalRecords());
        return response;
    }
    
    protected String readLinesFile2String(MultipartFile file) {
        try {
            String content = StreamUtil.readFile2String(file.getInputStream());
            if(content == null) {
                return "";
            }
            return content.trim();
        } catch (Exception e) {
            return "";
        }
    }
    
}

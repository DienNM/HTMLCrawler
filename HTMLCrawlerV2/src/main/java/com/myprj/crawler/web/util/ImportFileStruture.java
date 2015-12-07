package com.myprj.crawler.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class ImportFileStruture {
    
    private List<List<String>> mainLines = new ArrayList<List<String>>(); 
    private String json;
    
    public ImportFileStruture() {
    }
    
    public List<List<String>> getMainLines() {
        return mainLines;
    }
    public void setMainLines(List<List<String>> mainLines) {
        this.mainLines = mainLines;
    }
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    
}

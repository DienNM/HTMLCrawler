package com.myprj.crawler.service.impl;


import static com.myprj.crawler.enumeration.ProjectType.chotot;

import com.myprj.crawler.service.AbstractProjectSupport;

/**
 * @author DienNM
 **/

public class ChototProject extends AbstractProjectSupport {
    
    public ChototProject() {
        super();
    }
    
    @Override
    protected String getProjectType() {
        return chotot.name();
    }

}

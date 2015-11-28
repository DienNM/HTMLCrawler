package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.ProjectType.lazada;

import com.myprj.crawler.service.AbstractProjectSupport;

/**
 * @author DienNM
 **/

public class LazadaProject extends AbstractProjectSupport {

    public LazadaProject() {
        super();
    }

    @Override
    protected String getProjectType() {
        return lazada.name();
    }

}

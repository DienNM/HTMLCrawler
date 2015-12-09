package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.target.AttributeMappingData;
import com.myprj.crawler.web.util.ImportDataException;
import com.myprj.crawler.web.util.ParserDataException;

/**
 * @author DienNM (DEE)
 */

public interface AttributeMappingFacade {

    List<AttributeMappingData> importFromSource(InputStream inputStream) throws ImportDataException;

    List<AttributeMappingData> loadMappingsFromSource(InputStream inputStream) throws ParserDataException;

}

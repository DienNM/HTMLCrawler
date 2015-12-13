package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.RuleScriptData;

/**
 * @author DienNM (DEE)
 */

public interface RuleScriptFacade {

    List<RuleScriptData> loadScriptsFromSource(InputStream inputStream);

}

package com.myprj.crawler.client.util;

import static java.io.File.separator;

/**
 * @author DienNM (DEE)
 */

public class PathConstants {
    public static final String ROOT = "data".intern() + separator;
    public static final String GLOBAL_PROJECTS = ROOT + "global.json".intern();

    public static final String PROJECTS = ROOT + "projects".intern() + separator;
    public static final String TEMPLATES = ROOT + "templates".intern() + separator;

    public static final String TEMPLATE_CONFIG = TEMPLATES + "%s" + separator;
    public static final String PROJECT_CONFIG = PROJECTS + "%s" + separator;

    public static final String CONFIG_FILE = "config.properties".intern();
    public static final String LINK_FILE = "link.txt".intern();
}

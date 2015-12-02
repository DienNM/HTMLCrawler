package com.myprj.crawler.util;

/**
 * @author DienNM (DEE)
 */

public class H2Dialect extends org.hibernate.dialect.H2Dialect {
    @Override
    public boolean supportsUniqueConstraintInCreateAlterTable() {
        return false;
    }
}
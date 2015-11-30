package com.myprj.crawler.client.util;

import java.util.Random;

/**
 * @author DienNM (DEE)
 */

public final class IdGenerator {

    public synchronized static long generateId() {
        return new Random().nextLong();
    }
}

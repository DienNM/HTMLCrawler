package com.myprj.crawler.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public final class HandlerRegister {
    
    private static Logger logger = LoggerFactory.getLogger(HandlerRegister.class);
    
    private static final Map<AttributeType, AttributeHandler> handlers = new HashMap<AttributeType, AttributeHandler>();
    
    public static void register(AttributeType type, AttributeHandler handler) {
        handlers.put(type, handler);
        logger.info("Registered handler for {}", type);
    }
    
    public static AttributeHandler getHandler(AttributeType type) {
        if(!handlers.containsKey(type)) {
            throw new UnsupportedOperationException("Handler for type {} has not supported yet");
        } 
        return handlers.get(type);
    }
    
    public static void unregister(AttributeType type) {
        if(handlers.containsKey(type)) {
            handlers.remove(type);
            logger.info("Unregistered handler for {}", type);
        } else {
            logger.info("RHandler for {} does not exist", type);
        }
    }
    
}

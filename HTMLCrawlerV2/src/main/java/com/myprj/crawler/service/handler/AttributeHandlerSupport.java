package com.myprj.crawler.service.handler;

/**
 * @author DienNM (DEE)
 */

public abstract class AttributeHandlerSupport implements AttributeHandler {

    @Override
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

}

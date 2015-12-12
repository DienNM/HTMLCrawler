package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.domain.target.ConsolidationData;

/**
 * @author DienNM (DEE)
 */
public class ConsolidationCompletedEvent implements CrawlEvent {

    private static final long serialVersionUID = 1L;

    private ConsolidationData consolidation;

    public ConsolidationCompletedEvent() {
    }

    public ConsolidationCompletedEvent(ConsolidationData consolidation) {
        this.consolidation = consolidation;
    }

    public ConsolidationData getConsolidation() {
        return consolidation;
    }

    public void setConsolidation(ConsolidationData consolidation) {
        this.consolidation = consolidation;
    }

}

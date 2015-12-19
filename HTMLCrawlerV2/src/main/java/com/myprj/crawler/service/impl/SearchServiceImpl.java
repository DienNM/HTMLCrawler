package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.SearchParam;
import com.myprj.crawler.service.SearchService;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.ElasticSearchClient;

/**
 * @author DienNM (DEE)
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public PageResult<Map<String, String>> search(SearchParam searchParam) {
        Client client = ElasticSearchClient.getInstance();
        String index = Config.get("elasticsearch.index.name");

        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(
                QueryBuilders.multiMatchQuery(searchParam.getKeyword(), searchParam.toArrayFields())
                        .minimumShouldMatch("95%"));
                //.should(QueryBuilders.disMaxQuery().add(QueryBuilders.matchPhraseQuery("title", value)))
                //.should(QueryBuilders.matchPhraseQuery("description", value));

        SearchResponse response = client.prepareSearch(index).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query).setFrom(0).setSize(searchParam.getPageable().getPageSize()).execute().actionGet();
        
        long totalRecords = response.getHits().getTotalHits();
        long totalPages = totalRecords / searchParam.getPageable().getPageSize();
        if(totalRecords % searchParam.getPageable().getPageSize() != 0 ) {
            totalPages += 1;
        }
        
        Pageable resultPageable = new Pageable(searchParam.getPageable());
        resultPageable.setTotalPages(totalPages);
        resultPageable.setTotalRecords(totalRecords);
        
        PageResult<Map<String, String>> pageResult = new PageResult<Map<String, String>>(resultPageable);
        
        String keyId = Config.get("elasticsearch._key.id");
        String categoryKey = Config.get("elasticsearch._key.category");
        String itemKey = Config.get("elasticsearch._key.item");
        String siteKey = Config.get("elasticsearch._key.site");
        
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            Map<String, String> result = new HashMap<String, String>();
            result.put(keyId, source.get(keyId).toString());
            result.put(categoryKey, source.get(categoryKey).toString());
            result.put(itemKey, source.get(itemKey).toString());
            result.put(siteKey, source.get(siteKey).toString());
            
            for(String key : searchParam.getMainFields()) {
                result.put(key, source.get(key).toString());
            }
            results.add(result);
        }
        pageResult.setContent(results);
        return pageResult;
    }

}

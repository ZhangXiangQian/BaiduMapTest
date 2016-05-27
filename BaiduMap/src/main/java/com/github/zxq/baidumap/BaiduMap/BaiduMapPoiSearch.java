package com.github.zxq.baidumap.BaiduMap;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.sug.SuggestionResult;

import java.util.List;

/**
 * Created by zhang on 2016/5/26.
 */
public interface BaiduMapPoiSearch {
    public void onPoiSearchResult(PoiResult result);

    public void onSuggestionResult(List<SuggestionResult.SuggestionInfo> list);

    public void onGetPoiDetailResult(PoiDetailResult result);

}

package com.github.zxq.baidumap;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.OpenLogUtil;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.github.zxq.baidumap.BaiduMap.BaiduMapPoiSearch;

/**
 * 百度地图搜索
 */
public class BaiduMapPoiSearchUtils implements OnGetPoiSearchResultListener,OnGetSuggestionResultListener{
	public PoiSearch 						mPoiSearch;
	private SuggestionSearch				mSuggestionSearch;
	//private InfoWindow					mInfoWindow;
	private static final String             TAG = "BaiduMapPoiSearchUtils";
	
	private static BaiduMapPoiSearchUtils   mPoiSearchUtils;
	private Context							context;
	
	private BaiduMapPoiSearch 				search;
	private BaiduMapPoiSearchUtils(){
	}
	
	public static BaiduMapPoiSearchUtils  newInstance(){
		if(mPoiSearchUtils == null){
			mPoiSearchUtils = new BaiduMapPoiSearchUtils();
		}
		return mPoiSearchUtils;
	}
	
	public void init(Context context,BaiduMapPoiSearch search){
		this.context      = context;
		this.search       = search;
		mPoiSearch 		  = PoiSearch.newInstance();
		mSuggestionSearch = SuggestionSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
	}


	public void startSearch(String city,String keyWord,int pageNum){
		mPoiSearch.searchInCity(new PoiCitySearchOption().city(city)
														 .keyword(keyWord)
														 .pageNum(pageNum));
	}

	public void startSuggestSearch(String city,String keyWord){
		mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
						                     .keyword(keyWord)
				                             .city(city));
	}
	
	@Override
	public void onGetSuggestionResult(SuggestionResult result) {
		if(result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
			Log.i("BaiduMapPoiSearchUtils" ,result.error + "");
			return;
		}
		if(search != null){
			search.onSuggestionResult(result.getAllSuggestions());
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if(result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
			Toast.makeText(context, "未找到结果", Toast.LENGTH_SHORT).show();
			return;
		}
		if(search != null){
			search.onGetPoiDetailResult(result);
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		Log.i(TAG,result.error + "");
		if(result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD){
			String strInfo = "在";
			for(CityInfo c:result.getSuggestCityList()){
				strInfo += c.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(context, strInfo, Toast.LENGTH_SHORT).show();
			return;
		}
		if(result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
			Toast.makeText(context, "未找到结果", Toast.LENGTH_SHORT).show();
			return;
		}
		if(search != null){
			search.onPoiSearchResult(result);
		}
	}
	public void onDestroy(){
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
	}
}




















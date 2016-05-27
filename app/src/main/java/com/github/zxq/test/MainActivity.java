package com.github.zxq.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.github.zxq.baidumap.BaiduMap.BaiduMapLocation;
import com.github.zxq.baidumap.BaiduMap.BaiduMapPoiSearch;
import com.github.zxq.baidumap.BaiduMapLocationUtils;
import com.github.zxq.baidumap.BaiduMapPoiSearchUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaiduMapLocation, BaiduMapPoiSearch {

    private MapView mapView;
    private ListView mListView;
    private List<String> list = new ArrayList<>();
    private AutoCompleteTextView etKeyword;
    private String city = "北京";
    private BaiduMapLocationUtils location;
    private static final String TAG = "MainActivity";
    private boolean isFir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etKeyword = (AutoCompleteTextView) findViewById(R.id.etKeyword);
        mapView = (MapView) findViewById(R.id.mapView);

        location =  new BaiduMapLocationUtils(this, this);

        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setAdapter(mAdapter);

        BaiduMapPoiSearchUtils.newInstance().init(this,this);

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BaiduMapPoiSearchUtils.newInstance().startSearch(city,etKeyword.getText().toString(),1);
            }
        });
        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BaiduMapPoiSearchUtils.newInstance().startSuggestSearch(city,etKeyword.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onLocationResult(BDLocation location) {
        if(!isFir){
            Toast.makeText(this, location.getCity(), Toast.LENGTH_SHORT).show();
            isFir = true;
        }

    }

    @Override
    public void onPoiSearchResult(PoiResult result) {
        list.clear();
        if(result != null && result.getAllPoi() != null && result.getAllPoi().size() != 0){
            for(PoiInfo r:result.getAllPoi()){
                list.add(r.address);
                Log.i(TAG,r.address + ";" + r.name);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuggestionResult(List<SuggestionResult.SuggestionInfo> list) {
        if(list != null && list.size() != 0){
            this.list.clear();
            for(SuggestionResult.SuggestionInfo s : list){
                this.list.add(s.key);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaiduMapPoiSearchUtils.newInstance().onDestroy();
        location.onStop();
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter_item, null);
            }
            TextView txt = (TextView) convertView.findViewById(R.id.txt);
            txt.setText(list.get(position));
            return convertView;
        }
    };
}

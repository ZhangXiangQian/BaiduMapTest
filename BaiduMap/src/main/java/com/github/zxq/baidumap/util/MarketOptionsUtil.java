package com.github.zxq.baidumap.util;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * Created by zhang on 2016/5/26.
 */
public class MarketOptionsUtil {
    /**
     * BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
     * LatLng ll;
     * LatLngBounds.Builder builder = new Builder();
     * for (CloudPoiInfo info : result.poiList) {
     * ll = new LatLng(info.latitude, info.longitude);
     * OverlayOptions oo = new MarkerOptions().icon(bd).position(ll);
     * mBaiduMap.addOverlay(oo);
     * builder.include(ll);
     * }
     * LatLngBounds bounds = builder.build();
     * MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
     * mBaiduMap.animateMapStatus(u);
     * 地图标记指定坐标
     *
     * @param mBaiduMap
     * @param drawableId  图片
     * @param mLatLng   坐标
     */
    public static void MarketOptions(BaiduMap mBaiduMap, int drawableId, LatLng mLatLng) {
        OverlayOptions mOverlayOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(drawableId)).position(mLatLng);
        mBaiduMap.addOverlay(mOverlayOptions);
    }
}

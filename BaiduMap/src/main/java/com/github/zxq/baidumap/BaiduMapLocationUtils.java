package com.github.zxq.baidumap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.github.zxq.baidumap.BaiduMap.BaiduMapLocation;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**'
 * 百度地图 定位
 */
public class BaiduMapLocationUtils implements BDLocationListener{
	private static LocationClient				mLocationClient;		//定位客户端
	private LocationClientOption				mLocationClientOption;	
	
	private Context 							context;
	private String								currentCity = null;		//当前所在城市
	private static BaiduMapLocationUtils		mLocationUtils;
	private BaiduMapLocation                    mBaiduMapLocation;
	public BaiduMapLocationUtils(Context context,BaiduMapLocation location){
		this.mBaiduMapLocation = location;
		init(context);
		mLocationClient.start();
	}
	/**
	 * 初始化数据及定位相关
	 * @param context
	 */
	private void init(Context context){
		this.context = context;
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(this);
		
		mLocationClientOption = new LocationClientOption();
		mLocationClientOption.setOpenGps(true);
		mLocationClientOption.setCoorType("bd09ll");		//返回坐标类型
		mLocationClientOption.setScanSpan(1000 * 1);		//定位间隔时间
		mLocationClientOption.setAddrType("all");		    //是否需要地址信息，默认为false
		mLocationClient.setLocOption(mLocationClientOption);
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		//Receive Location
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());// 单位：公里每小时
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\nheight : ");
			sb.append(location.getAltitude());// 单位：米
			sb.append("\ndirection : ");
			sb.append(location.getDirection());// 单位度
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("gps定位成功");
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			//运营商信息
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
			sb.append("\ndescribe : ");
			sb.append("网络定位成功");
		} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
			sb.append("\ndescribe : ");
			sb.append("离线定位成功，离线定位结果也是有效的");
		} else if (location.getLocType() == BDLocation.TypeServerError) {
			sb.append("\ndescribe : ");
			sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
		} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
			sb.append("\ndescribe : ");
			sb.append("网络不同导致定位失败，请检查网络是否通畅");
		} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
			sb.append("\ndescribe : ");
			sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
		}
		sb.append("\nlocationdescribe : ");
		sb.append(location.getLocationDescribe());// 位置语义化信息
		List<Poi> list = location.getPoiList();// POI数据
		if (list != null) {
			sb.append("\npoilist size = : ");
			sb.append(list.size());
			for (Poi p : list) {
				sb.append("\npoi= : ");
				sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
			}
		}
			mBaiduMapLocation.onLocationResult(location);
	}
	/**
	 * 关闭定位功能
	 */
	public void onStop(){
		mLocationClient.stop();
	}
}

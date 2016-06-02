# BaiduMapTest
####注册Key
  将BaiduMap mouble中的清单文件中的key值替换为自己申请的key值
  ```java
    <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="pUHZ1D5uxs2R8N85G2W5BWZuki0ncYLw" />
  ```
####在Application中初始化SDK：
  ```java
  public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
  ```
####定位
#####开启定位功能
  ```java
    //初始化即可
    BaiduMapLocationUtils location =  new BaiduMapLocationUtils(this, new BaiduMapLocation() {
            @Override
            public void onLocationResult(BDLocation location) {
                //定位搜索结果
            }
        });
  ```
#####关闭定位功能
  ```java
   location.onStop();
  ```
####POI搜索
#####初始化
  ```java
    BaiduMapPoiSearchUtils.newInstance().init(this, new BaiduMapPoiSearch() {
            @Override
            public void onPoiSearchResult(PoiResult result) {
              //热点搜索结果
            }

            @Override
            public void onSuggestionResult(List<SuggestionResult.SuggestionInfo> list) {
              //suggestionSearch 搜索结果
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {
               //指定地点搜索结果详情
            }
        });
  ```
#####调用SuggestSearch
  依据用户输入关键字提供搜索建议
  ```java
  BaiduMapPoiSearchUtils.newInstance().startSuggestSearch(city,"关键字");
  ```
#####关闭PoiSearch
  ```java
  BaiduMapPoiSearchUtils.newInstance().onDestroy();
  ```

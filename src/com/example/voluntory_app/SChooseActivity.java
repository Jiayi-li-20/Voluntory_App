package com.example.voluntory_app;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;


public class SChooseActivity extends ActionBarActivity {
	LocationClient mLocClient;  
    BitmapDescriptor mCurrentMarker;  
  
    MapView mMapView;  
    BaiduMap mBaiduMap;  
    private MapStatusUpdate mUpdate;  
    private TextView tv;  
    private Button loc; 
    private Button find, upload;
    private BitmapDescriptor mIconMaker;
    private RelativeLayout mMarkerInfoLy;
    private String tag;
    SQLiteDatabase db;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_layout);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        tv = (TextView) findViewById(R.id.text_location);
        mMarkerInfoLy = (RelativeLayout) findViewById(R.id.marker_info);
		mBaiduMap.setMyLocationEnabled(true); 
        loc = (Button)findViewById(R.id.location);
        find = (Button)findViewById(R.id.find);
        upload=(Button)findViewById(R.id.cupload);
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        
        mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.loc1);
        
        mLocClient.registerLocationListener(new BDLocationListener() {  
        	  
            @Override  
            public void onReceiveLocation(BDLocation location) {  
                
                if (location == null || mMapView == null)  
                    return;  
                tv.setText(location.getAddrStr());  
                MyLocationData locData = new MyLocationData.Builder()  
                        .accuracy(location.getRadius())  
                        // 此处设置开发者获取到的方向信息，顺时针0-360  
                        .direction(100).latitude(location.getLatitude())  
                        .longitude(location.getLongitude()).build();  
                mBaiduMap.setMyLocationData(locData);  
                LatLng ll = new LatLng(location.getLatitude(), location  
                        .getLongitude());  
                mUpdate = MapStatusUpdateFactory.newLatLngZoom(ll, 14);  
                mBaiduMap.animateMapStatus(mUpdate);  
                //mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);  
                mBaiduMap  
                .setMyLocationConfigeration(new MyLocationConfiguration(  
                        LocationMode.FOLLOWING, true, null));  
            }  
        });  
        mLocClient.start();
        mMapView.removeViewAt(1);
        
        //点击定位自己位置
        loc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				 mLocClient.requestLocation();
				 
	                
			}
		});
        
        LoginActivity main = new LoginActivity();
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
                + "/test.dbs", null); 
        main.db = db;  
        Cursor cursor = db.query("vi", null, null, null, null, null, null);
        while(cursor.moveToNext()){
        	double lat = cursor.getDouble(cursor.getColumnIndex("latitude"));
        	double lon = cursor.getDouble(cursor.getColumnIndex("longitude"));
        	String qq = cursor.getString(cursor.getColumnIndex("qqnum"));
        	String phone = cursor.getString(cursor.getColumnIndex("phone"));
        	VInfo.vinfos.add(new VInfo(lat,lon,R.drawable.tree,qq,phone));
        	}
        
        //点击查找服务站点
        find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				
				addInfosOverlay(VInfo.vinfos);  
	                
			}
		});
      //跳转至上传当前定位界面
        upload.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent in = new Intent(SChooseActivity.this,InfoCollectionActivity.class);
				startActivity(in);
				
			}
        	
        });
        initMapClickEvent();
        initMarkerClickEvent();
    }
    
    private void initMapClickEvent()
	{
		mBaiduMap.setOnMapClickListener(new OnMapClickListener()
		{

			@Override
			public boolean onMapPoiClick(MapPoi arg0)
			{
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0)
			{
				mMarkerInfoLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();

			}
		});
	}
    private void initMarkerClickEvent()
	{
		// 对Marker的点击
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(final Marker marker)
			{
				// 获得marker中的数据
				VInfo vinfo = (VInfo) marker.getExtraInfo().get("vinfo");
				tag="error";

				InfoWindow mInfoWindow;
				// 生成一个TextView用户在地图中显示InfoWindow
				/*TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.location_tips);
				location.setPadding(30, 20, 30, 50);*/
				// 将marker所在的经纬度的信息转化成屏幕上的坐标
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				Log.e(tag, "--!" + p.x + " , " + p.y);  
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				 //为弹出的InfoWindow添加点击事件  
				//BitmapDescriptor btv = BitmapDescriptorFactory.fromView(location);
				mInfoWindow = new InfoWindow(InfoWindowView(vinfo), llInfo,-15);
                /*{  
					  
                    @Override  
                    public void onInfoWindowClick()  
                    {  
                        //隐藏InfoWindow  
                        mBaiduMap.hideInfoWindow();  
                    }  
                });*/
						
				/***********mInfoWindow = new InfoWindow(BitmapDescriptorFactory, location, llInfo, ofw);
                //显示InfoWindow  *************/
                mBaiduMap.showInfoWindow(mInfoWindow);  
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				// 根据商家信息为详细信息布局设置信息
				popupInfo(mMarkerInfoLy, vinfo);
				
				return true;
			}
		});
	}
    
    private class ViewHolder
	{
		ImageView infoImg;
		TextView infoQQnum;
		TextView infoTelephone;
	}
    
    protected void popupInfo(RelativeLayout mMarkerLy, VInfo vinfo)  
    {  
        ViewHolder viewHolder = null;  
        if (mMarkerLy.getTag() == null)  
        {  
            viewHolder = new ViewHolder();  
            viewHolder.infoImg = (ImageView) mMarkerLy  
                    .findViewById(R.id.info_img);
            viewHolder.infoQQnum = (TextView) mMarkerLy  
                    .findViewById(R.id.info_distance);  
            viewHolder.infoTelephone = (TextView) mMarkerLy  
                    .findViewById(R.id.info_zan);  
  
            mMarkerLy.setTag(viewHolder);  
        }  
        viewHolder = (ViewHolder) mMarkerLy.getTag();  
        viewHolder.infoImg.setImageResource(vinfo.getImgId());  
        viewHolder.infoQQnum.setText(vinfo.getQQnum());
        viewHolder.infoTelephone.setText(vinfo.getTelephone());  
    }  
    
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
        mLocClient.stop();  
        // 关闭定位图层  
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
    
    /*private void initMap() {
        //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);
        // 不显示缩放比例尺
        mMapView.showZoomControls(false);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        //百度地图
        mBaiduMap = mMapView.getMap();
        // 改变地图状态
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        
        }*/
    public void addInfosOverlay(List<VInfo> vinfos)  
    {  
        mBaiduMap.clear();  
        LatLng latLng = null;  
        OverlayOptions overlayOptions = null;  
        Marker marker = null;  
        for (VInfo vinfo : vinfos)  
        {  
            // 位置  
            latLng = new LatLng(vinfo.getLatitude(), vinfo.getLongitude());  
            // 图标  
            overlayOptions = new MarkerOptions().position(latLng)  
                    .icon(mIconMaker).zIndex(5);  
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));  
            Bundle bundle = new Bundle();  
            bundle.putSerializable("vinfo", vinfo);  
            marker.setExtraInfo(bundle);  
        }  
        // 将地图移到到最后一个经纬度位置  
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);  
        mBaiduMap.setMapStatus(u);  
    }  
    
    protected View InfoWindowView(final VInfo vinfo){  
        if(vinfo == null) return null;  
        // 生成一个TextView用户在地图中显示InfoWindow  
        TextView textView = new TextView(getApplicationContext());  
        textView.setBackgroundColor(Color.parseColor("#ffcc33"));
        textView.setPadding(30, 20, 30, 50);  
        textView.setText("爱心志愿者"+"\n"+"QQ号:"+vinfo.getQQnum()+"\n"
        		+"座机/手机："+vinfo.getTelephone());  
        textView.setTextColor(Color.parseColor("#000000"));  
        textView.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	Intent intent = new Intent();  
                intent.setAction("android.intent.action.CALL");  
              
                intent.setData(Uri.parse("tel:"+vinfo.getTelephone().toString()));  
                startActivity(intent);
            }  
        });  
        
        textView.setOnLongClickListener(new View.OnLongClickListener(){

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
						"mqqwpa://im/chat?chat_type=wpa&uin="+vinfo.getQQnum()+"&version=1")));  
				return false;
			}
        	
        });
        return textView;  
    }  
}

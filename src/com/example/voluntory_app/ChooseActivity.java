package com.example.voluntory_app;

import java.util.List;

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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ChooseActivity extends ActionBarActivity {
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
                        // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360  
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
        
        //�����λ�Լ�λ��
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
        Cursor cursor = db.query("si", null, null, null, null, null, null);
        while(cursor.moveToNext()){
        	double lat = cursor.getDouble(cursor.getColumnIndex("latitude"));
        	double lon = cursor.getDouble(cursor.getColumnIndex("longitude"));
        	String name = cursor.getString(cursor.getColumnIndex("name"));
        	String qq = cursor.getString(cursor.getColumnIndex("qqnum"));
        	String phone = cursor.getString(cursor.getColumnIndex("phone"));
        	int num = cursor.getColumnIndex("renum");
        	Info.infos.add(new Info(lat,lon,R.drawable.tree,name,qq,phone,num));
        	}
        
        //������ҷ���վ��
        find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				
				addInfosOverlay(Info.infos);  
	                
			}
		});
        //��ת���ϴ���ǰ��λ����
        upload.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent in = new Intent(ChooseActivity.this,VInfoCollectionActivity.class);
				startActivity(in);
				
			}
        	
        });
        initMapClickEvent();
        
     // ��Marker�ĵ��
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(final Marker marker)
			{
				// ���marker�е�����
				Info info = (Info) marker.getExtraInfo().get("info");
				//tag="error";

				InfoWindow mInfoWindow;
				// ����һ��TextView�û��ڵ�ͼ����ʾInfoWindow
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				Log.e(tag, "--!" + p.x + " , " + p.y);  
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				 //Ϊ������InfoWindow��ӵ���¼�  
				mInfoWindow = new InfoWindow(InfoWindowView(info), llInfo,-15);
               
                //��ʾInfoWindow 
				mBaiduMap.showInfoWindow(mInfoWindow); 
				mMarkerInfoLy.setVisibility(View.VISIBLE);
				// �����̼���ϢΪ��ϸ��Ϣ����������Ϣ
				popupInfo(mMarkerInfoLy, info);
				
				return true;
			}
		});
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
   
    
    private class ViewHolder
	{
		ImageView infoImg;
		TextView infoName;
		TextView infoQQ;
		TextView infoDistance;
		TextView infoZan;
	}
    
    protected void popupInfo(RelativeLayout mMarkerLy, Info info)  
    {  
        ViewHolder viewHolder = null;  
        if (mMarkerLy.getTag() == null)  
        {  
            viewHolder = new ViewHolder();  
            viewHolder.infoImg = (ImageView) mMarkerLy  
                    .findViewById(R.id.info_img);  
            viewHolder.infoName = (TextView) mMarkerLy  
                    .findViewById(R.id.info_name);  
            viewHolder.infoQQ = (TextView) mMarkerLy  
                    .findViewById(R.id.info_qq);  
            viewHolder.infoDistance = (TextView) mMarkerLy  
                    .findViewById(R.id.info_distance);  
            viewHolder.infoZan = (TextView) mMarkerLy  
                    .findViewById(R.id.info_zan);  
  
            mMarkerLy.setTag(viewHolder);  
        }  
        viewHolder = (ViewHolder) mMarkerLy.getTag();  
        viewHolder.infoImg.setImageResource(info.getImgId());  
        viewHolder.infoDistance.setText(info.getTelephone());  
        viewHolder.infoQQ.setText(info.getQQ()); 
        viewHolder.infoName.setText(info.getName());  
        viewHolder.infoZan.setText(info.getZan()+"");  
    }  
    
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();  
        mLocClient.stop();  
        // �رն�λͼ��  
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        }  
    
    /*private void initMap() {
        //��ȡ��ͼ�ؼ�����  
        mMapView = (MapView) findViewById(R.id.bmapView);
        // ����ʾ���ű�����
        mMapView.showZoomControls(false);
        // ����ʾ�ٶȵ�ͼLogo
        mMapView.removeViewAt(1);
        //�ٶȵ�ͼ
        mBaiduMap = mMapView.getMap();
        // �ı��ͼ״̬
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        
        }*/
    public void addInfosOverlay(List<Info> infos)  
    {  
        mBaiduMap.clear();  
        LatLng latLng = null;  
        OverlayOptions overlayOptions = null;  
        Marker marker = null;  
        for (Info info : infos)  
        {  
            // λ��  
            latLng = new LatLng(info.getLatitude(), info.getLongitude());  
            // ͼ��  
            overlayOptions = new MarkerOptions().position(latLng)  
                    .icon(mIconMaker).zIndex(5);  
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));  
            Bundle bundle = new Bundle();  
            bundle.putSerializable("info", info);  
            marker.setExtraInfo(bundle);  
        }  
        // ����ͼ�Ƶ������һ������ľ�γ��λ��  
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);  
        mBaiduMap.setMapStatus(u);  
    }  
    
    protected View InfoWindowView(final Info info){  
        if(info == null) return null;  
        // ����һ��TextView�û��ڵ�ͼ����ʾInfoWindow  
        TextView textView = new TextView(getApplicationContext());  
        textView.setBackgroundResource(R.drawable.tree);  
        textView.setPadding(30, 20, 30, 50);  
        textView.setText(info.getName()+"\n"+"QQ��:"+info.getQQ()+"\n"
        		+"����/�ֻ���"+info.getTelephone()+"\n"+"��Ҫ������"+info.getZan());  
        textView.setTextColor(Color.parseColor("#000000"));  
        textView.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	Intent intent1 = new Intent();  
                intent1.setAction("android.intent.action.CALL");  
              
                intent1.setData(Uri.parse("tel:"+info.getTelephone().toString()));  
                startActivity(intent1);
                
            }  
            
        });  
        textView.setOnLongClickListener(new View.OnLongClickListener(){

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
						"mqqwpa://im/chat?chat_type=wpa&uin="+info.getQQ()+"&version=1")));  
				return false;
			}
        	
        });
        return textView;  
    }  
}


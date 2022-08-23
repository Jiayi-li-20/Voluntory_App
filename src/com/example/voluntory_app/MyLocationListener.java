package com.example.voluntory_app;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener{

	private double jing;
	private double wei;
	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		//此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
			
        this.setJing(location.getLatitude());    //获取纬度信息
        this.setWei(location.getLongitude());    //获取经度信息
	}
	public double getJing() {
		return jing;
	}
	public void setJing(double jing) {
		this.jing = jing;
	}
	public double getWei() {
		return wei;
	}
	public void setWei(double wei) {
		this.wei = wei;
	}

}

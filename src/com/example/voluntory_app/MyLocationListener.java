package com.example.voluntory_app;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener{

	private double jing;
	private double wei;
	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		//�˴���BDLocationΪ��λ�����Ϣ�࣬ͨ�����ĸ���get�����ɻ�ȡ��λ��ص�ȫ�����
        //����ֻ�оٲ��ֻ�ȡ��γ����أ����ã��Ľ����Ϣ
        //��������Ϣ��ȡ˵�����������ο���BDLocation���е�˵��
			
        this.setJing(location.getLatitude());    //��ȡγ����Ϣ
        this.setWei(location.getLongitude());    //��ȡ������Ϣ
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

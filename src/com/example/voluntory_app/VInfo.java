package com.example.voluntory_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VInfo implements Serializable{
	 private static final long serialVersionUID = -758459502806858414L;  
	    /** 
	     * ���� 
	     */  
	    private double latitude;  
	    /** 
	     * γ�� 
	     */  
	    private double longitude;  
	    /** 
	     * ͼƬID����ʵ��Ŀ�п�����ͼƬ·�� 
	     */  
	    private int imgId;  
	    /** 
	     * QQ���� 
	     */  
	    private String qqNum;  
	    /** 
	     * ��ϵ��ʽ 
	     */  
	    private String tele;  

	  
	    public static List<VInfo> vinfos = new ArrayList<VInfo>();  
	  
	   /* static  
	    {  
	        vinfos.add(new VInfo(31.317537, 120.748634, R.drawable.tree, "1777777777",  
	                "4777777777"));  
	        
	    }  */
	  
	    public VInfo()  
	    {  
	    }  
	  
	    public VInfo(double latitude, double longitude, int imgId, String qqnum,  
	            String telephone)  
	    {  
	        super();  
	        this.latitude = latitude;  
	        this.longitude = longitude;  
	        this.imgId = imgId;  
	        this.qqNum = qqnum;  
	        this.tele = telephone;  
	    }  
	  
	    public double getLatitude()  
	    {  
	        return latitude;  
	    }  
	  
	    public void setLatitude(double latitude)  
	    {  
	        this.latitude = latitude;  
	    }  
	  
	    public double getLongitude()  
	    {  
	        return longitude;  
	    }  
	  
	    public void setLongitude(double longitude)  
	    {  
	        this.longitude = longitude;  
	    }  
	  
	    public String getQQnum()  
	    {  
	        return qqNum;  
	    }  
	  
	    public int getImgId()  
	    {  
	        return imgId;  
	    }  
	  
	    public void setImgId(int imgId)  
	    {  
	        this.imgId = imgId;  
	    }  
	  
	    public void getQQnum(String qqnum)  
	    {  
	        this.qqNum = qqnum;  
	    }  
	  
	    public String getTelephone()  
	    {  
	        return tele;  
	    }  
	  
	    public void setTelephone(String telephone)  
	    {  
	        this.tele = telephone;  
	    }  
	  
}

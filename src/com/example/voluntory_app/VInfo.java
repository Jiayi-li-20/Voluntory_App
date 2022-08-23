package com.example.voluntory_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VInfo implements Serializable{
	 private static final long serialVersionUID = -758459502806858414L;  
	    /** 
	     * 经度 
	     */  
	    private double latitude;  
	    /** 
	     * 纬度 
	     */  
	    private double longitude;  
	    /** 
	     * 图片ID，真实项目中可能是图片路径 
	     */  
	    private int imgId;  
	    /** 
	     * QQ号码 
	     */  
	    private String qqNum;  
	    /** 
	     * 联系方式 
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

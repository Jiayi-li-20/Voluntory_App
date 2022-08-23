package com.example.voluntory_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable{
	 private static final long serialVersionUID = -758459502806858414L;  
	    /** 
	     * 纬度 
	     */  
	    private double latitude;  
	    /** 
	     * 经度 
	     */  
	    private double longitude;  
	    /** 
	     * 图片ID，真实项目中可能是图片路径 
	     */  
	    private int imgId;  
	    /** 
	     * 商家名称 
	     */  
	    private String name;  
	    /** 
	     * QQ号
	     */  
	    private String qq;
	    /** 
	     * 联系方式 
	     */  
	    private String tele;  
	    /** 
	     * 需要人数 
	     */  
	    private int zan;  
	  
	    public static List<Info> infos = new ArrayList<Info>();  
	  
	   /* static  
	    {  
	        infos.add(new Info(32.117168, 118.949063, R.drawable.tree, "羊山公园服务站","12345",  
	                "4001000000", 5));  
	        infos.add(new Info(32.09741, 118.931169, R.drawable.tree, "仙林养老院","12345",
	                "13333333333", 8));  
	        infos.add(new Info(32.123406, 118.933253, R.drawable.tree, "南邮爱心站点","12345",  
	                "15555555555", 10));  
	        infos.add(new Info(32.10524, 118.936846, R.drawable.tree, "仙林中心志愿服务点","12345",  
	                "4008000000", 20));  
	        
	    }  */
	  
	    public Info()  
	    {  
	    }  
	  
	    public Info(double latitude, double longitude, int imgId, String name, String qq, 
	            String telephone, int zan)  
	    {  
	        super();  
	        this.latitude = latitude;  
	        this.longitude = longitude;  
	        this.imgId = imgId;  
	        this.name = name;  
	        this.qq = qq;
	        this.tele = telephone;  
	        this.zan = zan;  
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
	  
	    public String getName()  
	    {  
	        return name;  
	    }  
	  
	    public int getImgId()  
	    {  
	        return imgId;  
	    }  
	  
	    public void setImgId(int imgId)  
	    {  
	        this.imgId = imgId;  
	    }  
	  
	    public void setName(String name)  
	    {  
	        this.name = name;  
	    }  
	  
	    public String getTelephone()  
	    {  
	        return tele;  
	    }  
	  
	    public void setTelephone(String telephone)  
	    {  
	        this.tele = telephone;  
	    }  
	    
	    public String getQQ()  
	    {  
	        return qq;  
	    }  
	  
	    public void setQQ(String qq)  
	    {  
	        this.qq = qq;  
	    }  
	  
	    public int getZan()  
	    {  
	        return zan;  
	    }  
	  
	    public void setZan(int zan)  
	    {  
	        this.zan = zan;  
	    }  
}

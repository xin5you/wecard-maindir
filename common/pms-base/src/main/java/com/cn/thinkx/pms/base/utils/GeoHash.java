package com.cn.thinkx.pms.base.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

  
public class GeoHash {
	private LocationBean location;
	
    private static final  double EARTH_RADIUS = 6378140;//赤道半径(单位m)
    
	/**
	 * 1 2500km;2 630km;3 78km;4 30km
	 * 5 2.4km; 6 610m; 7 76m; 8 19m
	 */
	private int hashLength =5; //经纬度转化为geohash长度
	private int latLength = 20; //纬度转化为二进制长度
	private int lngLength = 20; //经度转化为二进制长度
	
	private double minLat;//每格纬度的单位大小
	private double minLng;//每个经度的倒下
	private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', 
				'8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public GeoHash(double lat, double lng) {
		location = new LocationBean(lat, lng);
		setMinLatLng();
	}
	
	public int gethashLength() {
		return hashLength;
	}
	
	/**
	 * @Author:lulei  
	 * @Description: 设置经纬度的最小单位
	 */
	private void setMinLatLng() {
		minLat = LocationBean.MAXLAT - LocationBean.MINLAT;
		for (int i = 0; i < latLength; i++) {
			minLat /= 2.0;
		}
		minLng = LocationBean.MAXLNG - LocationBean.MINLNG;
		for (int i = 0; i < lngLength; i++) {
			minLng /= 2.0;
		}
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 求所在坐标点及周围点组成的九个
	 */
	public List<String> getGeoHashBase32For9() {
		double leftLat = location.getLat() - minLat;
		double rightLat = location.getLat() + minLat;
		double upLng = location.getLng() - minLng;
		double downLng = location.getLng() + minLng;
		List<String> base32For9 = new ArrayList<String>();
		//左侧从上到下 3个
		String leftUp = getGeoHashBase32(leftLat, upLng);
		if (!(leftUp == null || "".equals(leftUp))) {
			base32For9.add(leftUp);
		}
		String leftMid = getGeoHashBase32(leftLat, location.getLng());
		if (!(leftMid == null || "".equals(leftMid))) {
			base32For9.add(leftMid);
		}
		String leftDown = getGeoHashBase32(leftLat, downLng);
		if (!(leftDown == null || "".equals(leftDown))) {
			base32For9.add(leftDown);
		}
		//中间从上到下 3个
		String midUp = getGeoHashBase32(location.getLat(), upLng);
		if (!(midUp == null || "".equals(midUp))) {
			base32For9.add(midUp);
		}
		String midMid = getGeoHashBase32(location.getLat(), location.getLng());
		if (!(midMid == null || "".equals(midMid))) {
			base32For9.add(midMid);
		}
		String midDown = getGeoHashBase32(location.getLat(), downLng);
		if (!(midDown == null || "".equals(midDown))) {
			base32For9.add(midDown);
		}
		//右侧从上到下 3个
		String rightUp = getGeoHashBase32(rightLat, upLng);
		if (!(rightUp == null || "".equals(rightUp))) {
			base32For9.add(rightUp);
		}
		String rightMid = getGeoHashBase32(rightLat, location.getLng());
		if (!(rightMid == null || "".equals(rightMid))) {
			base32For9.add(rightMid);
		}
		String rightDown = getGeoHashBase32(rightLat, downLng);
		if (!(rightDown == null || "".equals(rightDown))) {
			base32For9.add(rightDown);
		}
		return base32For9;
	}

	/**
	 * @param length
	 * @return
	 * @Author:lulei  
	 * @Description: 设置经纬度转化为geohash长度
	 */
	public boolean sethashLength(int length) {
		if (length < 1) {
			return false;
		}
		hashLength = length;
		latLength = (length * 5) / 2;
		if (length % 2 == 0) {
			lngLength = latLength;
		} else {
			lngLength = latLength + 1;
		}
		setMinLatLng();
		return true;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取经纬度的base32字符串
	 */
	public String getGeoHashBase32() {
		return getGeoHashBase32(location.getLat(), location.getLng());
	}
	
	/**
	 * @param lat
	 * @param lng
	 * @return
	 * @Author:lulei  
	 * @Description: 获取经纬度的base32字符串
	 */
	private String getGeoHashBase32(double lat, double lng) {
		boolean[] bools = getGeoBinary(lat, lng);
		if (bools == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bools.length; i = i + 5) {
			boolean[] base32 = new boolean[5];
			for (int j = 0; j < 5; j++) {
				base32[j] = bools[i + j];
			}
			char cha = getBase32Char(base32);
			if (' ' == cha) {
				return null;
			}
			sb.append(cha);
		}
		return sb.toString();
	}
	
	/**
	 * @param base32
	 * @return
	 * @Author:lulei  
	 * @Description: 将五位二进制转化为base32
	 */
	private char getBase32Char(boolean[] base32) {
		if (base32 == null || base32.length != 5) {
			return ' ';
		}
		int num = 0;
		for (boolean bool : base32) {
			num <<= 1;
			if (bool) {
				num += 1;
			}
		}
		return CHARS[num % CHARS.length];
	}
	
	/**
	 * @param lat
	 * @param lng
	 * @return
	 * @Author:lulei  
	 * @Description: 获取坐标的geo二进制字符串
	 */
	private boolean[] getGeoBinary(double lat, double lng) {
		boolean[] latArray = getHashArray(lat, LocationBean.MINLAT, LocationBean.MAXLAT, latLength);
		boolean[] lngArray = getHashArray(lng, LocationBean.MINLNG, LocationBean.MAXLNG, lngLength);
		return merge(latArray, lngArray);
	}
	
	/**
	 * @param latArray
	 * @param lngArray
	 * @return
	 * @Author:lulei  
	 * @Description: 合并经纬度二进制
	 */
	private boolean[] merge(boolean[] latArray, boolean[] lngArray) {
		if (latArray == null || lngArray == null) {
			return null;
		}
		boolean[] result = new boolean[lngArray.length + latArray.length];
		Arrays.fill(result, false);
		for (int i = 0; i < lngArray.length; i++) {
			result[2 * i] = lngArray[i];
		}
		for (int i = 0; i < latArray.length; i++) {
			result[2 * i + 1] = latArray[i];
		}
		return result;
	}
	
	/**
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 * @Author:lulei  
	 * @Description: 将数字转化为geohash二进制字符串
	 */
	private boolean[] getHashArray(double value, double min, double max, int length) {
		if (value < min || value > max) {
			return null;
		}
		if (length < 1) {
			return null;
		}
		boolean[] result = new boolean[length];
		for (int i = 0; i < length; i++) {
			double mid = (min + max) / 2.0;
			if (value > mid) {
				result[i] = true;
				min = mid;
			} else {
				result[i] = false;
				max = mid;
			}
		}
		return result;
	}
	
	  /** 
     * 转化为弧度(rad) 
     * */  
    private static double getRadian(double d){  
       return d * Math.PI / 180.0;  
    }  
    /** 
     * 根据经纬度计算两点之间的距离 
     * @param lat1 
     *            1点的纬度 
     * @param lng1 
     *            1点的经度 
     * @param lat2 
     *            2点的纬度 
     * @param lng2 
     *            2点的经度 
     * @return 距离 单位 米 
     * */  
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2){  
    	double radLat1 = getRadian(lat1);  
        double radLat2 = getRadian(lat2);  
        double a = radLat1 - radLat2;// 两点纬度差  
        double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)  
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 10000) / 10000;  
       return s;
    }  

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
	    double lat1=31.1960217702;
        double lon1=121.4194623615;    
       
        GeoHash g1 = new GeoHash(lat1, lon1);
        
        System.out.println("位置编码：" + g1.getGeoHashBase32());  

	}
}
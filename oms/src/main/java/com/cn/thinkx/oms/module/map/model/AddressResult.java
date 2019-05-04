package com.cn.thinkx.oms.module.map.model;

public class AddressResult {
	
	private String title;
	private LocationVo location;
	private AddressComponentsVo address_components; //解析后地址组件
	private double similarity; //查询字符串与查询结果的文本相似度
	private int deviation;  //误差距离，单位：米， 该值取决于输入地址的精确度；
    private int reliability; //可信度参考：值范围 1 <低可信> - 10 <高可信>
    
	public String getTitle() {
		return title;
	}
	public LocationVo getLocation() {
		return location;
	}
	public AddressComponentsVo getAddress_components() {
		return address_components;
	}
	public double getSimilarity() {
		return similarity;
	}
	public int getDeviation() {
		return deviation;
	}
	public int getReliability() {
		return reliability;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLocation(LocationVo location) {
		this.location = location;
	}
	public void setAddress_components(AddressComponentsVo address_components) {
		this.address_components = address_components;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}
	public void setReliability(int reliability) {
		this.reliability = reliability;
	}
}

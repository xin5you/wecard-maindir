package com.cn.thinkx.common.wecard.domain.phoneRecharge;

import com.cn.thinkx.pms.base.domain.BaseDomain;

public class PhoneRechargeShop extends BaseDomain {
	private String id;
	private String supplier;
	private String oper;
	private String shopNo;
	private String shopFace;
	private String shopPrice;
	private String shopType;
	private String isUsable;
	private String startValidity;
	private String endValidity;
	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String resv6;
    private String dataStat;
    
    private String code;	//返回码
    private String msg;		//返回信息
    
    private String province;	//地区 - 省
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopFace() {
		return shopFace;
	}
	public void setShopFace(String shopFace) {
		this.shopFace = shopFace;
	}
	public String getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(String shopPrice) {
		this.shopPrice = shopPrice;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getIsUsable() {
		return isUsable;
	}
	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}
	public String getStartValidity() {
		return startValidity;
	}
	public void setStartValidity(String startValidity) {
		this.startValidity = startValidity;
	}
	public String getEndValidity() {
		return endValidity;
	}
	public void setEndValidity(String endValidity) {
		this.endValidity = endValidity;
	}
	public String getResv1() {
		return resv1;
	}
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	public String getResv2() {
		return resv2;
	}
	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}
	public String getResv3() {
		return resv3;
	}
	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}
	public String getResv4() {
		return resv4;
	}
	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}
	public String getResv5() {
		return resv5;
	}
	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}
	public String getResv6() {
		return resv6;
	}
	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@Override
	public String toString() {
		return "PhoneRechargeShop [id=" + id + ", supplier=" + supplier + ", oper=" + oper + ", shopNo=" + shopNo
				+ ", shopFace=" + shopFace + ", shopPrice=" + shopPrice + ", shopType=" + shopType + ", isUsable="
				+ isUsable + ", startValidity=" + startValidity + ", endValidity=" + endValidity + ", resv1=" + resv1
				+ ", resv2=" + resv2 + ", resv3=" + resv3 + ", resv4=" + resv4 + ", resv5=" + resv5 + ", resv6=" + resv6
				+ ", dataStat=" + dataStat + ", code=" + code + ", msg=" + msg + ", province=" + province + "]";
	}
}

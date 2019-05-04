package com.cn.thinkx.wecard.api.module.pay.resp;

import com.cn.thinkx.wecard.api.module.pay.vo.BoxBaseModel;

public class PPScanCodeResp extends BoxBaseModel {
	
	private String code="";
	private String msg="";				//描述
	private String sub_code="";		// String 否 错误代码 错误代码，请见错误列表
	private String sub_msg="";			// String 否 错误码描述 错误代码描述
	private String pp_trade_no="";		// String 是 121775250120140703323336801 派派订单号
	private String transaction_id="";	// String 是 121775250120140703323336801 支付订单号
	private String total_fee="";		// int 是 433 订单金额，单位为分
	private String time_end="";		// String 否 20141030133525 交易完成时间，格式为
	private String pay_type="";		// String 是 WXPAY 支付方式[WXPAY|ALIPAY]
	private String print="";			// String 否 打印文本
	private String print_qr="";
	private String printType="";		 //0： receipt 无效 1：在默认小票后追加 receipt 内容 2： receipt 内容覆盖默认小票内容
	private String receipt="";
	public String getCode() {
		return code;
	}
	public String getSub_code() {
		return sub_code;
	}
	public String getSub_msg() {
		return sub_msg;
	}
	public String getPp_trade_no() {
		return pp_trade_no;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public String getTime_end() {
		return time_end;
	}

	public String getPay_type() {
		return pay_type;
	}
	public String getPrint() {
		return print;
	}
	public String getPrint_qr() {
		return print_qr;
	}

	public String getReceipt() {
		return receipt;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}
	public void setSub_msg(String sub_msg) {
		this.sub_msg = sub_msg;
	}
	public void setPp_trade_no(String pp_trade_no) {
		this.pp_trade_no = pp_trade_no;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public void setPrint_qr(String print_qr) {
		this.print_qr = print_qr;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
}

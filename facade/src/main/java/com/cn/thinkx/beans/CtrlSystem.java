package com.cn.thinkx.beans;

import java.io.Serializable;

/**
 * 日切控制类
 * 
 * @author pucker
 *
 */
public class CtrlSystem extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 结算日期
	 */
	private String settleDate;
	/**
	 * 上一清算日期
	 */
	private String preSettleDate;
	/**
	 * 交易允许状态
	 */
	private String transFlag;
	/**
	 * 当前流水表
	 */
	private String curLogNum;
	/**
	 * 上一流水表
	 */
	private String preLogNum;
	/**
	 * 批处理状态
	 */
	private String batchStat;
	/**
	 * 清算状态
	 */
	private String settleStat;

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getPreSettleDate() {
		return preSettleDate;
	}

	public void setPreSettleDate(String preSettleDate) {
		this.preSettleDate = preSettleDate;
	}

	public String getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	public String getCurLogNum() {
		return curLogNum;
	}

	public void setCurLogNum(String curLogNum) {
		this.curLogNum = curLogNum;
	}

	public String getPreLogNum() {
		return preLogNum;
	}

	public void setPreLogNum(String preLogNum) {
		this.preLogNum = preLogNum;
	}

	public String getBatchStat() {
		return batchStat;
	}

	public void setBatchStat(String batchStat) {
		this.batchStat = batchStat;
	}

	public String getSettleStat() {
		return settleStat;
	}

	public void setSettleStat(String settleStat) {
		this.settleStat = settleStat;
	}

}

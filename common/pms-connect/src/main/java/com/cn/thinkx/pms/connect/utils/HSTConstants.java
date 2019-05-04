package com.cn.thinkx.pms.connect.utils;


public class HSTConstants {
	
	//交易系统 
	public final static String SYS_TXN = "TXN";
	//账户系统
	public final static String SYS_ACC = "ACC";
	//结算系统
	public final static String SYS_STL = "STL";
	

	//########################  ACC  #################################
	/**
	 * 充值、充值激活、赎回
	 */
	public final static String RECHARGE_TXN = "vRechargeOrder";
	
	/**
	 * 心跳
	 */
	public final static String ACC_HEART_BEAT = "accCon";
	
	/**
	 * 卡片批量查询
	 */
	public final static String CARD_BATCH_QUERY = "vCardBatInq";
	
	/**
	 * 制卡通知
	 */
	public final static String MAKE_CARD = "vMakecard";
	
	/**
	 * 制卡查询
	 */
	public final static String CARD_QUERY = "vCardQry";
	
	/**
	 * 充值+激活
	 */
	public final static String RECHARGE = "vRecharge";
	
	/**
	 * 充值
	 */
	public final static String RECHARGE_NOACT = "vRechrgOrder";
	
	/**
	 * 激活
	 */
	public final static String ACTIVE = "vAct";	
	
	/**
	 * switch
	 */
	public final static String SWITCH = "Switch";
	
	/**
	 * 
	 */
	public final static String  CARDHOLDER_BAT_INQ ="vCardHolderBatInq";
	
	//#####################  TXN  ##############################
	/**
	 * 心跳
	 */
	public final static String TXN_HEART_BEAT = "txnCon";
	
	/**
	 * vTxn
	 */
	public final static String VTXN = "vTxn";
	
	/**
	 * 卡交易查询
	 */
	public final static String CARD_TXN_QUERY = "vCardTxnInq";
	
	/**
	 * 商户交易查询
	 */
	public final static String MCHNT_TXN_QUERY = "vMchntTxnInq";
	
	/**
	 * 重新加载卡路由信息
	 */
	public final static String RELOAD_SHM_PROC = "vReloadShmProc";
	
	//##################  STL  ###########################
	/**
	 * 心跳
	 */
	public final static String STL_HEART_BEAT = "stlCon";
	
	/**
	 * vBillProc
	 */
	public final static String BILL_PROC = "vBillProc";
	
	
	
	//交易系统服务名称
	public final static String[] SVR_TXN = {"vChgKeyServiceTxn","vRechargeOrder",
											"vTxn","vCardTxnInq","vMchntTxnInq","vReloadShmProc","txnCon"};
	
	//账户系统服务名称
	public final static String[] SVR_ACC = {"vCardBatInq","vCardPinCheck",
											"vChgKeyServiceAcc","vMakecard","vCardQry","vRecharge","vRechrgOrder","vAct","Switch","vCardHolderBatInq","accCon"};
	
	//结算系统服务名称
	public final static String[] SVR_STL = {"vBillProc","stlCon"};
	
	/**
	 * 根据服务名称查找目标系统，如果未找到，则返回NULL
	 * @param svrNm 服务名称
	 * @return
	 */
	public static String  getSysBySvr(String svrNm){
		for(int i=0; i<SVR_TXN.length; i++)
			if(SVR_TXN[i].equals(svrNm))
				return SYS_TXN;
		
		for(int i=0; i<SVR_ACC.length; i++)
			if(SVR_ACC[i].equals(svrNm))
				return SYS_ACC;
		
		for(int i=0; i<SVR_STL.length; i++)
			if(SVR_STL[i].equals(svrNm))
				return SYS_STL;
		
		return null;
	}
	
	/**
	 * 心跳标识 
	 * 1:发送心跳
	 * 0:不发送心跳
	 */
	public final static String HEARTBEAT_IN_Y = "1";
	public final static String HEARTBEAT_IN_N = "0";
	
	/**
	 * 链路状态
	 * 0:已关闭
	 * 1:建立中
	 * 2:已链接
	 */
	public final static String CONNECTION_STATUS_CLOSED ="0";
	public final static String CONNECTION_STATUS_CREATING ="1";
	public final static String CONNECTION_STATUS_ESTABLISHED ="2";
	
	/**
	 * 链路名称
	 */
	public final static String CONNECTION_TXN_1 = "CONNECTION_TXN_1";
	public final static String CONNECTION_TXN_2 = "CONNECTION_TXN_2";
	public final static String CONNECTION_ACC_1 = "CONNECTION_ACC_1";
	public final static String CONNECTION_ACC_2 = "CONNECTION_ACC_2";
	public final static String CONNECTION_STL_1 = "CONNECTION_STL_1";
	public final static String CONNECTION_STL_2 = "CONNECTION_STL_2";	
	
	/**
	 * CONNECTION_NM:链路名称
	 * REMOTE_IP:远端IP
	 * REMOTE_PORT:远端端口
	 */
	public final static String CONNECTION_NM = "CONNECTION_NM";
	public final static String REMOTE_IP = "REMOTE_IP";
	public final static String REMOTE_PORT = "REMOTE_PORT";
	public final static String LINK_UUID = "LINK_UUID";
	
	/**
	 * 搭建链路的最长允许时间（微秒）
	 */
	public final static long CONNECTION_CREATING_MAX_MILLIS = 9000;
}

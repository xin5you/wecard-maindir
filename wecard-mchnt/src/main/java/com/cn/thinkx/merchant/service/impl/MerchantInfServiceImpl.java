package com.cn.thinkx.merchant.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.Constants.ChannelCode;
import com.cn.thinkx.core.util.Constants.TransCode;
import com.cn.thinkx.merchant.domain.MerchantCashManager;
import com.cn.thinkx.merchant.domain.MerchantInf;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.domain.ShopInf;
import com.cn.thinkx.merchant.mapper.MerchantInfDao;
import com.cn.thinkx.merchant.mapper.MerchantManagerDao;
import com.cn.thinkx.merchant.service.MerchantInfListService;
import com.cn.thinkx.merchant.service.MerchantInfService;
import com.cn.thinkx.merchant.service.ShopInfService;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wxclient.domain.WxFansRole;
import com.cn.thinkx.wxclient.domain.WxRole;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.service.CtrlSystemService;
import com.cn.thinkx.wxclient.service.WxFansRoleService;
import com.cn.thinkx.wxclient.service.WxRoleService;
import com.cn.thinkx.wxclient.service.WxTransLogService;
import com.cn.thinkx.wxcms.service.AccountFansService;

@Service("merchantInfService")
public class MerchantInfServiceImpl implements MerchantInfService {
	private Logger logger = LoggerFactory.getLogger(MerchantInfServiceImpl.class);

	@Autowired
	@Qualifier("wxFansRoleService")
	private WxFansRoleService wxFansRoleService;
	
	
	@Autowired
	@Qualifier("wxRoleService")
	private WxRoleService wxRoleService;
	
	@Autowired
	private AccountFansService accountFansService;
	
	@Autowired
	@Qualifier("merchantInfDao")
	private MerchantInfDao merchantInfDao;
	
	@Autowired
	@Qualifier("merchantManagerDao")
	private MerchantManagerDao merchantManagerDao;
	
	@Autowired
	@Qualifier("merchantInfListService")
	private MerchantInfListService merchantInfListService;
	
	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;
	
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Autowired
	private Java2TxnBusinessFacade java2TxnBusinessFacade;
	
	@Autowired
	private ShopInfService shopInfService;
	
	/**
	 * 查找商户信息
	 */
	public MerchantInf getMerchantInfById(String mId){
		return merchantInfDao.getMerchantInfById(mId);
	}
	
	/**
	 * 查找商户信息
	 * @param mchntCode 商户号
	 * @return
	 */
	public MerchantInf getMerchantInfByCode(String mchntCode){
		
		return merchantInfDao.getMerchantInfByCode(mchntCode);
	}
	
	/**
	 * 查找商户提现管理信息
	 * @param mchntId
	 * @return
	 */
	public MerchantCashManager getMerchantCashManagerByMchntId(String mchntId){
		return merchantInfDao.getMerchantCashManagerByMchntId(mchntId);
	}

	/**
	 * 商户代表注册
	 * @throws Exception 
	 */
	public int addMerchantByBossReg(AccountFans accountFans,MerchantInf merchantInf) throws Exception {
		int optionNum=0;
		if(accountFans !=null){
			accountFans.setFansStatus(Constants.FansStatusEnum.Fans_STATUS_10.getCode());
			accountFans.setGroupid("100");
			accountFansService.updateAccountFansByMcht(accountFans); //更改粉丝菜单操作权限
			
			
			/***查找商户下所有门店***/
			ShopInf shopInf=new ShopInf();
			shopInf.setMchntId(merchantInf.getMchntId());
			List<ShopInf> shopInfList=shopInfService.findShopInfList(shopInf);
			
			/**注册为商户管理员**/
			MerchantManager merchantManager=merchantManagerDao.getMerchantInsInfByOpenId(accountFans.getOpenId());
			if(merchantManager !=null){
				return 0;
			}else{
				merchantManager=new MerchantManager();
			}
			merchantManager.setMangerName(accountFans.getOpenId());
			merchantManager.setMchntId(merchantInf.getMchntId());
			merchantManager.setRoleType(Constants.RoleNameEnum.BOSS_ROLE_MCHANT.getRoleType());
			merchantManager.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode());
			merchantManager.setPhoneNumber(merchantInf.getPhoneNumber());
			if(shopInfList !=null && shopInfList.size()>0){
				merchantManager.setShopId(shopInfList.get(0).getShopId());
			}
			merchantManagerDao.insertMerchantManager(merchantManager);
			
			/**添加权限**/
			WxRole wxRole=wxRoleService.findWxRoleByRoleType(Constants.RoleNameEnum.BOSS_ROLE_MCHANT.getRoleType()); //根据角色类型查找
			WxFansRole wxFansRole=new WxFansRole();
			wxFansRole.setFansId(accountFans.getId());
			wxFansRole.setRoleId(wxRole.getRoleId());
			wxFansRole.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode()); //启用标记
			wxFansRoleService.insertWxFansRole(wxFansRole);
			
			/**设置邀请码不可用***/
			optionNum=merchantInfListService.updateMerchantInfListDateStat(merchantInf.getInsId());
			
			/**商户开户**/
			TxnResp resp = new TxnResp();
			WxTransLog log=null;
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			if (cs != null) {
				if (Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
						String insCode=merchantInfDao.getInsCodeByInsId(merchantInf.getInsId());
					    log= new WxTransLog();
						String id = wxTransLogService.getPrimaryKey();
						log.setWxPrimaryKey(id);
						log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
						log.setSettleDate(cs.getSettleDate());// 交易日期
						log.setTransId(TransCode.MB80.getCode());// 交易类型 开户
						log.setTransSt(0);// 插入时为0，报文返回时更新为1
						log.setInsCode(insCode);// 商户所属的机构code
						log.setMchntCode(merchantInf.getMchntCode());
						
						int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
						if (i != 1) {
							resp.setCode(Constants.TXN_TRANS_EXCEPTION);
							logger.info("商户开户----->insertIntfaceTransLog微信端插入流水记录数量不为1");
							throw new Exception();
						}
						TxnPackageBean txnBean = new TxnPackageBean();
						Date currDate=DateUtil.getCurrDate();
						txnBean.setSwtFlowNo(log.getWxPrimaryKey()); //接口平台交易流水号
						txnBean.setTxnType(log.getTransId()+"0");// 0: 同步 1:异步
						txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_YYYYMMDD));
						txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_HHMMSS));
						txnBean.setSwtSettleDate(log.getSettleDate());
						txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
						txnBean.setIssChannel(log.getInsCode()); //机构渠道号
						txnBean.setInnerMerchantNo(log.getMchntCode()); //商户号
		
						// 远程调用消费接口
						String json = java2TxnBusinessFacade.merchantAccountOpeningITF(txnBean); //商户开户接口
						
						resp = JSONArray.parseObject(json, TxnResp.class);
						log.setTransSt(1);// 插入时为0，报文返回时更新为1
						log.setRespCode(resp.getCode());
						wxTransLogService.updateWxTransLog(log,resp); 
						if (!Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
							logger.info("商户开户----->"+resp.getInfo());
							throw new Exception();
						}
				} else {
					resp.setCode(Constants.TXN_TRANS_EXCEPTION);
					logger.info("商户开户----->日切信息交易允许状态：日切中");
					throw new Exception();
				}
		  }
		 if("00".equals(resp.getCode())){
			 optionNum=1;
		 }else{
			 throw new Exception();
		 }
		 return optionNum;
		 
		}else{
			return 0;
		}

	}
	
	
	/**
	 * 获取商户code 机构code
	 * @param mchntId
	 * @return
	 */
	public MerchantInf getMchntAndInsInfBymchntId(String mchntId){
		return merchantInfDao.getMchntAndInsInfBymchntId(mchntId);
	}
}

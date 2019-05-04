package com.cn.thinkx.wxclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.MessageUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wxclient.domain.MerchantShareInf;
import com.cn.thinkx.wxclient.mapper.MerchantShareInfDao;
import com.cn.thinkx.wxclient.service.MerchantShareInfService;

@Service("merchantShareInfService")
public class MerchantShareInfServiceImpl implements MerchantShareInfService {

	
	@Autowired
	@Qualifier("merchantShareInfDao")
	private  MerchantShareInfDao merchantShareInfDao;

	@Override
	public int insertMerchantShareInf(MerchantShareInf entity) {
		return merchantShareInfDao.insertMerchantShareInf(entity);
	}
	
	/**
	 * update 商户修改操作项
	 * @return
	 */
	public int updateMerchantShareInf(MerchantShareInf entity){
		return merchantShareInfDao.updateMerchantShareInf(entity);
	}
	
	@Override
	public int updateMerchantShareInfDataStat(String shareId) {
		return merchantShareInfDao.updateShareInfDateStat(shareId);
	}

	@Override
	public MerchantShareInf getMerchantShareInfById(String shareId) {
		return merchantShareInfDao.getMerchantShareInfById(shareId);
	}
	
	/**
	 * 分享操作
	 * @param entity
	 * @param flag  9：新增  ,0:分享成功，1：分享失败 ,8:失败
	 * @return
	 * @throws Exception 
	 */
	public ResultHtml editMerchantShareInf(MerchantShareInf entity,String flag) throws Exception{
		
		ResultHtml  result=new ResultHtml();
		MerchantShareInf m;
		
		entity.setDataStat(Constants.DataStatEnum.FALSE_STATUS.getCode());//先默认设置当前不可用
		int operNum=0; //定义操作影响数
		
		if("9".equals(flag)){
			
			if(!"".equals(StringUtil.nullToString(entity.getShareId()))){  //处理页面没刷新页面重复提交的的问题
				m=this.getMerchantShareInfById(entity.getShareId());
				if(m==null){
					operNum=insertMerchantShareInf(entity);
				}else{
					operNum=updateMerchantShareInf(entity);
				}
			}else{
				operNum=insertMerchantShareInf(entity);
			}
			if(operNum>0){
				result.setStatus(true);
				result.setCode(entity.getShareId()); //返回当前操作id
				return result;
			}else{
				result.setStatus(false);
				result.setMsg(MessageUtil.ERROR_MSSAGE);
				return result;
			}
		}else {
	
			m=this.getMerchantShareInfById(entity.getShareId());
			
			if(m !=null && Constants.DataStatEnum.FALSE_STATUS.getCode().equals(m.getDataStat())){ //判断当前链接是否已经生效
				if("0".equals(flag)){  //当前操作是分享成功
					m.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode());
					this.updateMerchantShareInf(m); //设置为可用状态
				}else if("1".equals(flag)){ //取消分享逻辑
					
				}else if("0".equals(flag)){ //分享失败逻辑
					
				}
			}else{
				result.setStatus(false);
				result.setMsg(MessageUtil.ERROR_MSSAGE);
				return result;
			}
		}
		
		return result;
		
	}

	
}

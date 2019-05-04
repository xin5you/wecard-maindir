package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.MerchantManagerTmpMapper;
import com.cn.thinkx.oms.module.merchant.model.MerchantManagerTmp;
import com.cn.thinkx.oms.module.merchant.service.MerchantManagerTmpService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;




@Service("merchantManagerTmpService")
public class MerchantManagerTmpServiceImpl implements MerchantManagerTmpService {
	
	@Autowired
	@Qualifier("merchantManagerTmpMapper")
	private MerchantManagerTmpMapper merchantManagerTmpMapper;

	@Override
	public MerchantManagerTmp getMerchantManagerTmpById(String mId) {
		
		return merchantManagerTmpMapper.getMerchantManagerTmpById(mId);
	}

	@Override
	public int insertMerchantManagerTmp(MerchantManagerTmp entity) {
		entity.setDataStat("0");
		return merchantManagerTmpMapper.insertMerchantManagerTmp(entity);
	}

	@Override
	public int updateMerchantManagerTmp(MerchantManagerTmp entity) {
		
		return merchantManagerTmpMapper.updateMerchantManagerTmp(entity);
	}

	@Override
	public int deleteMerchantManagerTmp(String entityId) {
		
		return merchantManagerTmpMapper.deleteMerchantManagerTmp(entityId);
	}

	@Override
	public List<MerchantManagerTmp> getMerchantManagerTmpList(MerchantManagerTmp entity) {
		List<MerchantManagerTmp> list = merchantManagerTmpMapper.getMerchantManagerTmpList(entity);
		if (list != null && list.size() > 0) {
			String roleTyle = null;
			String[] arry = null;
			for (MerchantManagerTmp tmp : list) {
				roleTyle = tmp.getRoleType();
				if (!StringUtil.isNullOrEmpty(roleTyle)) {
					arry = roleTyle.split(",");
					if (arry != null && arry.length > 0) {
						String roleName = "";
						for (int i=0; i<arry.length; i++) {
							roleName += BaseConstants.RoleNameEnum.findNameByCode(arry[i]) + ",";
						}
						roleName = roleName.substring(0, roleName.length() - 1);
						tmp.setRoleName(roleName);
					}
				}
			}
		}
		return list;
	}
	
	public MerchantManagerTmp getMchntManagerTmpByPhoneNumber(String phoneNumber){
		return merchantManagerTmpMapper.getMchntManagerTmpByPhoneNumber(phoneNumber);
	}
	/**
	 * 员工列表(临时表)
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
    public PageInfo<MerchantManagerTmp> getMerchantManagerTmpPage(int startNum, int pageSize, MerchantManagerTmp entity){
    	PageHelper.startPage(startNum, pageSize);
		List<MerchantManagerTmp> list = getMerchantManagerTmpList(entity);
		PageInfo<MerchantManagerTmp> page = new PageInfo<MerchantManagerTmp>(list);
		return page;
    }
}

package com.cn.thinkx.oms.module.withdrawBlacklist.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.customer.mapper.PersonInfMapper;
import com.cn.thinkx.oms.module.customer.model.PersonInf;
import com.cn.thinkx.oms.module.sys.mapper.UserMapper;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.withdrawBlacklist.mapper.BatchOpenWBMapper;
import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;
import com.cn.thinkx.oms.module.withdrawBlacklist.service.BatchOpenWBService;
import com.cn.thinkx.oms.module.withdrawBlacklist.util.PageWithdrawBlacklistUtil;
import com.cn.thinkx.oms.module.withdrawBlacklist.util.WBConstants;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("batchOpenWBService")
public class BatchOpenWBServiceImpl implements BatchOpenWBService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BatchOpenWBMapper batchOpenWBMapper;

	@Autowired
	private PersonInfMapper personInfMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public PageInfo<WithdrawBlacklist> getWithdrawBlacklistPage(int startNum, int pageSize, WithdrawBlacklist entity) {
		PageHelper.startPage(startNum, pageSize);
		List<WithdrawBlacklist> wbList = getWithdrawBlacklist(entity);
		for (WithdrawBlacklist withdrawBlacklist : wbList) {
			User user1 = userMapper.getUserById(withdrawBlacklist.getCreateUser());
			withdrawBlacklist.setCreateUser(user1.getLoginname());
			User user2 = userMapper.getUserById(withdrawBlacklist.getUpdateUser());
			withdrawBlacklist.setUpdateUser(user2.getLoginname());
		}
		PageInfo<WithdrawBlacklist> wbPage = new PageInfo<WithdrawBlacklist>(wbList);
		return wbPage;
	}

	@Override
	public List<WithdrawBlacklist> getWithdrawBlacklist(WithdrawBlacklist entity) {
		return batchOpenWBMapper.getWithdrawBlacklist(entity);
	}

	@Override
	public ModelMap insertWithdrawBlacklist(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		int a = 0;
		LinkedList<WithdrawBlacklist> wbInfList = PageWithdrawBlacklistUtil
				.getRedisWithdrawBlacklist(WBConstants.bathOpenWBSession);
		if (wbInfList == null) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "没有添加任何数据！！！");
			return resultMap;
		}
		try {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute(Constants.SESSION_USER);
			for (WithdrawBlacklist withdrawBlacklist : wbInfList) {
				withdrawBlacklist.setCreateUser(user.getId().toString());
				withdrawBlacklist.setUpdateUser(user.getId().toString());
				PersonInf personInf0 = personInfMapper.getPersonInfByPhoneAndChnl(withdrawBlacklist.getUserPhone(),
						BaseConstants.ChannelCode.CHANNEL0.toString());
				if (personInf0 != null) {
					if (withdrawBlacklist.getUserName().equals(personInf0.getPersonalName())) {
						withdrawBlacklist.setUserId(personInf0.getUserId());
						// 查询open_id
						PersonInf personInf1 = personInfMapper.getPersonInfByPhoneAndChnl(
								withdrawBlacklist.getUserPhone(), BaseConstants.ChannelCode.CHANNEL1.toString());
						if (personInf1 != null && !StringUtils.isNullOrEmpty(personInf1.getExternalId())) {
							withdrawBlacklist.setOpenId(personInf1.getExternalId());
						}
						// 通过手机号查看是否已在黑名单中
						WithdrawBlacklist wb = batchOpenWBMapper
								.getWithdrawBlacklistByUserPhone(withdrawBlacklist.getUserPhone());
						if (wb == null) {
							a = batchOpenWBMapper.insertWithdrawBlacklist(withdrawBlacklist);
						} else {
							a = 1;
						}
					} else {
						logger.error("## 批量提现黑名单数据提交出错,用户名:[{}]和管理平台开户用户名[{}]不一致,", withdrawBlacklist.getUserName(),
								personInf0.getPersonalName());
						resultMap.addAttribute("status", Boolean.FALSE);
						resultMap.addAttribute("msg", "用户姓名:" + withdrawBlacklist.getUserName() + "和管理平台开户用户名"
								+ personInf0.getPersonalName() + "不一致");
						return resultMap;
					}
				} else {
					logger.error("## 批量提现黑名单数据提交出错,管理平台未开户personInf:[{}]", personInf0);
					resultMap.addAttribute("status", Boolean.FALSE);
					resultMap.addAttribute("msg", "用户手机号:【" + withdrawBlacklist.getUserPhone() + "】,在管理平台未开户");
					return resultMap;
				}
			}
			if (a > 0) {
				JedisClusterUtils.getInstance().del(WBConstants.bathOpenWBSession);
			} else {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "批量添加提现黑名单异常,请联系管理员");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("## 批量开户数据提交出错---->>{}", e);
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "批量添加提现黑名单异常,请联系管理员");
			return resultMap;
		}
		return resultMap;
	}

	@Override
	public int deleteWithdrawBlacklistById(String id) {
		return batchOpenWBMapper.deleteWithdrawBlacklistById(id);
	}

	@Override
	public WithdrawBlacklist getWithdrawBlacklistByUserPhone(String userPhone) {
		return batchOpenWBMapper.getWithdrawBlacklistByUserPhone(userPhone);
	}

}

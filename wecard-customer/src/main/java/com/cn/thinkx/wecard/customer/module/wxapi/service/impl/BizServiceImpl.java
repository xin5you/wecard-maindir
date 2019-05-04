package com.cn.thinkx.wecard.customer.module.wxapi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountFansDao;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountMenuDao;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountMenuGroupDao;
import com.cn.thinkx.common.wecard.module.msg.mapper.MsgBaseDao;
import com.cn.thinkx.common.wecard.module.msg.mapper.MsgNewsDao;
import com.cn.thinkx.common.wecard.module.msg.mapper.MsgTextDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.wxapi.service.BizService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountFansService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountMenu;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgBase;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNews;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgText;
import com.cn.thinkx.wechat.base.wxapi.process.HttpMethod;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.MsgType;
import com.cn.thinkx.wechat.base.wxapi.process.MsgXmlUtil;
import com.cn.thinkx.wechat.base.wxapi.process.WxApi;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMessageBuilder;
import com.cn.thinkx.wechat.base.wxapi.vo.Matchrule;
import com.cn.thinkx.wechat.base.wxapi.vo.MsgRequest;
import com.cn.thinkx.wechat.base.wxapi.vo.SemaphoreMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 业务消息处理
 */
@Service("bizService")
public class BizServiceImpl implements BizService {

	@Autowired
	private MsgBaseDao msgBaseDao;

	@Autowired
	private MsgTextDao msgTextDao;

	@Autowired
	private MsgNewsDao msgNewsDao;

	@Autowired
	private AccountMenuDao menuDao;

	@Autowired
	private AccountMenuGroupDao menuGroupDao;

	@Autowired
	private AccountFansDao fansDao;

	@Autowired
	private AccountFansService accountFansService;

	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;

	/**
	 * 处理消息 根据用户发送的消息和自己的业务，自行返回合适的消息；
	 * 
	 * @param msgRequest
	 *            接收到的消息
	 * @param mpAccount
	 *            微信公众号
	 */
	public String processMsg(MsgRequest msgRequest, MpAccount mpAccount) {
		String msgtype = msgRequest.getMsgType();// 接收到的消息类型
		String respXml = null;// 返回的内容；
		if (msgtype.equals(MsgType.Text.toString())) {
			/**
			 * 文本消息，一般公众号接收到的都是此类型消息
			 */
			respXml = this.processTextMsg(msgRequest, mpAccount);
		} else if (msgtype.equals(MsgType.Event.toString())) {// 事件消息
			/**
			 * 用户订阅公众账号、点击菜单按钮的时候，会触发事件消息
			 */
			respXml = this.processEventMsg(msgRequest, mpAccount, true);

			// 其他消息类型，开发者自行处理
		} else {
			respXml = "success";
		}

		// 如果没有对应的消息，暂时先不默认返回订阅消息；
		// if (StringUtils.isEmpty(respXml)) {
		// MsgText text =
		// msgBaseDao.getMsgTextByInputCode(MsgType.SUBSCRIBE.toString());
		// if (text != null) {
		// respXml =
		// MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest,
		// text));
		// }
		// }
		//
		if (!StringUtil.isNullOrEmpty(msgRequest.getMsgId())) {
			if (SemaphoreMap.getSemaphore().containsKey(msgRequest.getMsgId())) {
				SemaphoreMap.getSemaphore().remove(msgRequest.getMsgId());
			}
		} else if (!StringUtil.isNullOrEmpty(msgRequest.getCreateTime())
				&& !StringUtil.isNullOrEmpty(msgRequest.getFromUserName())) {
			if (SemaphoreMap.getSemaphore().containsKey(msgRequest.getCreateTime() + msgRequest.getFromUserName())) {
				SemaphoreMap.getSemaphore().remove(msgRequest.getCreateTime() + msgRequest.getFromUserName());
			}
		}
		return respXml;
	}

	// 处理文本消息
	private String processTextMsg(MsgRequest msgRequest, MpAccount mpAccount) {
		String content = msgRequest.getContent();
		if (!StringUtils.isEmpty(content)) {// 文本消息，默认回复订阅消息
			String tmpContent = content.trim();
			MsgText msgText = msgTextDao.getRandomMsg(content);
			if (msgText != null) {// 回复文本
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, msgText));
			}
			List<MsgNews> msgNews = msgNewsDao.getRandomMsgByContent(tmpContent, mpAccount.getMsgcount());
			if (!CollectionUtils.isEmpty(msgNews)) {// 回复图文
				return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
			}
		}
		return "success";
	}

	// 处理事件消息
	private String processEventMsg(MsgRequest msgRequest, MpAccount mpAccount, boolean merge) {
		String key = msgRequest.getEventKey();
		if (MsgType.SUBSCRIBE.toString().equals(msgRequest.getEvent())) {// 订阅消息
			this.syncAccountFans(msgRequest.getFromUserName(), mpAccount, merge);
			MsgText text = null;
			/*** 用户是否已经注册汇卡包会员 **/
			UserInf user = userInfService.getUserInfByOpenId(msgRequest.getFromUserName());// TODO 放在缓存中
			if (user == null) {
				// 首次注册欢迎语 图文消息
//				List<MsgNews> newsList = msgNewsDao.getMsgNewsBySubscribe();// TODO 放在缓存中
				text = msgBaseDao.getMsgTextBySubscribe();
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
			} else {
				text = msgBaseDao.getMsgTextByAgainSubscribe(); // 再次关注，并且已经注册欢迎语 文本消息
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
			}
		} else if (MsgType.UNSUBSCRIBE.toString().equals(msgRequest.getEvent())) {// 取消订阅消息
			accountFansService.syncAccountFans(msgRequest.getFromUserName(), MsgType.UNSUBSCRIBE.getName());
			MsgText text = msgBaseDao.getMsgTextByInputCode(MsgType.UNSUBSCRIBE.toString());
			if (text != null) {
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
			}
		} else {// 点击事件消息
			if (MsgType.VIEW.toString().equals(msgRequest.getEvent())) {// 点击菜单跳转链接时的事件推送
				return "success";
			}
			if (MsgType.SCANCODE_WAITMSG.toString().equals(msgRequest.getEvent())) {
				return "success";
			}
			if (MsgType.Location.toString().equals(msgRequest.getEvent())) { // 获取地理位置接口
				return "success";
			}
			if (!StringUtils.isEmpty(key)) {
				// 固定消息 _fix_ ：在创建菜单的时候，做了限制，对应的event_key 加了 _fix_
				if (key.startsWith("_fix_")) {
					String baseIds = key.substring("_fix_".length());
					if (!StringUtils.isEmpty(baseIds)) {
						String[] idArr = baseIds.split(",");
						if (idArr.length > 1) {// 多条图文消息
							List<MsgNews> msgNews = msgBaseDao.listMsgNewsByBaseId(idArr);
							if (msgNews != null && msgNews.size() > 0) {
								return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
							}
						} else {// 图文消息，或者文本消息
							MsgBase msg = msgBaseDao.getById(baseIds);
							if (msg.getMsgtype().equals(MsgType.Text.toString())) {
								MsgText text = msgBaseDao.getMsgTextByBaseId(baseIds);
								if (text != null) {
									return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
								}
							} else {
								List<MsgNews> msgNews = msgBaseDao.listMsgNewsByBaseId(idArr);
								if (msgNews != null && msgNews.size() > 0) {
									return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
								}
							}
						}
					}
				}
			}
		}
		return "success";
	}

	// 发布菜单
	public JSONObject publishMenu(String gid, MpAccount mpAccount) {
		List<AccountMenu> menus = menuDao.listWxMenus(gid);

		Matchrule matchrule = new Matchrule();
		String menuJson = prepareMenus(menus, matchrule);
		JSONObject rstObj = WxApiClient.publishMenus(menuJson, mpAccount);// 创建普通菜单

		if (rstObj != null) {// 成功，更新菜单组
			if (rstObj.containsKey("menuid")) {
				menuGroupDao.updateMenuGroupEnable(gid);
			} else if (rstObj.containsKey("errcode") && rstObj.getInt("errcode") == 0) {
				menuGroupDao.updateMenuGroupEnable(gid);
			}
		}
		return rstObj;
	}

	// 发布个性化菜单
	public JSONObject publishMenuConditional(String gid, MpAccount mpAccount) {
		List<AccountMenu> menus = menuDao.listWxMenus(gid);

		Matchrule matchrule = new Matchrule();
		matchrule.setGroup_id(gid);
		String menuJson = prepareMenus(menus, matchrule);

		// 以下为创建个性化菜单demo，只为男创建菜单；其他个性化需求 设置 Matchrule 属性即可
		JSONObject rstObj = WxApiClient.publishAddconditionalMenus(menuJson, mpAccount);// 创建个性化菜单

		if (rstObj != null) {// 成功，更新菜单组
			if (rstObj.containsKey("menuid")) {
				menuGroupDao.updateMenuGroupEnable(gid);
			} else if (rstObj.containsKey("errcode") && rstObj.getInt("errcode") == 0) {
				menuGroupDao.updateMenuGroupEnable(gid);
			}
		}
		return rstObj;
	}

	// 删除菜单
	public JSONObject deleteMenu(MpAccount mpAccount) {
		JSONObject rstObj = WxApiClient.deleteMenu(mpAccount);
		if (rstObj != null && rstObj.getInt("errcode") == 0) {// 成功，更新菜单组
			menuGroupDao.updateMenuGroupDisable();
		}
		return rstObj;
	}

	// 获取用户列表
	public boolean syncAccountFansList(MpAccount mpAccount) {
		String nextOpenId = null;
		AccountFans lastFans = fansDao.getLastOpenId();
		if (lastFans != null) {
			nextOpenId = lastFans.getOpenId();
		}
		return doSyncAccountFansList(nextOpenId, mpAccount);
	}

	// 同步粉丝列表(开发者在这里可以使用递归处理)
	private boolean doSyncAccountFansList(String nextOpenId, MpAccount mpAccount) {
		String url = WxApi.getFansListUrl(WxApiClient.getAccessToken(mpAccount), nextOpenId);
		JSONObject jsonObject = WxApi.httpsRequest(url, HttpMethod.POST, null);
		if (jsonObject.containsKey("errcode")) {
			return false;
		}
		List<AccountFans> fansList = new ArrayList<AccountFans>();
		if (jsonObject.containsKey("data")) {
			if (jsonObject.getJSONObject("data").containsKey("openid")) {
				JSONArray openidArr = jsonObject.getJSONObject("data").getJSONArray("openid");
				int length = 5;// 同步5个
				if (openidArr.size() < length) {
					length = openidArr.size();
				}
				for (int i = 0; i < length; i++) {
					Object openId = openidArr.get(i);
					AccountFans fans = WxApiClient.syncAccountFans(openId.toString(), mpAccount);
					fansList.add(fans);
				}
				// 批处理
				fansDao.addList(fansList);
			}
		}
		return true;
	}

	// 获取用户信息接口 - 必须是开通了认证服务，否则微信平台没有开放此功能
	public AccountFans syncAccountFans(String openId, MpAccount mpAccount, boolean merge) {
		AccountFans fans = WxApiClient.syncAccountFans(openId, mpAccount);
		if (merge && null != fans) {
			AccountFans tmpFans = fansDao.getByOpenId(openId);
			if (tmpFans == null) {
				fans.setWxid(mpAccount.getAccount());
				fans.setFansStatus(BaseConstants.FansStatusEnum.Fans_STATUS_00.getCode());
				if (StringUtil.isNullOrEmpty(fans.getGroupid())) {
					fans.setGroupid("0");
				}
				fansDao.add(fans);
			} else {
				// 同步商户
				try {
					if (fans.getGroupid() != null
							&& !BaseConstants.GroupsIdStatEnum.groupdefauls_stat.getCode().equals(fans.getGroupid())) {
						this.updateFansGroupId(openId, fans.getGroupid(), mpAccount); // 同步商户分组
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				fans.setId(tmpFans.getId());
				fans.setWxid(mpAccount.getAccount());
				fansDao.update(fans);
			}
		}
		return fans;
	}

	// 根据openid 获取粉丝，如果没有，同步粉丝
	public AccountFans getFansByOpenId(String openId, MpAccount mpAccount) {
		AccountFans fans = fansDao.getByOpenId(openId);
		if (fans == null) {// 如果没有，添加
			fans = WxApiClient.syncAccountFans(openId, mpAccount);
			if (null != fans) {
				fansDao.add(fans);
			}
		}
		return fans;
	}

	/**
	 * 获取微信公众账号的菜单
	 * 
	 * @param menus
	 *            菜单列表
	 * @param matchrule
	 *            个性化菜单配置
	 * @return
	 */
	private String prepareMenus(List<AccountMenu> menus, Matchrule matchrule) {
		if (!CollectionUtils.isEmpty(menus)) {
			List<AccountMenu> parentAM = new ArrayList<AccountMenu>();
			Map<Long, List<JSONObject>> subAm = new HashMap<Long, List<JSONObject>>();
			for (AccountMenu m : menus) {
				if (m.getParentid() == 0L) {// 一级菜单
					parentAM.add(m);
				} else {// 二级菜单
					if (subAm.get(m.getParentid()) == null) {
						subAm.put(m.getParentid(), new ArrayList<JSONObject>());
					}
					List<JSONObject> tmpMenus = subAm.get(m.getParentid());
					tmpMenus.add(getMenuJSONObj(m));
					subAm.put(m.getParentid(), tmpMenus);
				}
			}
			JSONArray arr = new JSONArray();
			for (AccountMenu m : parentAM) {
				if (subAm.get(m.getId()) != null) {// 有子菜单
					arr.add(getParentMenuJSONObj(m, subAm.get(m.getId())));
				} else {// 没有子菜单
					arr.add(getMenuJSONObj(m));
				}
			}
			JSONObject root = new JSONObject();
			root.put("button", arr);
			root.put("matchrule", JSONObject.fromObject(matchrule).toString());
			return JSONObject.fromObject(root).toString();
		}
		return "error";
	}

	/**
	 * 此方法是构建菜单对象的；构建菜单时，对于 key 的值可以任意定义； 当用户点击菜单时，会把key传递回来；对已处理就可以了
	 * 
	 * @param menu
	 * @return
	 */
	private JSONObject getMenuJSONObj(AccountMenu menu) {
		JSONObject obj = new JSONObject();
		obj.put("name", menu.getName());
		obj.put("type", menu.getMtype());
		if ("click".equals(menu.getMtype())) {// 事件菜单
			if ("fix".equals(menu.getEventType())) {// fix 消息
				obj.put("key", "_fix_" + menu.getMsgId());// 以 _fix_ 开头
			} else {
				if (StringUtils.isEmpty(menu.getInputcode())) {// 如果inputcode为空，默认设置为subscribe，以免创建菜单失败
					obj.put("key", "subscribe");
				} else {
					obj.put("key", menu.getInputcode());
				}
			}
		} else if ("view".equals(menu.getMtype())) {// 链接菜单-view
			obj.put("url", menu.getUrl());
		} else if ("scancode_waitmsg".equals(menu.getMtype())) {// 扫码带提示菜单
			obj.put("key", "rselfmenu_0_0");
			obj.put("sub_button", "[ ]");
		} else if ("scancode_push".equals(menu.getMtype())) {// 扫码推事件菜单
			obj.put("key", "rselfmenu_0_1");
			obj.put("sub_button", "[ ]");
		}
		return obj;
	}

	// 移动用户分组
	public boolean updateFansGroupId(String openid, String togroupid, MpAccount mpAccount) {

		JSONObject rstObj = WxApiClient.updateMembersGorups(openid, togroupid, mpAccount);
		if (rstObj != null && rstObj.getInt("errcode") == 0) {
			return true;
		}
		return false;
	}

	private JSONObject getParentMenuJSONObj(AccountMenu menu, List<JSONObject> subMenu) {
		JSONObject obj = new JSONObject();
		obj.put("name", menu.getName());
		obj.put("sub_button", subMenu);
		return obj;
	}

	/**
	 * 消息回复内容
	 * 
	 * @param inputcode
	 * @return
	 */
	public MsgText getMsgTextByInputCode(String inputcode) {
		return msgBaseDao.getMsgTextByInputCode(inputcode);
	}
}

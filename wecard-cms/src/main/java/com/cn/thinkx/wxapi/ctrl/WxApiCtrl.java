package com.cn.thinkx.wxapi.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.core.spring.JsonView;
import com.cn.thinkx.core.util.DateUtil;
import com.cn.thinkx.core.util.UploadUtil;
import com.cn.thinkx.wxapi.process.ErrCode;
import com.cn.thinkx.wxapi.process.MediaType;
import com.cn.thinkx.wxapi.process.MpAccount;
import com.cn.thinkx.wxapi.process.WxApiClient;
import com.cn.thinkx.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxapi.process.WxSign;
import com.cn.thinkx.wxapi.service.BizService;
import com.cn.thinkx.wxapi.vo.Material;
import com.cn.thinkx.wxapi.vo.MaterialArticle;
import com.cn.thinkx.wxapi.vo.MaterialItem;
import com.cn.thinkx.wxapi.vo.TemplateMessage;
import com.cn.thinkx.wxcms.domain.AccountFans;
import com.cn.thinkx.wxcms.domain.MsgNews;

import net.sf.json.JSONObject;

/**
 * 微信与开发者服务器交互接口
 */
@Controller
@RequestMapping("/wxapi")
public class WxApiCtrl {

	@Autowired
	@Qualifier("bizService")
	private BizService bizService;


	// 创建微信公众账号菜单
	@RequestMapping(value = "/publishMenu")
	public ModelAndView publishMenu(HttpServletRequest request, String gid) {
		JSONObject rstObj = null;
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
		if (mpAccount != null) {
			String flag=request.getParameter("flag");
			if("1".equals(flag)){
				rstObj = bizService.publishMenuConditional(gid, mpAccount);
			}else{
				rstObj = bizService.publishMenu(gid, mpAccount);
			}
			if (rstObj != null) {// 成功，更新菜单组
				if (rstObj.containsKey("menuid")) {
					ModelAndView mv = new ModelAndView("common/success");
					mv.addObject("successMsg", "创建菜单成功");
					return mv;
				} else if (rstObj.containsKey("errcode") && rstObj.getInt("errcode") == 0) {
					ModelAndView mv = new ModelAndView("common/success");
					mv.addObject("successMsg", "创建菜单成功");
					return mv;
				}
			}
		}

		ModelAndView mv = new ModelAndView("common/failure");
		String failureMsg = "创建菜单失败，请检查菜单：";
		if (rstObj != null) {
			failureMsg += ErrCode.errMsg(rstObj.getInt("errcode"));
		}
		mv.addObject("failureMsg", failureMsg);
		return mv;
	}

	// 删除微信公众账号菜单
	@RequestMapping(value = "/deleteMenu")
	public ModelAndView deleteMenu(HttpServletRequest request) {
		JSONObject rstObj = null;
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		if (mpAccount != null) {
			rstObj = bizService.deleteMenu(mpAccount);
			if (rstObj != null && rstObj.getInt("errcode") == 0) {
				ModelAndView mv = new ModelAndView("common/success");
				mv.addObject("successMsg", "删除菜单成功");
				return mv;
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		String failureMsg = "删除菜单失败";
		if (rstObj != null) {
			failureMsg += ErrCode.errMsg(rstObj.getInt("errcode"));
		}
		mv.addObject("failureMsg", failureMsg);
		return mv;
	}

	// 获取用户列表
	@RequestMapping(value = "/syncAccountFansList")
	public ModelAndView syncAccountFansList() {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		if (mpAccount != null) {
			boolean flag = bizService.syncAccountFansList(mpAccount);
			if (flag) {
				return new ModelAndView("redirect:/accountfans/paginationEntity.html");
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", "获取用户列表失败");
		return mv;
	}

	// 根据用户的ID更新用户信息
	@RequestMapping(value = "/syncAccountFans")
	public ModelAndView syncAccountFans(String openId) {
		ModelAndView mv = new ModelAndView("common/failure");
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		if (mpAccount != null) {
			AccountFans fans = bizService.syncAccountFans(openId, mpAccount, true);// 同时更新数据库
			if (fans != null) {
				mv.setViewName("wxcms/fansInfo");
				mv.addObject("fans", fans);
				mv.addObject("cur_nav", "fans");
				return mv;
			}
		}
		mv.addObject("failureMsg", "获取用户信息失败,公众号信息或openid信息错误");
		return mv;
	}

	// 获取永久素材
	@RequestMapping(value = "/syncMaterials")
	public ModelAndView syncMaterials(Pagination<MaterialArticle> pagination) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号

		ModelAndView mv = new ModelAndView("wxcms/materialPagination");
		Integer offset = pagination.getStart();
		Integer count = pagination.getPageSize();
		Material material = WxApiClient.syncBatchMaterial(MediaType.News, offset, count, mpAccount);
		if (material != null) {
			List<MaterialArticle> materialList = new ArrayList<MaterialArticle>();
			List<MaterialItem> itemList = material.getItems();
			if (itemList != null) {
				for (MaterialItem item : itemList) {
					MaterialArticle m = new MaterialArticle();
					if (item.getNewsItems() != null && item.getNewsItems().size() > 0) {
						MaterialArticle ma = item.getNewsItems().get(0);// 用第一个图文的简介、标题、作者、url
						m.setAuthor(ma.getAuthor());
						m.setTitle(ma.getTitle());
						m.setUrl(ma.getUrl());
					}
					materialList.add(m);
				}
			}
			pagination.setTotalItemsCount(material.getTotalCount());
			pagination.setItems(materialList);
		}
		mv.addObject("page", pagination);
		mv.addObject("cur_nav", "material");
		return mv;
	}

	// 上传图文素材
	@RequestMapping(value = "/doUploadMaterial")
	public ModelAndView doUploadMaterial(MsgNews msgNews) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		String rstMsg = "上传图文消息素材";
		List<MsgNews> msgNewsList = new ArrayList<MsgNews>();
		msgNewsList.add(msgNews);
		JSONObject rstObj = WxApiClient.uploadNews(msgNewsList, mpAccount);
		if (rstObj.containsKey("media_id")) {
			ModelAndView mv = new ModelAndView("common/success");
			mv.addObject("successMsg", "上传图文素材成功,素材 media_id : " + rstObj.getString("media_id"));
			return mv;
		} else {
			rstMsg = ErrCode.errMsg(rstObj.getInt("errcode"));
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", rstMsg);
		return mv;
	}

	// 获取openid
	@RequestMapping(value = "/oauthOpenid")
	public ModelAndView oauthOpenid(HttpServletRequest request) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		if (mpAccount != null) {
			ModelAndView mv = new ModelAndView("wxweb/oauthOpenid");
			// 拦截器已经处理了缓存,这里直接取
			String openid = WxMemoryCacheClient.getOpenid(request.getSession().getId());
			AccountFans fans = bizService.syncAccountFans(openid, mpAccount, false);// 同时更新数据库
			mv.addObject("openid", openid);
			mv.addObject("fans", fans);
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("common/failureMobile");
			mv.addObject("message", "OAuth获取openid失败");
			return mv;
		}
	}

	/**
	 * 生成二维码
	 * 
	 * @param request
	 * @param num
	 *            二维码参数
	 * @return
	 */
	@RequestMapping(value = "/createQrcode", method = RequestMethod.POST)
	public ModelAndView createQrcode(HttpServletRequest request, Integer num) {
		ModelAndView mv = new ModelAndView("wxcms/qrcode");
		mv.addObject("cur_nav", "qrcode");
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		if (num != null) {
			byte[] qrcode = WxApiClient.createQRCode(180, num, mpAccount);// 有效期180s
			String url = UploadUtil.byteToImg(request.getServletContext().getRealPath("/"), qrcode);
			mv.addObject("qrcode", url);
		}
		mv.addObject("num", num);
		return mv;
	}

	// 以根据openid群发文本消息为例
	@RequestMapping(value = "/massSendTextMsg", method = RequestMethod.POST)
	public void massSendTextMsg(HttpServletResponse response, String openid, String content) {
		content = "群发文本消息";
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		String rstMsg = "根据openid群发文本消息失败";
		if (mpAccount != null && !StringUtils.isBlank(openid)) {
			List<String> openidList = new ArrayList<String>();
			openidList.add(openid);
			// 根据openid群发文本消息
			JSONObject result = WxApiClient.massSendTextByOpenIds(openidList, content, mpAccount);

			try {
				if (result.getInt("errcode") != 0) {
					response.getWriter().write("send failure");
				} else {
					response.getWriter().write("send success");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ModelAndView mv = new ModelAndView("common/failure");
		mv.addObject("failureMsg", rstMsg);
	}

	/**
	 * 发送客服消息
	 * 
	 * @param openId
	 *            ： 粉丝的openid
	 * @param content
	 *            ： 消息内容
	 * @return
	 */
	@RequestMapping(value = "/sendCustomTextMsg", method = RequestMethod.POST)
	public void sendCustomTextMsg(HttpServletRequest request, HttpServletResponse response, String openid) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		String content = "测试客服消息";
		JSONObject result = WxApiClient.sendCustomTextMessage(openid, content, mpAccount);
		try {
			if (result.getInt("errcode") != 0) {
				response.getWriter().write("send failure");
			} else {
				response.getWriter().write("send success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送模板消息
	 * 
	 * @param openId
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/sendTemplateMessage", method = RequestMethod.POST)
	public void sendTemplateMessage(HttpServletRequest request, HttpServletResponse response, String openid) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		TemplateMessage tplMsg = new TemplateMessage();

		tplMsg.setOpenid(openid);
		// 微信公众号号的template id，开发者自行处理参数
		tplMsg.setTemplateId("Wyme6_kKUqv4iq7P4d2NVldw3YxZIql4sL2q8CUES_Y");

		tplMsg.setUrl("http://www.weixinpy.com");
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("first", "微信派官方微信模板消息测试");
		dataMap.put("keyword1", "时间：" + DateUtil.COMMON.getDateText(new Date()));
		dataMap.put("keyword2", "关键字二：你好");
		dataMap.put("remark", "备注：感谢您的来访");
		tplMsg.setDataMap(dataMap);

		JSONObject result = WxApiClient.sendTemplateMessage(tplMsg, mpAccount);
		try {
			if (result.getInt("errcode") != 0) {
				response.getWriter().write("send failure");
			} else {
				response.getWriter().write("send success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取js ticket
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/jsTicket")
	@ResponseBody
	public String jsTicket(HttpServletRequest request, String url) {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		String jsTicket = WxApiClient.getJSTicket(mpAccount);
		WxSign sign = new WxSign(mpAccount.getAppid(), jsTicket, url);

		JsonView jv = new JsonView();
		jv.setData(sign);

		return jv.toString();
	}
	
}

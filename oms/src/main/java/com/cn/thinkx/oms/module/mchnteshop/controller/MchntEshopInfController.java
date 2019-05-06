package com.cn.thinkx.oms.module.mchnteshop.controller;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.common.service.ImageManagerService;
import com.cn.thinkx.oms.module.mchnteshop.model.MchntEshopInf;
import com.cn.thinkx.oms.module.mchnteshop.service.MchntEshopInfService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
@RequestMapping("mchnteshop/eShopInf")
public class MchntEshopInfController extends BaseController {

    Logger logger = LoggerFactory.getLogger(MchntEshopInfController.class);

    @Autowired
    @Qualifier("mchntEshopInfService")
    private MchntEshopInfService mchntEshopInfService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    @Autowired
    @Qualifier("imageManagerService")
    private ImageManagerService imageManagerService;


    /**
     * 查询商户商城列表
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/listMchntEshopInf")
    public ModelAndView listMchntEshopInf(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("merchant/mchntEshopInf/listMchntEshopInf");
        String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
        String eShopName = StringUtils.nullToString(req.getParameter("eShopName"));
        String mchntName = StringUtils.nullToString(req.getParameter("mchntName"));
        String mchntCode = StringUtils.nullToString(req.getParameter("mchntCode"));
        PageInfo<MchntEshopInf> pageList = null;

        int startNum = parseInt(req.getParameter("pageNum"), 1);
        int pageSize = parseInt(req.getParameter("pageSize"), 10);

        MchntEshopInf me = new MchntEshopInf();
        me.seteShopName(eShopName);
        me.setMchntName(mchntName);
        me.setMchntCode(mchntCode);
        try {
            pageList = mchntEshopInfService.getMchntEshopInfPage(startNum, pageSize, me);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询列表信息出错", e);
        }
        mv.addObject("pageInfo", pageList);
        mv.addObject("operStatus", operStatus);
        mv.addObject("eshop", me);
        return mv;
    }


    @RequestMapping(value = "/intoAddMchntEshopInf")
    public ModelAndView intoAddMchntEshopInf(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("merchant/mchntEshopInf/addMchntEshopInf");

        List<MerchantInf> mchntList = merchantInfService.getMerchantInfList(null);
        mv.addObject("mchntList", mchntList);

        return mv;
    }

    /**
     * 获取商户下的所有门店
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/intoAddMchntEshopInfGetShop")
    @ResponseBody
    public ModelMap intoAddMchntEshopInfGetShop(HttpServletRequest req, HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("status", Boolean.TRUE);
        String mchntCode = req.getParameter("mchntCode");
        ShopInf shopInf = new ShopInf();
        shopInf.setMchntCode(mchntCode);
        List<ShopInf> shopInfList = shopInfService.findShopInfList(shopInf);
        map.addAttribute("shopInfList", shopInfList);
        return map;
    }

    /**
     * 添加商户商城信息 提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/addMchntEshopInfCommit")
    @ResponseBody
    public ModelMap addMchntEshopInfCommit(HttpServletRequest req, HttpServletResponse response
//			@RequestParam(value = "eshopLogos", required = false) MultipartFile eshopLogos,  //商城logo
//			@RequestParam(value = "eshopBackGrounds", required = false) MultipartFile eshopBackGrounds  //商城背景图
    ) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile eshopLogos = multipartRequest.getFile("eshopLogos");//商城logo
        MultipartFile eshopBackGrounds = multipartRequest.getFile("eshopBackGrounds"); //商城背景图
        try {
            MchntEshopInf MchntEshopInf = getMchntEshopInfInf(req, null);
            MchntEshopInf mchntEshopInf = mchntEshopInfService.getMchntEshopInfByMchntCode(MchntEshopInf.getMchntCode());
            if (mchntEshopInf != null) {
                resultMap.addAttribute("status", Boolean.FALSE);
                resultMap.addAttribute("msg", "已有商城，请重新添加");
            } else {
                //上传商城logo
                String eshopLogo = imageManagerService.addUploadImange(MchntEshopInf.getMchntCode(),
                        Constants.ImageApplicationEnum.Application40.getCode(),
                        Constants.ImageApplicationTypeEnum.ApplicationType4001.getCode(),
                        eshopLogos);

                //上传商城logo
                String eshopBgUrl = imageManagerService.addUploadImange(MchntEshopInf.getMchntCode(),
                        Constants.ImageApplicationEnum.Application40.getCode(),
                        Constants.ImageApplicationTypeEnum.ApplicationType4002.getCode(),
                        eshopBackGrounds);
                MchntEshopInf.seteShopLogo(eshopLogo);
                MchntEshopInf.setBgUrl(eshopBgUrl);
                mchntEshopInfService.insertMchntEshopInf(MchntEshopInf);
                resultMap.addAttribute("operStatus", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "新增失败，请重新添加");
            logger.error(e.getLocalizedMessage(), e);
        }
        return resultMap;
    }


    /**
     * @param req
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/intoEditMchntEshopInf")
    public ModelAndView intoEditMchntEshopInf(HttpServletRequest req, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("merchant/mchntEshopInf/editMchntEshopInf");
        String eShopId = req.getParameter("eShopId");
        MchntEshopInf mchntEshopInf = mchntEshopInfService.getMchntEshopInfById(eShopId);
        mchntEshopInf.seteShopLogo(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + mchntEshopInf.geteShopLogo());
        mchntEshopInf.setBgUrl(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + mchntEshopInf.getBgUrl());
        mv.addObject("mchntEshopInf", mchntEshopInf);
        return mv;
    }


    /**
     * 编辑商户商城信息 提交
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/editMchntEshopInfCommit")
    @ResponseBody
    public ModelMap editMchntEshopInfCommit(HttpServletRequest req, HttpServletResponse response
    ) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.TRUE);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile eshopLogos = multipartRequest.getFile("eshopLogos");
        MultipartFile eshopBackGrounds = multipartRequest.getFile("eshopBackGrounds");

        try {
            MchntEshopInf mchntEshopInf = getMchntEshopInfInf(req, null);
            String eshopLogo = null;
            String eshopBgUrl = null;
            //上传商城logo
            if (eshopLogos != null) {
                eshopLogo = imageManagerService.addUploadImange(mchntEshopInf.getMchntCode(),
                        Constants.ImageApplicationEnum.Application40.getCode(),
                        Constants.ImageApplicationTypeEnum.ApplicationType4001.getCode(),
                        eshopLogos);
                mchntEshopInf.seteShopLogo(eshopLogo);
            }
            //上传商城logo
            if (eshopBackGrounds != null) {
                eshopBgUrl = imageManagerService.addUploadImange(mchntEshopInf.getMchntCode(),
                        Constants.ImageApplicationEnum.Application40.getCode(),
                        Constants.ImageApplicationTypeEnum.ApplicationType4002.getCode(),
                        eshopBackGrounds);
                mchntEshopInf.setBgUrl(eshopBgUrl);
            }

            mchntEshopInfService.updateMchntEshopInf(mchntEshopInf);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "编辑失败，请重新操作");
            logger.error(e.getLocalizedMessage(), e);
        }
        return resultMap;
    }


    /**
     * 删除商户商城信息 commit
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteMchntEshopInfCommit")
    @ResponseBody
    public ModelMap deleteMchntEshopInfCommit(HttpServletRequest req, HttpServletResponse response) {
        ModelMap resultMap = new ModelMap();
        resultMap.put("status", Boolean.TRUE);
        String eShopId = StringUtils.nullToString(req.getParameter("eShopId"));
//		String  mchntEshopId="2017061409370000000212";
        try {
            mchntEshopInfService.deleteMchntEshopInf(eShopId);
        } catch (Exception e) {

            resultMap.put("status", Boolean.FALSE);
            resultMap.put("msg", "删除商户商城失败，请重新操作");
            logger.error(e.getLocalizedMessage(), e);
        }
        return resultMap;
    }

    @RequestMapping(value = "/intoViewMchntEshopInf")
    public ModelAndView intoViewMchntEshopInf(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView("merchant/mchntEshopInf/viewMchntEshopInf");
        String eShopId = req.getParameter("eShopId");
        MchntEshopInf mchntEshopInf = mchntEshopInfService.getMchntEshopInfById(eShopId);
        mchntEshopInf.seteShopLogo(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + mchntEshopInf.geteShopLogo());
        mchntEshopInf.setBgUrl(RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + mchntEshopInf.getBgUrl());
        mchntEshopInf.seteShopUrl(RedisDictProperties.getInstance().getdictValueByCode("40006001_REQ_URL"));
        mv.addObject("mchntEshopInf", mchntEshopInf);
        return mv;
    }

    /**
     * @param @param  req
     * @param @return
     * @param @throws Exception
     * @return merchantInf    返回类型
     * @throws
     * @Title: getShopInfInfo
     * @Description: 商户商城表封装
     */
    private MchntEshopInf getMchntEshopInfInf(HttpServletRequest req, User user) throws Exception {
        MchntEshopInf mchntEshopInf = null;
        String eShopId = StringUtils.nullToString(req.getParameter("eShopId"));
        if (!StringUtils.isNullOrEmpty(eShopId)) {
            mchntEshopInf = mchntEshopInfService.getMchntEshopInfById(eShopId);
        } else {
            mchntEshopInf = new MchntEshopInf();
        }
        mchntEshopInf.seteShopName(StringUtils.nullToString(req.getParameter("eShopName")));
        mchntEshopInf.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
        mchntEshopInf.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
        mchntEshopInf.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
        mchntEshopInf.setChannelCode(StringUtils.nullToString(req.getParameter("channelCode")));
        if (user != null) {
            mchntEshopInf.setCreateUser(user.getId().toString());
            mchntEshopInf.setUpdateUser(user.getId().toString());
        }
        return mchntEshopInf;
    }


}

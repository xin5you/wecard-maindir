package com.cn.thinkx.pub.ctrl;

import com.cn.thinkx.pms.base.redis.util.BaseKeyUtil;
import com.cn.thinkx.pms.base.utils.DES3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("info", "欢迎来到薪无忧公众号后台管理系统，敬请期待...");
        return mv;
    }

    @RequestMapping(value = "/om")
    public ModelAndView index(@RequestParam(value = "p") String p) {
        String[] paramArr = null;
        try {
            paramArr = DES3Util.Decrypt3DES(p, BaseKeyUtil.getEncodingAesKey()).split("\\|");
        } catch (Exception e) {
            logger.error("商户收银台-->收款码二维码解密失败", e);
        }
        String mchntCode = paramArr[0];
        String shopCode = paramArr[1];
        return new ModelAndView("redirect:/customer/mchnt/openAccountByMchnt.html?mchntCode=" + mchntCode +
                "&shopCode=" + shopCode);
    }

}

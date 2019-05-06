package com.cn.thinkx.wxcms;

import com.cn.thinkx.core.spring.SpringBeanDefineService;
import com.cn.thinkx.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxcms.domain.Account;
import com.cn.thinkx.wxcms.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {

    private Logger logger = LoggerFactory.getLogger(AppDefineInitService.class);

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Override
    public void initApplicationCacheData() {
        Account account = accountService.getByAccount(Contents.ACCOUNT);
        logger.info("当前公众号为：【" + account.getName() + "】");
        WxMemoryCacheClient.addMpAccount(account);
    }

}

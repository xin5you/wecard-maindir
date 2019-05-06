package com.cn.thinkx.dubbo.filter;

import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DUBBO 允许访问的IP白名单集合
 *
 * @author pucker
 */
@Component
public class IpWhiteList {

    /**
     * 获得白名单IP集合
     *
     * @return
     */
    public List<String> getAllowedIps() {
        String ipWhiteList = RedisDictProperties.getInstance().getdictValueByCode("IP_WHITE_LIST");
        if (StringUtil.isNullOrEmpty(ipWhiteList))
            return null;

        String[] array = StringUtil.trim(ipWhiteList).split(",");
        if (array.length < 1)
            return null;

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 白名单开关
     *
     * @return
     */
    public boolean isEnabled() {
        String enabled = RedisDictProperties.getInstance().getdictValueByCode("IP_WHITE_ENABLED");
        if (!StringUtil.isNullOrEmpty(enabled)) {
            return Boolean.parseBoolean(StringUtil.trim(enabled));
        }
        return false;
    }

}

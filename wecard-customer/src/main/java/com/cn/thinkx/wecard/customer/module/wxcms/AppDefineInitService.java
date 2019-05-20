package com.cn.thinkx.wecard.customer.module.wxcms;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.domain.CnapsVO;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.utils.CnapsUtil;
import com.cn.thinkx.wecard.customer.core.spring.SpringBeanDefineService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.JedisCluster;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {
    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    public void initApplicationCacheData() {
        Account account = accountService.getByAccount(
//				jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_CUSTOMER_ACCOUNT"));
                jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_HUIKABAO_LIFE_ACCOUNT"));
        System.out.println("加载公众号信息：" + JSONObject.toJSONString(account));
        WxMemoryCacheClient.addMpAccount(account);

        String filePath = AppDefineInitService.class.getResource("/bank/cnaps.xlsx").getPath();
        FileInputStream fis = null;
        Workbook wookbook = null;

        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //得到工作簿
            wookbook = new XSSFWorkbook(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        //获得表头
        Row rowHead = sheet.getRow(0);
        //判断表头是否正确
        if (rowHead.getPhysicalNumberOfCells() != 4) {
            System.out.println("表头的数量不对!");
        }
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        //获得所有数据
        List<CnapsVO> list = new ArrayList<>();
        for (int i = 1; i <= totalRowNum; i++) {
            CnapsVO cnapsVO = new CnapsVO();
            //获得第i行对象
            Row row = sheet.getRow(i);
            //获得获得第i行第0列的 String类型对象
            cnapsVO.setBankName(row.getCell((short) 0).getStringCellValue());
            cnapsVO.setProvince(row.getCell((short) 1).getStringCellValue());
            cnapsVO.setCity(row.getCell((short) 2).getStringCellValue());
            cnapsVO.setCnapsNo(row.getCell((short) 3).getStringCellValue());
            list.add(cnapsVO);
        }

        CnapsUtil.setCnapsList(list);
    }

}

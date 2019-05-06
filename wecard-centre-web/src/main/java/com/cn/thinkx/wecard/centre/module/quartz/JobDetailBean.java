package com.cn.thinkx.wecard.centre.module.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

public class JobDetailBean extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(JobDetailBean.class.getName());

    private String targetObject;
    private String targetMethod;
    private ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Object bean = null;
        Method m = null;
        try {
            bean = ctx.getBean(targetObject);
            m = bean.getClass().getMethod(targetMethod);
            m.invoke(bean);
        } catch (Exception e) {
            logger.error("## job[{}.{}]执行异常", bean.getClass().getName(), m.getName(), e);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
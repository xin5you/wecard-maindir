package com.cn.thinkx.cgb.model;

import java.io.Serializable;

/**
 * 查询账户余额
 */
public class QueryAccountBalDTO implements Serializable {

    private static final long serialVersionUID = 3453938010478567043L;

    //账户号
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "QueryAccountBalDTO{" +
                "account='" + account + '\'' +
                '}';
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="login_page">
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

    <script src="${ctx}/resource/js/module/login/login.js"></script>
</head>
<body>
    <div class="login_box">

        <form action="${ctx}/user/doLogin.do" method="post" id="login_form">
            <div class="top_b">薪无忧运营管理平台</div>
            <div class="alert alert-info alert-login">
               	温馨提示：<span style="font-weight: bold;font-size: small;" id="errorcontent">${sessionScope['SECURITY_LOGIN_EXCEPTION']}</span>
            </div>
            <div class="cnt_b">
                <div class="formRow">
                    <div class="input-prepend">
                        <span class="add-on"><i class="icon-user"></i></span><input type="text" id="username" name="username" placeholder="账号" value="" autocomplete="off"/>
                    </div>
                </div>
                <div class="formRow">
                    <div class="input-prepend">
                        <span class="add-on"><i class="icon-lock"></i></span><input type="password" class="text-login" id="password" name="password" placeholder="密码" value="" autocomplete="off" />
                    </div>
                </div>
                <div class="formRow">
                    <div class="input-prepend">
                        <span class="add-on"><i class="icon-edit"></i></span>
                        <input name="authCode" id="authCode" maxlength="4" type="text" class="span1 text-login" placeholder="验证码" autocomplete="off" /> 
						<img src="" id="authCodeImg" class="auth-code-img" alt="点击换一张" title="点击换一张" />
                    </div>
                </div>                
            </div>
            <div class="btm_b clearfix" style="text-align: center;">
                <button class="btn btn-primary btn-submit" type="button">登 录</button>
            </div>
        </form>
    </div>
</body>
</html>

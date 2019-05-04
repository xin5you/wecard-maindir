﻿
$(function(){

    var loginUser = {
        login: function(){
            var messageLabel = $("#errorcontent");
            messageLabel.html("");
            var obj = {username:$("#username").val(), password:$("#password").val(), authCode:$("#authCode").val()};
            if(obj.username==''){
                messageLabel.html("<span style='color:red'>账号不能为空</span>");
                return false;
            }
            if(obj.password==''){
                messageLabel.html("<span style='color:red'>密码不能为空</span>");
                return false;
            }
            if(obj.authCode==''){
                messageLabel.html("<span style='color:red'>验证码不能为空</span>");
                return false;
            }

    		var md5_val = $.md5($.md5(obj.password) + obj.authCode);
    		
    		$("#password").val(md5_val)
    		
    		$("#login_form").submit();
    		
//            $.ajax({
//                url: Helper.getRootPath() + '/login/doLogin.do',
//                type: 'post',
//                dataType : "json",
//                data: {
//                    userName: obj.username,
//                    md5Code: md5_val,
//                    authCode: obj.authCode
//                },
//                success: function (message) {
//                    if (message.status) {
//                        messageLabel.html("<span style='color:limegreen'>" + message.message + "</span>");
//                        document.location.href = Helper.getRootPath() + "/login/main.do";
//
//                    } else {
//                    	loginUser.genAuthCodeImg();
//                    	$(".btn-submit").removeAttr('disabled');
//                        messageLabel.html("<span style='color:red'>" + message.message + "</span>");
//                    }
//                }
//            });
        },
         genAuthCodeImg:function(){
        	 var loginImg = Helper.genAuthCode(1);
 			 $("#authCodeImg").attr('src', loginImg);
         }
    }

    var form_wrapper = $('.login_box');

    function boxHeight() {
        form_wrapper.animate({
            marginTop : (-(form_wrapper.height() / 2) - 24)
        }, 400);
    }

    form_wrapper.css({
        marginTop : (-(form_wrapper.height() / 2) - 24)
    });

    $('#login_form').validate({
        onkeyup : false,
        errorClass : 'error',
        validClass : 'valid',
        rules : {
            username : {
                required : true,
                minlength : 3
            },
            password : {
                required : true,
                minlength : 3
            },
            authCode:{
            	 required : true
            }
        },
        highlight : function(element) {
            $(element).closest('div').addClass("f_error");
            setTimeout(function() {
                boxHeight()
            }, 200)
        },
        unhighlight : function(element) {
            $(element).closest('div').removeClass("f_error");
            setTimeout(function() {
                boxHeight()
            }, 200)
        },
        errorPlacement : function(error, element) {
            $(element).closest('div').append(error);
        }
    });

    $('.btn-submit').on('click', loginUser.login);
    
    $('.text-login').keyup(function(event){
    	  if(event.keyCode ==13){
    		  loginUser.login();
    	  }
    });
    
    $('.auth-code-img').on('click', loginUser.genAuthCodeImg);
    loginUser.genAuthCodeImg();
});
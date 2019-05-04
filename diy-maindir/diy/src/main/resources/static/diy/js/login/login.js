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

    $('#submitLogin').on('click', loginUser.login);
    
    $('.text-login').keyup(function(event){
    	  if(event.keyCode ==13){
    		  loginUser.login();
    	  }
    });
    
    $('.auth-code-img').on('click', loginUser.genAuthCodeImg);
    loginUser.genAuthCodeImg();
});
$(document).ready(function() {
	addUser.init();
})

var addUser = {
	init : function() {
		addUser.initEvent();
	},

	initEvent:function(){
		$('#roleId').change(addUser.changeShop);
		$('#btn-back').on('click', addUser.back);
		$('#btn-submit').on('click', addUser.add);
	},
	changeShop : function(){
		var role = $('#roleId').val();
		var roleIdTmp = $('#roleIdTmp').val();
		if(role == roleIdTmp) {
			$("#shopId").hide();
			$('#shopCode').attr('disabled',true);
			$('#shopCode').val('0');
		} else {
			$("#shopId").show();
			$('#shopCode').removeAttr("disabled");
		}
	},
	back : function(){
		Helper.post('/system/user/listUser');
	},
	add : function(){
		var userName = $('#userName').val().trim();
		var phoneNo = $('#phoneNo').val();
		var password = $('#password').val();
		var upassword = $('#upassword').val();
		var roleId = $('#roleId').val();
		var shopCode = $('#shopCode').val();
		var roleIdTmp = $('#roleIdTmp').val();
		if(userName.length > 0){
    		var reg = /^[\w\u4e00-\u9fa5][\s\w\u4e00-\u9fa5]*(?!\s)$/; 
    		if (!(reg.test(userName))) {
    			$("#warn-name").css('color','red');
				$("#warn-name").text('用户名只能输入中英文、数字、空格、下划线');
				return false;
			}else {
				$("#warn-name").text('');
			} 
    	}
		if (phoneNo.length < 11) {
			$("#warn-phone").css('color','red');
			$("#warn-phone").text('手机号必须要是11位');
//			$('#phoneNo').focus();
			return false;
		} else {
			var reg = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
			if (!(reg.test(phoneNo))) {
				$("#warn-phone").css('color','red');
				$("#warn-phone").text('请输入正确的手机号');
				return false;
			} else {
				$("#warn-phone").text('');
			}
		}
		if (password.length < 6) {
			$("#warn-pass").css('color','red');
			$("#warn-pass").text('为了您的账户安全，建议密码设置为6-16个字符');
			return false;
		} else {
			$("#warn-pass").text('');
		}
		if (password != upassword) {
			$("#warn-upass").css('color','red');
			$("#warn-upass").text('两次输入密码不一致');
			return false;
		} else {
			$("#warn-upass").text('');
		}
		if (roleId == 0) {
			$("#warn-role").css('color','red');
			$("#warn-role").text('请选择职位');
			return false;
		} else {
			$("#warn-role").text('');
		}
		if(roleId != roleIdTmp){
			if (shopCode == 0) {
				$("#warn-shop").css('color','red');
				$("#warn-shop").text('请选择门店');
				return false;
			} else {
				$("#warn-shop").text('');
			}
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/system/user/addUser',
            data: {
        		"userName" : $("#userName").val(),
        		"phoneNo": $("#phoneNo").val(),
        		"password": $("#password").val(),
        		"roleId": $("#roleId").val(),
        		"shopCode": $("#shopCode").val(),
        		"mchntCode": $("#mchntCode").val()
            },
            cache:false,
            dataType: 'json',
            success: function(data){
                if (data.code == '00') {
                	alert(data.msg);
                	addUser.back();
                } else {
                	alert(data.msg);
                	return false;
                }
            },
			error : function(){
				alert(data.msg);
			}
        });
	}
}
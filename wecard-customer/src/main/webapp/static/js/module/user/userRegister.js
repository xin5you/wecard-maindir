$(document).ready(
		function() {
			var userReg = {
				initEvent : function() {
					$('#userRegBtn').on('click', userReg.userRegCommit);
					$('#sendPhoneCode').on('click', userReg.sendPhoneSMS);
					// $('input[name="windowCloseInput"]').on('click',
					// userReg.closeWindow);
				},
				sendPhoneSMS : function() {
					var phoneNumber = $.trim($('#phoneNumber_page').val())
					var reg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;	//验证手机号
					if (phoneNumber == '') {
						$('#checkViews').html('请输入手机号，获取动态验证码');
						$('#checkViews').show();
						return;
					}
					if(phoneNumber.length!=11){
						$('#checkViews').html('请输入有效的手机号码');
						$('#checkViews').show();
			    		return;
					}
					if(!reg.test(phoneNumber)){
						$('#checkViews').html('请输入有效的手机号码');
						$('#checkViews').show();
			    		return;
					}
					var that = $('#sendPhoneCode');
					var bizCode = '01';
					$.ajax({
						url : CONETXT_PATH + '/pub/sendPhoneSMS.html',
						type : 'post',
						dataType : "json",
						data : {
							bizCode : bizCode,
							phoneNumber : phoneNumber
						},
						success : function(resp) {
							if (!resp.status) {
								$.toptip(resp.smg);
							} else {
								// $.toptip("发送成功");
							}
						}
					});
				},
				userRegCommit : function() {
					var $that = $("#userRegBtn");
					var phoneNumber = $.trim($('#phoneNumber_page').val());
					var phoneCode = $.trim($('#phoneCode').val());
					var password = $.trim($('#password').val());
					var passwordConfirm = $.trim($('#passwordConfirm').val());
					// pwd encryp
					var encrypPassword = RSAUtils.encryptedString(publicKey, password.split("").reverse().join("")) || '0';
					
					var reg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;	//验证手机号
					
					if (!phoneNumber) {
						$('#checkViews').html('请输入手机号，获取动态验证码');
						$('#checkViews').show();
						// $.toptip('请输入手机号，获取动态验证码');
						return;
					} else if (!phoneCode) {
						$('#checkViews').html('请输入手机动态口令');
						$('#checkViews').show();
						// $.toptip('请输入手机动态口令');
						return;
					} else if (!password || !passwordConfirm) {
						$('#checkViews').html('请输入您的6位数字交易密码');
						$('#checkViews').show();
						// $.toptip('请输入您的6位数字交易密码');
						return;
					} else if (password != passwordConfirm) {
						$('#checkViews').html('您输入的确认密码和交易密码不匹配,请重新输入');
						$('#checkViews').show();
						// $.toptip('您输入的确认密码和交易密码不匹配,请重新输入');
						return;
					}
					if(phoneNumber.length!=11){
						$('#checkViews').html('请输入有效的手机号码');
						$('#checkViews').show();
			    		return;
					}
					//手机号验证
					if(!reg.test(phoneNumber)){
						$('#checkViews').html('请输入有效的手机号码');
						$('#checkViews').show();
			    		return;
					}
					var pwd = /^\d{6}$/;
					if (!pwd.test(password)) {
						// $.toptip('交易密码请设置为6位数字');
						$('#checkViews').html('交易密码请设置为6位数字');
						$('#checkViews').show();
						return;
					} else {
						$('#userpwd').val(encrypPassword);
						$('#password').val('')
						$('#passwordConfirm').val('')
						$('#myForm').submit();

						// $that.attr("disabled", true);
						// $.showLoading('提交中...');
						//		   
						// var encrypPassword='0';
						// var encrypPasswordCfm='0';
						// try {
						// encrypPassword=RSAUtils.encryptedString(publicKey,password.split("").reverse().join(""))||'0';
						// encrypPasswordCfm=RSAUtils.encryptedString(publicKey,passwordConfirm.split("").reverse().join(""))||'0';
						// } catch(err) {
						// }
						// $.ajax({
						// url : CONETXT_PATH +
						// '/customer/user/userRegisterCommit.html',
						// type : 'POST',
						// dataType : 'JSON',
						// data:{
						// phoneNumber:phoneNumber,
						// phoneCode:phoneCode,
						// password:encrypPassword,
						// passwordConfirm:encrypPasswordCfm
						// },
						// success:function(data){
						// $.hideLoading();
						// $that.removeAttr("disabled");
						// if(data.code=='00'){
						// jfSwitch.sectionTabBack('register_success');
						// }else{
						// if(data.info==null || data.info=='' ){
						//	    					
						// }else{
						// $('#weui_error_msg_desc').html(data.info);
						// }
						// jfSwitch.sectionTabBack('register_fail');
						// }
						// },
						// error:function(){
						// $that.removeAttr("disabled");
						// jfSwitch.sectionTabBack('register_fail');
						// }
						// });
					}
				}
			};
			userReg.initEvent();
		});
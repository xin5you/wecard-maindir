$(document).ready(
		function() {

			var userSettings = {
				initEvent : function() {
					$('#userSetBtn').on('click', userSettings.userSetCommit);
					$('#sendPhoneCode').on('click', userSettings.sendPhoneSMS);
					$('input[name="windowCloseInput"]').on('click',
							userSettings.closeWindow)
				},
				closeWindow : function() {
					wx.closeWindow();
				},
				sendPhoneSMS : function() {
					var that = $('#sendPhoneCode');
					var bizCode = '02';
					$.ajax({
						url : CONETXT_PATH + '/pub/sendUserPhoneSMS.html',
						type : 'post',
						dataType : "json",
						data : {
							bizCode : bizCode
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
				userSetCommit : function() {
					var $that = $("#userSetBtn");
					var phoneCode = $.trim($('#phoneCode').val());
					var password = $.trim($('#password').val());
					var passwordConfirm = $.trim($('#passwordConfirm').val());

					// pwd encryp
					var encrypPassword = RSAUtils.encryptedString(publicKey,
							password.split("").reverse().join(""))
							|| '0';

					if (phoneCode == '') {
						$('#checkViews').html('请输入手机动态验证码');
						$('#checkViews').show();
						// $.toptip('请输入手机动态验证码');
						return;
					}
					if (password == '' || passwordConfirm == '') {
						$('#checkViews').html('请输入您的6位数字交易密码');
						$('#checkViews').show();
						// $.toptip('请输入您的6位数字交易密码');
						return;
					}
					if (password != passwordConfirm) {
						// $.toptip('您输入的确认密码和交易密码不匹配,请重新输入');
						$('#checkViews').html('您输入的确认密码和交易密码不匹配,请重新输入');
						$('#checkViews').show();
						return;
					}
					var pwd = /^\d{6}$/;
					if (!pwd.test(password)) {
						// $.toptip('交易密码请设置为6位数字');
						$('#checkViews').html('交易密码请设置为6位数字');
						$('#checkViews').show();
						return;
					}
					$('#userpwd').val(encrypPassword);
					$('#password').val('')
					$('#passwordConfirm').val('')
					$('#myForm').submit();
				}
			};

			userSettings.initEvent();

		});
//solve ie8 bug	
if(!Array.indexOf)
{
    Array.prototype.indexOf = function(obj)
    {              
        for(var i=0; i<this.length; i++)
        {
            if(this[i]==obj)
            {
                return i;
            }
        }
        return -1;
    }
}

Date.prototype.format = function(format)
  {
	/*
	 * format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

Number.prototype.toPercent = function(){
	return Math.round(this * 10000)/100 + '%';
}
 

$(document).ready(function() {
	DiyCommon.init();
});
    
DiyCommon = {
	safeCodeElements: [],	
	safeCodeCallback: null,
		
	init: function(){
		DiyCommon.initEvent();
	},
	initEvent: function(){
		$('#userPWdSubmitBtn').on('click', DiyCommon.updatePwdCommit);
		$('.logout_user').on('click',  DiyCommon.logout);
	},
	loadUpdatePwdModal : function(){
		$('#updatePWDModal').modal({
			backdrop : "static"
		});
	},
	updatePwdCommit:function(){
		var oldPasswrodpage = $("#oldPasswrodPage").val();
		var newPasswordPage =$("#newPasswordPage").val();
		var newPassword2Page =$("#newPassword2Page").val();
		
		if(oldPasswrodpage ==null || oldPasswrodpage==''){
			Helper.alert('请输入您的旧密码');
			return;
		}
		if(newPasswordPage ==null || newPasswordPage==''){
			Helper.alert('请输入您的新密码');
			return;
		}
		if(newPassword2Page ==null || newPassword2Page==''){
			Helper.alert('请输入您的二次确认密码');
			return;
		}
		
		if(newPassword2Page!=newPasswordPage){
			Helper.alert('您输入的新密码和确认密码不匹配，请重新输入');
			return;
		}
		
		Helper.confirm("确定修改密码？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/system/user/updatePwdCommit',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "oldPasswrod" :  $.md5(oldPasswrodpage), 
	                "newPasswordPage" : $.md5(newPasswordPage), 
	                "newPassword2Page" : $.md5(newPassword2Page)
	            },
	            success: function (data) {
	            	if(data.code == '00') {
	            		Helper.confirm_one("密码修改成功", function(){
	            			$.ajax({
		        		        url: Helper.getRootPath() + '/logout',
		        		        type: 'post',
		        		        dataType: "json",
		        		        data: {},
		        		        success: function (data) {
		        		            if (data.code == '00') {
		        		               location.href = Helper.getRootPath() + "/login";
		        		            }
		        		        }
		        		    });
	            		});
	                } else {
	                	Helper.alert(data.msg);
	                	return false;
	                }
	            },
	            error:function(){
	            	Helper.alert(data.msg);
	            }
		    });
		});
	},
	logout: function(){
		Helper.confirm('确认要退出？', function(){
			$.ajax({
		        url: Helper.getRootPath() + '/logout',
		        type: 'post',
		        dataType: "json",
		        data: {},
		        success: function (data) {
		            if (data.code == '00') {
		               location.href = Helper.getRootPath() + "/login";
		            }
		        }
		    });
		});
	},
	 sendMsgCurCount : 60,
		sendMsgAgain: function(that) {
			if (DiyCommon.sendMsgCurCount == 0) { 
				that.removeAttr("disabled");//启用按钮
				that.css('color','#000').val('获取验证码');//结束后重心获取验证码
			    DiyCommon.sendMsgCurCount = 60; 
			 } else { 
				 that.attr("disabled", "true");
			 	var str_t=DiyCommon.sendMsgCurCount;
				if(DiyCommon.sendMsgCurCount<10){
					str_t="0"+DiyCommon.sendMsgCurCount;
				}
				that.attr("disabled", true).val('重新发送'+str_t+'s');
				DiyCommon.sendMsgCurCount--; 
				DiyCommon.sendMsgTimer = setTimeout(function() { DiyCommon.sendMsgAgain(that); }, 1000);
			 }
		},
		sendPhoneSMS: function(phoneNumber,bizCode){
			$.ajax({
		        url:Helper.getRootPath()+'/pub/sendPhoneSMS',
		        type: 'post',
		        dataType : "json",
		        data: {"phoneNumber": phoneNumber,
		        		"bizCode":bizCode},
		        success: function (resp) {
//		            if (!resp.status) {
//		            	$.toptip(resp.smg);
//		            }else{
//		            	$.toast("发送成功");
//		            }
		        }
		    });
		},
};

//* tooltips
gebo_tips = {
	init: function() {
		if(!is_touch_device()){
			var shared = {
			style		: {
					classes: 'ui-tooltip-shadow ui-tooltip-tipsy'
				},
				show		: {
					delay: 100
				},
				hide		: {
					delay: 0
				}
			};
			if($('.ttip_b').length) {
				$('.ttip_b').qtip( $.extend({}, shared, {
					position	: {
						my		: 'top center',
						at		: 'bottom center',
						viewport: $(window)
					}
				}));
			}
			if($('.ttip_t').length) {
				$('.ttip_t').qtip( $.extend({}, shared, {
					position: {
						my		: 'bottom center',
						at		: 'top center',
						viewport: $(window)
					}
				}));
			}
			if($('.ttip_l').length) {
				$('.ttip_l').qtip( $.extend({}, shared, {
					position: {
						my		: 'right center',
						at		: 'left center',
						viewport: $(window)
					}
				}));
			}
			if($('.ttip_r').length) {
				$('.ttip_r').qtip( $.extend({}, shared, {
					position: {
						my		: 'left center',
						at		: 'right center',
						viewport: $(window)
					}
				}));
			};
		}
	}
};
	
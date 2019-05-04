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

Date.prototype.pattern=function(fmt) {                                                                                                                                                                                                                       
    var o = {                                                                                                                                                                                                                                                
    "M+" : this.getMonth()+1, //月份                                                                                                                                                                                                                         
    "d+" : this.getDate(), //日                                                                                                                                                                                                                              
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时                                                                                                                                                                                         
    "H+" : this.getHours(), //小时                                                                                                                                                                                                                           
    "m+" : this.getMinutes(), //分                                                                                                                                                                                                                           
    "s+" : this.getSeconds(), //秒                                                                                                                                                                                                                           
    "q+" : Math.floor((this.getMonth()+3)/3), //季度                                                                                                                                                                                                         
    "S" : this.getMilliseconds() //毫秒                                                                                                                                                                                                                      
    };                                                                                                                                                                                                                                                       
    var week = {                                                                                                                                                                                                                                             
    "0" : "/u65e5",                                                                                                                                                                                                                                          
    "1" : "/u4e00",                                                                                                                                                                                                                                          
    "2" : "/u4e8c",                                                                                                                                                                                                                                          
    "3" : "/u4e09",                                                                                                                                                                                                                                          
    "4" : "/u56db",                                                                                                                                                                                                                                          
    "5" : "/u4e94",                                                                                                                                                                                                                                          
    "6" : "/u516d"                                                                                                                                                                                                                                           
    };                                                                                                                                                                                                                                                       
    if(/(y+)/.test(fmt)){                                                                                                                                                                                                                                    
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));                                                                                                                                                                    
    }                                                                                                                                                                                                                                                        
    if(/(E+)/.test(fmt)){                                                                                                                                                                                                                                    
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);                                                                                                                   
    }                                                                                                                                                                                                                                                        
    for(var k in o){                                                                                                                                                                                                                                         
        if(new RegExp("("+ k +")").test(fmt)){                                                                                                                                                                                                               
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));                                                                                                                                         
        }                                                                                                                                                                                                                                                    
    }                                                                                                                                                                                                                                                        
    return fmt;                                                                                                                                                                                                                                              
}         

Number.prototype.toPercent = function(){
	return Math.round(this * 10000)/100 + '%';
}
 

$(document).ready(function() {
		PayCommon.init();
});
    
PayCommon = {
		
	safeCodeElements: [],	
	
	safeCodeCallback: null,
		
	init: function(){
		$('#userPWdSubmitBtn').on('click', PayCommon.updatePwdCommit);
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
	            url: Helper.getRootPath() + '/sys/user/updatePwdCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "oldPasswrod" :  $.md5(oldPasswrodpage), 
	                "newPasswordPage" : $.md5(newPasswordPage), 
	                "newPassword2Page" : $.md5(newPassword2Page)
	            },
	            success: function (data) {
	            	if(data.status) {
	            		Helper.alert("密码修改成功");
	                } else {
	                	Helper.alert(data.msg);
	                	return false;
	                }
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
		    });
		});
	},
	
	calculate: function(){
		var val  = "";
		try{
			val = eval(Calc.calculate_input.value);
			val = Math.round( val * 1000)/1000;
			Calc.calculate_input.value = val;
		}catch(e){
			Helper.alert("输入的表达式不合法！");
			$('#calculate_input').focus();
		}
	},

	loadSafeCodeModal: function(callback){
		
		 PayCommon.safeCodeCallback = callback;
		
         $('#safe_code_modal').modal({
          	backdrop : "static"
         });
          
  		$("#safe_code_modal").on("hidden.bs.modal", function(e) {
  			PayCommon.resetTipForm();
  		});
  		
  		var encryCode = $.trim($("#user_encry_code").val());
  		if(encryCode ==''){
  			Helper.getUserEncryCode('user_encry_code');
  		}
	},


	genAuthCodeImg: function(){
		var loginImg = Helper.genAuthCode(11);
		$("#auth_code_img").attr('src', loginImg);
	},
	resetTipForm : function(){
		$.each(PayCommon.safeCodeElements, function(i, val){      
				val.qtip('destroy');
		  });   
	}
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
	
	var Calculator = {
			cleanInput : function(){
				Calc.Input.value = '';
			},
			
			addDigit : function(e){
				var val =  $(this).val();
				Calc.Input.value += val;
			},
			
			addOperator : function(e){
				var val =  $(this).val();
				Calc.Input.value +=  ' ' + val + ' ';
			},
			
			calculate : function(){
				Calc.Input.value = Math.round( eval(Calc.Input.value) * 1000)/1000;
			}
		};
	
	

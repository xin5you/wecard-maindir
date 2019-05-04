$(document).ready(function () {
	CardIn.init();
});

var CardIn = {
	init:function(){
		CardIn.initEvent();
	},
	initEvent: function () {
	    $('#mobile').on('keyup', CardIn.mobileValid);
	    $('.phone_fare').on('click', CardIn.checkPhoneAmount);
	    $('#phoneRecharge').on('click', CardIn.phoneRecharge);
	},
	mobileValid:function(){
	    var mobile = $.trim($('#mobile').val());
	    var reg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;	//验证手机号
	    if (!mobile) {
	    	$('.phone_fare').attr('class','phone_fare');
	    	$(".mynumber").text('');
	    	$('.phone_fare').find('p.phone_fare_price span').text('');
	    	return false;
	    }
	    if (mobile.length != 11) {
	    	$(".mynumber").text('');
	    	$('.phone_fare').attr('class','phone_fare');
	    	$('.phone_fare').find('p.phone_fare_price span').text('');
	    	return false;
	    } else {
	    	if(!reg.test(mobile)){
		    	$('.phone_fare').attr('class','phone_fare');
		    	$(".mynumber").text('');
		    	$('.phone_fare').find('p.phone_fare_price span').text('');
		    	jfShowTips.toastShow({'text' : '请输入正确的手机号'});
		    	return false;
			}
	    }
	    	
	    $.ajax({
	    	url : CONETXT_PATH + '/phoneRecharge/phoneRechargeMobileValid.html',
	    	type : 'post',
	    	dataType : "json",
	    	data : {
	    		mobile : mobile
	    	},
	    	success : function(data) {
	    		if (data != null) {
	    			$.each(data, function(i,o){
	    				$(".mynumber").text(o.province+" "+o.oper);
	    				$('.phone_fare[data=' + o.shopFace + ']').addClass('can_checked').attr('value',o.id);
	    				$($('.phone_fare[data=' + o.shopFace + ']').find('p.phone_fare_price span')[0]).text('售价 ' + o.shopPrice + '元');
	    			});
	    		} else {
	    			$(".mynumber").text('');
	    			$('.phone_fare').attr('class','phone_fare');
	    			$('.phone_fare').attr('class','phone_fare').attr('value','');;
	    			$('.phone_fare').find('p.phone_fare_price span').text('');
	    		}
	    	}
	    });
	},
	checkPhoneAmount:function(){
	    	if (!$(this).hasClass('can_checked') && !$(this).hasClass('checked_orange')) {
	    		return false;
	    	}
	    	var rechargeShopId = $(this).attr("value");
	    	var type = $(this).attr("phoneRe");
	    	$('#rechargeShopId').val(rechargeShopId);
	    	$('#type').val(type);
	    },
	    phoneRecharge:function(){
	    	if (!$('.phone_fare').hasClass('checked_orange')) {
	    		jfShowTips.toastShow({'text' : '请选择充值项'});
	    		return false;
	    	}
	    	var mobile = $.trim($('#mobile').val());
	    	var rechargeShopId = $('#rechargeShopId').val();
	    	var type = $('#type').val();
	    	if (!mobile) {
    			jfShowTips.toastShow({'text' : '请输入手机号'});
    			return false;
    		}
    		if (!rechargeShopId || !type) {
    			jfShowTips.toastShow({'text' : '请选择充值项'});
    			return false;
    		}
    		$('#phoneRecharge').attr("disabled",true); 
    		loadingChange.showLoading();
    		window.location.href=CONETXT_PATH+'/phoneRecharge/phoneRechargeCommit.html?mobile='+mobile+'&rechargeShopId='+rechargeShopId+'&type='+type;
	    }
}


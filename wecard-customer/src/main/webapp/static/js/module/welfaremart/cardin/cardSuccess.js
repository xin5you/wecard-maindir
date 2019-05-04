$(document).ready(function () {
	CardSuccess.init();
});

var CardSuccess = {
		init:function(){
			CardSuccess.initEvent();
		},
	    initEvent: function () {
	    	$('.pay_success_btn').on('click', CardSuccess.backHtml);
	    },
	    backHtml:function(){
	    	var check = $('#check').val();
	    	if (check == "phoneRecharge") {
	    		window.location.href = CONETXT_PATH + '/phoneRecharge/toPhoneRecharge.html';
	    	} else if (check == "cardRecharge") {
	    		window.location.href = CONETXT_PATH + '/welfareMart/toWelfareMartHomePage.html';
	    	}
	    }
}


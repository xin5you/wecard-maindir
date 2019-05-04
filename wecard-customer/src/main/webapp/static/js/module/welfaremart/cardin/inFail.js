$(document).ready(function () {
	InFail.init();
});

var InFail = {
		init:function(){
			InFail.initEvent();
		},
	    initEvent: function () {
	    	$('.pay_success_btn').on('click', InFail.backHtml);
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


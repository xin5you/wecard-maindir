$(function () {
	var cardBagList = {
			 init: function () {
		        	//关闭页面
		        	$('.card_blank_text').bind("click", function(){
		        		location = CONETXT_PATH + '/customer/surround/surroundPreferentialPage.html';
			        });
		        },
	};
	
	cardBagList.init();
});
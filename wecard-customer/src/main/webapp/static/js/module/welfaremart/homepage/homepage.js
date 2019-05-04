$(document).ready(function () {
	homePage.init();
});

var homePage = {
		init:function(){
			homePage.initEvent();
		},
		initEvent: function () {
			$('.number_right').on("click", homePage.toCardBagList);
			$('.number_left').on("click", homePage.toCardTransList);
		},
		toCardBagList:function(){
			location.href = CONETXT_PATH + '/welfareMart/welfareCardBagList.html';
		},
		toCardTransList:function(){
			var mchntCode = $('#mchntCode').val();
			window.location.href = CONETXT_PATH + '/customer/card/cardTransList.html?innerMerchantNo='+mchntCode;
		}
}
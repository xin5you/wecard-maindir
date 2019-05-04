$(document).ready(function () {
	viewPaymentChannelApiInf.init();
})

var viewPaymentChannelApiInf = {
	init:function(){
		$('#addSubmitBtn').on('click', viewPaymentChannelApiInf.viewPaymentChannelApiInf);
	},
    viewPaymentChannelApiInf:function(){
    	var channelId = $("#channelId").val();
		location = Helper.getRootPath() + '/channel/paymentChannelApi/listPaymentChannelApi.do?channelId='+channelId;
    }
};


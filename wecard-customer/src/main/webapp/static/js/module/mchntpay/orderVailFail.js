$(function () {
	
	var payFailed = {
        init: function () {
        	//关闭页面
        	$('.main_btn').bind("click", function(){
	        	wx.closeWindow();
	        });
        },
    };
	payFailed.init();
});
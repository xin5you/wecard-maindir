$(function () {
	var pageManager = {
        init: function () {
            $.ajax({
        		url : CONETXT_PATH + '/wxapi/jsTicket.html' ,
        		type : 'post',
    	        dataType : "json",
        	    data:{url : location.href.split('#')[0]},
        	    success:function(data){
        	    	var ajaxobj=data;
        	    	var errcode = ajaxobj.errcode;
        	    	var sign = ajaxobj.data;
        	    	if(errcode == 0){//没有错误
        	    		wx.config({
        	    		    debug: false,//true的时候可以alert信息 
        	    		    appId: sign.appId,
        	    		    timestamp: sign.timestamp,
        	    		    nonceStr: sign.nonceStr, 
        	    		    signature: sign.signature,
        	    		    jsApiList: ['hideOptionMenu'] //使用接口时，这里必须先声明 
        	    		});
        	    		wx.ready(function(){
                    		wx.hideOptionMenu();// 隐藏右上角菜单接口
                    	});
        	    	}
        	    }
        	});
        },
        
        push: function (config) {
            this._configs.push(config);
            return this;
        }
    };
        
    pageManager.init();
        
    //$("#barcode").empty().barcode($("#openid").val(), "code128",{barWidth:1, barHeight:100, showHRI:true});
    //$('#code').qrcode("123|321");
    $("#qrcode").qrcode({ 
        render : "canvas",    					//设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
        text : $("#pay_url").val(),  			//扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
        width : "220",               			//二维码的宽度
        height : "220",              			//二维码的高度
        background : "#ffffff",      			//二维码的后景色
        foreground : "#000000",      			//二维码的前景色
        src: CONETXT_PATH+'/res/image/hkb_logo.jpg' //二维码中间的图片
    });
});
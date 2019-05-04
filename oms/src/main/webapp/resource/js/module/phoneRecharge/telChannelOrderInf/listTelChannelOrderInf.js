$(document).ready(function() {
	listTelChannelOrderInf.init();
})

var listTelChannelOrderInf = {
	init : function() {
		listTelChannelOrderInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-search').on('click', listTelChannelOrderInf.searchData);
		$('.btn-reset').on('click', listTelChannelOrderInf.searchReset);
		$('.btn-again-submit').on('click', listTelChannelOrderInf.callBackNotifyChannel);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/channel/channelOrder/listTelChannelOrderInf.do';
	},
	callBackNotifyChannel: function(){
		var channelOrderId = $(this).attr('channelOrderId');
		Helper.confirm("是否重新回调通知分销商？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/channel/channelOrder/doCallBackNotifyChannel.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "channelOrderId": channelOrderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		listTelChannelOrderInf.searchReset();
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	
	}
}

$(document).ready(function() {
	listPaymentChannelApiInf.init();
})

var listPaymentChannelApiInf = {

	init : function() {
		listPaymentChannelApiInf.initEvent();
//		var operStatus=$("#operStatus").val();
//		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listPaymentChannelApiInf.editPaymentChannelApiInf);
		$('.btn-delete').on('click', listPaymentChannelApiInf.deletePaymentChannelApiInfById);
		$('.btn-add').on('click', listPaymentChannelApiInf.addPaymentChannelApiInf);
		$('.btn-view').on('click', listPaymentChannelApiInf.intoViewPaymentChannelApiInf);
		$('.btn-reset').on('click', listPaymentChannelApiInf.searchReset);
	},
	searchReset:function(){
		var channelId = $("#channelId").val();
		location = Helper.getRootPath() + '/channel/paymentChannelApi/listPaymentChannelApi.do?channelId='+channelId;
	},
	addPaymentChannelApiInf:function(){
		var channelId = $("#channelId").val();
		var url = Helper.getRootPath()+"/channel/paymentChannelApi/intoAddPaymentChannelApiInf.do?channelId="+channelId;
		location.href=url;
	},
	editPaymentChannelApiInf:function(){
		var id = $(this).attr('id');
		var channelId = $("#channelId").val(); 
		var url = Helper.getRootPath()+"/channel/paymentChannelApi/editPaymentChannelApiInf.do?id="+id+"&channelId="+channelId;
		location.href=url;
	},
	intoViewPaymentChannelApiInf:function(){
		var id = $(this).attr('id');
		var url = Helper.getRootPath()+"/channel/paymentChannelApi/intoViewPaymentChannelApiInf.do?id="+id;
		location.href=url;
	},
	deletePaymentChannelApiInfById:function(){
		var id = $(this).attr('id');
		var channelId = $("#channelId").val(); 
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/channel/paymentChannelApi/deletePaymentChannelApiInfById.do?id='+id,
	            type: 'post',
	            dataType : "json",
	            data: {
	                "id": id
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/channel/paymentChannelApi/listPaymentChannelApi.do?channelId='+channelId;
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

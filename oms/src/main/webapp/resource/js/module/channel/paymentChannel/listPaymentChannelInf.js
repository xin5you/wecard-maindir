$(document).ready(function() {
	listPaymentChannelInf.init();
})

var listPaymentChannelInf = {

	init : function() {
		listPaymentChannelInf.initEvent();
//		var operStatus=$("#operStatus").val();
//		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listPaymentChannelInf.editPaymentChannelInf);
		$('.btn-delete').on('click', listPaymentChannelInf.deletePaymentChannelInfById);
		$('.btn-add').on('click', listPaymentChannelInf.addPaymentChannelInf);
		$('.btn-view').on('click', listPaymentChannelInf.intoViewPaymentChannelInf);
		$('.btn-reset').on('click', listPaymentChannelInf.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/channel/paymentChannel/listPaymentChannel.do';
	},
	addPaymentChannelInf:function(){
		var url = Helper.getRootPath()+"/channel/paymentChannel/intoAddPaymentChannelInf.do";
		location.href=url;
	},
	editPaymentChannelInf:function(){
		var id = $(this).attr('id');
		var url = Helper.getRootPath()+"/channel/paymentChannel/editPaymentChannelInf.do?id="+id;
		location.href=url;
	},
	intoViewPaymentChannelInf:function(){
		var id = $(this).attr('id');
		var url = Helper.getRootPath()+"/channel/paymentChannelApi/listPaymentChannelApi.do?channelId="+id;
		location.href=url;
	},
	deletePaymentChannelInfById:function(){
		var id = $(this).attr('id');
		$.ajax({								  
            url: Helper.getRootPath() + '/channel/paymentChannel/checkPaymentChannelInf.do?id='+id,
            type: 'post',
            dataType : "json",
            data: {
                "id": id
            },
            success: function (result) {
            	if(result.status){
            		Helper.confirm("您是否删除该记录？",function(){
            		    $.ajax({								  
            	            url: Helper.getRootPath() + '/channel/paymentChannel/deletePaymentChannelInfById.do?id='+id,
            	            type: 'post',
            	            dataType : "json",
            	            data: {
            	                "id": id
            	            },
            	            success: function (result) {
            	            	if(result.status){
            	            		location.href=Helper.getRootPath() + '/channel/paymentChannel/listPaymentChannel.do';
            	            	}else{
            	            		Helper.alert(result.msg);
            	            	}
            	            },
            	            error:function(){
            	            	Helper.alert("系统故障，请稍后再试");
            	            }
            	      });
            		});
            	}else{
            		Helper.alert(result.msg);
            	}
            },
            error:function(){
            	Helper.alert("系统故障，请稍后再试");
            }
      });
	}
}

$(document).ready(function() {
	listTelChannelProduct.init();
})

var listTelChannelProduct = {
	init : function() {
		listTelChannelProduct.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listTelChannelProduct.intoEditTelChannelProduct);
		$('.btn-delete').on('click', listTelChannelProduct.deleteTelChannelProductCommit);
		$('.btn-add').on('click', listTelChannelProduct.intoAddTelChannelProduct);
		$('.btn-view').on('click', listTelChannelProduct.intoViewTelChannelProduct);
		$('.btn-search').on('click', listTelChannelProduct.searchData);
		$('.btn-reset').on('click', listTelChannelProduct.searchReset);
//		$('.btn-grant-role').on('click', listTelChannelProduct.intoAddChannelItemList);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/channel/product/listTelChannelProduct.do';
	},
	intoAddTelChannelProduct:function(){
		var url = Helper.getRootPath()+"/channel/product/intoAddTelChannelProduct.do";
		location.href=url;
	},
	intoEditTelChannelProduct:function(){
		var productId = $(this).attr('productId');
		var url = Helper.getRootPath()+"/channel/product/intoEditTelChannelProduct.do?productId="+productId;
		location.href=url;
	},
	intoViewTelChannelProduct:function(){
		var productId = $(this).attr('productId');
		var url = Helper.getRootPath()+"/channel/product/intoViewTelChannelProduct.do?productId="+productId;
		location.href=url;
	},
//	intoAddChannelItemList:function(){
//		var productId = $(this).attr('productId');
//		var url = Helper.getRootPath()+"/channel/product/intoAddChannelItemList.do?productId="+productId;
//		location.href=url;
//	},
	deleteTelChannelProductCommit:function(){
		var productId = $(this).attr('productId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/channel/product/deleteTelChannelProductCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "productId": productId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/channel/product/listTelChannelProduct.do?operStatus=4';
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

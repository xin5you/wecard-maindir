$(document).ready(function() {
	listPhoneRechargeShop.init();
})

var listPhoneRechargeShop = {
	init : function() {
		listPhoneRechargeShop.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listPhoneRechargeShop.intoEditPhoneRechargeShop);
		$('.btn-delete').on('click', listPhoneRechargeShop.deletePhoneRechargeShopCommit);
		$('.btn-add').on('click', listPhoneRechargeShop.intoAddPhoneRechargeShop);
		$('.btn-view').on('click', listPhoneRechargeShop.intoViewPhoneRechargeShop);
		$('.btn-search').on('click', listPhoneRechargeShop.searchData);
		$('.btn-reset').on('click', listPhoneRechargeShop.searchReset);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/phone/phoneRecharge/listPhoneRechargeShop.do';
	},
	
	intoAddPhoneRechargeShop:function(){
		var url = Helper.getRootPath()+"/phone/phoneRecharge/intoAddPhoneRechargeShop.do";
		location.href=url;
	},
	intoEditPhoneRechargeShop:function(){
		var goodsID = $(this).attr('goodsID');
		var url = Helper.getRootPath()+"/phone/phoneRecharge/intoEditPhoneRechargeShop.do?goodsID="+goodsID;
		location.href=url;
	},
	intoViewPhoneRechargeShop:function(){
		var goodsID = $(this).attr('goodsID');
		var url = Helper.getRootPath()+"/phone/phoneRecharge/intoViewPhoneRechargeShop.do?goodsID="+goodsID;
		location.href=url;
	},
	deletePhoneRechargeShopCommit:function(){
		var goodsID = $(this).attr('goodsID');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/phone/phoneRecharge/deletePhoneRechargeShopCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "goodsID": goodsID
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/phone/phoneRecharge/listPhoneRechargeShop.do?operStatus=4';
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

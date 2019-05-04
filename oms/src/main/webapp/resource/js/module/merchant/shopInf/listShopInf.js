$(document).ready(function() {
	listShopInf.init();
})

var listShopInf = {

	init : function() {
		listShopInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
	
		$('.btn-edit').on('click', listShopInf.intoEditShopInf);
		$('.btn-delete').on('click', listShopInf.deleteShopInfCommit);
		$('.btn-add').on('click', listShopInf.intoAddShopInf);
		$('.btn-view').on('click', listShopInf.intoViewShopInf);
		$('.btn-download').on('click', listShopInf.downloadQrcode);
		$('.btn-reset').on('click', listShopInf.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/merchant/shopInf/listShopInf.do';
	},
	intoAddShopInf:function(){
		var url = Helper.getRootPath()+"/merchant/shopInf/intoAddShopInf.do";
		location.href=url;
	},
	intoEditShopInf:function(){
		var shopId = $(this).attr('shopId');
		var url = Helper.getRootPath()+"/merchant/shopInf/intoEditshopInf.do?shopId="+shopId;
		location.href=url;
	},
	intoViewShopInf:function(){
		var shopId = $(this).attr('shopId');
		var url = Helper.getRootPath()+"/merchant/shopInf/intoViewShopInf.do?shopId="+shopId;
		location.href=url;
	},
	deleteShopInfCommit:function(){
		var shopId = $(this).attr('shopId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/merchant/shopInf/deleteshopInfCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "shopId": shopId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/merchant/shopInf/listShopInf.do?operStatus=4';
	            	}else{
	            		Helper.alert(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	},
	downloadQrcode:function(){
		var shopId = $(this).attr('shopId');
		var form = $("<form>"); //定义一个form表单
        form.attr('style', 'display:none'); //在form表单中添加查询参数
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', Helper.getRootPath() + '/shopQrcodeDownload');

        var input1 = $('<input>');
        input1.attr('type', 'hidden');
        input1.attr('name', 'shopId');
        input1.attr('value', shopId);
        $('body').append(form); //将表单放置在web中 
        form.append(input1); //将查询参数控件提交到表单上
        form.submit();
	}
}

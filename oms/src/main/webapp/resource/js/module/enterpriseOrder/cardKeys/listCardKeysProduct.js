$(document).ready(function() {
	listCardKeysProduct.init();
})

var listCardKeysProduct = {

	init : function() {
		listCardKeysProduct.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-add').on('click', listCardKeysProduct.intoAddCardKeysProduct);
		$('.btn-edit').on('click', listCardKeysProduct.intoEditCardKeysProduct);
		$('.btn-delete').on('click', listCardKeysProduct.deleteCardKeysProductCommit);
		$('.btn-pencil').on('click', listCardKeysProduct.intoPencilCardKeysProduct);
		$('.btn-view').on('click', listCardKeysProduct.intoViewCardKeysProduct);
		$('.btn-search').on('click', listCardKeysProduct.searchData);
		$('.btn-reset').on('click', listCardKeysProduct.searchReset);
		$('.btn-submit').on('click', listCardKeysProduct.pencilCardKeysProductCommit);
	},
	searchData:function(){
		document.forms['searchForm'].submit();
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/cardKeys/listCardKeysProduct.do';
	},
	intoAddCardKeysProduct:function(){
		var url = Helper.getRootPath()+"/cardKeys/intoAddCardKeysProduct.do";
		location.href=url;
	},
	intoEditCardKeysProduct:function(){
		var productCode = $(this).attr('productId');
		var url = Helper.getRootPath()+"/cardKeys/intoEditCardKeysProduct.do?productCode="+productCode;
		location.href=url;
	},
	intoPencilCardKeysProduct:function(){
		var productCode = $(this).attr('productId');
		listCardKeysProduct.loadCardKeysProductModal(productCode);
	},
	intoViewCardKeysProduct:function(){
		var productCode = $(this).attr('productId');
		var url = Helper.getRootPath()+"/cardKeys/intoViewCardKeysProduct.do?productCode="+productCode;
		location.href=url;
	},
	pencilCardKeysProductCommit:function(){
		var productCode = $("#pId").val();
		var isPutaway = $("#ckp_dataStat").val();
		Helper.confirm("确定修改该卡密产品上下架吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/cardKeys/pencilCardKeysProductCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "productCode" : productCode, 
	                "isPutaway" : isPutaway
	            },
	            success: function (data) {
	            	if(data > 0) {
	                	location = Helper.getRootPath() + '/cardKeys/listCardKeysProduct.do';
	                } else {
	                	Helper.alert("系统故障，请稍后再试");
	                	return false;
	                }
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
		    });
		});
	},
	deleteCardKeysProductCommit:function(){
		var productCode = $(this).attr('productId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/cardKeys/deleteCardKeysProductCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "productCode": productCode
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/cardKeys/listCardKeysProduct.do?operStatus=4';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	},
	loadCardKeysProductModal : function(productCode){
		$('#cardKeysProductModal').modal({
			backdrop : "static"
		});
		
		$.ajax({								  
            url: Helper.getRootPath() + '/cardKeys/getProductIsPutaway.do',
            type: 'post',
            dataType : "json",
            data: {
                "productCode": productCode
            },
            success: function (data) {
            	$('#pId').val(data.productCode);
            	$('#ckp_dataStat').val(data.isPutaway);
            },
            error:function(){
            	Helper.alert("系统故障，请稍后再试");
            }
	    });
		
		$("#cardKeysProductModal").on("hidden.bs.modal", function(e) {
			$(".btn-submit").removeAttr('disabled');
		});
	}
}

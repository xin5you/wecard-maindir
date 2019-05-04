$(document).ready(function() {
	listProduct.init();
})

var listProduct = {

	init : function() {
		listProduct.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
	
		$('.btn-edit').on('click', listProduct.intoEditProduct);
		$('.btn-add').on('click', listProduct.intoAddProduct);
		$('.btn-view').on('click', listProduct.intoViewProduct);
		$('.btn-reset').on('click', listProduct.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/merchant/product/listProduct.do';
	},
	intoAddProduct:function(){
		var url = Helper.getRootPath()+"/merchant/product/intoAddProduct.do";
		location.href=url;
	},
	intoEditProduct:function(){
		var productCode = $(this).attr('productCode');
		var url = Helper.getRootPath()+"/merchant/product/intoEditProduct.do?productCode="+productCode;
		location.href=url;
	},
	intoViewProduct:function(){
		var productCode = $(this).attr('productCode');
		var url = Helper.getRootPath()+"/merchant/product/intoViewProduct.do?productCode="+productCode;
		location.href=url;
	}
}

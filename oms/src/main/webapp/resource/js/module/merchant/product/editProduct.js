$(document).ready(function () {
	EditProduct.init();
})

var EditProduct = {

    init: function () {
    	EditProduct.initEvent();
    },
	initEvent:function(){
		$('#returnBtn').on('click', EditProduct.ReturnToProductList);
		$('#editSubmitBtn').on('click', EditProduct.EditProductCommit);
	},
	ReturnToProductList:function(){
		var url = Helper.getRootPath()+"/merchant/product/listProduct.do";
		location.href=url;
	},
	showProductDetail :function(product){
		$('#productName').val(product.productName);
		if(product.onymousStat != null & product.onymousStat != ''){
			$('#productCode').val(product.productCode);
		}else{
			$("#productCode").attr("disabled",true);
		}	
		$('#cardBin').val(product.cardBin);
		if(product.onymousStat != null & product.onymousStat != ''){
			$('#onymousStat').val(product.onymousStat);
		}else{
			$('#onymousStat').val("00");
		}
		if(product.businessType != null & product.businessType != ''){
			$('#businessType').val(product.businessType);
		}else{
			$('#businessType').val("40");
		}
		if(product.productType != null & product.productType != ''){
			$('#productType').val(product.productType);
		}else{
			$('#productType').val("00");
		}
		$('#maxBalance').val(product.maxBalance);
		$('#consumTimes').val(product.consumTimes);
		$('#rechargeTimes').val(product.rechargeTimes);
		if(product.lastCardNum != null & product.lastCardNum != ''){
			$('#lastCardNum').val(product.lastCardNum);
		}else{
			$('#lastCardNum').val("0");
			$("#lastCardNum").attr("disabled",true);
		}
	},
	cleanProductDetail :function(){
		$('#productName').val('');
		$('#productCode').val('');
		$('#cardBin').val('');
		$('#onymousStat').val('');
		$('#businessType').val('');
		$('#productType').val('');
		$('#maxBalance').val('');
		$('#consumTimes').val('');
		$('#rechargeTimes').val('');
		$('#lastCardNum').val('');
	},
    EditProductCommit:function(){
    	var mchntId=$('#mercahnt_select').val();
    	if(mchntId ==''){
    		Helper.alert("请选择所属商户");
    		return false;
    	}
    	var productName=$('#productName').val();
    	if(productName ==''){
    		Helper.alert("请输入产品名称");
    		return false;
    	}
    	$("#mainForm").submit();
    }
};


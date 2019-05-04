$(document).ready(function () {
    AddProduct.init();
})

var AddProduct = {

    init: function () {
    	AddProduct.initEvent();
    },
	initEvent:function(){
		$('#returnBtn').on('click', AddProduct.ReturnToProductList);
		$('#addSubmitBtn').on('click', AddProduct.AddProductCommit);
		$('#mercahnt_select').change(AddProduct.getProductDetail);
	},
	ReturnToProductList:function(){
		var url = Helper.getRootPath()+"/merchant/product/listProduct.do";
		location.href=url;
	},
	getProductDetail:function(){
		var mchntId=$(this).children('option:selected').val();       //获取商户ID
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/product/getProductDetail.do',
            data: {
            		"mchntId" :mchntId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	
                if(data.status) {
                	if(data.product.productCode==null || data.product.productCode == ''){
                		AddProduct.cleanProductDetail();
                		AddProduct.showProductDetail(data.product);
                		$("#addSubmitBtn").removeAttr("disabled");
                	}else{
                		AddProduct.cleanProductDetail();
                		AddProduct.showProductDetail(data.product);
                		Helper.alert("该商户已经添加产品，不允许重复添加");
                		
                		$("#addSubmitBtn").attr("disabled",true);
                	}
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            },
            error:function(){
            	Helper.alert("系统异常，请联系管理员");
            }
        });
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
		$('#remarks').val(product.remarks);
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
		$('#remarks').val('');
	},
    AddProductCommit:function(){
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


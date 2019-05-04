$(document).ready(function () {
    editPhoneRechargeShop.init();
})

var editPhoneRechargeShop = {

    init: function () {
    	editPhoneRechargeShop.initEvent();
    },
	initEvent:function(){
		$('#addSubmitBtn').on('click', editPhoneRechargeShop.editPhoneRechargeShopCommit);
		$('#shopType').on('change',addPhoneRechargeShop.getShopUnit);
	},
    editPhoneRechargeShopCommit:function(){
    	var supplier=$('#supplier').val();
    	if(supplier ==''){
    		Helper.alert("请选择供应商");
    		return false;
    	}
    	var oper=$('#oper').val();
    	if(oper ==''){
    		Helper.alert("请选择运营商");
    		return false;
    	}
    	
    	var shopFace=$('#shopFace').val();
    	if(shopFace ==''){
    		Helper.alert("请输入商品面值");
    		return false;
    	}
    	var shopPrice=$('#shopPrice').val();
    	if(shopPrice ==''){
    		Helper.alert("请输入商品售价");
    		return false;
    	}
    	
    	var shopType=$('#shopType').val();
    	if(shopType ==''){
    		Helper.alert("请选择商品类型");
    		return false;
    	}
    	var resv1=$('#resv1').val();
    	if(resv1 ==''){
    		Helper.alert("请选择商品单位");
    		return false;
    	}
    	$("#mainForm").submit();
    },
    getShopUnit:function(){
    	var shopTypeCode = $(this).val();
    	$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/phone/phoneRecharge/getShopUnit.do',
            data: {
            		"shopTypeCode" : shopTypeCode
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
        		$("#resv1").empty();
        		$("#resv1").append("<option value=''>");
            	$.each(data.list,function(i,d){
            		$("#resv1").append(
            				"<option value='" + this.code + "'>" + this.value + "</option>");
            	});
            }
        });
    }
};


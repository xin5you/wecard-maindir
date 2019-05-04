$(document).ready(function () {
    addShopInf.init();
})

var addShopInf = {

    init: function () {
    	addShopInf.initEvent();
    },
	initEvent:function(){
		$('.btn-map-address').on('click', addShopInf.getLbs);
		$('#addSubmitBtn').on('click', addShopInf.addShopInfCommit);
		$('#mercahnt_select').on('change',addShopInf.bindMchnt);
	},
	getLbs:function(){
		var province=$("#provincePage").find("option:selected").text();
		var city= $("#cityPage").find("option:selected").text();
		var district= $("#districtPage").find("option:selected").text();
    	var address=$("#address").val();

		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/map/getGeocoder.do',
            data: {
            		"province" :province,
            		"city": city,
            		"district":district,
            		"address":address
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-map-address").removeAttr('disabled');
                if(data.status=='0') {
					$("#latitude").val(data.result.location.lat); //纬度
					$("#longitude").val(data.result.location.lng); //经度
                }else{
                	Helper.alert(data.message);
                	return false;
                }
            }
        });
	},
    addShopInfCommit:function(){
    	var mchntId=$('#mercahnt_select').val();
    	if(mchntId ==''){
    		Helper.alert("请选择所属商户");
    		return false;
    	}
    	var shopName=$('#shopName').val();
    	if(shopName ==''){
    		Helper.alert("请输入门店名称");
    		return false;
    	}
    	$("#province").val($("#provincePage").find("option:selected").attr("data-code"));
    	$("#city").val($("#cityPage").find("option:selected").attr("data-code"));
    	$("#district").val($("#districtPage").find("option:selected").attr("data-code"));
    	
    	if($('#longitude').val()=='' || $('#latitude').val()=='' ){
    		Helper.alert("请输入详细地址后获取默认门店经纬度");
    		return
    	}
    	$("#mainForm").submit();

    },
	
	bindMchnt:function(){
		var mc = $('#mercahnt_select').val();
	
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/shopInf/bindMchnt.do',
            data: {
            		"mchntId" : mc
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
        		$("#shop_select").empty();
        		$("#shop_select").append("<option value=''>");
            	$.each(data.shopList,function(i,d){
            		$("#shop_select").append(
            				"<option value='" + this.shopCode + "'>" + this.shopName+"("+this.shopCode+")" + "</option>");
            	});
            }
        });
		
	}
};


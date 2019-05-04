$(document).ready(function () {
    addMerchantInf.init();
})

var addMerchantInf = {

    init: function () {
    	addMerchantInf.initEvent();
    },
	initEvent:function(){
		$('.btn-map-address').on('click', addMerchantInf.getLbs);
		$('#addMerchantInfBtn1').on('click', addMerchantInf.addMerchantInfNext2);
		$('#addMerchantInfBtn2').on('click', addMerchantInf.addMerchantInfNext3);
		$('#addMerchantInfBtn3').on('click', addMerchantInf.addMerchantInfCommit);
	},
	addMerchantInfNext2:function(){
		//新增商户step1. 下一步操作
		$("#addMerchantInf_step2_btn").click();

	},
	addMerchantInfNext3:function(){
		//新增商户step2. 下一步操作
		$("#addMerchantInf_step3_btn").click();
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
    addMerchantInfCommit:function(){
    	var mchntName=$('#mchntName').val();
    	if(mchntName ==''){
    		Helper.alert("请输入商户名称");
    		return false;
    	}
    	var shopName=$('#shopName').val();
    	if(shopName ==''){
    		Helper.alert("请输入默认门店旗舰店名称");
    		return false;
    	}
    	if($('#longitude').val()=='' || $('#latitude').val()=='' ){
    		Helper.alert("请输入详细地址后获取默认门店经纬度");
    		return
    	}
    	var inviteCode=$('#inviteCode').val();
    	if(inviteCode ==''){
    		Helper.alert("请输入商户明细邀请码");
    		return false;
    	}
    	var mchntId="";
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/merchantInf/checkMchtInviteCode.do',
            data: {
            		"inviteCode" :inviteCode,
            		"mchntId":mchntId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-map-address").removeAttr('disabled');
                if(data.status) {
                	$("#province").val($("#provincePage").find("option:selected").attr("data-code"));
                	$("#city").val($("#cityPage").find("option:selected").attr("data-code"));
                	$("#district").val($("#districtPage").find("option:selected").attr("data-code"));
                	$("#merchantForm").submit();
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


$(document).ready(function () {
    addMchntCashManage.init();
})

var addMchntCashManage = {

    init: function () {
    	$('#addSubmitBtn').attr('disabled',true);
    	addMchntCashManage.initEvent();
    },
    reset:function(){
    	$('#addSubmitBtn').on('click', addMchntCashManage.addMerchantCashManageCommit);
    	$('#resetBtn').click();
    },
	initEvent:function(){
		$('#mercahnt_select').change(addMchntCashManage.checkMchtCashManage);
	},
	checkMchtCashManage:function(){
		var mchntId=$('#mercahnt_select').val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/margin/mchntCashManage/checkMchtCashManage.do',
            data: {
            		'mchntId' :mchntId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.status) {
                	$('#addSubmitBtn').removeAttr('disabled');
                	addMchntCashManage.reset();
                	$('#mercahnt_select').val(mchntId);
                }else{
                	Helper.alert(data.msg);
                	if(data.merchantCashManage != undefined){
                     	$('#mortgageFlg').val(data.merchantCashManage.mortgageFlg);
                    	$('#mortgageAmt').val(data.merchantCashManage.mortgageAmt/100);
                    	$('#rechargeAmt').val(data.merchantCashManage.rechargeAmt/100);
                    	$('#getQuota').val(data.merchantCashManage.getQuota/100);
                    	$('#rechargeFeecAmt').val(data.merchantCashManage.rechargeFeecAmt/100);
                	}
                	$('#addSubmitBtn').attr('disabled',true);
                	return false;
                }
            }
        });
	},
	addMerchantCashManageCommit:function(){
		var mchntId=$('#mercahnt_select').val();
    	if(mchntId ==''){
    		Helper.alert("请选择所属商户");
    		return false;
    	}
    	var mortgageFlg=$('#mortgageFlg').val();
    	
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/margin/mchntCashManage/addMerchantCashManageCommit.do',
            data: {
            		'mchntId':mchntId,
            		'mortgageFlg':mortgageFlg
            },
            cache:false,
            dataType: 'json',
            success: function(data){
            	$('.btn-map-address').removeAttr('disabled');
                if(data.status) {
                	Helper.alert("添加成功");
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


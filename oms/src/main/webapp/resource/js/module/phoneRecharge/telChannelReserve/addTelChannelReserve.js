$(document).ready(function () {
    addTelChannelReserve.init();
})

var addTelChannelReserve = {

    init: function () {
    	addTelChannelReserve.initEvent();
    },
	initEvent:function(){
		$('#addSubmitBtn').on('click', addTelChannelReserve.addTelChannelReserveCommit);
	},
    addTelChannelReserveCommit:function(){
    	var channelId=$('#channelId').val();
    	if(channelId ==''){
    		Helper.alert("请选择分销商");
    		return false;
    	}
    	var reserveAmt=$('#reserveAmt').val();
    	if(reserveAmt ==''){
    		Helper.alert("请输入备付金");
    		return false;
    	}
    	var reserveType=$('#reserveType').val();
    	if(reserveType ==''){
    		Helper.alert("请选择备付金类型");
    		return false;
    	}
    	$("#mainForm").submit();
    },
};


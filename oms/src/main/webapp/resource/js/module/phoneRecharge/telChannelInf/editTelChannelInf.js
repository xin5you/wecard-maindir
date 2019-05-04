$(document).ready(function () {
    editTelChannelInf.init();
})

var editTelChannelInf = {

    init: function () {
    	editTelChannelInf.initEvent();
    },
	initEvent:function(){
		$('#addSubmitBtn').on('click', editTelChannelInf.editTelChannelInfCommit);
	},
    editTelChannelInfCommit:function(){
    	var channelName=$('#channelName').val();
    	if(channelName ==''){
    		Helper.alert("请输入分销商名称");
    		return false;
    	}
    	var channelCode=$('#channelCode').val();
    	if(channelCode ==''){
    		Helper.alert("请输入分销商编号");
    		return false;
    	}
    	
    	var channelKey=$('#channelKey').val();
    	if(channelKey ==''){
    		Helper.alert("请输入分销商KEY");
    		return false;
    	}
    	$("#mainForm").submit();
    },
	
};


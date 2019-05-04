$(document).ready(function () {
    editMerchantInf.init();
})

var editMerchantInf = {

    init: function () {
    	editMerchantInf.initEvent();
    },
	initEvent:function(){
		$('#editMerchantInfBtn1').on('click', editMerchantInf.editMerchantInfNext3);
		$('#editMerchantInfBtn3').on('click', editMerchantInf.editMerchantInfCommit);
	},
	editMerchantInfNext3:function(){
		//新增商户step2. 下一步操作
		$("#editMerchantInf_step3_btn").click();
	},
    editMerchantInfCommit:function(){
    	var mchntName=$('#mchntName').val();
    	if(mchntName ==''){
    		Helper.alert("请输入商户名称");
    		return false;
    	}
    	$("#merchantForm").submit();
    }
};


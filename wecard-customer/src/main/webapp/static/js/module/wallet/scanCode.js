$(function () {
	var pageManager = {
		isPayState:true,
        init: function () {
        	pageManager.initEvent();
        },
        initEvent:function(){
        	 $('#pay_next_step2').on('click', pageManager.userConfirmOrder);
        },
        userConfirmOrder:function(){
        	var payAmount = $('#key_input').val();
        	$('#payAmount').val(payAmount);
        	if(payAmount==null || payAmount.trim()==''){
        		$('#checkViews').html("请输入正确金额");
        		$('#checkViews').show();
        		return false;
        	}
        	if(payAmount <= 0){
        		$('#checkViews').html("请输入正确金额");
        		$('#checkViews').show();
        		return false;
        	}
        	$('#myForm').submit();
        	
        }
	}
    pageManager.init();
});
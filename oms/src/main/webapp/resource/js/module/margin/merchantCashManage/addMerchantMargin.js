$(document).ready(function () {
    addMerchantMargin.init();
})

var addMerchantMargin = {
	applyStat:10,
    init: function () {
    	addMerchantMargin.initTip();
    	$('#submitApplyBtn').on('click', addMerchantMargin.addMerchntMarginCommit20);
    	$('#addSaveBtn').on('click', addMerchantMargin.addMerchntMarginCommit10);
    	$('#backBtn').on('click', addMerchantMargin.goBack);
    },
    goBack:function(){
    	var chashId=$("#chashId").val();
    	var mchntId=$("#mchntId").val();
    	location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMerchantMargin.do?chashId='+chashId;
    },
    addMerchntMarginCommit20:function(){
    	addMerchantMargin.applyStat=20;
    	$("#mainForm").submit();
    },
    addMerchntMarginCommit10:function(){
    	addMerchantMargin.applyStat=10;
    	$("#mainForm").submit();
    },
    getapplyStat:function(flag){
    	addMerchantMargin.applyStat=flag;
    	
    },
    initTip: function () {
        var ttip_validator = $('.form_validation_tip').validate({
            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            ignore: "",
            highlight: function(element) {
                $(element).closest('div').addClass("f_error");
            },
            unhighlight: function(element) {
                $(element).closest('div').removeClass("f_error");
            },
            rules:{
            	addMortgageAmt: { required: true,isPayAmount:true},
            	addGetQuota: { required: true,isPayAmount:true}
            },
            messages: {
            	addMortgageAmt: { required: "请输入追加金额",isPayAmount:"追加金额请输入正确的金额"},
            	addGetQuota: { required: "请输入追加获取总额度",isPayAmount:"追加获取总额度请输入正确的金额"}
            },
            invalidHandler: function(form, validator) {
                //$.sticky("There are some errors. Please corect them and submit again.", {autoclose : 5000, position: "top-right", type: "st-error" });
            },
            errorPlacement: function(error, element) {
                // Set positioning based on the elements position in the form
                var elem = $(element);

                var isPlaceholder = Helper.isPlaceholderSupported();
                // Check we have a valid error message
                if (!error.is(':empty')) {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        // Apply the tooltip only if it isn't valid
                        elem.filter(':not(.valid)').parent('label').parent('div').find('.error_placement').qtip({
                                overwrite: false,
                                content: error,
                                position: {
                                    my: 'left center',
                                    at: 'center right',
                                    viewport: $(window),
                                    adjust: {
                                        x: 6
                                    }
                                },
                                show: {
                                    event: false,
                                    ready: true
                                },
                                hide: false,
                                style: {
                                    classes: 'ui-tooltip-red ui-tooltip-rounded' // Make it red... the classic error colour!
                                }
                            })
                            // If we have a tooltip on this element already, just update its content
                            .qtip('option', 'content.text', error);
                    } else {
                        var xPoint = 5;
           
                        elem.filter(':not(.valid)').qtip({
                                overwrite: false,
                                content: error,
                                position: {
                                    my: 'left center',
                                    at: 'center right',
                                    viewport: $(window),
                                    adjust: { x: xPoint, y: 0 }
                                },
                                show: {
                                    event: false,
                                    ready: true
                                },
                                hide: false,
                                style: {
                                    classes: 'ui-tooltip-red ui-tooltip-rounded' // Make it red... the classic error colour!
                                }
                            })
                            // If we have a tooltip on this element already, just update its content
                            .qtip('option', 'content.text', error);

                    };
                }
                // If the error is empty, remove the qTip
                else {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.parent('label').parent('div').find('.error_placement').qtip('destroy');
                    } else {
                        elem.qtip('destroy');
                    }
                }
            },
            submitHandler: function(form) {
            	addMerchantMargin.addMerchantMarginCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    addMerchantMarginCommit:function(){
    	var chashId=$("#chashId").val();
    	var addMortgageAmt=$("#addMortgageAmt").val();
    	var addGetQuota=$("#addGetQuota").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/margin/mchntCashManage/addMerchantMarginCommit.do',
            data: {
            		"chashId":chashId,
            		"addMortgageAmt": addMortgageAmt,
            		"addGetQuota":addGetQuota,
            		"approveStat":addMerchantMargin.applyStat
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/margin/mchntCashManage/listMerchantMargin.do?chashId='+chashId+'&operStatus=1';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


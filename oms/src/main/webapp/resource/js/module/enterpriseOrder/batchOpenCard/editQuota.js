$(document).ready(function() {
	editQuota.init();
})



var editQuota = {
	
	init : function() {
		editQuota.initTip();
	},
	
	initTip:function(){

		var ttip_validator = $('.form_validation_tip').validate({

            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            highlight: function (element) {
                $(element).closest('div').addClass("f_error");
            },
            unhighlight: function (element) {
                $(element).closest('div').removeClass("f_error");
            },
            rules: {
            	maxQuota: { required: true }
            },
            messages: {
            	maxQuota: {
                    required: "请输入网上交易限额"
                }
            },
            invalidHandler: function (form, validator) {
                //$.sticky("There are some errors. Please corect them and submit again.", {autoclose : 5000, position: "top-right", type: "st-error" });
            },
            errorPlacement: function (error, element) {
                var elem = $(element);
                if (!error.is(':empty')) {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
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
                        }).qtip('option', 'content.text', error);
                    } else {
                        var xPoint = 5;
                        /*if (elem.attr('name') == 'authCode'){//特殊处理验证码
                            xPoint = 115;
                        }*/
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
                                classes: 'ui-tooltip-red ui-tooltip-rounded' 
                            }
                        }).qtip('option', 'content.text', error);
                    };
                } else {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.parent('label').parent('div').find('.error_placement').qtip('destroy');
                    } else {
                        elem.qtip('destroy');
                    }
                }
            },
            submitHandler: function (form) {
            	editQuota.editQuotaCommit();
            },
            success: $.noop 
        
		});
//		var uploadMainForm = $('#uploadMainForm').validate({
//
//            onkeyup: false,
//            errorClass: 'error',
//            validClass: 'valid',
//            
//            submitHandler: function (form) {
//            	addOpenCard.accountImport();
//            },
//            success: $.noop 
//        
//		});
		
		
		
	},
	
	editQuotaCommit:function(){
		Helper.confirm("您确定修改网上交易限额吗",function(){
			$("#pageMainForm").ajaxSubmit({
				type:'post', // 提交方式 get/post
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/editQuotaCommit.do', // 需要提交的 url
	            dataType: 'json',
	            data: {
	            	"orderId":$("#orderId").val(),
	            	"maxQuota":$("#maxQuota").val()
				},
				success: function(data){
					if(data.status) {
	                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do?operStatus=2';
	                }else{
	                	$('#msg').modal('hide');
	                	Helper.alert(data.msg);
	                	return false;
	                }
				}
			});
		});
	}
}
	
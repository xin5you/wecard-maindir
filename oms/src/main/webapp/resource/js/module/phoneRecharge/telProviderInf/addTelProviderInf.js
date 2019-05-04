$(document).ready(function () {
    addTelProviderInf.init();
})

var addTelProviderInf = {

    init: function () {
    	addTelProviderInf.initTip();
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
            	providerName: { required: true},
            	appUrl: { required: true},
            	appSecret: { required: true},
            	accessToken: { required: true},
            	providerRate: { required: true},
            	operSolr: { required: true}
            },
            messages: {
            	providerName: { required: "请输入供应商名称"},
            	appUrl: { required: "请输入app_url"},
            	appSecret: { required: "请输入app_Secret"},
            	accessToken: { required: "请输入access_token"},
            	providerRate: { required: "请输入供应商折扣"},
            	operSolr: { required: "请输入操作顺序"}
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
            	addTelProviderInf.addTelProviderInfCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    addTelProviderInfCommit:function(){
    	var providerName=$("#providerName").val().trim();
    	var appUrl=$("#appUrl").val().trim();
    	var appSecret=$("#appSecret").val().trim();
    	var accessToken = $('#accessToken').val().trim();
    	var defaultRoute=$("#defaultRoute").val().trim();
    	var providerRate=$("#providerRate").val().trim();
    	var operSolr=$("#operSolr").val().trim();
    	var remarks=$("#remarks").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/provider/providerInf/addTelProviderInfCommit.do',
            data: {
            		"providerName" :providerName,
            		"appUrl": appUrl,
            		"appSecret":appSecret,
            		"accessToken":accessToken,
            		"defaultRoute":defaultRoute,
            		"providerRate":providerRate,
            		"operSolr":operSolr,
            		"remarks":remarks
            },
            cache:false,
            dataType: 'json',
            success: function(data){
            	$("#addSubmitBtn").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/provider/providerInf/listTelProviderInf.do?operStatus=1';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


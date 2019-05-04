$(document).ready(function () {
    editTelChannelProductRate.init();
})

var editTelChannelProductRate = {

    init: function () {
    	editTelChannelProductRate.initTip();
    	$('.btn-back').on('click', editTelChannelProductRate.backBackTelChannelProductRateList);
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
            	channelRate: { required: true}
            },
            messages: {
            	channelRate: { required: "请输入折扣率"}
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
            	editTelChannelProductRate.editTelChannelProductRateCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    editTelChannelProductRateCommit:function(){
    	var channelId = $("#channelId").val();
    	var itemId=$("#itemId").val();
    	var channelRate = $("#channelRate").val();
    	var reg = /^0\.{1}\d{1,4}$/;
    	if(!(reg.test(channelRate))){
    		Helper.alert("请输入正确的折扣率：例如：折扣率是99.94，就输入0.9994");
        	return false;
    	}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/channel/channelInf/editTelChannelProductRateCommit.do',
            data: {
            	"itemId" :itemId,
        		"channelRate":channelRate
            },
            cache:false,
            dataType: 'json',
            success: function(data){
            	$("#addSubmitBtn").removeAttr('disabled');
                if(data.status) {
                	var productId = $("#productId").val();
            		location = Helper.getRootPath() + '/channel/channelInf/intoAddTelChannelRate.do?channelId='+channelId;
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    },
    backBackTelChannelProductRateList:function(){
    	var channelId = $("#channelId").val();
    	var url = Helper.getRootPath()+"/channel/channelInf/intoAddTelChannelRate.do?channelId="+channelId;
		location.href=url;
    }
    
};


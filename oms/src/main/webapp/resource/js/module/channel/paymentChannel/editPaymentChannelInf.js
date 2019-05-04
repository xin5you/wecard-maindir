$(document).ready(function () {
	editPaymentChannelInf.init();
})

var editPaymentChannelInf = {
	init:function(){
		editPaymentChannelInf.initTip();
		$('.btn-search').on('click', editPaymentChannelInf.searchReset);
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
            	channelName: { required: true},
            	rate: { required: true},
            	channelType: { required: true}
            },
            messages: {
            	channelName: { required: "请输入通道名称"},
            	rate: { required: "请输入万分比的整数"},
            	channelType: { required: "请选择通道类型"}
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
            	editPaymentChannelInf.editPaymentChannelInfCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    searchReset:function(){
		location = Helper.getRootPath() + '/channel/paymentChannel/listPaymentChannel.do';
	},
    editPaymentChannelInfCommit:function(){
		var channelName = $('#channelName').val();
		var rate = $('#rate').val();
		var channelType = $('#channelType').val();
		var description = $('#description').val();
		var id = $('#id').val();
		var enable = $('#enable').val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/channel/paymentChannel/editPaymentChannelInfCommit.do',
            data: {
            		'id' :id,
            		'channelName' :channelName,
            		'rate' :rate,
            		'channelType' :channelType,
            		'description':description,
            		'enable':enable
            	},
            dataType: 'json',
            success: function(data){
            	if(data.status) {
                	location.href=Helper.getRootPath() + '/channel/paymentChannel/listPaymentChannel.do';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	}
};


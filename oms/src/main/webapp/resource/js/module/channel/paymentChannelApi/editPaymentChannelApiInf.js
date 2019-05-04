$(document).ready(function () {
	editPaymentChannelApiInf.init();
})

var editPaymentChannelApiInf = {
	init:function(){
		editPaymentChannelApiInf.initTip();
		$('.btn-search').on('click', editPaymentChannelApiInf.searchReset);
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
            	name: { required: true},
            	url: { required: true},
            	apiType: { required: true}
            },
            messages: {
            	name: { required: "请输入通道API名称"},
            	url: { required: "请输入通道API地址"},
            	apiType: { required: "请选择通道API类型"}
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
            	editPaymentChannelApiInf.editPaymentChannelApiInfCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    searchReset:function(){
		var channelId = $("#channelId").val();
		location = Helper.getRootPath() + '/channel/paymentChannelApi/listPaymentChannelApi.do?channelId='+channelId;
	},
    editPaymentChannelApiInfCommit:function(){
    	var name = $('#name').val();
		var url = $('#url').val();
		var apiType = $('#apiType').val();
		var description = $('#description').val();
		var channelId = $("#channelId").val();
		var id = $('#id').val();
		var enable = $('#enable').val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/channel/paymentChannelApi/editPaymentChannelApiInfCommit.do',
            data: {
            		'id' :id,
            		'channelId' : channelId,
            		'name' :name,
            		'url' :url,
            		'apiType' :apiType,
            		'description':description,
            		'enable': enable
            	},
            dataType: 'json',
            success: function(data){
            	if(data.status) {
                	location.href=Helper.getRootPath() + '/channel/paymentChannelApi/listPaymentChannelApi.do?channelId='+channelId;
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	}
};


$(document).ready(function () {
    editTelChannelProduct.init();
})

var editTelChannelProduct = {

    init: function () {
    	editTelChannelProduct.initTip();
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
            	operName: { required: true},
            	operId: { required: true},
            	areaFlag: { required: true},
            	productAmt: { required: true},
            	productPrice: { required: true},
            	productType: { required: true}
            },
            messages: {
            	operName: { required: "请输入运营商产品名称"},
            	operId: { required: "请选择运营商"},
            	areaFlag: { required: "请选择区分地区标识"},
            	productAmt: { required: "请输入产品面额"},
            	productPrice: { required: "请输入产品售价"},
            	productType: { required: "请选择产品类型"}
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
            	editTelChannelProduct.editTelChannelProductCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    editTelChannelProductCommit:function(){
    	var productId=$("#productId").val();
    	var operName=$("#operName").val().trim();
    	var operId=$("#operId").val().trim();
    	var areaFlag=$("#areaFlag").val().trim();
    	var productAmt = $('#productAmt').val().trim();
    	var productPrice=$("#productPrice").val().trim();
    	var productType=$("#productType").val().trim();
    	var remarks=$("#remarks").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/channel/product/editTelChannelProductCommit.do',
            data: {
            		"productId" :productId,
            		"operName" :operName,
            		"operId": operId,
            		"areaFlag":areaFlag,
            		"productAmt":productAmt,
            		"productPrice":productPrice,
            		"productType":productType,
            		"remarks":remarks
            },
            cache:false,
            dataType: 'json',
            success: function(data){
            	$("#addSubmitBtn").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/channel/product/listTelChannelProduct.do?operStatus=2';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


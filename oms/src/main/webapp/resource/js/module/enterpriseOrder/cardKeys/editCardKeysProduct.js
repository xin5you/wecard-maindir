$(document).ready(function () {
	editCardKeysProduct.init();
})

var editCardKeysProduct = {
	init:function(){
		editCardKeysProduct.initTip();
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
            	/*productCode: { required: true},*/
            	productName: { required: true},
            	productType: { required: true},
            	orgAmount: { required: true},
            	productUnit: { required: true},
            	amount: { required: true}
//            	supplier: { required: true}
            },
            messages: {
            	/*productCode: { required: "请输入产品号"},*/
            	productName: { required: "请输入产品名称"},
            	productType: { required: "请选择产品类型"},
            	orgAmount: { required: "请输入面额"},
            	productUnit: { required: "请选择产品单位"},
            	amount: { required: "请输入金额"}
//            	supplier: { required: "请输入供应商"}
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
            	editCardKeysProduct.editCardKeysProductCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    editCardKeysProductCommit:function(){
			$("#mainForm").ajaxSubmit({
				type:'post', // 提交方式 get/post
	            url: Helper.getRootPath() + '/cardKeys/editCardKeysProductCommit.do', // 需要提交的 url
	            dataType: 'json',
	            success: function(data){
	                if(data.status) {
	                	location.href=Helper.getRootPath() + '/cardKeys/listCardKeysProduct.do?operStatus=2';
	                }else{
	                	Helper.alert(data.msg);
	                	return false;
	                }
	            }
		});
	}
};


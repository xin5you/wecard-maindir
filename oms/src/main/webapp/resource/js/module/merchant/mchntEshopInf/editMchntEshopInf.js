$(document).ready(function () {
	editMchntEshopInf.init();
})

var editMchntEshopInf = {
	init:function(){
		editMchntEshopInf.initTip();
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
            	mchntCode: { required: true},
            	shopCode: { required: true},
            	eShopName: { required: true},
            	eShopUrl: { required: true},
            	channelCode:{ required: true}
            },
            messages: {
            	mchntCode: { required: "请选择商户"},
            	shopCode: { required: "请选择门店"},
            	eShopName: { required: "请输入商城名称"},
            	eShopUrl: { required: "请输入要跳转的门店地址"},
            	channelCode:{ required: "请输入渠道号"}
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
            	editMchntEshopInf.editMchntEshopInfCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
	editMchntEshopInfCommit:function(){
			$("#mainForm").ajaxSubmit({
				type:'post', // 提交方式 get/post
	            url: Helper.getRootPath() + '/mchnteshop/eShopInf/editMchntEshopInfCommit.do', // 需要提交的 url
	            dataType: 'json',
	            success: function(data){
	                if(data.status) {
	                	location.href=Helper.getRootPath() + '/mchnteshop/eShopInf/listMchntEshopInf.do?operStatus=2';
	                }else{
	                	Helper.alert(data.msg);
	                	return false;
	                }
	            }
		});
	}
};


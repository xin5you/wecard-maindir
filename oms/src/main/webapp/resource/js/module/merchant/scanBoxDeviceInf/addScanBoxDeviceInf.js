
$(document).ready(function () {
	addScanBoxDeviceInf.init();
})

var addScanBoxDeviceInf = {
	init:function(){
		addScanBoxDeviceInf.initTip();
		addScanBoxDeviceInf.initEvent();
	},
    initEvent: function () {
		$('#mercahnt_select').change(addScanBoxDeviceInf.getShopInfList);
		$('#shopCode1').change(addScanBoxDeviceInf.getShopInfList2);
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
            	deviceType: { required: true},
            	deviceNo: { required: true},
            	mchntCode: { required: true},
            	channelNo: { required: true},
            	shopCode1: { required: true},
            	shopCode: { required: true}
            },
            messages: {
            	deviceType: { required: "请选择设备类型"},
            	deviceNo: { required: "请输入设备号"},
            	mchntCode: { required: "请选择商户"},
            	channelNo: { required: "请选择支付通道"},
            	shopCode1: { required: "请选择所属一级门店"},
            	shopCode: { required: "请选择所属二级门店"}
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
            	addScanBoxDeviceInf.addScanBoxDeviceInfCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    getShopInfList:function(){
		var mchntCode=$('#mercahnt_select').val();
		$("#shopCode1").empty();
		$("#shopCode").empty();
		$('#shopCode1').append('<option value="">--请选择一级门店--</option>');
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/scanBoxDeviceInf/intoAddMchntEshopInfGetShop1.do',
            data: {
            		'mchntCode' :mchntCode
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.status) {
                	for(var i=0;i<data.shopInfList.length;i++){
	                	$('#shopCode1').append('<option value="'+data.shopInfList[i].shopCode+'">'+data.shopInfList[i].shopName+'</option>');
                	}
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	},
	getShopInfList2:function(){
		var shopCode1=$('#shopCode1').val();
		$("#shopCode").empty();
		$('#shopCode').append('<option value="">--请选择二级门店--</option>');
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/scanBoxDeviceInf/intoAddMchntEshopInfGetShop2.do',
            data: {
            		'shopCode1' :shopCode1
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.status) {
                	for(var i=0;i<data.shopInfList.length;i++){
	                	$('#shopCode').append('<option value="'+data.shopInfList[i].shopCode+'">'+data.shopInfList[i].shopName+'</option>');
                	}
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	},
	addScanBoxDeviceInfCommit:function(){
		var fixedPayAmt = $('#fixedPayAmt').val();
		var reg = /^\d{1,10}(\.\d{1,2})?$/;		//验证有1-2位小数的正实数
		if(!reg.test(fixedPayAmt)){
			Helper.alert("定额支付金额最多输入有二位小数的数字，且整数总长度不超过10，请重新输入");
			$('#fixedPayAmt').val("0");
			return false;
		}
		var deviceType = $('#deviceType').val();
		var deviceNo = $('#deviceNo').val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/scanBoxDeviceInf/getScanBoxDeviceInfByDeviceTypeAndDeviceNo.do',
            data: {
            		'deviceType' :deviceType,
            		'deviceNo' :deviceNo,
            		'state' :1,
            		'deviceId':null
            	},
            dataType: 'json',
            success: function(data){
            	if(data.status){
            		$("#mainForm").ajaxSubmit({
        				type:'post', // 提交方式 get/post
        	            url: Helper.getRootPath() + '/merchant/scanBoxDeviceInf/addScanBoxDeviceInfCommit.do', // 需要提交的 url
        	            dataType: 'json',
        	            success: function(data){
        	                if(data.status) {
        	                	location.href=Helper.getRootPath() + '/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do?operStatus=1';
        	                }else{
        	                	Helper.alert(data.msg);
        	                	return false;
        	                }
        	            }
                	 });
            	}else{
            		Helper.alert("同一设备类型下的设备号不允许重复");
        			$('#deviceNo').val("");
            		return false;
            	}
            }
        });
		
		
		
		
	}
};


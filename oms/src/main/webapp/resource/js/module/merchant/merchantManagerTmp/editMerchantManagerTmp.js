
$(document).ready(function () {
    editMerchantManagerTmp.init();
})

var editMerchantManagerTmp = {
    init: function () {
    	editMerchantManagerTmp.initTip();
		$('#mercahnt_select').change(editMerchantManagerTmp.getShopInfList);
		
		var roleType = $("#roleType").val();
		var arry = roleType.split(",");
		if (arry != "" && arry.length > 0) {
			for (var i=0; i<arry.length; i++) {
				$(":checkbox[name='roleType'][value='"+arry[i]+"']").attr("checked", "checked");
			}
		}
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
            	mchntId: { required: true},
            	name: { required: true},
            	phoneNumber: { required: true}
            },
            messages: {
            	mchntId: { required: "请选择商户"},
            	name: { required: "请选择姓名"},
            	phoneNumber: { required: "请选择请输入手机号"}
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
            	editMerchantManagerTmp.editMerchantManagerTmpCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
	getShopInfList:function(){
		var mchntId=$('#mercahnt_select').val();
		$("#shopId").empty();
		$('#shopId').append('<option value="">--全部--</option>');
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/managerTmp/getShopInfListByMchntId.do',
            data: {
            		'mchntId' :mchntId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.status) {
                	for(var i=0;i<data.shopInfList.length;i++){
                		$('#shopId').append('<option value="'+data.shopInfList[i].shopId+'">'+data.shopInfList[i].shopName+'</option>');
                	}
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	},
    editMerchantManagerTmpCommit:function(){
    	var mangerId=$('#mangerId').val();
    	var mchntId=$('#mercahnt_select').val();
    	var shopId=$("#shopId").val();
    	var name=$("#name").val();
    	var phoneNumber=$("#phoneNumber").val();
    	var remarks=$("#remarks").val();
    	var roleType="";
    	$("input[name='roleType']:checkbox:checked").each(function(){ 
    		roleType += $(this).val() + "," 
    	})
    	if (roleType != "" && roleType != null) {
    		roleType = roleType.substring(0, roleType.length-1);
    	}

		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/merchant/managerTmp/editMerchantManagerTmpCommit.do',
            data: {
            		"mangerId":mangerId,
            		"mchntId" :mchntId,
            		"shopId": shopId,
            		"name":name,
            		"phoneNumber":phoneNumber,
            		"roleType":roleType,
            		"remarks":remarks
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/merchant/managerTmp/listMerchantManagerTmp.do?operStatus=1';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


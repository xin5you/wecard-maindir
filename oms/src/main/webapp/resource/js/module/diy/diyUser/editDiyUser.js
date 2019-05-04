$(document).ready(function () {
    editDiyUser.init();
})

var editDiyUser = {

    init: function () {
    	editDiyUser.initTip();
    },

    initTip: function () {
    	editDiyUser.checkRoleId();
    	$('#mchntCode').change(editDiyUser.getShopInfList);//获取门店信息
    	$('#roleIds').change(editDiyUser.checkRoleId);
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
            	userName: { required: true},
            	phoneNo: { required: true},
            	password: { required: true},
            	mchntCode: { required: true},
            	roleIds: { required: true}
            },
            messages: {
            	userName: { required: "请输入用户名"},
            	phoneNo: { required: "请输入手机号码"},
            	mchntCode: { required: "请选择商户"},
            	roleIds: { required: "请选择角色"}
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
            	editDiyUser.editDiyUserCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    checkRoleId : function(){
		var roleId = $('#roleIds').val();
		var roleIdFinance = $('#roleIdFinance').val();
		var roleIdBoss = $('#roleIdBoss').val();
		if(roleId == roleIdFinance){
			$("#shopId").hide();
			$('#shopCode').attr("disabled",true);
//			$('#shopCode').val('0');
		}else if(roleId == roleIdBoss){
			$("#shopId").hide();
			$('#shopCode').attr("disabled",true);
//			$('#shopCode').val('0');
		}else{
			$("#shopId").show();
			$('#shopCode').attr("disabled",false);
		}
	},
    getShopInfList:function(){
		var mchntCode=$('#mchntCode').val();
		$("#shopCode").empty();
		$('#shopCode').append('<option value="0">--全部--</option>');
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/diy/diyUser/intoAddMchntEshopInfGetShop.do',
            data: {
            		'mchntCode' :mchntCode
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
    editDiyUserCommit:function(){
    	var userName=$("#userName").val().trim();
    	var phoneNo=$("#phoneNo").val();
    	var mchntCode=$("#mchntCode").val();
    	var shopCode = $("#shopCode").val();
    	var roleId=$("#roleIds").val();
    	var userId =$("#userId").val();
    	var roleIdFinance = $('#roleIdFinance').val();
		var roleIdBoss = $('#roleIdBoss').val();
    	if (phoneNo.length < 11) {
    		Helper.alert("手机号必须要是11位");
        	return false;
    	}else{
    		var reg = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
			if (!(reg.test(phoneNo))) {
				Helper.alert("请输入正确的手机号");
	        	return false;
			} 
    	}
    	if(userName.length > 0){
    		var reg = /^[\w\u4e00-\u9fa5][\s\w\u4e00-\u9fa5]*(?!\s)$/; 
    		if (!(reg.test(userName))) {
    			Helper.alert("用户名只能输入中英文、数字、空格、下划线");
            	return false;
			} 
    	}
    	if(mchntCode == 0){
    		Helper.alert("请选择商户");
        	return false;
    	}
    	if(roleId == 0){
    		Helper.alert("请选择角色");
        	return false;
    	}
    	if(roleId == roleIdFinance || roleId == roleIdBoss){
    		shopCode = "";
		}else{
			if (shopCode == 0) {
				Helper.alert("请选择门店");
				return false;
			}
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/diy/diyUser/editDiyUserCommit.do',
            data: {
            	"userId" :userId,
            	"userName" :userName,
        		"phoneNo": phoneNo,
        		"mchntCode":mchntCode,
        		"shopCode":shopCode,
        		"roleId":roleId
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/diy/diyUser/listDiyUser.do?operStatus=2';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


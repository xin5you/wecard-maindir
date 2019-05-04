$(document).ready(function() {
	addRecharge.init();
})

var addRecharge = {
	init : function() {
		addRecharge.initEvent();
		addRecharge.initTip();
	},
	initTip:function(){
		var ttip_validator = $('.form_validation_tip').validate({

            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            highlight: function (element) {
                $(element).closest('div').addClass("f_error");
            },
            unhighlight: function (element) {
                $(element).closest('div').removeClass("f_error");
            },
            rules: {
            	companyName:{ required: true },
            	orderName: { required: true },
            	productCode:{required: true },
            	bizType:{required: true }
            },
            messages: {
            	companyName: {
                    required: "请输入公司名称"
                },
            	orderName: {
                    required: "请输入订单名称"
                },
                productCode:{
                	required:"请选择卡产品"
                },
                bizType:{
                	required:"请选择充值类型"
                }
            },
            invalidHandler: function (form, validator) {
                //$.sticky("There are some errors. Please corect them and submit again.", {autoclose : 5000, position: "top-right", type: "st-error" });
            },
            errorPlacement: function (error, element) {
                var elem = $(element);
                if (!error.is(':empty')) {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
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
                        }).qtip('option', 'content.text', error);
                    } else {
                        var xPoint = 5;
                        /*if (elem.attr('name') == 'authCode'){//特殊处理验证码
                            xPoint = 115;
                        }*/
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
                                classes: 'ui-tooltip-red ui-tooltip-rounded' 
                            }
                        }).qtip('option', 'content.text', error);
                    };
                } else {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.parent('label').parent('div').find('.error_placement').qtip('destroy');
                    } else {
                        elem.qtip('destroy');
                    }
                }
            },
            submitHandler: function (form) {
            	addRecharge.addRechargeCommit();
            },
            success: $.noop 
        
		});
		var uploadMainForm = $('#uploadMainForm').validate({

            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            
            submitHandler: function (form) {
            	addRecharge.accountImport();
            },
            success: $.noop 
        
		});
	},
	initEvent:function(){
		$('.btn-recharge-list').on('click', addRecharge.loadRechargeListModal);
		$('.btn-addRecharge').on('click', addRecharge.loadAddRechargeModal);
		$('.btn-mould-download').on('click', addRecharge.loadMouldDownload);
		$('.btn-submit').on('click', addRecharge.addAccountInf);
		$('.btn-delete').on('click', addRecharge.deleteAccountInf);
	},
	loadRechargeListModal:function(){
		$('#rechargeListModal').modal({
			backdrop : "static"
		});
	},
	loadAddRechargeModal:function(){
		$('#addRechargeModal').modal({
			backdrop : "static"
		});
	},
	loadMouldDownload:function(){
		var url = Helper.getRootPath()+"/common/excelDownload/excelUpload.do?batchType=recharge";
		location.href=url;
	},
	addRechargeCommit:function(){
		$('#msg').modal({
			backdrop : "static"
		});
		$("#pageMainForm").ajaxSubmit({
			type:'post', // 提交方式 get/post
            url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/addRechargeCommit.do', // 需要提交的 url
            dataType: 'json',
            data: {
            	"companyName":$("#companyName").val(),
            	"orderName":$("#orderName").val(),
            	"productCode":$("#productCode").val(),
            	"bizType":$("#bizType").val()
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do?operStatus=1';
                }else{
                	$('#msg').modal('hide');
                	Helper.alert(data.msg);
                	return false;
                }
			}
		});
	},
	accountImport:function(){
		$('.btn-import').addClass("disabled");
		$('#imorptMsg').modal({
			backdrop : "static"
		});
		$("#uploadMainForm").ajaxSubmit({
			type:'post', // 提交方式 get/post
            url: Helper.getRootPath() + '/common/excelImport/excelImp.do', // 需要提交的 url
            dataType: 'json',
            data: {
				"batchType":"recharge"
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/intoAddRecharge.do';
                }else{
                	$('#imorptMsg').modal('hide');
                	Helper.alert(data.msg);
                	return false;
                }
			}
		});
	},
	addAccountInf:function(){
		var name = $("#name").val();
		var phone = $("#phone").val();
		var card = $("#card").val();
		var money = $("#money").val();
		var re = /^1\d{10}$/;
		if(name==''){
			Helper.alert("请输入姓名");
    		return false;
		}
		if(phone==''){
			Helper.alert("请输入手机号码");
    		return false;
		}
		if(phone.length!=11){
			Helper.alert("请输入有效的手机号码");
    		return false;
		}
		if(!re.test(phone)){
			Helper.alert("请输入有效的手机号码");
    		return false;
		}
		if(card.length>18){
			Helper.alert("请输入有效的身份证");
    		return false;
		}
		if(money==''){
			Helper.alert("请输入充值金额");
    		return false;
		}
		if(!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(money)){
			Helper.alert("请输入正确的充值金额");
    		return false;
		}
		$.ajax({
			url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/addAccountInf.do',
            type: 'post',
            dataType : "json",
            data: {
                "name" : name, 
                "phone" : phone, 
                "card" : card,
                "money":money
            },
            success: function (result) {
            	if(result.status) {
                	location = Helper.getRootPath() + '/enterpriseOrder/batchRecharge/intoAddRecharge.do';
                } else {
                	Helper.alert(result.msg);
                	return false;
                }
            },
            error:function(){
            	Helper.alert("系统故障，请稍后再试");
            }
		});
	
	},
	deleteAccountInf:function(){
		var puid = $(this).attr('accountInfPuid');
		Helper.confirm("您确定删除该用户信息？",function(){
			$.ajax({								  
				url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/deleteAccountInf.do',
				type: 'post',
				dataType : "json",
				data: {
					"puid": puid
				},
				success: function (result) {
					if(result.status) {
						location = Helper.getRootPath() + '/enterpriseOrder/batchRecharge/intoAddRecharge.do';
					} else {
						Helper.alert(result.msg);
						return false;
					}
				},
				error:function(){
					Helper.alert("系统故障，请稍后再试");
				}
			});
		});
	}
}

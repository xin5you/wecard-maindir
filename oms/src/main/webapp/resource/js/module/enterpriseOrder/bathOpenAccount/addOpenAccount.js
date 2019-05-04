$(document).ready(function() {
	addOpenAccount.init();
})

var addOpenAccount = {
	init : function() {
		addOpenAccount.initEvent();
		addOpenAccount.initTip();
	},

	initEvent:function(){
		$('.btn-account-list').on('click', addOpenAccount.loadAccountListModal);
		$('.btn-addAccount').on('click', addOpenAccount.loadAddAccountModal);
		$('.btn-mould-download').on('click', addOpenAccount.loadMouldDownload);
		$('.btn-submit').on('click', addOpenAccount.addAccountInf);
		$('.btn-delete').on('click', addOpenAccount.deleteAccountInf);
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
            	orderName: { required: true },
            },
            messages: {
            	orderName: {
                    required: "请输入订单名称"
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
            	   addOpenAccount.addOpenAccountCommit();
            },
            success: $.noop 
        
		});
		var uploadMainForm = $('#uploadMainForm').validate({

            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            
            submitHandler: function (form) {
            	addOpenAccount.accountImport();
            },
            success: $.noop 
        
		});
	},
	
	addOpenAccountCommit:function(){
		$('#msg').modal({
			backdrop : "static"
		});
		$("#pageMainForm").ajaxSubmit({
			type:'post', // 提交方式 get/post
            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/addOpenAccountCommit.do', // 需要提交的 url
            dataType: 'json',
            data: {
				"orderName":$("#orderName").val()
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/listOpenAccount.do?operStatus=1';
                }else{
                	$('#msg').modal('hide');
                	Helper.alert(data.msg);
                	return false;
                }
			}
		});
	},
	loadAccountListModal:function(){
		$('#accountListModal').modal({
			backdrop : "static"
		});
	},
	loadAddAccountModal:function(){
		$('#addAccountModal').modal({
			backdrop : "static"
		});
	},
	loadMouldDownload:function(){
		var url = Helper.getRootPath()+"/common/excelDownload/excelUpload.do?batchType=openAccount";
		location.href=url;
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
				"batchType":"openAccount"
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/intoAddOpenAccount.do';
                }else{
                	$('#imorptMsg').modal('hide');
                	Helper.alert(data.msg);
                	return false;
                }
			},
			error:function(){
				$('#imorptMsg').modal('hide');
				Helper.alert("网络异常！");
				return false;
			}
		});
	},
	addAccountInf:function(){
		var name = $("#name").val();
		var phone = $("#phone").val();
		var card = $("#card").val();
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
		$.ajax({
			url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/addAccountInf.do',
            type: 'post',
            dataType : "json",
            data: {
                "name" : name, 
                "phone" : phone, 
                "card" : card
            },
            success: function (result) {
            	if(result.status) {
                	location = Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/intoAddOpenAccount.do';
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
				url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/deleteAccountInf.do',
				type: 'post',
				dataType : "json",
				data: {
					"puid": puid
				},
				success: function (result) {
					if(result.status) {
						location = Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/intoAddOpenAccount.do';
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

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
		$('#saveWBAccount').on('click', addOpenAccount.addOpenAccountCommit);
		$('.btn-delete').on('click', addOpenAccount.deleteWBAccountInf);
		$('#subWBAccount').on('click', addOpenAccount.addWBAccountInf);
	},
	initTip:function(){
		
		var uploadMainForm = $('#uploadMainForm').validate({	//文件上传提交

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
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/addOpenWBAccountCommit.do',
            cache:false,
            dataType: 'json',
            success: function(data){
            	if(data.status) {
					location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do?operStatus=1';
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
				"batchType":"openWBAccount"
			},
			success: function(data){
				if(data.status) {
                	location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/intoAddOpenWBAccount.do';
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
	addWBAccountInf:function(){
		var name = $("#name").val();
		var phone = $("#phone").val();
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
		$.ajax({
			url: Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/addWBAccountInf.do',
            type: 'post',
            dataType : "json",
            data: {
                "name" : name, 
                "phone" : phone
            },
            success: function (result) {
            	if(result.status) {
            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/intoAddOpenWBAccount.do';
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
	deleteWBAccountInf:function(){
		var puid = $(this).attr('puid');
		Helper.confirm("您确定删除该用户信息？",function(){
			$.ajax({								  
				url: Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/deleteWBAccountInf.do',
				type: 'post',
				dataType : "json",
				data: {
					"puid": puid
				},
				success: function (result) {
					if(result.status) {
						location = Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/intoAddOpenWBAccount.do';
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

$(document).ready(function() {
	editUser.init();
})

var editUser = {
	init : function() {
		editUser.initEvent();
	},

	initEvent:function(){
		$('.cancel_btn').on('click', editUser.back);
		$('.confirm_btn').on('click', editUser.edit);
		$('.btn_white').on('click', editUser.back);
		$('#roleId').change(editUser.checkRoleId);
	},
	checkRoleId : function(){
		var roleId = $('#roleId').val();
		var roleIdTemp = $('#roleIdTmp').val();
		if(roleId == roleIdTemp){
			$("#shopId").hide();
			$('#shopCode').attr("disabled",true);
			$('#shopCode').val('0');
		}else{
			$("#shopId").show();
			$('#shopCode').attr("disabled",false);
		}
	},
	back : function(){
		Helper.post('/system/user/listUser');
	},
	edit : function(){
		var userId = $("#userId").val();
		var roleId = $('#roleId').val();
		var shopCode = $('#shopCode').val();
		var mchntCode = $('#mchntCode').val();
		var userName = $('#userName').val().trim();
		var phoneNo = $('#phoneNo').val();
		if(userName.length > 0){
    		var reg = /^[\w\u4e00-\u9fa5][\s\w\u4e00-\u9fa5]*(?!\s)$/; 
    		if (!(reg.test(userName))) {
    			Helper.alert('系统提示','用户名只能输入中英文、数字、空格、下划线');
            	return false;
			} 
    	}
		if (roleId == 0) {
			Helper.alert('系统提示','请选择角色');
			return false;
		}
		var roleIdTmp = $('#roleIdTmp').val();
		if(roleId != roleIdTmp){
			if (shopCode == 0) {
				Helper.alert('系统提示','请选择门店');
				return false;
			}
		}else{
			shopCode = "";
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/system/user/editUserCommit',
            data: {
            	"id": userId,
        		"roleId": roleId,
        		"shopCode": shopCode,
        		"mchntCode": mchntCode,
        		"userName": userName,
        		"phoneNo": phoneNo
            },
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.code == '00') {
                	alert(data.msg);
                	editUser.back();
                }else if(data.code == ''){
                	alert(data.msg);
                	return false;
                }else{
                	alert(data.msg);
                	return false;
                }
            },
			error : function(){
				alert(data.msg);
				return false;
			}
        });
	}
}
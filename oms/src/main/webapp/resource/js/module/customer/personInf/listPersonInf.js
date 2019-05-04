$(document).ready(function() {
	listPersonInf.init();
})

var listPersonInf = {

	init : function() {
		listPersonInf.initEvent();
	},

	initEvent:function(){
		$('.btn-delete').on('click', listPersonInf.deletePersonInfByUserId);
		$('.btn-reset').on('click', listPersonInf.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/customer/personInf/getPersonInfList.do';
	},
	deletePersonInfByUserId:function(){
		var userId = $(this).attr('userId');
		Helper.confirm("确认注销当前手机号的用户信息及所有卡账户信息？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/customer/personInf/deletePersonInfByUserId.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "userId": userId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/customer/personInf/getPersonInfList.do';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	}
}

$(document).ready(function() {
	listUser.init();
})

var listUser = {

	init : function() {
		listUser.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){

		$('.btn-edit').on('click', listUser.intoEditUser);
		$('.btn-delete').on('click', listUser.deleteUserCommit);
		$('.btn-add').on('click', listUser.intoAddUser);
	},

	intoAddUser:function(){
		var url = Helper.getRootPath()+"/sys/user/intoAddUser.do";
		location.href=url;
	},
	intoEditUser:function(){
		var userId = $(this).attr('userId');
		var url = Helper.getRootPath()+"/sys/user/intoEditUser.do?userId="+userId;
		location.href=url;
	},
	deleteUserCommit:function(){
		var userId = $(this).attr('userId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/sys/user/deleteUserCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "userId": userId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/sys/user/listUser.do?operStatus=4';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统超时，请稍微再试试");
	            }
	      });
		});
	}
}

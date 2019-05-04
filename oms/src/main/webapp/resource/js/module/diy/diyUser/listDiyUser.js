$(document).ready(function() {
	listDiyUser.init();
})

var listDiyUser = {

	init : function() {
		listDiyUser.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listDiyUser.intoEditDiyUser);
		$('.btn-delete').on('click', listDiyUser.deleteDiyUserCommit);
		$('.btn-add').on('click', listDiyUser.intoAddDiyUser);
		$('.btn-reset').on('click', listDiyUser.searchReset);
	},
	searchReset:function(){
		location.href=Helper.getRootPath() + '/diy/diyUser/listDiyUser.do';
	},
	intoAddDiyUser:function(){
		var url = Helper.getRootPath()+"/diy/diyUser/intoAddDiyUser.do";
		location.href=url;
	},
	intoEditDiyUser:function(){
		var userId = $(this).attr('userId');
		var url = Helper.getRootPath()+"/diy/diyUser/intoEditDiyUser.do?userId="+userId;
		location.href=url;
	},
	deleteDiyUserCommit:function(){
		var userId = $(this).attr('userId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/diy/diyUser/deleteDiyUserCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "userId": userId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/diy/diyUser/listDiyUser.do?operStatus=4';
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

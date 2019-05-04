$(document).ready(function() {
	listRole.init();
})

var listRole = {

	init : function() {
		listRole.initEvent();
	},

	initEvent:function(){
		$('.btn-grant-role').on('click', listRole.loadGrantRoleBox);
		$('.btn-edit-role').on('click', listRole.intoEditRole);
		$('.btn-delete').on('click', listRole.deleteRoleCommit);
		$('.btn-add').on('click', listRole.intoAddRole);
	},
	loadGrantRoleBox : function() {
		var roleId = $(this).attr('orderId');
		$('#grantRoleModal').on('show',
				function() {
					var url = Helper.getRootPath()+"/sys/role/roleAuthorization.do?roleId="+roleId;
					$('#grantRoleIframe').attr("src", url);
				});
		$('#grantRoleModal').modal({
			backdrop : "static",
			width : 450,
			height : 450
		});
	},
	intoAddRole:function(){
		var url = Helper.getRootPath()+"/sys/role/intoAddRole.do";
		location.href=url;
	},
	intoEditRole:function(){
		var roleId= $(this).attr('orderId');
		var url = Helper.getRootPath()+"/sys/role/intoEditRole.do?roleId="+roleId;
		location.href=url;
	},
	deleteRoleCommit:function(){
		var roleId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/sys/role/deleteRoleCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                roleId: roleId
	            },
	            success: function (result) {
	            	if(result.status){
	            		Helper.stickySuccess("角色删除成功");
	            		location.href=Helper.getRootPath() + '/sys/role/listRole.do';
	            	
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

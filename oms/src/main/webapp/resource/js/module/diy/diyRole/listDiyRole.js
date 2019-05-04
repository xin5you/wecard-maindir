$(document).ready(function() {
	listDiyRole.init();
})

var listDiyRole = {

	init : function() {
		listDiyRole.initEvent();
	},

	initEvent:function(){
		$('.btn-grant-role').on('click', listDiyRole.loadGrantRoleBox);
		$('.btn-edit-role').on('click', listDiyRole.intoEditDiyRole);
		$('.btn-delete').on('click', listDiyRole.deleteDiyRoleCommit);
		$('.btn-add').on('click', listDiyRole.intoAddDiyRole);
	},
	loadGrantRoleBox : function() {
		var roleId = $(this).attr('orderId');
		$('#grantRoleModal').on('show',
				function() {
					var url = Helper.getRootPath()+"/diy/diyRole/diyRoleAuthorization.do?id="+roleId;
					$('#grantRoleIframe').attr("src", url);
				});
		$('#grantRoleModal').modal({
			backdrop : "static",
			width : 450,
			height : 450
		});
	},
	intoAddDiyRole:function(){
		var url = Helper.getRootPath()+"/diy/diyRole/intoAddDiyRole.do";
		location.href=url;
	},
	intoEditDiyRole:function(){
		var roleId= $(this).attr('orderId');
		var url = Helper.getRootPath()+"/diy/diyRole/intoEditDiyRole.do?id="+roleId;
		location.href=url;
	},
	deleteDiyRoleCommit:function(){
		var roleId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/diy/diyRole/deleteDiyRoleCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                id: roleId
	            },
	            success: function (result) {
	            	if(result.status){
	            		Helper.stickySuccess("角色删除成功");
	            		location.href=Helper.getRootPath() + '/diy/diyRole/listDiyRole.do';
	            	
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

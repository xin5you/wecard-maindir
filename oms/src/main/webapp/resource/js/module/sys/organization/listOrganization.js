$(document).ready(function() {
	listOrganization.init();
})

var listOrganization = {

	init : function() {
		listOrganization.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$("#dt_gal").treeTable();
	    
		$('.btn-edit').on('click', listOrganization.intoEditOrganization);
		$('.btn-delete').on('click', listOrganization.deleteOrganizationCommit);
		$('.btn-add').on('click', listOrganization.intoAddOrganizationUser);
	},

	intoAddOrganizationUser:function(){
		var url = Helper.getRootPath()+"/sys/organization/intoAddOrganization.do";
		location.href=url;
	},
	intoEditOrganization:function(){
		var organId = $(this).attr('organId');
		var url = Helper.getRootPath()+"/sys/organization/intoEditOrganization.do?organId="+organId;
		location.href=url;
	},
	deleteOrganizationCommit:function(){
		var organId = $(this).attr('organId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/sys/organization/deleteOrganizationCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "organId": organId
	            },
	            success: function (result) {
	            	if(result.status){
	            		
	            		location.href=Helper.getRootPath() + '/sys/organization/listOrganization.do?operStatus=4';
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

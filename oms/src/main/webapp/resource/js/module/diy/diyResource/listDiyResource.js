$(document).ready(function() {
	listDiyResource.init();
})

var listDiyResource = {

	init : function() {
		listDiyResource.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$("#dt_gal").treeTable();
		$('.btn-edit').on('click', listDiyResource.intoEditDiyResource);
		$('.btn-delete').on('click', listDiyResource.deleteResourceCommit);
		$('.btn-add').on('click', listDiyResource.intoAddDiyResource);
	},
	intoAddDiyResource:function(){
		var resourceId = $(this).attr('resourceId');
		if(resourceId ==null || resourceId==undefined){
			resourceId='';
		}
		var url = Helper.getRootPath()+"/diy/diyResource/intoAddDiyResource.do?resourceId="+resourceId;
		location.href=url;
	},
	intoEditDiyResource:function(){
		var resourceId = $(this).attr('resourceId');
		var url = Helper.getRootPath()+"/diy/diyResource/intoEditDiyResource.do?resourceId="+resourceId;
		location.href=url;
	},
	deleteResourceCommit:function(){
		var resourceId = $(this).attr('resourceId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/diy/diyResource/deleteDiyResourceCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "resourceId": resourceId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/diy/diyResource/listDiyResource.do?operStatus=4';
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

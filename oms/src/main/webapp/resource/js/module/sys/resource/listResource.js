$(document).ready(function() {
	listResource.init();
})

var listResource = {

	init : function() {
		listResource.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$("#dt_gal").treeTable();
		$('.btn-edit').on('click', listResource.intoEditResource);
		$('.btn-delete').on('click', listResource.deleteResourceCommit);
		$('.btn-add').on('click', listResource.intoAddResource);
	},
	intoAddResource:function(){
		var resourceId = $(this).attr('resourceId');
		if(resourceId ==null || resourceId==undefined){
			resourceId='';
		}
		var url = Helper.getRootPath()+"/sys/resource/intoAddResource.do?resourceId="+resourceId;
		location.href=url;
	},
	intoEditResource:function(){
		var resourceId = $(this).attr('resourceId');
		var url = Helper.getRootPath()+"/sys/resource/intoEditResource.do?resourceId="+resourceId;
		location.href=url;
	},
	deleteResourceCommit:function(){
		var resourceId = $(this).attr('resourceId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/sys/resource/deleteResourceCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "resourceId": resourceId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/sys/resource/listResource.do?operStatus=4';
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

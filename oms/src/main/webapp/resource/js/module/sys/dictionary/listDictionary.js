$(document).ready(function() {
	listDictionary.init();
})

var listDictionary = {

	init : function() {
		listDictionary.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$("#dt_gal").treeTable();
		$('.btn-edit').on('click', listDictionary.intoEditDictionary);
		$('.btn-delete').on('click', listDictionary.deleteDictionaryCommit);
		$('.btn-add').on('click', listDictionary.intoAddDictionaryUser);
	},

	intoAddDictionaryUser:function(){
		var url = Helper.getRootPath()+"/sys/dictionary/intoAddDictionary.do";
		location.href=url;
	},
	intoEditDictionary:function(){
		var dictId = $(this).attr('dictId');
		var url = Helper.getRootPath()+"/sys/dictionary/intoEditDictionary.do?dictId="+dictId;
		location.href=url;
	},
	deleteDictionaryCommit:function(){
		var dictId = $(this).attr('dictId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/sys/dictionary/deleteDictionaryCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "dictId": dictId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/sys/dictionary/listDictionary.do?operStatus=4';
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

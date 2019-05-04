$(document).ready(function() {
	listTelProviderInf.init();
})

var listTelProviderInf = {
	init : function() {
		listTelProviderInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-edit').on('click', listTelProviderInf.intoEditTelProviderInf);
		$('.btn-delete').on('click', listTelProviderInf.deleteTelProviderInfCommit);
		$('.btn-add').on('click', listTelProviderInf.intoAddTelProviderInf);
		$('.btn-view').on('click', listTelProviderInf.intoViewTelProviderInf);
		$('.btn-search').on('click', listTelProviderInf.searchData);
		$('.btn-reset').on('click', listTelProviderInf.searchReset);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/provider/providerInf/listTelProviderInf.do';
	},
	intoAddTelProviderInf:function(){
		var url = Helper.getRootPath()+"/provider/providerInf/intoAddTelProviderInf.do";
		location.href=url;
	},
	intoEditTelProviderInf:function(){
		var providerId = $(this).attr('providerId');
		var url = Helper.getRootPath()+"/provider/providerInf/intoEditTelProviderInf.do?providerId="+providerId;
		location.href=url;
	},
	intoViewTelProviderInf:function(){
		var providerId = $(this).attr('providerId');
		var url = Helper.getRootPath()+"/provider/providerInf/intoViewTelProviderInf.do?providerId="+providerId;
		location.href=url;
	},
	deleteTelProviderInfCommit:function(){
		var providerId = $(this).attr('providerId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/provider/providerInf/deleteTelProviderInfCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "providerId": providerId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/provider/providerInf/listTelProviderInf.do?operStatus=4';
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

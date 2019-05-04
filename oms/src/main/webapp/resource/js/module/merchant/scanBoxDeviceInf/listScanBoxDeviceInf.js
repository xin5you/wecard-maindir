$(document).ready(function() {
	listScanBoxDeviceInf.init();
})

var listScanBoxDeviceInf = {

	init : function() {
		listScanBoxDeviceInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
	
		$('.btn-edit').on('click', listScanBoxDeviceInf.editScanBoxDeviceInf);
		$('.btn-delete').on('click', listScanBoxDeviceInf.deleteScanBoxDeviceInfByDeviceId);
		$('.btn-add').on('click', listScanBoxDeviceInf.addScanBoxDeviceInf);
		$('.btn-view').on('click', listScanBoxDeviceInf.intoViewScanBoxDeviceInf);
		$('.btn-reset').on('click', listScanBoxDeviceInf.searchReset);
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do';
	},
	addScanBoxDeviceInf:function(){
		var url = Helper.getRootPath()+"/merchant/scanBoxDeviceInf/addScanBoxDeviceInf.do";
		location.href=url;
	},
	editScanBoxDeviceInf:function(){
		var deviceId = $(this).attr('deviceId');
		var url = Helper.getRootPath()+"/merchant/scanBoxDeviceInf/editScanBoxDeviceInf.do?deviceId="+deviceId;
		location.href=url;
	},
	intoViewScanBoxDeviceInf:function(){
		var deviceId = $(this).attr('deviceId');
		var url = Helper.getRootPath()+"/merchant/scanBoxDeviceInf/intoViewScanBoxDeviceInf.do?deviceId="+deviceId;
		location.href=url;
	},
	deleteScanBoxDeviceInfByDeviceId:function(){
		var deviceId = $(this).attr('deviceId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/merchant/scanBoxDeviceInf/deleteScanBoxDeviceInfByDeviceId.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "deviceId": deviceId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do?operStatus=4';
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

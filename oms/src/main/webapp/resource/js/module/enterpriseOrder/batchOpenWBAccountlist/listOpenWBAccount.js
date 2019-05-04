$(document).ready(function() {
	listOpenWBAccount.init();
})

var listOpenWBAccount = {
	init : function() {
		listOpenWBAccount.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.date-time-picker').datetimepicker({
	        format: 'yyyy-MM-dd hh:mm:ss',
	        language: 'zh-CN',
	        pickDate: true,
	        pickTime: true,
	        hourStep: 1,
	        minuteStep: 5,
	        secondStep: 10,
	        endDate: new Date(new Date()),
	        initialDate: new Date(new Date()),
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('#datetimepicker1').show();
		$('#datetimepicker2').show();
		$('.btn-search').on('click', listOpenWBAccount.searchData);
		$('.btn-reset').on('click', listOpenWBAccount.searchReset);
		$('.btn-add').on('click', listOpenWBAccount.intoAddOpenWBAccount);
		$('.btn-delete').on('click', listOpenWBAccount.deleteOpenWBAccountCommit);
	},
	searchData:function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('交易开始时间不能大于结束时间');
			return false;
		}
		document.forms['searchForm'].submit();
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do';
	},
	intoAddOpenWBAccount:function(){
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenWBAccount/intoAddOpenWBAccount.do";
		location.href=url;
	},
	deleteOpenWBAccountCommit:function(){
		var id = $(this).attr('id');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/deleteOpenWBAccountCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "id": id
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do?operStatus=4';
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

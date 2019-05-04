$(document).ready(function() {
	listOpenAccount.init();
})

var listOpenAccount = {
	init : function() {
		listOpenAccount.initEvent();
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
		$('.btn-search').on('click', listOpenAccount.searchData);
		$('.btn-reset').on('click', listOpenAccount.searchReset);
		$('.btn-edit').on('click', listOpenAccount.intoEditOpenAccount);
		$('.btn-delete').on('click', listOpenAccount.deleteOpenAccountCommit);
		$('.btn-add').on('click', listOpenAccount.intoAddOpenAccount);
		$('.btn-view').on('click', listOpenAccount.intoViewOpenAccount);
		$('.btn-submit').on('click', listOpenAccount.addOrderCommit);
		$('.btn-again-submit').on('click', listOpenAccount.addOrderAgainCommit);
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
		location = Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/listOpenAccount.do';
	},
	intoAddOpenAccount:function(){
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenAccount/intoAddOpenAccount.do";
		location.href=url;
	},
	intoEditOpenAccount:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenAccount/intoEditOpenAccount.do?orderId="+orderId;
		location.href=url;
	},
	intoViewOpenAccount:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenAccount/intoViewOpenAccount.do?orderId="+orderId;
		location.href=url;
	},
	deleteOpenAccountCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/deleteOpenAccountCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/listOpenAccount.do?operStatus=4';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	},
	addOrderCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您确认提交该订单吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/addOrderCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/listOpenAccount.do?operStatus=1';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	},
	addOrderAgainCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您确认提交该订单吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/addOrderAgainCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenAccount/listOpenAccount.do?operStatus=1';
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

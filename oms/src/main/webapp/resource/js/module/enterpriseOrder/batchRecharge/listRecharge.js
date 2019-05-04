$(document).ready(function() {
	listRecharge.init();
})

var listRecharge = {
	init : function() {
		listRecharge.initEvent();
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
		
		$('.btn-edit').on('click', listRecharge.intoEditRecharge);
		$('.btn-delete').on('click', listRecharge.deleteRechargeCommit);
		$('.btn-add').on('click', listRecharge.intoAddRecharge);
		$('.btn-view').on('click', listRecharge.intoViewRecharge);
		$('.btn-search').on('click', listRecharge.searchData);
		$('.btn-reset').on('click', listRecharge.searchReset);
		$('.btn-submit').on('click', listRecharge.addOrderCommit);
		$('.btn-again-submit').on('click', listRecharge.addOrderAgainCommit);
		$('.a').on('click', listRecharge.intoAddBatchInvoiceOrder);
		$('.b').on('click', listRecharge.intoViewBatchInvoiceOrder);
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
		location = Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do';
	},
	intoAddRecharge:function(){
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/intoAddRecharge.do";
		location.href=url;
	},
	intoEditRecharge:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/intoEditRecharge.do?orderId="+orderId;
		location.href=url;
	},
	intoViewRecharge:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/intoViewRecharge.do?orderId="+orderId;
		location.href=url;
	},
	deleteRechargeCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/deleteRechargeCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do?operStatus=4';
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
	            url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/addOrderCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do?operStatus=1';
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
	            url: Helper.getRootPath() + '/enterpriseOrder/batchRecharge/addOrderAgainCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchRecharge/listRecharge.do?operStatus=1';
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
	intoAddBatchInvoiceOrder:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/intoAddBatchInvoiceOrder.do?orderId="+orderId;
		location.href=url;
	},
	intoViewBatchInvoiceOrder:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchRecharge/intoViewBatchInvoiceOrder.do?orderId="+orderId;
		location.href=url;
	}
}

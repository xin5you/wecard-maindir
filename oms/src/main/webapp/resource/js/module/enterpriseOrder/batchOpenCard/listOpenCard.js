$(document).ready(function() {
	listOpenCard.init();
})

var listOpenCard = {
	init : function() {
		listOpenCard.initEvent();
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
		
		$('.btn-edit').on('click', listOpenCard.intoEditOpenCard);
		$('.btn-delete').on('click', listOpenCard.deleteOpenCardCommit);
		$('.btn-add').on('click', listOpenCard.intoAddOpenCard);
		$('.btn-view').on('click', listOpenCard.intoViewOpenCard);
		$('.btn-search').on('click', listOpenCard.searchData);
		$('.btn-reset').on('click', listOpenCard.searchReset);
		$('.btn-submit').on('click', listOpenCard.addOrderCommit);
		$('.btn-again-submit').on('click', listOpenCard.addOrderAgainCommit);
		$('.btn-edit-quota').on('click', listOpenCard.intoEditQuota);
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
		location = Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do';
	},
	intoAddOpenCard:function(){
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenCard/intoAddOpenCard.do";
		location.href=url;
	},
	intoEditOpenCard:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenCard/intoEditOpenCard.do?orderId="+orderId;
		location.href=url;
	},
	intoViewOpenCard:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenCard/intoViewOpenCard.do?orderId="+orderId;
		location.href=url;
	},
	intoEditQuota:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/enterpriseOrder/batchOpenCard/intoEditQuota.do?orderId="+orderId;
		location.href=url;
	},
	deleteOpenCardCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/deleteOpenCardCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do?operStatus=4';
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
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/addOrderCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do?operStatus=1';
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
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/addOrderAgainCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do?operStatus=1';
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

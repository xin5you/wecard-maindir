$(document).ready(function() {
	listCardTicketTransOrder.init();
})

var listCardTicketTransOrder = {

	init : function() {
		listCardTicketTransOrder.initEvent();
		var operStatus = $("#operStatus").val();
		Helper.operTip(operStatus);
		listCardTicketTransOrder.showDatetimepicker();
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
	        endDate: new Date(new Date() - 86400000),
	        initialDate: new Date(new Date() - 86400000),
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('.btn-search').on('click', listCardTicketTransOrder.searchData);
		$('#queryType').on('change', listCardTicketTransOrder.showDatetimepicker);
		$('.btn-reset').on('click', listCardTicketTransOrder.searchReset);
		$('.btn-pencil').on('click', listCardTicketTransOrder.resetCardTicketTransOrder);
		$('.btn-view').on('click', listCardTicketTransOrder.intoViewCardTicketTransLog);
	},
	searchData: function(){
		if ($('#queryType').val() == 'his') {
			var sd = $('#startTime').val();
			var ed = $('#endTime').val();
			if (sd == '' || sd == null) {
				Helper.alert('交易开始时间不能为空');
				return false;
			}
			if (ed == '' || ed == null) {
				Helper.alert('交易结束时间不能为空');
				return false;
			}
			if (sd != '' && ed != '' && sd.localeCompare(ed) > 0) {
				Helper.alert('开始时间不能大于结束时间');
				return false;
			}
		}
		document.forms['searchForm'].submit();
	},
	showDatetimepicker : function() {
		var queryType = $('#queryType').val();
		if (queryType == 'his') {
			$('#datetimepicker1').show();
			$('#datetimepicker2').show();
		} else if (queryType == 'cur') {
			$('#datetimepicker1').hide();
			$('#datetimepicker2').hide();
			$('#startTime').val('');
			$('#endTime').val('');
		} else {
			$('#datetimepicker1').hide();
			$('#datetimepicker2').hide();
			$('#startTime').val('');
			$('#endTime').val('');
		}
	},
	searchReset:function(){
		location = Helper.getRootPath() + '/cardTicketTrans/listCardTicketTransOrder.do';
	},
	resetCardTicketTransOrder:function(){
		var orderId = $(this).attr('orderId');
		
		Helper.confirm("确定重置该卡券交易订单吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/cardTicketTrans/resetCardTicketTransOrderCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId" : orderId
	            },
	            success: function (data) {
	            	if(data) {
	                	location = Helper.getRootPath() + '/cardTicketTrans/listCardTicketTransOrder.do';
	                } else {
	                	Helper.alert("系统故障，请稍后再试");
	                	return false;
	                }
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
		    });
		});
	},
	intoViewCardTicketTransLog:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/cardTicketTrans/intoViewCardTicketTransLog.do?orderId="+orderId;
		location.href = url;
	}
}

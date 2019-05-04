$(document).ready(function() {
	listChannelOrder.init();
});

var listChannelOrder = {
		init : function() {
			listChannelOrder.initEvent();
		},
		initEvent:function(){
			$('#btn-search').on('click', listChannelOrder.searchDate);
			$('#btn-reset').on('click', listChannelOrder.searchReset);
			$("#startDate").on('click',listChannelOrder.showStartDate);
			$("#endDate").on('click',listChannelOrder.showEndDate);
			$("#btn_upload").on('click',listChannelOrder.listOrderUpload);
		},
		showStartDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd HH:mm:ss',
				maxDate:'%y-%M-{%d} 00\:00\:00',
				readOnly:true,
				opposite:true
				});
		},
		showEndDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd HH:mm:ss',
				maxDate:'%y-%M-{%d} 23\:59\:59',
				readOnly:true,
				opposite:true
				});
		},
		searchDate:function(){
			var sd = $('#startDate').val();
			var ed = $('#endDate').val();
			var end = Helper.lastMonthDate(ed);
			if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
				Helper.alert('系统提示','开始时间不能大于结束时间');
				return false;
			}
			if(sd <= end){
				Helper.alert('系统提示',"时间间隔不能大于一个月！");
			 	return false;
			}
			document.forms['searchForm'].submit();
		},
		searchReset:function(){
			location = Helper.getRootPath() +'/channel/order/listOrder';
		},
		listOrderUpload:function(){
			var channelOrderId = $("#channelOrderId").val();
			var rechargePhone = $("#rechargePhone").val();
			var rechargeState = $("#rechargeState").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() +"/channel/order/listOrderUpload?channelOrderId="+channelOrderId+"&rechargePhone="+rechargePhone+"&rechargeState="+rechargeState+"&startDate="+startDate+"&endDate="+endDate;
		}
};
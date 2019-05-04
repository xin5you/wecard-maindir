$(document).ready(function() {
	listShopStatisticsSet.init();
})

var listShopStatisticsSet = {
	init : function() {
		listShopStatisticsSet.initEvent();
	},

	initEvent:function(){
		$('#btn-reset').on('click', listShopStatisticsSet.searchReset);
		$('#btn-back').on('click', listShopStatisticsSet.back);

		$('#btn-search').on('click', listShopStatisticsSet.listSearch);
		$('.btn-edit').on('click', listShopStatisticsSet.monthEdit);
		$('.btn-view').on('click', listShopStatisticsSet.monthView);
		$('.btn-dayview').on('click', listShopStatisticsSet.dayView);
		$(".Wdate").on('click',listShopStatisticsSet.showDate);

	},
	showDate:function(){
		WdatePicker({
			el:this,
			dateFmt:'yyyy-MM-dd',
			maxDate:'%y-%M-{%d-1}',
			readOnly:true
			});
	},
	searchReset : function(){
		location = Helper.getRootPath() +'/operate/posit/listShopStatisticsSet';
	},
	back : function(){
		var startDate = $(this).attr("startDate");
		var endDate = $(this).attr("endDate");
		location = Helper.getRootPath() +'/operate/posit/listShopStatisticsSet?startDate='+startDate+'&endDate='+endDate;
	},
	listSearch : function(){

		var start = $("#startDate").val();
		var end = $("#endDate").val();
		var ed = Helper.lastYearDate(end);
		if(start ==''){
			Helper.alert('系统提示','请选择开始时间');
			return false;
		}
		if(end ==''){
			Helper.alert('系统提示','请选择结束时间');
			return false;
		}
		if (start.localeCompare(Helper.nowDate())>=0) {
			Helper.alert('系统提示','开始时间必须为当天以前时间');
			return false;
		}
		if (end.localeCompare(Helper.nowDate())>=0) {
			Helper.alert('系统提示','结束必须为当天以前时间');
			return false;
		}
		if(start !='' && end !='' && start.localeCompare(end)>0){
			Helper.alert('系统提示','开始时间不能大于结束时间');
			return false;
		}
		if(start <= ed){
			Helper.alert('系统提示',"当前查询时间间隔不能大于一年！");
		 	return false;
		}
		$("#searchForm").submit();
	},
	monthEdit:function(){
		var shopStatisticsOprId = $(this).attr("shopStatisticsId");
		var settleDate =$(this).attr("settledate");
		var startDate =$("#startDate").val();
		var endDate =$("#endDate").val();
		var payAmt = $(this).attr("payamt");
		location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+payAmt
		+'&startDate='+startDate+'&endDate='+endDate;
//$("#month_edit").submit();
	},
	monthView:function(){
		var shopStatisticsOprId = $(this).attr("shopStatisticsId");
		var settleDate =$(this).attr("settledate");
		var startDate =$("#startDate").val();
		var endDate =$("#endDate").val();
		var payAmt = $(this).attr("payamt");
		location = Helper.getRootPath() +'/operate/posit/monthView?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+payAmt
		+'&startDate='+startDate+'&endDate='+endDate;
	},
	dayView:function(){
		
		var shopOprStatisticId = $(this).attr("ssid");
		var settleDate = $(this).attr("settledate");
		var startDate = $("#searchForm").find('[name=startDate]').val();
		var endDate = $("#searchForm").find('[name=endDate]').val();
		var stat = $(this).attr("stat");
		var payAmt =$(this).attr("payamt");	
		var totalPay = $(this).attr("totalPay");
		location = Helper.getRootPath() +'/operate/posit/dayView?shopStatisticsOprId='+shopOprStatisticId+'&settleDate='+settleDate+'&stat='+stat+'&totalPay='+totalPay+'&payAmt='+payAmt
		+'&startDate='+startDate+'&endDate='+endDate+'&state=2';

	}


}
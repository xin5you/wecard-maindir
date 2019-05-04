$(document).ready(function() {
	monthEdit.init();
})

var monthEdit = {
	init : function() {
		monthEdit.initEvent();
	},

	initEvent:function(){
		$('#btn-reset').on('click', monthEdit.searchReset);
		$('#btn-back').on('click', monthEdit.back);
		$('#btn-search').on('click', monthEdit.listSearch);
		$('.btn-edit').on('click', monthEdit.dayEdit);
		$('.btn-file').on('click', monthEdit.file);
		$('.btn-view').on('click', monthEdit.dayView);
		
	},
	searchReset : function(){
		var shopStatisticsOprId =$("#searchForm").find('[name=shopStatisticsOprId]').val();
		var settleDate =$("#searchForm").find('[name=settleDate]').val();
		var payAmt = $("#searchForm").find('[name=totalPay]').val();
		var startDate = $("#searchForm").find('[name=startDate]').val();
		var endDate = $("#searchForm").find('[name=endDate]').val();
		location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+payAmt
		+'&startDate='+startDate+'&endDate='+endDate;

	},
	back : function(){
		var startDate = $(this).attr("startDate");
		var endDate = $(this).attr("endDate");
		location = Helper.getRootPath() +'/operate/posit/listShopStatisticsSet?startDate='+startDate+'&endDate='+endDate;
	},
	listSearch : function(){


		$("#searchForm").submit();
	},
	dayEdit:function(){

		var shopOprStatisticId = $(this).attr("ssid");
		var settleDate = $(this).attr("settledate");
		var startDate = $("#searchForm").find('[name=startDate]').val();
		var endDate = $("#searchForm").find('[name=endDate]').val();
		var stat = $(this).attr("stat");	
		var payAmt = $(this).attr("payamt");
		var totalPay = $(this).attr("totalPay");
		location = Helper.getRootPath() +'/operate/posit/dayEdit?shopStatisticsOprId='+shopOprStatisticId+'&settleDate='+settleDate+'&stat='+stat+'&totalPay='+totalPay+'&payAmt='+payAmt
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
		+'&startDate='+startDate+'&endDate='+endDate+'&state=1';

	},
	file:function(){
		
		var shopStatisticsOprId =$("#searchForm").find('[name=shopStatisticsOprId]').val();
		var settleDate =$("#searchForm").find('[name=settleDate]').val();
		var payAmt = $("#searchForm").find('[name=totalPay]').val();
		var startDate = $("#searchForm").find('[name=startDate]').val();
		var endDate = $("#searchForm").find('[name=endDate]').val();
		
		
		if(confirm('归档后不可编辑！确定要归档吗？')==false){
			return false;
		}else{
			var sid = $(this).attr("ssid");
			var name = $(this).attr("name");
			var stat =  $(this).attr("stat");
			$.post(Helper.getRootPath() +'/operate/posit/file',{'sid':sid,'name':name,'stat':stat},function(data){
				if(data.result=="1"){
					alert("归档成功！");
				}else{
					alert("归档失败！");
				}
				location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+payAmt
				+'&startDate='+startDate+'&endDate='+endDate;
			});
		}

	},

}


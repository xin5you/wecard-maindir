$(document).ready(function() {
	invoOrdStatistics.init();
});

var invoOrdStatistics = {
		init : function() {
			invoOrdStatistics.initEvent();
		},
		initEvent:function(){
			$("#btn-reset").on('click', invoOrdStatistics.reset);
			$("#btn-search").on('click', invoOrdStatistics.searchDate);
			$(".Wdate").on('click',invoOrdStatistics.showDate);
		},
		showDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd',
				maxDate:'%y-%M-{%d-1}',
				readOnly:true
				});
		},
		searchDate:function(){
			var sd = $('#startDate').val();
			var ed = $('#endDate').val();
			var end = Helper.lastMonthDate(ed);
			if(sd ==''){
				Helper.alert('系统提示','请选择开始时间');
				return false;
			}
			if(ed ==''){
				Helper.alert('系统提示','请选择结束时间');
				return false;
			}
			if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
				Helper.alert('系统提示','开始时间不能大于结束时间');
				return false;
			}
			document.forms['searchForm'].submit();
		},
		reset:function(){
			location = Helper.getRootPath() + '/invoice/invoOrdStatistics/getInvoOrdStatistics';
		}
};
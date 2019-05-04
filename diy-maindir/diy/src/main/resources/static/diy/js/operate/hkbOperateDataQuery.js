$(document).ready(function() {
	listPositOprStatistics.init();
})

var listPositOprStatistics = {
	init : function() {
		listPositOprStatistics.initEvent();
	},

	initEvent:function(){
		$('#btn-reset').on('click', listPositOprStatistics.searchReset);
		$('#btn-search').on('click', listPositOprStatistics.listSearch);
		$('#btn_upload').on('click', listPositOprStatistics.positOprUpload);
		$(".Wdate").on('click',listPositOprStatistics.showDate);
	},
	searchReset : function(){
		location = Helper.getRootPath() +'/operate/posit/listOprStatistics';
	},
	showDate:function(){
		WdatePicker({
			el:this,
			dateFmt:'yyyy-MM-dd',
			maxDate:'%y-%M-{%d-1}',
			readOnly:true
			});
	},
	listSearch : function(){
			var start = $("#startDate").val();
			var end = $("#endDate").val();
			var ed = Helper.lastThirdyMonthDate(end);
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
				Helper.alert('系统提示',"当前查询时间间隔不能大于三个月！");
			 	return false;
			}
			$("#searchForm").submit();
	},
	positOprUpload:function(){
		var start = $("#startDate").val();
		var end = $("#endDate").val();
		var sd = Date.parse(new Date(start));
		var ed = Date.parse(new Date(end));
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
			Helper.alert('系统提示',"当前查询时间间隔不能大于三个月！");
		 	return false;
		}
		location = Helper.getRootPath() + '/operate/posit/getPositOprUploadList?startDate=' + start + '&endDate=' +end;
	}
}
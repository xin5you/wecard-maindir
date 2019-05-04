$(document).ready(function() {
	listOperationSummarizing.init();
})

var listOperationSummarizing = {
	init : function() {
		listOperationSummarizing.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.date-time-picker').datetimepicker({
	        format: 'yyyy-MM-dd',
	        language: 'zh-CN',
	        pickDate: true,
	        pickTime: false,
	        hourStep: 1,
	        minuteStep: 5,
	        secondStep: 10,
	        endDate: new Date(new Date() - 86400000),
	        initialDate: new Date(new Date() - 86400000),
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('#datetimepicker1').show();
		$('#datetimepicker2').show();
		$('.btn-search').on('click', listOperationSummarizing.searchData);
		$('.btn-reset').on('click', listOperationSummarizing.searchReset);
		$('.btn-upload').on('click',listOperationSummarizing.uploadOperationSummarizing);
	},
	searchData: function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(sd ==''){
			Helper.alert('请选择开始时间');
			return false;
		}
		if(ed ==''){
			Helper.alert('请选择结束时间');
			return false;
		}
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('开始时间不能大于结束时间');
				return false;
		}
		if(sd <= end){
			Helper.alert("当前查询时间间隔不能大于一个月！");
		 	return false;
		}
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/statement/operationStatement/listOperationSummarizing.do';
	},
	uploadOperationSummarizing:function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(sd ==''){
			Helper.alert('请选择开始时间');
			return false;
		}
		if(ed ==''){
			Helper.alert('请选择结束时间');
			return false;
		}
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('开始时间不能大于结束时间');
				return false;
		}
		if(sd <= end){
			Helper.alert("当前查询时间间隔不能大于一个月！");
		 	return false;
		}
		location = Helper.getRootPath() + '/statement/operationStatement/uploadOperationSummarizing.do?startTime='+sd+'&endTime='+ed;
	}
}

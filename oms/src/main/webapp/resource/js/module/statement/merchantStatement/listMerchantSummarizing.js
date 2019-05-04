$(document).ready(function() {
	listMerchantSummarizing.init();
})

var listMerchantSummarizing = {
	init : function() {
		listMerchantSummarizing.initEvent();
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
		$('.btn-search').on('click', listMerchantSummarizing.searchData);
		$('.btn-reset').on('click', listMerchantSummarizing.searchReset);
		$('.btn-upload').on('click',listMerchantSummarizing.uploadMerchantSummarizing);
	},
	searchData: function(){
		var mchntCode = $('#mercahnt_select').val();
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(mchntCode ==''){
			Helper.alert('请选择商户');
			return false;
		}
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
		location = Helper.getRootPath() + '/statement/merchantStatement/listMerchantSummarizing.do';
	},
	uploadMerchantSummarizing:function(){
		var mchntCode = $('#mercahnt_select').val();
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(mchntCode ==''){
			Helper.alert('请选择商户');
			return false;
		}
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
		location = Helper.getRootPath() + '/statement/merchantStatement/uploadMerchantSummarizing.do?mchntCode='+mchntCode+'&startTime='+sd+'&endTime='+ed;
	}
}

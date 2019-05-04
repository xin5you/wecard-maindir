$(document).ready(function() {
	hkbTransCount.init();
})

var hkbTransCount = {
	init : function() {
		hkbTransCount.initEvent();
	},

	initEvent:function(){
		$('#btn-reset').on('click', hkbTransCount.searchReset);
		$('#btn-search').on('click', hkbTransCount.listSearch);
		$(".Wdate").on('click',hkbTransCount.showDate);
	},
	searchReset : function(){
		location = Helper.getRootPath() +'/trans/mchnt/getTransCount';
	},
	showDate:function(){
		WdatePicker({
			el:this,
			dateFmt:'HH:mm:ss',
			readOnly:true
			});
	},
	listSearch : function(){

		var start = $("#startDate").val();
		var end = $("#endDate").val();
		if(start ==''){
			Helper.alert('系统提示','请选择开始时间');
			return false;
		}
		if(end ==''){
			Helper.alert('系统提示','请选择结束时间');
			return false;
		}
		if(start !='' && end !='' && start>end){
			Helper.alert('系统提示','开始时间不能大于结束时间');
			return false;
		}
		$("#searchForm").submit();
	},


}
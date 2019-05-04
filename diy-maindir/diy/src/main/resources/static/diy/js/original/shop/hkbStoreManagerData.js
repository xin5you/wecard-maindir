$(document).ready(function() {
	hkbStoreManagerData.init();
});

var hkbStoreManagerData = {
		init : function() {
			hkbStoreManagerData.initEvent();
		},
		initEvent:function(){
			$('#btn-search').on('click', hkbStoreManagerData.searchDate);
			$('#btn-reset').on('click', hkbStoreManagerData.searchReset);
			$('.btn-detail').on('click', hkbStoreManagerData.positDetail);
			$("#btn_upload").on('click', hkbStoreManagerData.positUpload);
			$(".Wdate").on('click',hkbStoreManagerData.showDate);
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
			if (sd.localeCompare(Helper.nowDate())>=0) {
				Helper.alert('系统提示','开始时间必须为当天以前时间');
				return false;
			}
			if (ed.localeCompare(Helper.nowDate())>=0) {
				Helper.alert('系统提示','结束必须为当天以前时间');
				return false;
			}
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
		searchReset : function(){
			location = Helper.getRootPath() + '/original/shop/getShopDataList';
		},
		positDetail:function(){
			var positCode = $(this).attr('name');
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/shop/getPositDetailList?positCode='+positCode+"&startDate="+startDate+"&endDate="+endDate;
		},
		positUpload:function(){
			var shCode = $('#shCode').val();
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
			if (sd.localeCompare(Helper.nowDate())>=0) {
				Helper.alert('系统提示','开始时间必须为当天以前时间');
				return false;
			}
			if (ed.localeCompare(Helper.nowDate())>=0) {
				Helper.alert('系统提示','结束必须为当天以前时间');
				return false;
			}
			if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
				Helper.alert('系统提示','开始时间不能大于结束时间');
				return false;
			}
			if(sd <= end){
				Helper.alert('系统提示',"时间间隔不能大于一个月！");
			 	return false;
			}
			location = Helper.getRootPath() + '/original/posit/getPositStatisticsUploadList?shCode='+shCode+"&startDate="+sd+"&endDate="+ed;
		}
};

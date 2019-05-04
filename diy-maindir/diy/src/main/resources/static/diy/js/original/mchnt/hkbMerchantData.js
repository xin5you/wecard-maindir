$(document).ready(function() {
	hkbMerchantData.init();
});

var hkbMerchantData = {
		init : function() {
			hkbMerchantData.initEvent();
		},
		initEvent:function(){
			$('#btn-search').on('click', hkbMerchantData.searchDate);
			$('#btn-reset').on('click', hkbMerchantData.searchReset);
			$('.btn-detail').on('click', hkbMerchantData.shopDetail);
			$('#btn_upload').on('click', hkbMerchantData.shopUpload);
			$(".Wdate").on('click',hkbMerchantData.showDate);
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
			location = Helper.getRootPath() +'/original/mchnt/getMchntDataList';
		},
		shopDetail:function(){
			var shopCode = $(this).attr('name');
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/mchnt/getPostiList?shopCode='+shopCode+"&startDate="+startDate+"&endDate="+endDate;
		},
		shopUpload:function(){
			var mchntCode = $('#mchntCode').val();
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
//			/original/shop/getShopStatisticsUploadList
			location = Helper.getRootPath() + '/original/shop/getShopStatisticsUploadList?mchntCode=' + mchntCode+'&startDate=' + sd + '&endDate=' +ed;
		}
};
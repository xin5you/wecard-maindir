$(document).ready(function() {
	hkbStoreData.init();
});

var hkbStoreData = {
		init : function() {
			hkbStoreData.initEvent();
		},
		initEvent:function(){
			$("#btn_upload").on('click', hkbStoreData.positUpload);
			$(".btn_back").on('click', hkbStoreData.backShop);
			$(".Wdate").on('click',hkbStoreData.showDate);
		},
		showDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd',
				maxDate:'%y-%M-{%d-1}',
				readOnly:true
				});
		},
		positUpload:function(){
			var shCode = $('#shCode').val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/posit/getPositStatisticsUploadList?shCode='+shCode+"&startDate="+startDate+"&endDate="+endDate;
		},
		backShop:function(){
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/mchnt/getMchntDataList?startDate='+startDate+'&endDate='+endDate;
		}
};
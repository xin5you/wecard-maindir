$(document).ready(function() {
	hkbStoreDataDetails.init();
});

var hkbStoreDataDetails = {
		init : function() {
			hkbStoreDataDetails.initEvent();
		},
		initEvent:function(){
			$('#btn_upload').on('click', hkbStoreDataDetails.positTranUpload);
			$(".btn_back").on('click', hkbStoreDataDetails.backShop);
			$(".Wdate").on('click',hkbStoreDataDetails.showDate);
		},
		showDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd',
				maxDate:'%y-%M-{%d-1}',
				readOnly:true
				});
		},
		positTranUpload:function(){
			var positCode = $("#positCode").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/posit/getPositDetailUploadList?positCode='+positCode+"&startDate="+startDate+"&endDate="+endDate;
		},
		backShop:function(){
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			location = Helper.getRootPath() + '/original/shop/getShopDataList?startDate='+startDate+'&endDate='+endDate;
		}
};
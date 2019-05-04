   $(document).ready(function() {
	listTelChannelReserve.init();
})

var listTelChannelReserve = {
	init : function() {
		listTelChannelReserve.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-add').on('click', listTelChannelReserve.intoAddTelChannelReserve);
		$('.btn-view').on('click', listTelChannelReserve.intoViewTelChannelReserve);
		$('.btn-search').on('click', listTelChannelReserve.searchData);
		$('.btn-reset').on('click', listTelChannelReserve.searchReset);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/statement/financingStatement/listTelChannelReserve.do';
	},
	
	intoAddTelChannelReserve:function(){
		var url = Helper.getRootPath()+"/channel/reserve/intoAddTelChannelReserve.do";
		location.href=url;
	},
	intoViewTelChannelReserve:function(){
		var reserveId = $(this).attr('reserveId');
		var url = Helper.getRootPath()+"/channel/reserve/intoViewTelChannelReserve.do?reserveId="+reserveId;
		location.href=url;
	},
}
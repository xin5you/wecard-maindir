$(document).ready(function() {
	listTelProviderInf.init();
})

var listTelProviderInf = {
	init : function() {
		listTelProviderInf.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.btn-search').on('click', listTelProviderInf.searchData);
		$('.btn-reset').on('click', listTelProviderInf.searchReset);
	},
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/provider/providerOrder/listTelProviderOrderInf.do';
	}
}

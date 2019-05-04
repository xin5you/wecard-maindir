$(document).ready(function() {
	listTelChannelProductRate.init();
})

var listTelChannelProductRate = {
	init : function() {
		listTelChannelProductRate.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('#selectAll').on('click', listTelChannelProductRate.selectAll);
		$('.btn-search').on('click', listTelChannelProductRate.searchData);
		$('.btn-reset').on('click', listTelChannelProductRate.searchReset);
		
		$('.btn-add').on('click', listTelChannelProductRate.addTelChannelProductRates);
		
		$('.btn-edit').on('click', listTelChannelProductRate.intoEditTelChannelProductRates);
	},
	selectAll: function(){
		 if ($("#selectAll").attr("checked")) {
			 $(":checkbox").attr("checked", true);
		 } else {
			 $(":checkbox").attr("checked", false);
		 }
	 },
	searchData: function(){
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		var channelId = $("#channelId").val();
		var url = Helper.getRootPath()+"/channel/channelInf/intoAddTelChannelRate.do?channelId="+channelId;
		location.href=url;
	},
	addTelChannelProductRates : function() {
		var channelRate = $("#channelRate").val();
		var channelId = $("#channelId").val();
		if(channelRate == null || channelRate == ""){
			Helper.alert("分销商折扣率不能为空");
			return false;
		}
		var reg = /^0\.{1}\d{1,4}$/;
    	if(!(reg.test(channelRate))){
    		Helper.alert("请输入正确的折扣率：例如：折扣率是99.94，就输入0.9994");
        	return false;
    	}
    	if(channelId == null || channelId == ""){
			Helper.alert("分销商不能为空");
			return false;
		}
		var obj = [];
		$("input:checkbox[name='productId']:checked").each(function() {
			if(!$(this).prop("disabled")){
				obj.push($(this).val());
			}
		});
		var ids = obj.join(",");
		$(".btn-add").attr('disabled',"true");
		$.ajax({
			url : Helper.getRootPath() + '/channel/channelInf/addTelChannelRateCommit.do',
			type : 'post',
			dataType : "json",
			data : {
				channelId : channelId,
				channelRate : channelRate,
				ids : ids
			},
			success : function(data) {
				$(".btn-add").removeAttr("disabled");
				if (data.status) {
					location = Helper.getRootPath() + '/channel/channelInf/intoAddTelChannelRate.do?channelId='+channelId;
				} else {
					Helper.alert(data.msg);
					return false;
				}
			},
	        error : function(){
	        	Helper.alert(data.msg);
				return false;
	        }
		});
	},
	intoEditTelChannelProductRates:function(){
		var id = $(this).attr('id');
		var url = Helper.getRootPath()+"/channel/channelInf/intoEditTelChannelProductRate.do?id="+id;
		location.href=url;
	}
}

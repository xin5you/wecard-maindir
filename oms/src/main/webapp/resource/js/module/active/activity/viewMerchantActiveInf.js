$(document).ready(function () {
    MerchantActiveInf.init();
})

var MerchantActiveInf = {
    init: function () {
    	MerchantActiveInf.initEvent();
    	MerchantActiveInf.initTip();
    },
	initEvent:function(){
		$('.btn-mchnt-list').on('click', MerchantActiveInf.loadMchntListModal);
		$('.btn-return').on('click', MerchantActiveInf.rtnList);
		$('.btn-add-comm').on('click', MerchantActiveInf.loadCommListModal);
	},
	initTip: function () {
		
    },
    rtnList:function(){
    	location = Helper.getRootPath()+"/active/activity/listMerchantActives.do";
    },
	loadCommListModal : function(type, code){
		$('#commListModal').modal({
			backdrop : "static",
			width: 750,
			height:300
		});
		$("#commListModal").on("shown.bs.modal", function(e) {
			$.ajax({
	            type: 'POST',
	            url: Helper.getRootPath() + '/active/activity/getCommoListByActiveId.do',
	            data: {
	            	activeId : $("#activeId").val(),
	            	mchntId : $("#mchntId_h").val()
	            },
	            cache:false,
	            dataType: 'json',
	            success: function(data) {
	            	if(data != null && data.length > 0) {
	            		for(var i=0; i<data.length; i++) {
		            		$('#commList').append('<tr>' +
		            			"<td>" + data[i].commodityName + "</td>" +
			            		"<td>"+data[i].commodityFacevalue+"</td>"+
			            		"<td>"+data[i].commodityCost+"</td>"+
			            		"<td>" + data[i].sellingPrice + "</td>" +
			            		"<td>" + data[i].stock + "</td>" +
            				'</tr>');
		            	}
	            	} else {
	            		$('#commList').append('<td id="null_record" colspan="5">无记录</td>');
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	        });
		});
		$("#commListModal").on("hidden.bs.modal", function(e) {
			$('#commList').html('');
			$('#comm_opr_info').text('');
		});
	}
};


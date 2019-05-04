$(document).ready(function() {
	listCommodities.init();
})

var listCommodities = {
	init : function() {
		listCommodities.initEvent();
	},

	initEvent:function(){
		$('.btn-edit').on('click', listCommodities.intoEditCommodityInf);
		$('.btn-delete').on('click', listCommodities.deleteCommodityInf);
		$('.btn-add').on('click', listCommodities.intoAddCommodityInf);
		$('.btn-view').on('click', listCommodities.intoViewCommodityInf);
		$('.btn-submit').on('click', listCommodities.editCommodityInf);
		$('.btn-reset').on('click', listCommodities.searchReset);
	},

	intoAddCommodityInf:function(){
		location = Helper.getRootPath()+"/active/commodityInf/intoAddCommodityInf.do";;
	},
	intoEditCommodityInf:function(){
		var commId = $(this).attr('commId');
		listCommodities.loadCommodityInfModal(1, commId);
	},
	intoViewCommodityInf:function(){
		var commId = $(this).attr('commId');
		listCommodities.loadCommodityInfModal(2, commId);
	},
	editCommodityInf:function(){
		var commId = $("#commodity_id").val();
		var commName = $("#commodity_name").val();
		Helper.confirm("确定修改该商品？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/active/commodityInf/editCommodityInf.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "commId" : commId, 
	                "commName" : commName
	            },
	            success: function (data) {
	            	if(data > 0) {
	                	location = Helper.getRootPath() + '/active/commodityInf/listCommodities.do';
	                } else {
	                	Helper.alert("系统故障，请稍后再试");
	                	return false;
	                }
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
		    });
		});
	},
	deleteCommodityInf:function(){
		var commId = $(this).attr('commId');
		Helper.confirm("确定删除该商品？",function(){
			$.ajax({								  
				url: Helper.getRootPath() + '/active/commodityInf/deleteCommodityInf.do',
				type: 'post',
				dataType : "json",
				data: {
					"commId": commId
				},
				success: function (data) {
					if(data > 0) {
						location = Helper.getRootPath() + '/active/commodityInf/listCommodities.do';
					} else {
						Helper.alert("系统故障，请稍后再试");
						return false;
					}
				},
				error:function(){
					Helper.alert("系统故障，请稍后再试");
				}
			});
		});
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/active/commodityInf/listCommodities.do';
	},
	loadCommodityInfModal : function(type, commId){
		$('#commodityInfModal').modal({
			backdrop : "static"
		});
		if(type == 1){
			$('#commodityInfModal_h').html("商品编辑");
		}else if(type == 2){
			$('#commodityInfModal_h').html("商品详情");
			$("#commodity_name").attr("readonly","readonly");
			$("#commodity_dataStat").attr("disabled","true");
			$(".btn-submit").attr("disabled","true");
		}
		
		$.ajax({								  
            url: Helper.getRootPath() + '/active/commodityInf/getCommodityInfById.do',
            type: 'post',
            dataType : "json",
            data: {
                "commId": commId
            },
            success: function (data) {
            	$('#commodity_id').val(data.commodityId);
            	$('#mchnt_name').val(data.mchntName);
            	$('#commodity_name').val(data.commodityName);
            	$('#commodity_facevalue').val(data.commodityFacevalue);
            	$('#commodity_cost').val(data.commodityCost);
            	$('#commodity_dataStat').val(data.dataStat);
            },
            error:function(){
            	Helper.alert("系统故障，请稍后再试");
            }
	    });
		
		$("#commodityInfModal").on("hidden.bs.modal", function(e) {
			$("#commodity_name").removeAttr('readonly');
			$("#commodity_dataStat").removeAttr('disabled');
			$(".btn-submit").removeAttr('disabled');
		});
	}
}

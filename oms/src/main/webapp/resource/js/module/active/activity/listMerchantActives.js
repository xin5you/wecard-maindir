$(document).ready(function() {
	listMerchantActives.init();
})

var listMerchantActives = {
	init : function() {
		listMerchantActives.initEvent();
	},

	initEvent:function(){
		$('.date-time-picker').datetimepicker({
	        format: 'yyyy-MM-dd hh:mm:ss',
	        language: 'zh-CN',
	        pickDate: true,
	        pickTime: true,
	        hourStep: 1,
	        minuteStep: 5,
	        secondStep: 10,
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('.btn-search').on('click', listMerchantActives.searchData);
		$('.btn-reset').on('click', listMerchantActives.searchReset);
		$('.btn-add').on('click', listMerchantActives.intoAdd);
		$('.btn-edit').on('click', listMerchantActives.intoEdit);
		$('.btn-delete').on('click', listMerchantActives.deleteEntity);
		$('.btn-view').on('click', listMerchantActives.intoView);
	},

	searchData: function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('活动开始时间不能大于结束时间');
			return false;
		}
		document.forms['searchForm'].submit();
	},
	intoAdd:function(){
		location = Helper.getRootPath()+"/active/activity/intoAdd.do";
	},
	intoEdit:function(){
		var activeId = $(this).attr('activeId');
		if(activeId == null || activeId == '') {
			Helper.alert("系统故障，请稍后再试");
			return false;
		}
		location = Helper.getRootPath()+"/active/activity/intoEdit.do?activeId="+activeId;
	},
	intoView:function(){
		var activeId = $(this).attr('activeId');
		if(activeId == null || activeId == '') {
			Helper.alert("系统故障，请稍后再试");
			return false;
		}
		location = Helper.getRootPath()+"/active/activity/intoView.do?activeId="+activeId;
	},
	deleteEntity:function(){
		var activeId = $(this).attr('activeId');
		if(activeId == null || activeId == '') {
			Helper.alert("系统故障，请稍后再试");
			return false;
		}
		Helper.confirm("确定删除该优惠活动？",function(){
			$.ajax({								  
				url: Helper.getRootPath() + '/active/activity/deleteEntity.do',
				type: 'post',
				dataType : "json",
				data: {
					"activeId": activeId
				},
				success: function (data) {
					if(data > 0) {
						location = Helper.getRootPath() + '/active/activity/listMerchantActives.do';
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
		location = Helper.getRootPath() + '/active/activity/listMerchantActives.do';
	},
	loadCommodityInfModal : function(type, commId){
		$('#activityModal').modal({
			backdrop : "static"
		});
		if(type == 1){
			$('#activityModal_h').html("商品编辑");
		}else if(type == 2){
			$('#activityModal_h').html("商品详情");
			$("#commodity_name").attr("readonly","readonly");
			$(".btn-submit").attr("disabled","true");
		}
		
		$.ajax({								  
            url: Helper.getRootPath() + '/active/activity/getCommodityInfById.do',
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
            },
            error:function(){
            	Helper.alert("系统故障，请稍后再试");
            }
	    });
		
		$("#activityModal").on("hidden.bs.modal", function(e) {
			$("#commodity_name").removeAttr('readonly');
			$(".btn-submit").removeAttr('disabled');
		});
	}
}

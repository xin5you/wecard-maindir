$(document).ready(function () {
    MerchantActiveInf.init();
})

var MerchantActiveInf = {
    init: function () {
    	MerchantActiveInf.initEvent();
    	MerchantActiveInf.initTip();
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
		$('.btn-mchnt-list').on('click', MerchantActiveInf.loadMchntListModal);
		$('.btn-return').on('click', MerchantActiveInf.rtnList);
		$('#mchntId').chosen();
	},
	initTip: function () {
		var ttip_validator = $('.form_validation_tip').validate({
            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            highlight: function (element) {
                $(element).closest('div').addClass("f_error");
            },
            unhighlight: function (element) {
                $(element).closest('div').removeClass("f_error");
            },
            rules: {
            	activeName: { required: true },
            	activeStat: { required: true },
            	startTime: { required: true },
            	endTime: { required: true }
            },
            messages: {
            	activeName: {
                    required:"请输入活动名称"
                },
                activeStat: {
					required:"请选择活动状态"
				},
				startTime: {
					required:"请输入活动开始时间"
				},
				endTime: {
					required:"请输入活动结束时间"
				}
            },
            invalidHandler: function (form, validator) {
                //$.sticky("There are some errors. Please corect them and submit again.", {autoclose : 5000, position: "top-right", type: "st-error" });
            },
            errorPlacement: function (error, element) {
                var elem = $(element);
                if (!error.is(':empty')) {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.filter(':not(.valid)').parent('label').parent('div').find('.error_placement').qtip({
                            overwrite: false,
                            content: error,
                            position: {
                                my: 'left center',
                                at: 'center right',
                                viewport: $(window),
                                adjust: {
                                    x: 6
                                }
                            },
                            show: {
                                event: false,
                                ready: true
                            },
                            hide: false,
                            style: {
                                classes: 'ui-tooltip-red ui-tooltip-rounded' // Make it red... the classic error colour!
                            }
                        }).qtip('option', 'content.text', error);
                    } else {
                        var xPoint = 5;
                        if (elem.attr('name') == 'startTime'){
                            xPoint = 15;
                        }
                        if (elem.attr('name') == 'endTime'){
                        	xPoint = 15;
                        }
                        elem.filter(':not(.valid)').qtip({
                            overwrite: false,
                            content: error,
                            position: {
                                my: 'left center',
                                at: 'center right',
                                viewport: $(window),
                                adjust: { x: xPoint, y: 0 }
                            },
                            show: {
                                event: false,
                                ready: true
                            },
                            hide: false,
                            style: {
                                classes: 'ui-tooltip-red ui-tooltip-rounded' 
                            }
                        }).qtip('option', 'content.text', error);
                    };
                } else {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.parent('label').parent('div').find('.error_placement').qtip('destroy');
                    } else {
                        elem.qtip('destroy');
                    }
                }
            },
            submitHandler: function (form) {
            	MerchantActiveInf.addMerchantActiveInf();
                return false;
            },
            success: $.noop 
        });
    },
    addMerchantActiveInf:function(){
    	var st = $('#startTime').val();
		var et = $('#endTime').val();
		if(st !='' && et !='' && st.localeCompare(et)>0){
			Helper.alert('活动开始时间不能大于结束时间');
			return false;
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/activity/addEntity.do',
            data: {
            		"mchntId" : $("#mchntId").val(),
            		"activeName" : $("#activeName").val(),
            		"activeStat" : $("#activeStat").val(),
            		"startTime": $("#startTime").val(),
            		"endTime": $("#endTime").val(),
            		"activeExplain": $("#activeExplain").val(),
            		"activeRule": $("#activeRule").val()
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data != null && data != '') {
                	Helper.confirm_one('新增优惠活动成功', function(){
                		location = Helper.getRootPath()+"/active/activity/intoEdit.do?activeId="+data;
                	});
                }else{
                	Helper.alert("系统故障，请稍后再试");
                	return false;
                }
            }
        });
    },
    rtnList:function(){
    	location = Helper.getRootPath()+"/active/activity/listMerchantActives.do";
    },
	loadMchntListModal : function(type, code){
		$('#mchntListModal').modal({
			backdrop : "static"
		});
		$("#mchntListModal").on("shown.bs.modal", function(e) {
			$('#mchnt_name').text($("#mchntId").find("option:selected").text());
			var mchnts = $("#mchntId").val().split(",");
	    	var mchntId = mchnts[0];
			$.ajax({
	            type: 'POST',
	            url: Helper.getRootPath() + '/active/activity/getCommoditiesByMchntId.do',
	            data: {mchntId : mchntId},
	            cache:false,
	            dataType: 'json',
	            success: function(data){
	            	if(data.length > 0) {
	            		for(var i=0; i<data.length; i++) {
		            		$('#mchnt_commodities').append('<tr>' +
		            				'<td>'+data[i].commodityId+'</td>' +
		            				'<td>'+data[i].commodityName+'</td>' +
		            				'<td>'+data[i].commodityFacevalue+'</td>' +
		            				'<td>'+data[i].commodityCost+'</td>' +
		            				'</tr>');
		            	}
	            	} else {
	            		$('#mchnt_commodities').append('<td colspan="4">无记录</td>');
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	        });
		});
		$("#mchntListModal").on("hidden.bs.modal", function(e) {
			$('#mchnt_commodities').html('');
		});
	}
};


$(document).ready(function () {
    CommodityInf.init();
})

var CommodityInf = {
    init: function () {
    	CommodityInf.initEvent();
    	CommodityInf.initTip();
    },
	initEvent:function(){
		$('.btn-mchnt-list').on('click', CommodityInf.loadMchntListModal);
		$('.btn-return').on('click', CommodityInf.rtnListCommodities);
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
            	commodityName: { required: true },
            	commodityFacevalue: { required: true},
            	commodityCost: { required: true}
            },
            messages: {
            	commodityName: {
                    required: "请输入商品名称"
                },
                commodityFacevalue: {
					required:"请输入商品面额"
				},
				commodityCost: {
					required:"请输入商品成本"
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
                        /*if (elem.attr('name') == 'authCode'){//特殊处理验证码
                            xPoint = 115;
                        }*/
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
            	CommodityInf.addCommodityInf();
                return false;
            },
            success: $.noop 
        });
    },
    addCommodityInf:function(){
    	var mchnts = $("#mchntId").val().split(",");
    	var mchntId = mchnts[0];
    	var productCode = mchnts[1];
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/commodityInf/addCommodityInf.do',
            data: {
            		"mchntId" : mchntId,
            		"productCode" : productCode,
            		"commodityName" : $("#commodityName").val(),
            		"commodityFacevalue": $("#commodityFacevalue").val(),
            		"commodityCost": $("#commodityCost").val()
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data != null && data != '') {
                	Helper.confirm_one('新增商品成功', function(){
                		CommodityInf.rtnListCommodities();
                	});
                }else{
                	Helper.alert("系统故障，请稍后再试");
                	return false;
                }
            }
        });
    },
    rtnListCommodities:function(){
    	location = Helper.getRootPath()+"/active/commodityInf/listCommodities.do";
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
	            url: Helper.getRootPath() + '/active/commodityInf/getCommoditiesByMchntId.do',
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


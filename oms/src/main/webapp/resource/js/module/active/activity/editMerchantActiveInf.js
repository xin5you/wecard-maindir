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
		$('.btn-add-comm').on('click', MerchantActiveInf.loadCommListModal);
		$('.btn-add').on('click', MerchantActiveInf.addCommTr);
		$('#mchntId').chosen();
		$('#activeStat').change(function(){
			var activeStat = $('#activeStat').val();
			if (activeStat == 1) {
				$('#activeName').attr('readonly','readonly');
				$('.data-time-icon').attr('hidden','hidden');
			} else{
				$('#activeName').removeAttr('readonly');
				$('.data-time-icon').removeAttr('hidden');
			}
		});
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
            	MerchantActiveInf.editEntity();
                return false;
            },
            success: $.noop 
        });
    },
    editEntity:function(){
    	var st = $('#startTime').val();
		var et = $('#endTime').val();
		if(st !='' && et !='' && st.localeCompare(et)>0){
			Helper.alert('活动开始时间不能大于结束时间');
			return false;
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/activity/editEntity.do',
            data: {
            	"activeId" : $("#activeId").val(),
        		"mchntId" : $("#mchntId").val(),
        		"activeName" : $("#activeName").val(),
        		"activeStat" : $("#activeStat").val(),
        		"startTime": st,
        		"endTime": et,
        		"activeExplain": $("#activeExplain").val(),
        		"activeRule": $("#activeRule").val()
        	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data > 0) {
                	Helper.confirm_one('编辑优惠活动成功', function(){
                		location = location;
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
	loadCommListModal : function(type, code){
		$('#commListModal').modal({
			backdrop : "static",
			width: 750,
			height:300
		});
		$("#commListModal").on("shown.bs.modal", function(e) {
			var activeStat = $("#activeStat_h").val();
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
	            	$("#commNum").val(0);// 设置行初始值（默认为0）
	            	if(data != null && data.length > 0) {
	            		$("#commNum").val(data.length);// 设置已有商品数量
	            		var cnStr,spStr,stStr,saveBtn,delBtn,commIdStr_h;
	            		for(var i=0; i<data.length; i++) {
	            			spStr = "<td><input id='sellingPrice"+i+"'"+" value='"+data[i].sellingPrice+"' style='margin-bottom: 0px' type='text' class='span1' /></td>";
            				stStr = "<td><input id='stock"+i+"'"+" value='"+data[i].stock+"' style='margin-bottom: 0px' type='text' class='span1' /></td>";
	            			saveBtn = "<a title='保存' href='#' class='btn-mini btn-ok-"+i+"'><i class='icon-ok'></i></a>";
	            			delBtn = "<a title='删除' href='#' class='btn-mini btn-remove-"+i+"'><i class='icon-remove'></i></a>";
	            			commIdStr_h = "<input id='commId_h_"+i+"' value='"+data[i].commodityId+"' type='hidden' />";
	            			if (activeStat==0) {
	            				var options='';
	            				var commArr = data[i].commList;
	                    		for(var j=0; j<commArr.length; j++) {
	                    			options += "<option value='"+commArr[j].commodityId+"|"+commArr[j].commodityFacevalue+"|"+commArr[j].commodityCost+"|"+i+"'>"+commArr[j].commodityName+"</option>";
	        					}
	            				cnStr = "<td><select id='commId"+i+"'"+" class='span2'>"+options+"</select></td>";
	            			} else {
	            				cnStr = "<td>" + data[i].commodityName + "</td>";
	            				if (activeStat==1) {
	            					spStr = "<td>" + data[i].sellingPrice + "</td>";
		            				stStr = "<td>" + data[i].stock + "</td>";
		            				saveBtn = '';
		            				delBtn = '';
	            				}
	            			}
		            		$('#commList').append('<tr>' +
			            		cnStr +
			            		"<td><span id='commodityFacevalue"+i+"'>"+data[i].commodityFacevalue+"</span></td>"+
			            		"<td><span id='commodityCost"+i+"'>"+data[i].commodityCost+"</span></td>"+
	            				 spStr +
	            				 stStr +
	            				"<td>" + saveBtn + delBtn + commIdStr_h + "</td>"+
            				'</tr>');
		            		if($('#commId'+i).length && $('#commId'+i).length>0) {
		            			$('#commId'+i).val(data[i].commodityId+"|"+data[i].commodityFacevalue+"|"+data[i].commodityCost+"|"+i);
		            			$('#commId'+i).change(function(){
		            				var commArr = this.value.split("|");
		            				$("#commodityFacevalue"+commArr[3]).text(commArr[1]);
		            				$("#commodityCost"+commArr[3]).text(commArr[2]);
		            			});
		            		}
		            		if($('.btn-ok-'+i).length && $('.btn-ok-'+i).length>0) {
		            			$('.btn-ok-'+i).attr('onClick', 'MerchantActiveInf.editComm('+i+',"'+data[i].activeListId+'","'+data[i].commodityName+'")');
		            		}
		            		if($('.btn-remove-'+i).length && $('.btn-remove-'+i).length>0) {
		            			$('.btn-remove-'+i).attr('onClick', 'MerchantActiveInf.delComm(this,"'+data[i].activeListId+'")');
		            		}
		            	}
	            	} else {
	            		$('#commList').append('<td id="null_record" colspan="6">无记录</td>');
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
	},
	addCommTr : function() {
		var i = parseInt($("#commNum").val());
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/activity/getCommoListByMchntId.do',
            data: {
        		"mchntId" : $("#mchntId_h").val()
        	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	if(data != null && data.length > 0) {
            		var trNum = $("#commList>tr").length;
            		if (data.length <= trNum) {
            			$('#comm_opr_info').text('对不起，没有更多的商品可以添加');
            			return false;
            		}
            		$('#comm_opr_info').text('');
            		// 去除'无记录'显示行
            		if($('#null_record').length && $('#null_record').length>0) {
            			$('#null_record').remove();
            		}
            		// 设置默认活动商品ID
            		var options='';
            		for(var j=0; j<data.length; j++) {
            			options += "<option value='"+data[j].commodityId+"|"+data[j].commodityFacevalue+"|"+data[j].commodityCost+"'>"+data[j].commodityName+"</option>";
					}
            		$('#commList').append(
            		'<tr>' +
        				"<td><select id='commId"+i+"'"+" style='margin-bottom: 0px;' class='span2'>"+options+"</select></td>" +
        				"<td><span id='commodityFacevalue"+i+"'>"+data[0].commodityFacevalue+"</span></td>" +
        				"<td><span id='commodityCost"+i+"'>"+data[0].commodityCost+"</span></td>" +
        				"<td><input id='sellingPrice"+i+"'"+" style='margin-bottom: 0px' type='text' class='span1' /></td>" +
        				"<td><input id='stock"+i+"'"+" style='margin-bottom: 0px' type='text' class='span1' /></td>" +
        				"<td>" + 
	        				"<a title='保存' href='#' class='btn-mini btn-ok-"+i+"'><i class='icon-ok'></i></a>" + 
	        				"<a title='删除' href='#' class='btn-mini btn-remove-"+i+"'><i class='icon-remove'></i></a>" + 
	        				"<input id='commId_h_"+i+"' value='"+data[0].commodityId+"' type='hidden' />" + 
        				"</td>" + 
        			'</tr>');
        	   		$('#commId'+i).change(function(){
        				var commArr = $("#commId"+i).val().split("|");
        				$("#commId_h_"+i).val(commArr[0]);
        				$("#commodityFacevalue"+i).text(commArr[1]);
        				$("#commodityCost"+i).text(commArr[2]);
        			});
        	   		$('.btn-ok-'+i).attr('onClick', 'MerchantActiveInf.addComm('+i+')');
        	   		$('.btn-remove-'+i).attr('onClick', 'MerchantActiveInf.delCommAdd(this)');
        	   		$("#commNum").val(i + 1);
                }else{
                	Helper.alert("系统故障，请稍后再试");
                	return false;
                }
            }
        });
   	},
   	addComm : function(k) {
   		var name = $("#commId"+k).find("option:selected").text();
   		if($('#sellingPrice'+k).length && $('#sellingPrice'+k).length>0) {
			var sellingPrice = $.trim($('#sellingPrice'+k).val());
			if (sellingPrice == '' || sellingPrice == null) {
				$('#comm_opr_info').text('【'+name+'】活动售价不能为空');
			}
			if (isNaN(sellingPrice)) {
				$('#comm_opr_info').text('【'+name+'】活动售价必须为数字，请重新输入');
				return false;
			}
		}
		if($('#stock'+k).length && $('#stock'+k).length>0) {
			var stock = $.trim($('#stock'+k).val());
			if (stock == '' || stock == null) {
				$('#comm_opr_info').text('【'+name+'】库存不能为空');
			}
			if (isNaN(stock)) {
				$('#comm_opr_info').text('【'+name+'】库存必须为数字，请重新输入');
				return false;
			}
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/activity/addActiveList.do',
            data: { 
            	"activeId" : $("#activeId").val(),
            	"commodityId" : $("#commId_h_"+k).val(),
            	"sellingPrice" : $('#sellingPrice'+k).val(),
            	"stock" : $('#stock'+k).val()
            },
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data != null && data !='') {
                	$('#comm_opr_info').text('【'+name+'】添加成功');
                	$('.btn-ok-'+k).hide();
                }else{
                	Helper.alert("系统故障，请稍后再试");
                	return false;
                }
            }
        });
   	},
	editComm : function(k, id, name) {
		if($('#sellingPrice'+k).length && $('#sellingPrice'+k).length>0) {
			if($('#commId'+k).length && $('#commId'+k).length>0) {
				name = $("#commId"+k).find("option:selected").text();
			}
			var sellingPrice = $.trim($('#sellingPrice'+k).val());
			if (sellingPrice == '' || sellingPrice == null) {
				$('#comm_opr_info').text('【'+name+'】活动售价不能为空');
			}
			if (isNaN(sellingPrice)) {
				$('#comm_opr_info').text('【'+name+'】活动售价必须为数字，请重新输入');
				return false;
			}
		}
		if($('#stock'+k).length && $('#stock'+k).length>0) {
			var stock = $.trim($('#stock'+k).val());
			if (stock == '' || stock == null) {
				$('#comm_opr_info').text('【'+name+'】库存不能为空');
			}
			if (isNaN(stock)) {
				$('#comm_opr_info').text('【'+name+'】库存必须为数字，请重新输入');
				return false;
			}
		}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/active/activity/editActiveList.do',
            data: { 
            	"activeListId" : id,
            	"commodityId" : $('#commId_h_'+k).val(),
            	"sellingPrice" : $('#sellingPrice'+k).val(),
            	"stock" : $('#stock'+k).val()
            },
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data > 0) {
                	$('#comm_opr_info').text('【'+name+'】保存成功');
                }else{
                	Helper.alert("系统故障，请稍后再试");
                	return false;
                }
            }
        });
	},
	delCommAdd : function(k) {
		$(k).parent().parent().remove();
	},
	delComm : function(k,id) {
		Helper.confirm("确定删除该商品？",function(){
			$.ajax({
				type: 'POST',
				url: Helper.getRootPath() + '/active/activity/deleteActiveList.do',
				data: { "activeListId" : id },
				cache:false,
				dataType: 'json',
				success: function(data){
					if(data > 0) {
						$(k).parent().parent().remove();
					}else{
						Helper.alert("系统故障，请稍后再试");
						return false;
					}
				}
			});
		});	
	}
};


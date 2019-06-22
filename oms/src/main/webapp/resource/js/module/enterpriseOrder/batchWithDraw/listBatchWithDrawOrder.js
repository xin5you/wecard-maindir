$(document).ready(function() {
    listBatchOrder.init();
})

var listBatchOrder = {
	init : function() {
        listBatchOrder.initEvent();
	/*	var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);*/
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
	        endDate: new Date(new Date()),
	        initialDate: new Date(new Date()),
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('#datetimepicker1').show();
		$('#datetimepicker2').show();

        $('.btn-submit').on('click', listBatchOrder.addOrderAgainCommit);
		$('.btn-delete').on('click', listBatchOrder.deleteCommit);
        $('.btn-view-list').on('click', listBatchOrder.intoOrderDetialList);
		$('.btn-into-export-order').on('click', listBatchOrder.intoExportOrderModal);

	},
	searchData:function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('交易开始时间不能大于结束时间');
			return false;
		}
		document.forms['searchForm'].submit();
	},
	searchReset:function(){
		location.href = Helper.getRootPath() + '/batchWithdrawOrder/listBatchWithdrawOrder.do';
	},
	intoExportOrderModal:function(){
        $('#batchExportOrderModal').modal({
            backdrop : "static"
        });
	},

	orderImportCommit:function(){
		$("#orderImportBtn").attr("disabled",true);

        $('#imorptMsg').modal({
            backdrop : "static"
        });
        var batchOrderName=$("#batchOrderName").val();
        $("#uploadMainForm").ajaxSubmit({
            type:'post', // 提交方式 get/post
            timeout : 120000,
            url: Helper.getRootPath() + '/batchWithdrawOrder/excelImp.do', // 需要提交的 url
            dataType: 'json',
            data: {
                "batchOrderName":batchOrderName
            },
            success: function(data){
                if(data.status) {
                    location.href=Helper.getRootPath() + '/batchWithdrawOrder/listBatchWithdrawOrder.do';
                }else{
                    $('#imorptMsg').modal('hide');
                    Helper.alert(data.msg);
                    $("#orderImportBtn").removeAttr("disabled");
                    return false;
                }
            }
        });
    },
	intoOrderDetialList:function(){
		var orderId = $(this).attr('orderId');
		var url = Helper.getRootPath()+"/batchWithdrawOrderDetail/listBatchWithDrawOrderDetail.do?orderId="+orderId;
		location.href=url;
	},
	deleteCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您是否删除该记录？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/batchWithdrawOrder/deleteCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/batchWithdrawOrder/listBatchWithdrawOrder.do?operStatus=4';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	},
	addOrderAgainCommit:function(){
		var orderId = $(this).attr('orderId');
		Helper.confirm("您确认提交该订单吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/addOrderAgainCommit.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "orderId": orderId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location.href=Helper.getRootPath() + '/enterpriseOrder/batchOpenCard/listOpenCard.do?operStatus=1';
	            	}else{
	            		Helper.alter(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	}
}

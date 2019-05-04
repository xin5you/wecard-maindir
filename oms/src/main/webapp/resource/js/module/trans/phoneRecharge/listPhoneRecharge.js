$(document).ready(function() {
	listPhoneRecharge.init();
})

var listPhoneRecharge = {
	init : function() {
		listPhoneRecharge.initEvent();
//		listPhoneRecharge.showDatetimepicker();
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
	        endDate: new Date(new Date() - 86400000),
	        initialDate: new Date(new Date() - 86400000),
	        inputMask: true
	      }).on('changeDate', function(ev) {
	    	  //alert(ev.date.valueOf());
	    });
		$('#datetimepicker1').show();
		$('#datetimepicker2').show();
		$('.btn-search').on('click', listPhoneRecharge.searchData);
		$('.btn-reset').on('click', listPhoneRecharge.searchReset);
		$('.btn-upload').on('click',listPhoneRecharge.uploadListPhoneRecharge);
		$('.btn-edit').on('click',listPhoneRecharge.refundPhoneRecharge);
	},

	searchData: function(){
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		if(sd != '' || sd != null){
			if (sd != '' && ed != '' && sd.localeCompare(ed) > 0) {
				Helper.alert('开始时间不能大于结束时间');
				return false;
			}
		}
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/phone/phoneRecharge/getPhoneRechargeList.do';
	},
	uploadListPhoneRecharge:function(){
		var rId = $("#rId").val();
		var supplierOrderNo = $("#supplierOrderNo").val();
		var channelOrderNo = $("#channelOrderNo").val();
		var personalName = $("#personalName").val();
		var mobilePhoneNo = $("#mobilePhoneNo").val();
		var phone = $("#phone").val();
		var transStat = $("#transStat").val();
		var orderType = $('#orderType').val();
		var reqChannel = $('#reqChannel').val();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		location = Helper.getRootPath() + '/phone/phoneRecharge/uploadListPhoneRecharge.do?rId='+rId+'&supplierOrderNo='+supplierOrderNo+'&channelOrderNo='+channelOrderNo+'&personalName='+personalName+'&mobilePhoneNo='+mobilePhoneNo+'&phone='+phone+'&transStat='+transStat+'&orderType='+orderType+'&reqChannel='+reqChannel+'&startTime='+startTime+'&endTime='+endTime;
		
	},
	refundPhoneRecharge:function(){
		var rId = $(this).attr('rId');
		
		Helper.confirm("该订单【"+rId+"】确定要退款吗？",function(){
		    $.ajax({								  
	            url: Helper.getRootPath() + '/phone/phoneRecharge/refundPhoneRecharge.do',
	            type: 'post',
	            dataType : "json",
	            data: {
	                "rId": rId
	            },
	            success: function (result) {
	            	if(result.status){
	            		location = Helper.getRootPath() + '/phone/phoneRecharge/getPhoneRechargeList.do';
	            	}else{
	            		Helper.alert(result.msg);
	            	}
	            },
	            error:function(){
	            	Helper.alert("系统故障，请稍后再试");
	            }
	      });
		});
	}
}

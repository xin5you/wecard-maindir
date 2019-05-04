$(document).ready(function() {
	listTrans.init();
})

var listTrans = {
	init : function() {
		listTrans.initEvent();
		listTrans.showDatetimepicker();
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
		$('.btn-search').on('click', listTrans.searchData);
		$('.btn-reset').on('click', listTrans.searchReset);
		$('#queryType').on('change', listTrans.showDatetimepicker);
		$('.btn-upload').on('click',listTrans.uploadListTrans);
	},

	searchData: function(){
		var queryType = $('#queryType').val();
		if (queryType == 'his') {
			var sd = $('#startTime').val();
			var ed = $('#endTime').val();
			if (ed == '' || ed == null) {
				Helper.alert('交易结束时间不能为空');
				return false;
			}
			var s_d = sd.replace(new RegExp("-","g"),"").substring(0, 8);
			var e_d = ed.replace(new RegExp("-","g"),"").substring(0, 8);
			var now = new Date();
			var year = now.getFullYear();
		    var month =(now.getMonth() + 1).toString();
		    var day = (now.getDate()).toString();
		    if (month.length == 1) {
		        month = "0" + month;
		    }
		    if (day.length == 1) {
		        day = "0" + day;
		    }
		    var c_d = year + month + day;
			if (s_d == c_d) {
				Helper.alert('交易开始时间不能为当天');
				return false;
			}
			if (e_d == c_d) {
				Helper.alert('交易结束时间不能为当天');
				return false;
			}
			if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
				Helper.alert('交易开始时间不能大于结束时间');
				return false;
			}
		}
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/merchant/transInf/listTrans.do';
	},
	showDatetimepicker: function(){
		var queryType = $('#queryType').val();
		if (queryType == 'his') {
			$('#datetimepicker1').show();
			$('#datetimepicker2').show();
		} else {
			$('#datetimepicker1').hide();
			$('#datetimepicker2').hide();
			$('#startTime').val('');
			$('#endTime').val('');
		}
	},
	uploadListTrans:function(){
		var mchntName = $("#mchntName").val();
		var mchntCode = $("#mchntCode").val();
		var shopName = $("#shopName").val();
		var shopCode = $("#shopCode").val();
		var channelCode = $("#channelCode").val();
		var userName = $("#userName").val();
		var transType = $("#transType").val();
		var queryType = $('#queryType').val();
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		if (queryType == 'his') {
			if (ed == '' || ed == null) {
				Helper.alert('交易结束时间不能为空');
				return false;
			}
			var s_d = sd.replace(new RegExp("-","g"),"").substring(0, 8);
			var e_d = ed.replace(new RegExp("-","g"),"").substring(0, 8);
			var now = new Date();
			var year = now.getFullYear();
		    var month =(now.getMonth() + 1).toString();
		    var day = (now.getDate()).toString();
		    if (month.length == 1) {
		        month = "0" + month;
		    }
		    if (day.length == 1) {
		        day = "0" + day;
		    }
		    var c_d = year + month + day;
			if (s_d == c_d) {
				Helper.alert('交易开始时间不能为当天');
				return false;
			}
			if (e_d == c_d) {
				Helper.alert('交易结束时间不能为当天');
				return false;
			}
			if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
				Helper.alert('交易开始时间不能大于结束时间');
				return false;
			}
			location = Helper.getRootPath() + '/merchant/transInf/uploadListTrans.do?mchntCode='+mchntCode+'&mchntName='+mchntName+'&shopCode='+shopCode+'&shopName='+shopName+'&queryType='+queryType+'&transType='+transType+'&userName='+userName+'&channelCode='+channelCode+'&startTime='+sd+'&endTime='+ed;
		} else {
			location = Helper.getRootPath() + '/merchant/transInf/uploadListTrans.do?mchntCode='+mchntCode+'&mchntName='+mchntName+'&shopCode='+shopCode+'&shopName='+shopName+'&queryType='+queryType+'&transType='+transType+'&userName='+userName+'&channelCode='+channelCode;
		}
		
	}
}

$(document).ready(function() {
	hkbTransQuery.init();
});

var hkbTransQuery = {
		init : function() {
//			detailDialog = new hkbShowDialog({
//				'ele' : 'addMember'
//			});
			hkbTransQuery.initEvent();
			hkbTransQuery.showDatetimepicker();
		},
		initEvent:function(){
			$('#queryType').on('change', hkbTransQuery.showDatetimepicker);
			$('#btn-search').on('click', hkbTransQuery.searchDate);
			$('#btn-reset').on('click', hkbTransQuery.searchReset);
			$('.trans_refund').on('click', hkbTransQuery.transRefund);
			$("#identifyingCode").on('click', hkbTransQuery.sendPhoneSMS);
			$("#drawBack").on('click', hkbTransQuery.transRefundCommit);
			$("#hkb_close").on('click', hkbTransQuery.hkbClose);
			$(".Wdate").on('click',hkbTransQuery.showDate);
		},
		showDatetimepicker: function(){
			var queryType = $('#queryType').val();
			if (queryType == 'his') {
				$('#date1').show();
				$('#date2').show();
			} else {
				$('#date1').hide();
				$('#date2').hide();
				$('#startTransTime').val('');
				$('#endTransTime').val('');
			}
		},
		showDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd HH:mm:ss',
				maxDate:'%y-%M-{%d-1} 23\:59\:59',
				readOnly:true,
				opposite:true
				});
		},
		searchDate:function(){
			var queryType = $('#queryType').val();
			if (queryType == 'his') {
				var sd = $('#startTransTime').val();
				var ed = $('#endTransTime').val();
				var end = Helper.lastMonthDate(ed);
				if(sd ==''){
					Helper.alert('系统提示','请选择开始时间');
					return false;
				}
				if(ed ==''){
					Helper.alert('系统提示','请选择结束时间');
					return false;
				}
				if (sd.localeCompare(Helper.nowDate())>=0) {
					Helper.alert('系统提示','开始时间必须为当天以前时间');
					return false;
				}
				if (ed.localeCompare(Helper.nowDate())>=0) {
					Helper.alert('系统提示','结束必须为当天以前时间');
					return false;
				}
				if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
					Helper.alert('系统提示','开始时间不能大于结束时间');
					return false;
				}
				if(sd <= end){
					Helper.alert('系统提示',"时间间隔不能大于一个月！");
				 	return false;
				}
			}
			document.forms['searchForm'].submit();
		},
		searchReset:function(){
			location = Helper.getRootPath() +'/trans/mchnt/getTransQuery';
		},
		transRefund:function(){
			 //退款弹出框
		    memberManagement.refundsDom();
			var dmsKey = $(this).attr('dmsKey');
			$.ajax({
				type : 'post',
				url : Helper.getRootPath() + '/trans/mchnt/intoNotarizeTrans',
				data : {
					"dmsRelatedKey" : dmsKey
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					var reg = /^(\d{3})\d{4}(\d{4})$/;
					$("#dmsKey").html(data.dmsRelatedKey);
					$("#txnKey").val(data.txnPrimaryKey);
					$("#phone").val(data.phoneNumber);
					$("#settleDate").html(data.settleDate);
					$("#transAmt").html(data.transAmt+"元");
					$("#phoneNumber").html(data.phoneNumber.replace(reg,"$1****$2"));
					$("#identifyingCode").attr("phoneNumber",data.phoneNumber);
				},
				error : function() {
					alert("查看详情出错");
				}
			});
		},
		sendPhoneSMS:function(){
			var phone = $(this).attr("phoneNumber");
			DiyCommon.sendPhoneSMS(phone,"03");
		},
		transRefundCommit:function(){
			var txnKey = $("#txnKey").val();
			var phone = $("#phone").val();
			var phoneCode = $("#phoneCode").val();
			if(phoneCode== ''){
				$("#dialog_error").html("*请输入验证码");
				return false;
			}
			$.ajax({
				type : 'post',
				url : Helper.getRootPath() + '/trans/mchnt/transRefundCommit',
				data : {
					"txnKey": txnKey,
					"phone": phone,
					"phoneCode":phoneCode
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data.status){
						refundsDialog.hide()
				        setTimeout(function () {
				            stateDialog.show()
				        },600);
//						var r = confirm("退款成功！！！");
//						if(r==true){
//							location = Helper.getRootPath() + '/trans/mchnt/getTransQuery';
//						}
					}else{
						$("#dialog_error").html("*"+data.msg);
					}
				},
				error : function() {
					alert("查看详情出错");
				}
			});
		},
		hkbClose:function(){
			stateDialog.hide();
			location = Helper.getRootPath() + '/trans/mchnt/getTransQuery';
		}
};
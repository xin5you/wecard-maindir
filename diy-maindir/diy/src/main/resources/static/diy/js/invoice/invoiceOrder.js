$(document).ready(function() {
	hkbInvoiceOrder.init();
});

var hkbInvoiceOrder = {
		init : function() {
//			detailDialog = new hkbShowDialog({
//				'ele' : 'addMember'
//			});
			hkbInvoiceOrder.initEvent();
			hkbInvoiceOrder.showDatetimepicker();
		},
		initEvent:function(){
			$('#queryType').on('change', hkbInvoiceOrder.showDatetimepicker);
			$('#btn-search').on('click', hkbInvoiceOrder.searchDate);
			$("a[name='invoice']").on('click', hkbInvoiceOrder.transRefund);
			$("a[name='invoiceDetails']").on('click',hkbInvoiceOrder.invoiceDetails);
			$("#btn-reset").on('click', hkbInvoiceOrder.reset);
			$("#drawBack").on('click', hkbInvoiceOrder.invoiceOrderCommit);
			$("#hkb_close").on('click', hkbInvoiceOrder.hkbClose);
			$(".Wdate").on('click',hkbInvoiceOrder.showDate);
		},
		showDate:function(){
			WdatePicker({
				el:this,
				dateFmt:'yyyy-MM-dd',
				maxDate:'%y-%M-{%d-1}',
				readOnly:true
				});
		},
		showDatetimepicker: function(){
			var queryType = $('#queryType').val();
			if (queryType == 'his') {
				$('#transTime').show();
			} else {
				$('#transTime').hide();
				$('#startTransTime').val('');
				$('#endTransTime').val('');
			}
		},
		searchDate:function(){
			var queryType = $('#queryType').val();
			if(queryType == 'his'){
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
				if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
					Helper.alert('系统提示','开始时间不能大于结束时间');
					return false;
				}
			}
			var dmsRelatedKey = $("#dmsRelatedKey").val();
			var personName = $("#personName").val();
			var invoiceStat = $("#invoiceStat").val();
			var queryType = $("#queryType").val();
			if(dmsRelatedKey == '' && personName == '' && invoiceStat == '-1' && queryType == '-1'){
				Helper.alert('系统提示','查询条件不能全部为空');
				return false;
			}
			document.forms['searchForm'].submit();
		},
		transRefund:function(){
			 //开票弹出框
		    memberManagement.refundsDom();
			var dmsKey = $(this).attr('dmsKey');
			$('.dialog_top p').html("开票确认");
			$("#invoiceInf").val("").attr("readonly",false);
			$("#dialog_error").html("");
			$("#createtime1").hide();
			$('#drawBack').show();
			$.ajax({
				type : 'post',
				url : Helper.getRootPath() + '/invoice/invoiceOrder/intoInvoieceOrder',
				data : {
					"dmsRelatedKey" : dmsKey
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					var reg = /^(\d{3})\d{4}(\d{4})$/;
					$("#dmsKey").html(data.dmsRelatedKey);
					$("#itfPrimaryKey").val(data.dmsRelatedKey);
					$("#phone").val(data.phoneNumber);
					$("#settleDate").html(data.settleDate);
					$("#transAmt").html(data.transAmt+"元");
					$("#phoneNumber").html(data.phoneNumber.replace(reg,"$1****$2"));
					$("#mchntName").html(data.mchntName);
					$("#mchntCode").val(data.mchntCode);
					$("#shopName").html(data.shopName);
					$("#shopCode").val(data.shopCode);
					$("#invoiceAmt").val(data.transAmt);
				},
				error : function() {
					alert("开票信息出错");
				}
			});
		},
		invoiceDetails:function(){
			 //开票详情弹出框
			memberManagement.refundsDom();
			var dmsKey = $(this).attr('dmsKey');
			$('.dialog_top p').html("开票详情");
			$("#dialog_error").html("");
			$("#createtime1").show();
			$('#drawBack').hide();
			$.ajax({
				type : 'post',
				url : Helper.getRootPath() + '/invoice/invoiceOrder/getInvoiceByItfPrimaryKey',
				data : {
					"dmsRelatedKey" : dmsKey
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					var reg = /^(\d{3})\d{4}(\d{4})$/;
					$("#dmsKey").html(data.dmsRelatedKey);
					$("#transAmt").html(data.transAmt+"元");
					$("#phoneNumber").html(data.phoneNumber.replace(reg,"$1****$2"));
					$("#mchntName").html(data.mchntName);
					$("#shopName").html(data.shopName);
					$("#invoiceInf").val(data.invoiceInfo).attr("readonly","readonly");
					$("#createtime").html(data.createTime);
				},
				error : function() {
					alert("查看详情出错");
				}
			});
		},
		reset:function(){
			location = Helper.getRootPath() + '/invoice/invoiceOrder/getTransQuery';
		},
		invoiceOrderCommit:function(){
			var itfPrimaryKey = $("#itfPrimaryKey").val();
			var invoiceAmt = $("#invoiceAmt").val();
			var phone = $("#phone").val();
			var mchntCode = $("#mchntCode").val();
			var shopCode = $("#shopCode").val();
			var invoiceInfo = $("#invoiceInf").val();
			$.ajax({
				type : 'post',
				url : Helper.getRootPath() + '/invoice/invoiceOrder/invoieceOrderCommit',
				data : {
					"itfPrimaryKey": itfPrimaryKey,
					"phone": phone,
					"invoiceAmt": invoiceAmt,
					"mchntCode":mchntCode,
					"shopCode":shopCode,
					"invoiceInfo":invoiceInfo
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					if (data.code == '00') {
						refundsDialog.hide()
				        setTimeout(function () {
				            stateDialog.show()
				        },600);
	                } else {
	                	$("#dialog_error").html("*"+data.msg);
	                	return false;
	                }
				},
				error : function() {
					alert("查看详情出错");
				}
			});
		},
		hkbClose:function(){
			stateDialog.hide();
			location = Helper.getRootPath() + '/invoice/invoiceOrder/getTransQuery';
		}
};
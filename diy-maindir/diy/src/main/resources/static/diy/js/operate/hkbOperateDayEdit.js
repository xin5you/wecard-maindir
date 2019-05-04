$(document).ready(function() {
	dayEdit.init();
})

var dayEdit = {
	init : function() {
		dayEdit.initEvent();
	},

	initEvent:function(){

		$('#btn-save').on('click', dayEdit.daySave);
		$('#btn-back2').on('click', dayEdit.searchReset);
		$('#btn-back').on('click', dayEdit.back);

	},
	searchReset : function(){

		var shopStatisticsOprId = $(this).attr("ssid");
		var settleDate = $(this).attr("settledate");
		var totalPay = $(this).attr("totalpay");	
		var startDate =$(this).attr("startdate");
		var endDate =$(this).attr("enddate");

		location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+totalPay
		+'&startDate='+startDate+'&endDate='+endDate;
	},


	listSearch : function(){


		$("#searchForm").submit();
	},


	daySave:function(){
		var name = $(this).attr("username");
		var shopStatisticsOprId = $(this).attr("ssid");
		var settleDate = $(this).attr("settledate");
		var totalPay = $(this).attr("totalPay");	
		var startDate =$(this).attr("startdate");
		var endDate =$(this).attr("enddate");
		var array = new Array();
		$("input:checked").each(function(){  
			var row=$(this).parent("td").parent("tr");  
			var id=row.find("[name='id']").val();  
			var uPayAmt = row.find("[name='uPayAmt']").val(); 
			var posit = {'id':id,'payAmt':uPayAmt,'updateUser':name};
			array.push(posit);
		});  
		for(var i=0;i<array.length;i++){
			if (array[i].payAmt.match(/^\d+(\.\d+)?$/) == null ||array[i].payAmt == "") {
				Helper.alert('系统提示',"请输入正确金额!");
				return false;
			}else if(array[i].payAmt.length >12){
				Helper.alert('系统提示',"最大长度不能超过12!");
				return false;
			}
		}
/*		$.ajax({
			type: "POST",
			async: false,
			url: Helper.getRootPath() +"/operate/posit/savePositOprStatistics",
			data: "positData="+JSON.stringify(array),
			success: function(data){
				if(data.results=="1"){ 
					Helper.alert("保存成功!");
				}else if(data.results=="2"){
					Helper.alert("保存失败!修改次数超过三次，不能再修改!");
				}else if(data.results=="-1"){
					Helper.alert("保存失败!");
				}else{
					Helper.alert("系统繁忙，请稍后再试...");
				}
				location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+totalPay
				+'&startDate='+startDate+'&endDate='+endDate;
			}
		});*/
		$.post(Helper.getRootPath() +"/operate/posit/savePositOprStatistics",{"positData":JSON.stringify(array),"sid":shopStatisticsOprId,"user":name},function(data){
			if(data.results=="1"){ 
				alert("保存成功!");
			}else if(data.results=="2"){
				alert("保存失败!修改次数超过三次，不能再修改!");
			}else if(data.results=="-1"){
				alert("保存失败!");
			}else{
				alert("系统繁忙，请稍后再试...");
			}
			location = Helper.getRootPath() +'/operate/posit/monthEdit?shopStatisticsOprId='+shopStatisticsOprId+'&settleDate='+settleDate+'&totalPay='+totalPay
			+'&startDate='+startDate+'&endDate='+endDate;
		});
	},

	back : function(){
		var startDate = $(this).attr("startDate");
		var endDate = $(this).attr("endDate");
		location = Helper.getRootPath() +'/operate/posit/listShopStatisticsSet?startDate='+startDate+'&endDate='+endDate;
	},
}
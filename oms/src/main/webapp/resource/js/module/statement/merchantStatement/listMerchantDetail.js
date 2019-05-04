$(document).ready(function() {
	listMerchantDetail.init();
})

var listMerchantDetail = {
	init : function() {
		listMerchantDetail.initEvent();
		var operStatus=$("#operStatus").val();
		Helper.operTip(operStatus);
	},

	initEvent:function(){
		$('.date-time-picker').datetimepicker({
	        format: 'yyyy-MM-dd',
	        language: 'zh-CN',
	        pickDate: true,
	        pickTime: false,
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
		$('#mercahnt_select').change(listMerchantDetail.getShopInfList);
		$('.btn-search').on('click', listMerchantDetail.searchData);
		$('.btn-reset').on('click', listMerchantDetail.searchReset);
		$('.btn-upload').on('click',listMerchantDetail.uploadShopDetail);
	},
	searchData: function(){
		var mchntCode = $('#mercahnt_select').val();
		var shopCode = $('#shopCode').val();
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(mchntCode ==''){
			Helper.alert('请选择商户');
			return false;
		}
		if(shopCode == ''){
			Helper.alert('请选择门店');
			return false;
		}
		if(sd ==''){
			Helper.alert('请选择开始时间');
			return false;
		}
		if(ed ==''){
			Helper.alert('请选择结束时间');
			return false;
		}
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('开始时间不能大于结束时间');
				return false;
		}
		if(sd <= end){
			Helper.alert("当前查询时间间隔不能大于一个月！");
		 	return false;
		}
		document.forms['searchForm'].submit();
	},
	searchReset: function(){
		location = Helper.getRootPath() + '/statement/merchantStatement/listMerchantDetail.do';
	},
	uploadShopDetail:function(){
		var mchntCode = $('#mercahnt_select').val();
		var shopCode = $('#shopCode').val();
		var sd = $('#startTime').val();
		var ed = $('#endTime').val();
		var end = Helper.lastMonthDate(ed);
		if(mchntCode ==''){
			Helper.alert('请选择商户');
			return false;
		}
		if(shopCode == ''){
			Helper.alert('请选择门店');
			return false;
		}
		if(sd ==''){
			Helper.alert('请选择开始时间');
			return false;
		}
		if(ed ==''){
			Helper.alert('请选择结束时间');
			return false;
		}
		if(sd !='' && ed !='' && sd.localeCompare(ed)>0){
			Helper.alert('开始时间不能大于结束时间');
				return false;
		}
		if(sd <= end){
			Helper.alert("当前查询时间间隔不能大于一个月！");
		 	return false;
		}
		location = Helper.getRootPath() + '/statement/merchantStatement/uploadMerchantDetail.do?mchntCode='+mchntCode+'&shopCode='+shopCode+'&startTime='+sd+'&endTime='+ed;
	},
	getShopInfList:function(){
		var mchntCode=$('#mercahnt_select').val();
		$("#shopCode").empty();
		$('#shopCode').append('<option value="">--全部--</option>');
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/statement/merchantStatement/intoAddMchntEshopInfGetShop.do',
            data: {
            		'mchntCode' :mchntCode
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
                if(data.status) {
                	for(var i=0;i<data.shopInfList.length;i++){
	                	$('#shopCode').append('<option value="'+data.shopInfList[i].shopCode+'">'+data.shopInfList[i].shopName+'</option>');
                	}
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
	}
}

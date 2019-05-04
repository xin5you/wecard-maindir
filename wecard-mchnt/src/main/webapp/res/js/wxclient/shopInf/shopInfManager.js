$(document).ready(function(){


var router = new Router({
		container: '#container',
	    enterTimeout: 50,
	    leaveTimeout: 50
});

var editShopInf={
		initEvent: function(){
			 $('#editShopInfBtn').on('click', editShopInf.editShopInfCommit);
		},
		editShopInfCommit:function(){
		   	var $that= $('#editShopInfBtn');
		   	
         	var shopId=$.trim($("#shopId").val());
         	var flag=$.trim($("#flag").val());
         	
         	var shopName=$.trim($("#shopName").val());
         	var shopAddr=$.trim($("#shopAddr").val());
         	
         	if(shopName==''){
         		$.toptip("请输入门店名称");
         		return;
         	}
         	if(shopAddr==''){
         		$.toptip("请输入门店地址");
         		return;
         	}
         	$that.attr("disabled", true);
         	$.showLoading('提交中...');
          	$.ajax({
                 type: 'POST',
                 data: {
                 	"shopId":shopId,
                    "flag":flag,
                    "shopName":shopName,
                    "shopAddr":shopAddr
     	        },
     	        url:CONETXT_PATH+'/wxclient/mchnt/shopInfCommit.html',
                 
                 dataType: 'json',
                 success: function(data){
                	 $.hideLoading();
                	 $that.removeAttr("disabled");
                 	if(data.status){
                 		$('#container').html('');
	  	            	 router.go('/msg');
                 	}else{
                 		$('#weui_cells_tips').html(data.msg)
                 	}
                 },
     	        error:function (XMLHttpRequest, textStatus, errorThrown) {
     	        	 $that.removeAttr("disabled");
     	    	}
          	})
		}
	}

var msg = {
	url: '/msg',
    className: 'msg',
    render: function () {
        return $('#tpl_msg').html();
    }
};
router.push(msg);

editShopInf.initEvent();
})
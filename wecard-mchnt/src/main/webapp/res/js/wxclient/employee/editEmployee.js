$(document).ready(function(){

	var router = new Router({
		container: '#container',
	    enterTimeout: 0,
	    leaveTimeout: 0
		
});

var editEmployee={
		init: function(){
			editEmployee.initEvent();
		},
		initEvent: function(){
			 $('#editEmployee').on('click', editEmployee.editEmployeeCommit);
		},
		editEmployeeCommit:function(){
         	var name=$.trim($("#name_page").val());
         	var managerId=$.trim($("#managerId_page").val());
         	var flag=$.trim($("#flag_page").val());
         	
         	$that=$(this);
         	$that.attr("disabled", true);
         	$.showLoading('提交中...');
          	$.ajax({
                 type: 'POST',
                 data: {
                 	"managerId":managerId,
                    "name":name,
                    "flag":flag
     	         },
                 url:CONETXT_PATH+'/wxclient/mchnt/editEmployeeCommit.html',
                 dataType: 'json',
                 success: function(data){
                	 $that.removeAttr("disabled");
                	 $.hideLoading();
                 	if(data.status){
                 		$('#container').html('');
             			router.go('/msg');
                 	}else{
                 		$.toptip(data.msg)
                 	}
                 },
     	        error:function (XMLHttpRequest, textStatus, errorThrown) {
     	        	$.hideLoading();
     	        	 $that.removeAttr("disabled");
     	    	}
          	})
		}
}

var msg = {
	url: '/msg',
    className: 'msg',
    render: function () {
        return $('#tpl_success_msg').html();
    }
};
router.push(msg);
editEmployee.init();
})
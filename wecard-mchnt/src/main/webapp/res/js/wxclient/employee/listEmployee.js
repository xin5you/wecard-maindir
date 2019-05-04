$(document).ready(function(){
	listEmployee.init();
})

var listEmployee ={
		init: function(){
			
		},
		showDialogconfirm:function(managerId){
            $.confirm("确定解除该员工权限吗?", "操作确认", function() {
            	listEmployee.editEmployeeCommit(managerId);
              }, function() {
                //取消操作
              });
		},
		editEmployeeCommit:function(managerId){
         	$that=$(this);
         	$that.attr("disabled", true);
          	$.ajax({
                 type: 'POST',
                 data: {
                 	"managerId":managerId,
                    "flag":'2'
     	        },
                 url:CONETXT_PATH+'/wxclient/mchnt/editEmployeeCommit.html',
                 dataType: 'json',
                 success: function(data){
                	 $that.removeAttr("disabled");
                 	if(data.status){
                 		$.toptip("员工解除权限操作成功");
                        $.noti({
                            title: "操作成功",
                            text: "解除员工权限操作成功",
                            time: 2000
                          });
                 		$("#row"+managerId).remove();
                 		return;
                 	}else{
                 		$.toptip(data.msg);
                 	}
                 },
     	        error:function (XMLHttpRequest, textStatus, errorThrown) {
     	        	 $that.removeAttr("disabled");
     	    	}
          	})
		}
}
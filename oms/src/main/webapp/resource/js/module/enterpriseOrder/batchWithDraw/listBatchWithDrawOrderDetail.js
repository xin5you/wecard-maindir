$(document).ready(function() {
    listBatchOrderDetail.init();
})

var listBatchOrderDetail = {
	init : function() {
        listBatchOrderDetail.initEvent();
	},

	initEvent:function(){
        $('.btn-synchron').on('click', listBatchOrderDetail.synchronCommit);
	},
    synchronCommit:function(){
        var orderId =$("#orderId").val();
        if(!orderId){
            Helper.alert("信息获取失败，请重新刷新页面");
            return false;
        }
        Helper.confirm("您是否同步该订单所有记录？",function(){
            Helper.wait("系统执行中,请执行返回前不要关闭页面...");
            $.ajax({
                url: Helper.getRootPath() + '/batchWithdrawOrderDetail/synchronCommit.do',
                type: 'post',
                dataType : "json",
                data: {
                    "orderId": orderId
                },
                success: function (result) {
                    Helper.closeWait();
                    if(result.status){
                        location.href=Helper.getRootPath() + '/batchWithdrawOrderDetail/listBatchWithDrawOrderDetail.do?orderId='+orderId;
                    }else{
                        Helper.alert(result.msg);
                        return false;
                    }
                },
                error:function(){
                    Helper.closeWait();
                    Helper.alert("系统故障，请稍后再试");
                }
            });
        });
    }
}

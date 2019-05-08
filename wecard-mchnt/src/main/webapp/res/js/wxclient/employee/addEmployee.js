$(document).ready(function(){


var router = new Router({
		container: '#container',
	    enterTimeout: 0,
	    leaveTimeout: 0
		
});


var addEmployee={
	shareId:'',
	flag:'9',
	initEvent: function(){
		addEmployee.initJsTicket();
	},
	initJsTicket:function(){
    	$.ajax({
    		url : CONETXT_PATH + '/wxapi/jsTicket.html' ,
    		type : 'post',
	        dataType : 'json',
    	    data:{url : location.href.split('#')[0]},
    	    success:function(data){
    	    	var ajaxobj=data;
    	    	var errcode = ajaxobj.errcode;
    	    	var sign = ajaxobj.data;
    	    	if(errcode == 0){//没有错误
    	    		wx.config({
    	    		    debug: false,//true的时候可以alert信息 
    	    		    appId: sign.appId,
    	    		    timestamp: sign.timestamp,
    	    		    nonceStr: sign.nonceStr, 
    	    		    signature: sign.signature,
    	    		    jsApiList: ['onMenuShareAppMessage'] //使用接口时，这里必须先声明 
    	    		});
    	    	}
    	    }
    	});
	},
	shareManager:function(shareId,flag){
    	$.ajax({
    		url : CONETXT_PATH + '/wxclient/mchnt/shareInfManager.html' ,
     		type : 'post',
  	        dataType : 'json',
      	    data:{
                  'flag':flag,
                  'shareId':shareId
      	    },
        	success:function(data){
        		$('.container').html('');
        		router.go('/s_msg');
             },
             error:function(){
            	 $('.container').html('');
            	 router.go('/e_msg');
             }
    	});
	}
};


var s_msg = {
	url: '/s_msg',
    className: 'msg',
    render: function () {
        return $('#tpl_success_msg').html();
    }
};
    
var e_msg = {
    url: '/e_msg',
    className: 'msg',
    render: function () {
        return $('#tpl_error_msg').html();
    }
};



wx.ready(function(){
	  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
	  document.querySelector('#addOnMenuShareAppMessage').onclick = function () {
		  	var $that=$('#addOnMenuShareAppMessage');
		  	var roleType=$.trim( $('#roleType option:checked').val());
	      	var name=$.trim($('#name').val());
	     	var shopId=$.trim($('#shopId option:checked').val());
	     	
	     	var roleName=$.trim($('#roleType option:checked').text());
	     	$that.attr('disabled', true);
	    	$.ajax({
      		url : CONETXT_PATH + '/wxclient/mchnt/shareInfManager.html',
      		type : 'post',
  	        dataType : 'json',
      	    data:{
      	    	  'shareId':addEmployee.shareId,
      	    	  'roleType':roleType,
                  'name':name,
                  'flag':addEmployee.flag,
                  'shopId':shopId
      	    },
        	success:function(data){
               	if(data.status){
               		$that.hide();
               		addEmployee.shareId=data.code;
            	    wx.onMenuShareAppMessage({
		          	      title: '薪无忧商户平台',
		          	      desc:  '您好: 我在薪无忧公众号中分配了'+roleName+' 角色,请关注公众号后，点击认证',
		          	      link:  data.msg+'/wxclient/mchnt/employeeRegByMchant.html?shareId='+data.code,
		          	      imgUrl: data.msg +'/res/image/sharelog.jpg',
		          	      trigger: function (res) {
		          	        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		          	        
		          	      },
		          	      success: function (res) {
		          	    	  addEmployee.shareId='';
		          	    	  addEmployee.flag='9'
		          	    	  addEmployee.shareManager(data.code,'0');//分享成功
		          	    	  $that.show();
		          	      },
		          	      cancel: function (res) {
		          	    	 $that.removeAttr('disabled');
		          	    	 $that.show();
		          	    	//addEmployee.shareManager(data.code,'1');//取消分享
		          	      },
		          	      fail: function (res) {
		          	    	 $that.removeAttr('disabled');
		          	    	$that.show();
		          	    	  //addEmployee.shareManager(data.code,'8');//分享失败
		          	      }
	          	    });
               	}else{
               		$('.container').html('');
               		router.go('/e_msg');
               	}
             },
             error:function(){
            	 
             }
      	}); 
	  };
});

router.push(s_msg)
	  .push(e_msg);

addEmployee.initEvent();

})
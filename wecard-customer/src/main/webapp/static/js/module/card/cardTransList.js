$(function () {
	var cardTransList = {
		pageNum:1,//当前页
		itemSize:10,//页大小
		isLoad:false,
        ajaxCardTransList:function(msg){
        	
        	//加载中返回
        	if(cardTransList.isLoad){
        		return ;
        	}
        	jfShowTips.loadingShow({
        		'text' : '加载中',
        		'thisNode':loadInnerHtml.node.loading,//loading动画
        		'thisClass':'small'//loading动画为'small'
        	});
        	cardTransList.isLoad=true; //加载中...
        	
        	var innerMerchantNo=$('#innerMerchantNo').val();
        	if(msg!=""){
        		$(".bill_check_none").html("<span>"+msg+"</span>");
        	}
    		$.ajax({
        		url :  CONETXT_PATH+'/customer/card/cardTransListAjax.html' ,
        		type : 'post',
    	        dataType : "json",
        	    data:{
        	    	'innerMerchantNo':innerMerchantNo,//商户号
        			'pageNum':cardTransList.pageNum,//当前页
        			'itemSize':cardTransList.itemSize//页大小
        	    },
        	    success:function(data){
        	    	jfShowTips.loadingRemove();
        	    	if(data==null  || data.transList==null || data.transList=='null' || data.transList==''){
        	    		$(".bill_check_none").html("<span>&#x6CA1;&#x6709;&#x66F4;&#x591A;&#x6570;&#x636E;&#x4E86;</span>");
        	    		return ;
        	    	}
        	    	var pageSize =data.pageSize;
        	    	var img = ''; //图片路径
        	    	var bool = '';//判断属于充值或者消费。false为充值，true为消费
        	    	var jsonList=new Array();
        	    	var jsonBean;
        	    	if(data.code=='00'){
        	    		var txnAmount='';
        	    		var mchntShopName='';
	        	    	for(var i in data.transList){
	        	    		if(data.transList[i].transId=='W10' || data.transList[i].transId=='W71' ){
	        	    			txnAmount='-'+data.transList[i].txnAmount;
	        	    			mchntShopName=data.transList[i].shopName;
	        	    		}else{
	        	    			txnAmount='+'+data.transList[i].txnAmount;
	        	    			mchntShopName='';
	        	    		}
	        	    		if(data.transList[i].transIdDesc=='消费' || data.transList[i].transIdDesc=='快捷消费'){
	        	    			img = CONETXT_PATH+"/static/images/consumption.png";
	        	    			bool = true;
	        	    		}
	        	    		if(data.transList[i].transIdDesc=='充值'){
	        	    			img = CONETXT_PATH+"/static/images/recharge.png";
	        	    			bool = false;
	        	    		}
	        	    		if(data.transList[i].transIdDesc=='退款' || data.transList[i].transIdDesc=='退款（快捷）'){
	        	    			img = CONETXT_PATH+"/static/images/return.png";
	        	    			bool = false;
	        	    		}
	        	    		if(data.transList[i].transIdDesc=='退货' || data.transList[i].transIdDesc=='退货（快捷）'){
	        	    			img = CONETXT_PATH+"/static/images/refund.png";
	        	    			bool = false;
	        	    		}
	        	    		if(data.transList[i].transIdDesc=='返利'){
	        	    			img = CONETXT_PATH+"/static/images/rebate.png";
	        	    			bool = false;
	        	    		}
	        	    		jsonBean={
	        	                        "src": img,
	        	                        "billtext": mchntShopName+"  "+data.transList[i].transIdDesc,
	        	                        "time":data.transList[i].dateStr+"  "+data.transList[i].timeStr,//消费时间
	        	                        'pay':bool,
	        	                        "money": txnAmount,//消费金额或者充值金额
	        	                        'code':data.transList[i].txnFlowNo,
	        	                        "banlace": data.transList[i].balance//余额
	        	    		};
	        	    		jsonList.push(jsonBean);
	        	    	}
	                	jfLazyLoading.ajaxContentInit({//触发的脚本(动态添加元素)
	                		listdata:jsonList
	                	});
	                	cardTransList.isLoad=false; //加载完毕
	        	    	cardTransList.pageNum++;
        	    	}
        	    },
        	   error: function(xhr, type){
        		   jfShowTips.loadingRemove();
        			cardTransList.isLoad=false;
                }
    		});
        },
        init:function(){
        	cardTransList.ajaxCardTransList("");
        	//异步加载刷新
            jfLazyLoading.ajaxLoadInit({
            	"bottomDistance":"50",
            	 fn:function(){
            		 cardTransList.ajaxCardTransList("正在加载中...");
            	 }
            });
            
          //下拉刷新
            moveRefresh.init({
                fn:function(){
                	jfShowTips.loadingRemove();
                	cardTransList.pageNum=1;
                	cardTransList.isLoad=false;
                	$('.hkb_bill_check').html('');
                	$(".bill_check_none").html("");
                	cardTransList.ajaxCardTransList("");
                }
            })
        }
    };
	
	cardTransList.init();
});
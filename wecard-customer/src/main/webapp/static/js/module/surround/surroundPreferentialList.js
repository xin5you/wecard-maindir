$(function () {
	var preferential = {
		latitude:0,//纬度，浮点数，范围为90 ~ -90
		longitude:0,//经度，浮点数，范围为180 ~ -180。
		distance:'5000000',  
		industryType:'',  //行业类型
		sort:'00',	//排序类型
		pageNum:1,//当前页
		itemSize:10,//页大小
		isLoad:false,
        initLocation: function () {
            $.ajax({
        		url : CONETXT_PATH + '/wxapi/jsTicket.html' ,
        		type : 'post',
    	        dataType : "json",
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
        	    		    jsApiList: ['hideOptionMenu','getLocation'] //使用接口时，这里必须先声明 
        	    		});
        	    		wx.ready(function(){
                    		wx.hideOptionMenu();// 隐藏右上角菜单接口
                    		jfShowTips.loadingShow({
                        		'text' : '正在获取定位',
                        		'thisNode':loadInnerHtml.node.loading,//loading动画
                        		'thisClass':'small'//loading动画为'small'
                        	});
                    		 wx.getLocation({
                        		    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                        		    success: function(res){
                        		        preferential.latitude = res.latitude;//纬度，浮点数，范围为90 ~ -90
                        		        preferential.longitude = res.longitude;//经度，浮点数，范围为180 ~ -180。
                        		        var speed = res.speed;// 速度，以米/每秒计
                        		        var accuracy = res.accuracy;// 位置精度
                        				preferential.ajaxSurroundShopList('');
                        		    }
                        		});
                    	});
        	    	}
        	    }
        	});
        },
        ajaxSurroundShopList:function(flag){
        	//加载中返回
        	if(preferential.isLoad){
        		return ;
        	}
        	/**是否加载到当前用户的坐标**/
        	if(preferential.latitude==0 || preferential.latitude=='' || preferential.longitude==0 ||  preferential.longitude==''){
        		jfShowTips.loadingShow({
            		'text' : '无法获取您的位置信息，请在手机系统的[设置]>[隐私]>[定位服务]，并允许微信使用定位服务。',
            		'thisNode':loadInnerHtml.node.loadingAttention,//loading动画
            		'thisClass':'attention'//loading动画为'small'
            	});
        		return false;
        	}
        	jfShowTips.loadingShow({
        		'text' : '加载中',
        		'thisNode':loadInnerHtml.node.loading,//loading动画
        		'thisClass':'small'//loading动画为'small'
        	});
        	preferential.isLoad=true; //加载中...
    		$.ajax({
        		url : CONETXT_PATH + '/customer/surround/surroundPreferentialList.html' ,
        		type : 'post',
    	        dataType : "json",
        	    data:{
        			'latitude':preferential.latitude,//纬度，浮点数，范围为90 ~ -90
        			'longitude':preferential.longitude,//经度，浮点数，范围为180 ~ -180。
        			'distance':preferential.distance,  
        			'industryType':preferential.industryType,//行业类型
        			'sort':preferential.sort,	//排序类型
        			'pageNum':preferential.pageNum,//当前页
        			'itemSize':preferential.itemSize//页大小
        	    },
        	    success:function(data){
        	    	jfShowTips.loadingRemove();
        	    	if(data==null  || data.shopList==null || data.shopList=='null'){
        	    		$(".nearby_end").html("<span>&#x6CA1;&#x6709;&#x66F4;&#x591A;&#x6570;&#x636E;&#x4E86;</span>");
        	    		return ;
        	    	}
        	    	var arrLen =  data.shopList.length;
        	    	if('initLoad'==flag){
        	    		$('.nearby_list_content').html(''); //重新加载页面时候，clear页面数据
        	    	}
        	    	var resultStr='';
        	    	var starsStr='';
        	    	var jsonList=new Array();
        	    	var jsonBean;
        	    	for(var i in data.shopList){
        	    		starsStr='';
        	    		for(var j in data.shopList[i].stars){
        	    			if(data.shopList[i].stars[j]=='1'){
        	    				starsStr+= '<span class="star_on"></span>';
        	    			}else if(data.shopList[i].stars[j]=='0'){
        	    				starsStr+= '<span class="star_off"></span>';
        	    			}else{
        	    				starsStr+='<span class="star_half"></span>';
        	    			}
        	    		}
        	    		var activeExplain;
        	    		if(data.shopList[i].activeExplain !=null && data.shopList[i].activeExplain !='null'){
        	    			jsonBean={
            	    				'data_href':'/customer/surround/viewMchntShopInf.html?innerMerchantNo='+data.shopList[i].mchntCode+'&innerShopNo='+data.shopList[i].shopCode+'&detailFlag=1',
                                    'shopname':data.shopList[i].mchntName+'('+data.shopList[i].shopName+')',	//商户门店名称
                                    'picture':data.shopList[i].brandLogo,			//商店Logo
                                    'category':data.shopList[i].industryType,	//类型
                                    'times':data.shopList[i].soldCount,			//已购次数
                                    'star':'<div class="star">'+starsStr+'</div>',
                                    'distance':data.shopList[i].distance,		//距离
                                    'discount':true,				//有活动介绍时为：true
                                    'discount_text':data.shopList[i].activeExplain	//优惠说明内容
            	    		}
    					}else{
    						jsonBean={
            	    				'data_href':'/customer/surround/viewMchntShopInf.html?innerMerchantNo='+data.shopList[i].mchntCode+'&innerShopNo='+data.shopList[i].shopCode+'&detailFlag=1',
                                    'shopname':data.shopList[i].mchntName+'('+data.shopList[i].shopName+')',	//商户门店名称
                                    'picture':data.shopList[i].brandLogo,			//商店Logo
                                    'category':data.shopList[i].industryType,	//类型
                                    'times':data.shopList[i].soldCount,			//已购次数
                                    'star':'<div class="star">'+starsStr+'</div>',
                                    'distance':data.shopList[i].distance,		//距离
                                    'discount':false,			//没有活动介绍时为：false
            	    		}
        	    		}
        	    		jsonList.push(jsonBean);
        	    	}
        	    	hkbLazyLoading.ajaxContentInit({//触发的脚本(动态添加元素)
        	               listdata:jsonList
        	        });
        	    	 preferential.isLoad=false; //加载完毕
        	    	 $(".nearby_end").html("<span>&#x6CA1;&#x6709;&#x66F4;&#x591A;&#x6570;&#x636E;&#x4E86;</span>")
        	    	 preferential.pageNum++;
        	    },
        	   error: function(xhr, type){
        		   preferential.isLoad=false;
        		   jfShowTips.loadingRemove();
                }
    		});
        },
        bestSalesFun:function(){ //销量最好
        	preferential.sort='10';
        	preferential.pageNum='1';
        	preferential.industryType='';
        	preferential.ajaxSurroundShopList('initLoad');
        },
        bestEvaluationFun:function(){//评价最好
        	preferential.sort='20';
        	preferential.pageNum='1';
        	preferential.industryType='';
        	preferential.ajaxSurroundShopList('initLoad');
        },
        industryTypeSortFun:function(){
        	var industryType=$(this).attr("industryType");
        	preferential.sort='';
        	preferential.pageNum='1';
        	preferential.industryType=industryType;
        	preferential.ajaxSurroundShopList('initLoad');
        },
        initEvent:function(){
        	preferential.initLocation();
        	$('#bestSales').on('click', preferential.bestSalesFun);
        	$('#bestEvaluate').on('click', preferential.bestEvaluationFun);
        	$('.sort_list > DIV').on('click', preferential.industryTypeSortFun);
        	
        	hkbLazyLoading.ajaxLoadInit({
                "bottomDistance":"50",//距离底部的距离，触发事件，单位省略
                fn:function(){//触发的脚本
                	preferential.ajaxSurroundShopList('');
            	 }
            });
        	//下拉刷新
            moveRefresh.init({
                moveEleClass:'nearby_list',//刷新的盒子
                moveDis:'20',//刷新移动距离
                fn:function(){
                	window.location.reload(true);//刷新
                	jfShowTips.loadingRemove();
                	preferential.pageNum=1;
                	preferential.isLoad=false;
                	preferential.ajaxSurroundShopList('');
                }
            });
        	
        },
    };
	preferential.initEvent();
    
});
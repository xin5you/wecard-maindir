addEventListener('load',function(){
//	navUserDrop();
	function navUserDrop() {
		var clickEle = document.getElementsByClassName('hkb_user')[0];
		var thisEle = document.getElementById('userList');
		var hrefBtn = document.getElementsByClassName('user_operation')[0].getElementsByTagName('a')
		clickEle.addEventListener('click',function () {
			if(thisEle.className.indexOf('show') > -1){
				transitionMove(thisEle)
				thisEle.className = thisEle.className.replace(' show','');
        }else {

			thisEle.style.display = 'block';
				setTimeout(function () {
					thisEle.className += ' show';
				},1)
			}
			function transitionMove(ele) {
				// Safari 3.1 到 6.0 代码
				ele.addEventListener("webkitTransitionEnd", MFunction);
				// 标准语法
				ele.addEventListener("transitionend", MFunction);
				function MFunction() {
					ele.style.display = 'none';
					// Safari 3.1 到 6.0 代码
					ele.removeEventListener("webkitTransitionEnd", MFunction);
					// 标准语法
					ele.removeEventListener("transitionend", MFunction);
				}
			}
			addevent();
			function addevent() {
				for (var i=0;i<hrefBtn.length;i++){
					hrefBtn[i].addEventListener('click',function () {
						event.preventDefault();
						thisEle.className = thisEle.className.replace(' show', '')

					},false)
				}
			}
		},false)
	}
	basicPlatform.tabChange();
	/*

  
    iframeHeight();
    function iframeHeight() {
        var ele = document.getElementById('hkbFrameContent')
        var winHeight = document.body.clientHeight;
        ele.style.minHeight = winHeight - 111 +'px'
    }
	 */
})

$(document).ready(function() {
	listNavbar.init();
})

var listNavbar = {
	init : function() {
		listNavbar.initEvent();
	},

	initEvent:function(){
		$('#userLogOut').on('click', listNavbar.userLogOut);
		$('#member').on('click', listNavbar.listMember);
		$('#mchnt').on('click', listNavbar.listMchnt);
		$('#shopStatics').on('click', listNavbar.listShopStatisticsSet);
		$('#positOprStatistics').on('click', listNavbar.listPositOprStatistics);
		$('#shop').on('click',listNavbar.listShop);
		$("#transCount").on('click',listNavbar.listTransCount);
		$("#transQuery").on('click',listNavbar.listTransQuery);
		$("#invoiceOrder").on('click',listNavbar.listInvoiceOrder);
		$("#invoOrdStatistics").on('click',listNavbar.listInvoOrdStatistics);
		$("#listOrder").on('click',listNavbar.listOrder);
		$("#channel").on('click',listNavbar.listChannel);
	},
	userLogOut : function() {
		Helper.post('/logout');
	},
	listMember : function(){
		location = Helper.getRootPath() + '/system/user/listUser';
//		Helper.post('/system/user/listUser');
	},
	listMchnt:function(){
		location = Helper.getRootPath() + '/original/mchnt/getMchntDataList';
//		Helper.post('/original/mchnt/getMchntDataList');
	},

	listShopStatisticsSet:function(){
		location = Helper.getRootPath() +'/operate/posit/listShopStatisticsSet';
	},

	listShop:function(){
		location = Helper.getRootPath() + '/original/shop/getShopDataList';
//		Helper.post('/original/shop/getShopDataList');
	},
	

	listPositOprStatistics:function(){
//		location = Helper.getRootPath() + '/original/mchnt/getMchntDataList';
		location = Helper.getRootPath() +'/operate/posit/listOprStatistics';
	},
	listTransCount:function(){
		location = Helper.getRootPath() +'/trans/mchnt/getTransCount';
	},
	listTransQuery:function(){
		location = Helper.getRootPath() +'/trans/mchnt/getTransQuery';
	},
	listInvoiceOrder:function(){
		location = Helper.getRootPath() +'/invoice/invoiceOrder/getTransQuery';
	},
	listInvoOrdStatistics:function(){
		location = Helper.getRootPath() +'/invoice/invoOrdStatistics/getInvoOrdStatistics';
	},
	listOrder:function(){
		location = Helper.getRootPath() +'/channel/order/listOrder';
	},
	listChannel:function(){
		location = Helper.getRootPath() +'/channel/listChannel';
	}
}
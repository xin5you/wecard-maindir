addEventListener('load',function(){
		navUserDrop();
		function navUserDrop() {
    	var clickEle = document.getElementsByClassName('hkb_user')[0];
    	var thisEle = document.getElementById('userList');
    	var hrefBtn = document.getElementsByClassName('user_operation')[0].getElementsByTagName('a')
    	clickEle.addEventListener('click',function () {
        	if(thisEle.className.indexOf('show') > -1){
            	transitionMove(thisEle)
                thisEle.className = thisEle.className.replace(' show','');
        	}
        	else {
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
	            for (var i = 0; i < hrefBtn.length; i++){
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
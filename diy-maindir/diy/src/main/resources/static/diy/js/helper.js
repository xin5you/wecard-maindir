
var Helper = {
		ver: 0.1,
		waitModal: null,
		alert: function (title,msg) {
			basicHkb.dialogShow({
				dialogTitle: title,
				dialogContent: msg,
				noCheck:true,
				cancleBtnText:'关闭'
			});
		},
		confirm: function (msg, callback) {
			$.teninedialog({
				title: "系统提示",
				showCloseButton: false,
				otherButtons: ["确定", "取消"],
				otherButtonStyles: ['btn-primary', 'btn'],
				content: msg,
				bootstrapModalOption: {
					backdrop: "static"
				},
				clickButton: function (sender, modal, index) {
					if (index == 0) {
						callback();
					}
					$(this).closeDialog(modal);
				}
			});
		},
		confirm_one: function (msg, callback) {
			$.teninedialog({
				title: "系统提示",
				showCloseButton: false,
				otherButtons: ["确定"],
				otherButtonStyles: ['btn-primary'],
				content: msg,
				bootstrapModalOption: {
					backdrop: "static"
				},
				clickButton: function (sender, modal, index) {
					if (index == 0) {
						callback();
					}
					$(this).closeDialog(modal);
				},
				dialogHidden: function () {
					callback();
				}
			});
		},
		wait: function (msg) {
			msg = msg || '请稍候...';
			var modalID = 'eoms_wait_modal_id';
			var tmpHtml = '<div id="{ID}" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"><div class="modal-header"><h3 id="myModalLabel">{title}</h3></div><div class="modal-body"><p>{body}</p></div></div>';
			// 替换模板标记
			tmpHtml = tmpHtml.replace(/{ID}/g, modalID).replace(/{title}/g, '系统提示').replace(/{body}/g, msg);
			$("body").append(tmpHtml);
			var modalObj = $('#' + modalID);

			modalObj.on('hidden', function () {
				modalObj.remove();
			});

			modalObj.modal({
				backdrop: "static"
			});

			this.waitModal = modalObj;
		},
		closeWait: function () {
			if (this.waitModal) {
				this.waitModal.modal('hide');
				this.waitModal = null;
			}
		},
		checkBlank: function (id, info) {
			var obj = $('#' + id);
			var val = $.trim(obj.val());
			info = info || '不能为空！';
			if (val == '' || val == undefined) {
				Helper.alert(info);
				obj.focus();
				return false;
			}
			return true;
		},
		getLength: function (str) {
			return str.replace(/[^\x00-\xff]/g, '__').length;
		},
		checkLength: function (id, len, info) {
			var obj = $('#' + id);
			var val = $.trim(obj.val());
			var length = Helper.getLength(val);
			if (length > len) {
				Helper.alert(info);
				obj.focus();
				return false;
			}
			return true;
		},
		//正整数，除了零
		checkPositiveInt: function (id, info) {
			var obj = $('#' + id);
			var val = $.trim(obj.val());
			var reg = /^([1-9][0-9]*)$/;
			info = info || '格式不为正整数！';
			if (!reg.test(val)) {
				Helper.alert(info);
				obj.focus();
				return false;
			}
			return true;
		},
		//正整数
		checkInt: function (id, info) {
			var obj = $('#' + id);
			var val = $.trim(obj.val());
			var reg = /^\d+$/;
			info = info || '格式不为正整数！';
			if (!reg.test(val)) {
				Helper.alert(info);
				obj.focus();
				return false;
			}
			return true;
		},
		checkFloat: function (id, info, num) {
			var obj = $('#' + id);
			var val = $.trim(obj.val());
			var num = num || 2;
			var re = "^[0-9]+([.][0-9]{" + num + "})?$";
			var reg = new RegExp(re, "g");
			info = info || '不为实数！';
			if (!reg.test(val)) {
				Helper.alert(info);
				obj.focus();
				return false;
			}
			return true;
		},
		checkFloatNum: function (num, info, digit) {
			var digit = digit || 2;
			var re = "^[0-9]+([.][0-9]{" + digit + "})?$";
			var reg = new RegExp(re, "g");
			info = info || '不为实数！';
			if (!reg.test(num)) {
				Helper.alert(info);
				return false;
			}
			return true;
		},
		onlyInputInt: function () {
			$(this).val($(this).val().replace(/\D/g, ''));
		},
		getRootPath : function(){
			var location = (window.location+'').split('/');
//			return location[0] + '//' + location[2];
			return location[0]+'//'+location[2]+'/'+location[3];
		},	
		post: function(short_url) {
			var full_url = Helper.getRootPath() + short_url;
			var form = $("<form></form>");
			form.attr('action', full_url);
			form.attr('method', 'post');
			form.attr('target', '_self');
			form.appendTo("body");
			form.css('display', 'none');
			form.submit();
		},
		isRepeat: function (arr) {
			var hash = {};
			for (var i in arr) {
				if (hash[arr[i]]) {
					return arr[i];
				}
				hash[arr[i]] = arr[i];
			}
			return null;
		},
		isPlaceholderSupported: function () {
			var isOperaMini = Object.prototype.toString.call(window.operamini) == '[object OperaMini]';
			var isInputSupported = 'placeholder' in document.createElement('input') && !isOperaMini;
			return isInputSupported;
		},
		md6: function (pw, code) {
			var codes = (code + '').split('');
			var len = pw.length - 1;
			var ppw = [], startChar, endChar, startIndex, endIndex;
			for (var t = 0; t < pw.length; t++) {
				ppw.push(pw.charAt(t));
			}
			$.each(codes, function (index, arr) {
				startIndex = parseInt(arr, 16);
				endIndex = len - startIndex;
				startChar = pw.slice(startIndex, startIndex + 1);
				endChar = pw.slice(endIndex, endIndex + 1);
				ppw.splice(startIndex, 1, endChar);
				ppw.splice(endIndex, 1, startChar);
				//alert(startIndex + ' ' + endIndex + ' ' + startChar + ' ' + endChar + ' ' );
			});

			return ppw.join('');
		},
		genAuthCode: function (codeType) {
			var authImg = Helper.getRootPath() + "/authcode/genAuthCode/" + codeType + "/" + Math.random();
			return authImg;
		},
		getUserEncryCode: function (id) {
			var idOrObj = id || 'userEncryCode';
			$.ajax({
				url: Helper.getRootPath() + '/user/getEncryCode',
				type: 'post',
				dataType: 'json',
				data: {},
				success: function (resp) {
					$("#" + id).val(resp.encryCode);
				},
				error: function (req, status, error) {
					Helper.alert('得到用户加密算法失败');
				}
			});
		},
		getUrlParam: function(name)
		{
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if(r!=null)
				return unescape(r[2]); 
			return null;
		},
		toPercent: function(val){
			return Math.round(val * 10000)/100 + '%';
		},
		toRound: function(val, num){
			var n = num || 6;
			var vv = Math.pow(10,n);  
			return Math.round(val * vv)/vv;  
		},
		toMoney: function(val, num){
			var round  = Helper.toRound(val, num);
			var number = String(round);
			var numbers = number.split('.');
			var intNum = numbers[0];
			var floatNum = numbers.length>1?"."+numbers[1]:"";
			var re = /(-?\d+)(\d{3})/;
			while(re.test(intNum)) intNum = intNum.replace(re,"$1,$2");
			var fullNum = intNum + floatNum;
			return fullNum;  
		},
		//获取当前日期的上一个月日期 yyyy-MM-dd  
		//param yyyy-MM-dd  
		//return yyyy-MM-dd
		lastMonthDate:function(date){
			var Nowdate = new Date(date);
			var vYear = Nowdate.getFullYear();
			var vMon = Nowdate.getMonth() + 1;
			var vDay = Nowdate.getDate();//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
			var daysInMonth = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
			if(vMon==1){
				vYear = Nowdate.getFullYear()-1;
				vMon = 12;
			}else{
				vMon = vMon -1;
			}//若是闰年，二月最后一天是29号
			if(vYear%4 == 0 && vYear%100 != 0  || vYear%400 == 0 ){
				daysInMonth[2]= 29;
			}
			if(daysInMonth[vMon] < vDay){
				vDay = daysInMonth[vMon];
			}
			if(vDay<10){
				vDay="0"+vDay;
			}
			if(vMon<10){
				vMon="0"+vMon;
			}
			var date =vYear+"-"+ vMon +"-"+vDay;
			return date;
		},
		//获取当前日期的上三个月日期 yyyy-MM-dd  
		//param yyyy-MM-dd  
		//return yyyy-MM-dd
		lastThirdyMonthDate:function(date){
			var Nowdate = new Date(date);
			var vYear = Nowdate.getFullYear();
			var vMon = Nowdate.getMonth() + 1;
			var vDay = Nowdate.getDate();//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
			var daysInMonth = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
			if(vMon<=3){
				vYear = Nowdate.getFullYear()-1;
				vMon = 12+vMon-3;
			}else{
				vMon = vMon -3;
			}//若是闰年，二月最后一天是29号
			if(vYear%4 == 0 && vYear%100 != 0  || vYear%400 == 0 ){
				daysInMonth[2]= 29;
			}
			if(daysInMonth[vMon] < vDay){
				vDay = daysInMonth[vMon];
			}
			if(vDay<10){
				vDay="0"+vDay;
			}
			if(vMon<10){
				vMon="0"+vMon;
			}
			var date =vYear+"-"+ vMon +"-"+vDay;
			return date;
		},


		//获取当前日期的去年日期 yyyy-MM-dd  
		//param yyyy-MM-dd  
		//return yyyy-MM-dd
		lastYearDate:function(date){
			var Nowdate = new Date(date);
			var vYear = Nowdate.getFullYear();
			var vMon = Nowdate.getMonth() + 1;
			var vDay = Nowdate.getDate();//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
			var daysInMonth = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
			vYear = Nowdate.getFullYear()-1;
			
			if(vYear%4 == 0 && vYear%100 != 0  || vYear%400 == 0 ){
				daysInMonth[2]= 29;
			}
			if(daysInMonth[vMon] < vDay){
				vDay = daysInMonth[vMon];
			}
			
			if(vDay<10){
				vDay="0"+vDay;
			}
			if(vMon<10){
				vMon="0"+vMon;
			}
			var date =vYear+"-"+ vMon +"-"+vDay;

			return date;
		},
		nowDate:function(){
			var now = new Date();
			var year = now.getFullYear();
		    var month =(now.getMonth() + 1).toString();
		    var day = (now.getDate()).toString();
		    if (month.length == 1) {
		        month = "0" + month;
		    }
		    if (day.length == 1) {
		        day = "0" + day;
		    }
		    var nowDate = year+"-"+ month +"-"+ day;
		    return nowDate;
		}
};
$.ajaxSetup( {  
    //设置ajax请求结束后的执行动作  
    complete : function(XMLHttpRequest, textStatus) {  
        // 通过XMLHttpRequest取得响应头，sessionstatus  
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");  
        if (sessionstatus == "TIMEOUT") {  
            var win = window;  
            while (win != win.top){  
                win = win.top;  
            }  
                win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");  
        }  
    }  
});  

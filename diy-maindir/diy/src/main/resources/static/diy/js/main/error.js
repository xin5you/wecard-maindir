addEventListener('load',function(){})

$(document).ready(function() {
	error.init();
})

var error = {
	init : function() {
		error.initEvent();
	},

	initEvent:function(){
		$('#reset').on('click', error.resetMain);
	},
	resetMain : function(){
		location = Helper.getRootPath() + '/main';
	}
}
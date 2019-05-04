var Page = {
	goTo : function(i) {
		var form = Page.getPageForm();
		if (form) {
			if (i < 1) {
				i = 1;
			}
			Page.setCurrPage(i);
			$("#hid_pageTurning").val("true");
			form.submit();
		}
	},
	getPageForm : function() {
		return $(".page-div").parents("form")[0];
		//return document.forms[0];
	},
	getCurrPage : function() {
		return parseInt($("#hid_pageNum").val());
	},
	setCurrPage : function(num) {
		$("#hid_pageNum").val(num);
	},
	getLastPage : function() {
		return $("#hid_totalPages").val();
	},
	hasFirst : function() {
		return Page.getCurrPage() > 1;
	},
	hasLast : function() {
		return Page.getCurrPage() < Page.getLastPage();
	}
};

var changePageSize = function(e) {
	$("#hid_pageSize").val($(e).val());
	Page.goTo(Page.getCurrPage());
};
var first = function() {
	if (Page.hasFirst()) {
		Page.goTo(1);
	}
};
var last = function() {
	if (Page.hasLast()) {
		Page.goTo(Page.getLastPage());
	}
};
var pre = function() {
	if (Page.hasFirst()) {
		Page.goTo(Page.getCurrPage() - 1);
	}
};
var next = function() {
	if (Page.hasLast()) {
		Page.goTo(Page.getCurrPage() + 1);
	}
};
var skip = function(num) {
	Page.goTo(parseInt(num));
};

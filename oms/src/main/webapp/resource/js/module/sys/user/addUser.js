$(document).ready(function () {
    addUser.init();
})

var addUser = {

    init: function () {
    	addUser.initTip();
    },

    initTip: function () {
        var ttip_validator = $('.form_validation_tip').validate({
            onkeyup: false,
            errorClass: 'error',
            validClass: 'valid',
            ignore: "",
            highlight: function(element) {
                $(element).closest('div').addClass("f_error");
            },
            unhighlight: function(element) {
                $(element).closest('div').removeClass("f_error");
            },
            rules:{
            	loginname: { required: true},
            	name: { required: true},
            	password: { required: true},
            	organizationId: { required: true},
            	roleIds: { required: true}
            },
            messages: {
            	loginname: { required: "请输入登录名"},
            	name: { required: "请输入姓名"},
            	password: { required: "请输入密码"},
            	organizationId: { required: "请选择部门"},
            	roleIds: { required: "请选择角色"}
            },
            invalidHandler: function(form, validator) {
                //$.sticky("There are some errors. Please corect them and submit again.", {autoclose : 5000, position: "top-right", type: "st-error" });
            },
            errorPlacement: function(error, element) {
                // Set positioning based on the elements position in the form
                var elem = $(element);

                var isPlaceholder = Helper.isPlaceholderSupported();
                // Check we have a valid error message
                if (!error.is(':empty')) {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        // Apply the tooltip only if it isn't valid
                        elem.filter(':not(.valid)').parent('label').parent('div').find('.error_placement').qtip({
                                overwrite: false,
                                content: error,
                                position: {
                                    my: 'left center',
                                    at: 'center right',
                                    viewport: $(window),
                                    adjust: {
                                        x: 6
                                    }
                                },
                                show: {
                                    event: false,
                                    ready: true
                                },
                                hide: false,
                                style: {
                                    classes: 'ui-tooltip-red ui-tooltip-rounded' // Make it red... the classic error colour!
                                }
                            })
                            // If we have a tooltip on this element already, just update its content
                            .qtip('option', 'content.text', error);
                    } else {
                        var xPoint = 5;
           
                        elem.filter(':not(.valid)').qtip({
                                overwrite: false,
                                content: error,
                                position: {
                                    my: 'left center',
                                    at: 'center right',
                                    viewport: $(window),
                                    adjust: { x: xPoint, y: 0 }
                                },
                                show: {
                                    event: false,
                                    ready: true
                                },
                                hide: false,
                                style: {
                                    classes: 'ui-tooltip-red ui-tooltip-rounded' // Make it red... the classic error colour!
                                }
                            })
                            // If we have a tooltip on this element already, just update its content
                            .qtip('option', 'content.text', error);

                    };
                }
                // If the error is empty, remove the qTip
                else {
                    if ((elem.is(':checkbox')) || (elem.is(':radio'))) {
                        elem.parent('label').parent('div').find('.error_placement').qtip('destroy');
                    } else {
                        elem.qtip('destroy');
                    }
                }
            },
            submitHandler: function(form) {
            	addUser.addUserCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    addUserCommit:function(){

    	var loginname=$("#loginname").val();
    	var name=$("#name").val();
    	var password=$("#password").val();
    	var organizationId=$("#organizationId").val();
    	var roleIds=$("#roleIds").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/sys/user/addUserCommit.do',
            data: {
            		"loginname" :loginname,
            		"name": name,
            		"password":password,
            		"organizationId":organizationId,
            		"rolesIds":roleIds
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/sys/user/listUser.do?operStatus=1';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


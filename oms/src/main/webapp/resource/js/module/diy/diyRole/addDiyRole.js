$(document).ready(function () {
    addDiyRole.init();
})

var addDiyRole = {

    init: function () {
    	addDiyRole.initTip();
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
            rules: {
            	roleName: { required: true},
            	seq: { required: true}
            },
            messages: {
            	roleName: {required: "请输入角色名称"},
            	seq: {required: "请输入排序"}
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

				$(".btn-submit").attr('disabled',"true");
				addDiyRole.addDiyRoleCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    addDiyRoleCommit:function(){
    	var roleName=$("#roleName").val();
    	var seq=$("#seq").val();
    	var description=$("#description").val();
    	if(roleName.length > 0){
    		var reg = /^[\w\u4e00-\u9fa5\-_][\s\w\u4e00-\u9fa5\-_]*[\w\u4e00-\u9fa5\-_]$/; 
    		if (!(reg.test(roleName))) {
    			Helper.alert("请输入正确的角色名称:只能输入中文，英文数字空格下划线都行，首尾不能为空格，字符串中间可以为空格");
            	return false;
			} 
    	}
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/diy/diyRole/addDiyRoleCommit.do',
            data: {
            	"roleName" :roleName,
            	"seq": seq,
            	"description":description
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/diy/diyRole/listDiyRole.do';
					Helper.stickySuccess("角色添加成功");
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


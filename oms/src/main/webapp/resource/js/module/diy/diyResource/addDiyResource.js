$(document).ready(function () {
    addDiyResource.init();
})

var addDiyResource = {

    init: function () {
    	addDiyResource.initTip();
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
            	resourceName: { required: true},
            	key:{ required: true},
            	seq:{ required: true}
            },
            messages: {
            	resourceName: { required: "请输入资源名称"},
            	key: { required: "请输入资源key"},
            	seq: { required: "请输入排序"}
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
            	$(".btn-submit").attr('disabled',"true");
            	addDiyResource.addDiyResourceCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    addDiyResourceCommit:function(){
    	var resourceName=$("#resourceName").val();
    	var key=$("#key").val();
    	var resourceType=$("#resourceType").val();
    	var url=$("#url").val();
    	var seq=$("#seq").val();
    	var icon=$("#icon").val();
    	var state=$("#state").val();
    	var pid=$("#pid").val();
    	
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/diy/diyResource/addDiyResourceCommit.do',
            data: {
            		"resourceName": resourceName,
            		"key": key,
            		"resourceType":resourceType,
            		"url":url,
            		"seq":seq,
            		"icon":icon,
            		"state":state,
            		"pid":pid,
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/diy/diyResource/listDiyResource.do?operStatus=1';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


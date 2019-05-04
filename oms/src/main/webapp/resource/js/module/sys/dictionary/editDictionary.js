$(document).ready(function () {
	editDictionary.init();
})

var editDictionary = {
    init: function () {
    	editDictionary.initTip();
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
            	name: { required: true}
            },
            messages: {
            	name: { required: "请输入字典名称"}
            },
            invalidHandler: function(form, validator) {
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
            	editDictionary.editDictionaryCommit();
            },
            success: $.noop // Odd workaround for errorPlacement not firing!
        });
    },
    editDictionaryCommit:function(){
    	var dictId=$("#dictId").val();
    	var name=$("#name").val();
    	var code=$("#code").val();
    	var value=$("#value").val();
    	var type=$("#type").val();
    	var seq=$("#seq").val();
    	var state=$("#state").val();
    	var pid=$("#pid").val();
		$.ajax({
            type: 'POST',
            url: Helper.getRootPath() + '/sys/dictionary/editDictionaryCommit.do',
            data: {
            		"dictId":dictId,
            		"name": name,
            		"code":code,
            		"value":value,
            		"seq":seq,
            		"type":type,
            		"state":state,
            		"pid":pid,
            	},
            cache:false,
            dataType: 'json',
            success: function(data){
            	$(".btn-submit").removeAttr('disabled');
                if(data.status) {
					location.href=Helper.getRootPath() + '/sys/dictionary/listDictionary.do?operStatus=2';
                }else{
                	Helper.alert(data.msg);
                	return false;
                }
            }
        });
    }
};


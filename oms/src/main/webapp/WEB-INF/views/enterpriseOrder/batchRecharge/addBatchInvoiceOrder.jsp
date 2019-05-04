<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/enterpriseOrder/batchRecharge/addBatchInvoiceOrder.js"></script>
</head>

<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
          <!-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>订单管理</li>
			                    <li><a href="${ctx }/enterpriseOrder/batchRecharge/listRecharge.do">批量充值</a></li>
			                     <li>批量充值开票</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx }/merchant/shopInf/addShopInfCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">充值开票确认</h3>
				                 	<input type="hidden" name="orderId" id="orderId" value="${batchInvoiceOrder.orderId }">
				                 	<input type="hidden" name="invoiceAmt" id="invoiceAmt" value="${batchInvoiceOrder.invoiceAmt }">
				                 	<input type="hidden" name="mchntCode" id="mchntCode" value="${batchInvoiceOrder.mchntCode }">	
				                 		<div class="control-group">
							             <label class="control-label">订单号</label>
							             <div class="controls">
							                 <input type="text" class="span6"  value="${batchInvoiceOrder.orderId }" readonly="readonly" />
							             </div>
							     		</div>
				                 		
				                 	   <div class="control-group">
							             <label class="control-label">开票金额</label>
							             <div class="controls">
							                 <input type="text" class="span6"  value="${batchInvoiceOrder.invoiceAmt }" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">操作人</label>
							             <div class="controls">
							             	 <input type="text" class="span6"  value="${batchInvoiceOrder.createUser }" readonly="readonly" />
							             </div>
							            </div>
							            
							            <div class="control-group">
							             <label class="control-label">开票商户</label>
							             <div class="controls">
							             	<input type="text" class="span6"  value="${batchInvoiceOrder.mchntName }" readonly="readonly" />
							             </div>
							            </div>
							            
							     		<div class="control-group">
							             <label class="control-label">开票门店</label>
							             <div class="controls">
							               		<select name="shopCode" id="shopCode" class="chzn_a span6">
							               		<option value="">--请选择--</option>
												<c:forEach var="sl" items="${shopInfList }">
													<option value="${sl.shopCode }">${sl.shopName }</option>
												</c:forEach>
				                                </select>
							            </div>
							     		</div>
								     	<div class="control-group">
								             <label class="control-label">开票信息描述</label>
								             <div class="controls">
								                  <textarea  rows="6" class="span6" name="invoiceInfo" id = "invoiceInfo"></textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
								       <div class="control-group ">
					                            <div class="controls">
					                            	<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_ADDCOMMIT')">
					                                <button class="btn btn-primary" type="button" id="addSubmitBtn" >确认开票</button>
					                                </sec:authorize>
					                                <button class="btn btn-inverse btn-reset" type="reset">返回</button>
					                            </div>
					                  	</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
<script>


$(function(){
    $('#mercahnt_select').chosen();
});

// $('#fileShopInfImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
//     language: 'fr',
//     uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
// $('#fileShopMenuImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
// 	uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
//     language: 'fr',
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
$('#fileShopInStoreImgs').fileinput({
	language: 'zh', //设置语言
	uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
	allowedFileExtensions : ['jpg', 'png','gif','jpeg'],//接收的文件后缀,
	maxFileCount:10,
	enctype: 'multipart/form-data',
	showUpload: false, //是否显示上传按钮
	showCaption: false,//是否显示标题
	browseClass: "btn btn-primary", //按钮样式 
	previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
	msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
});
</script>
</html>
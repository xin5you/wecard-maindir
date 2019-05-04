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
							             	 <input type="text" class="span6"  value="${batchInvoiceOrder.invoiceUserName }" readonly="readonly" />
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
							               		<select name="pShopCode" id="shopCode" data-placeholder="请点击选择门店" class="chzn_a span6" disabled="disabled">
													<option value="${batchInvoiceOrder.shopName }">${batchInvoiceOrder.shopName }</option>
				                                </select>
							            </div>
							     		</div>
								     	<div class="control-group">
								             <label class="control-label">开票信息描述</label>
								             <div class="controls">
								                  <textarea  rows="6" class="span6" name="invoiceInfo" value="${batchInvoiceOrder.invoiceInfo }" id = "invoiceInfo" readonly="readonly">${batchInvoiceOrder.invoiceInfo }</textarea>
								                 <span class="help-block"></span>
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
</script>
</html>
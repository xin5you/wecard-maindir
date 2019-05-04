<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <!-- colorbox -->
      <link rel="stylesheet" href="${ctx}/resource/colorbox/colorbox.css" />
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
      <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/merchant/scanBoxDeviceInf/editScanBoxDeviceInf.js"></script>
     
      <script src="${ctx}/resource/colorbox/jquery.colorbox.min.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
          <-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>设备信息</li>
			                    <li><a href="${ctx }/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do">设备管理</a></li>
			                     <li>编辑设备信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/merchant/scanBoxDeviceInf/editScanBoxDeviceInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								 <input type="hidden" id="deviceId" name="deviceId"  value="${scanBoxDeviceInf.deviceId }" />
								 <h3 class="heading">编辑设备信息</h3>
						         	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 	   <div class="control-group formSep">
							             <label class="control-label">设备类型</label>
							             <div class="controls">
							             		<select name="deviceType" id="deviceType" data-placeholder="--请选择设备类型--" class="span6">
													<option value="${scanBoxDeviceInf.deviceType}">${scanBoxDeviceInf.deviceType}</option>
												</select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">设备号</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="deviceNo" name="deviceNo" value="${scanBoxDeviceInf.deviceNo}" />
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntCode" id="mercahnt_select" data-placeholder="${scanBoxDeviceInf.mchntName} (${scanBoxDeviceInf.mchntCode})" class="span6" >
												<option value="${scanBoxDeviceInf.mchntCode}"></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntCode}">${rs.mchntName} (${rs.mchntCode})</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属一级门店</label>
							             <div class="controls">
							                 <select name="shopCode1"  id="shopCode1"  class="span6">
							                  <c:forEach var="s" items="${shopInfList1}" >
							                 	<option value = "${s.shopCode}" <c:if test="${s.shopCode == shopInf.pShopCode }">selected="selected"</c:if>>${s.shopName}</option>
							                 </c:forEach>
							                 </select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属二级门店</label>
							             <div class="controls">
							                 <select name="shopCode"  id="shopCode"  class="span6">
							                  <c:forEach var="shopInf" items="${shopInfList2}" >
							                 	<option value = "${shopInf.shopCode}" <c:if test="${shopInf.shopCode == scanBoxDeviceInf.shopCode }">selected="selected"</c:if>>${shopInf.shopName}</option>
							                 </c:forEach>
							                 </select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">通道名称</label>
							             <div class="controls">
							               		<select name="channelNo" id="channelNo" data-placeholder="--请选择通道名称--" class="span6">
							               		<option value="">--请选择通道名称--</option>
												 <c:forEach var="pcList" items="${pcList}" varStatus="st">
												 		<option value="${pcList.channelNo}" <c:if test="${pcList.channelNo == scanBoxDeviceInf.channelNo }">selected="selected"</c:if>>${pcList.channelName}</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">定额支付状态</label>
							             <div class="controls">
							                 <select name="fixedPayFlag" id=fixedPayFlag  class="span6">
							                 	<c:if test="${scanBoxDeviceInf.fixedPayFlag == 1 }">
							                 		<option selected="selected" value="1">是</option>
							                 		<option  value="0">否</option>
							                 	</c:if>
							                 	<c:if test="${scanBoxDeviceInf.fixedPayFlag == 0 }">
							                 		<option value="1">是</option>
							                 		<option selected="selected" value="0">否</option>
							                 	</c:if>
												
											 </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">定额支付金额</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="fixedPayAmt" name="fixedPayAmt" value="${scanBoxDeviceInf.fixedPayAmt}" maxlength="13"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>

		                                <div class="control-group formSep">
		                                    <label class="control-label">打印文本</label>
		                                    <div class="controls">
                                             <input type="text" class="span6" id="print" name="print" maxlength="256" value="${scanBoxDeviceInf.print}"/>
                                             <span class="help-block"></span>
                                         </div>
		                                </div>
		                                
		                                <div class="control-group formSep">
                                            <label class="control-label">打印二维码</label>
                                            <div class="controls">
                                             <input type="text" class="span6" id="printQr" name="printQr" maxlength="128" value="${scanBoxDeviceInf.printQr}"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">附加信息打印标记</label>
                                         <div class="controls">
                                         	
                                         	<select name="printType" id="printType"  class="span6">
	                                         	<c:if test="${scanBoxDeviceInf.printType==0}">	
									                <option selected="selected" value="0">无效</option>
													<option value="1">追加</option>
													<option value="2">覆盖</option>
												</c:if>
												<c:if test="${scanBoxDeviceInf.printType==1}">	
									                <option  value="0">无效</option>
													<option selected="selected" value="1">追加</option>
													<option value="2">覆盖</option>
												</c:if>
												<c:if test="${scanBoxDeviceInf.printType==2}">	
									                <option  value="0">无效</option>
													<option value="1">追加</option>
													<option selected="selected" value="2">覆盖</option>
												</c:if>	
											</select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                             <label class="control-label">附加信息</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="receipt" id = "receipt" maxlength="256" value="${scanBoxDeviceInf.receipt}">${scanBoxDeviceInf.receipt}</textarea>
                                             </div>
                                        </div>
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks" id = "remarks" maxlength="256" value="${scanBoxDeviceInf.remarks}">${scanBoxDeviceInf.remarks}</textarea>
                                             </div>
                                        </div>
								       <div class="control-group">
					                            <div class="controls">
					                            <sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_EDITCOMMIT')">
					                                <button class="btn btn-primary"  type="submit" id="editSubmitBtn" >保存</button>
					                             </sec:authorize>
					                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
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
			                     <li>设备信息详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
								 <input type="hidden" id="eShopId" name="eShopId"  value="${mchntEshopInf.eShopId }" >
								 <h3 class="heading">设备信息详情</h3>
						         	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 		<div class="control-group">
							             <label class="control-label">设备类型</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${scanBoxDeviceInf.deviceType}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">设备号</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="deviceNo" name="deviceNo" value="${scanBoxDeviceInf.deviceNo}"  readonly="readonly"  />
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
                                       
                                        <div class="control-group formSep">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntCode" id="mchntCode"  class="span6" readonly="readonly" disabled="disabled">
												<option value="${scanBoxDeviceInf.mchntCode}">${scanBoxDeviceInf.mchntName}</option>
				                                </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">所属一级门店</label>
							             <div class="controls">
							                 <select name="shopCode"  id="shopCode"  class="span6" readonly="readonly" disabled="disabled">
												<option value="${shopInf.shopCode}">${shopInf.shopName}</option>
							                 </select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">所属二级门店</label>
							             <div class="controls">
							                 <select name="shopCode"  id="shopCode"  class="span6" readonly="readonly" disabled="disabled">
												<option value="${scanBoxDeviceInf.shopCode}">${scanBoxDeviceInf.shopName}</option>
							                 </select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">通道名称</label>
							             <div class="controls">
							               		<select name="channelNo" id="channelNo" data-placeholder="--请选择支付通道--" class="span6" readonly="readonly" disabled="disabled">
												 <c:forEach var="pcList" items="${pcList}" varStatus="st">
												 		<option value="${pcList.channelNo}" <c:if test="${pcList.channelNo == scanBoxDeviceInf.channelNo }">selected="selected"</c:if>>${pcList.channelName}</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">定额支付状态 </label>
							             <div class="controls">
							                 	<c:choose>
				                    				<c:when test="${scanBoxDeviceInf.fixedPayFlag==1}">
				                    					<select name="fixedPayFlag" id=fixedPayFlag  class="span6" readonly="readonly" disabled="disabled">
							                 				<option value="1">是</option>
							                 			</select>
							                 		</c:when>
				                    				<c:otherwise>
				                    					<select name="fixedPayFlag" id=fixedPayFlag  class="span6" readonly="readonly" disabled="disabled">
															<option selected="selected" value="0">否</option>
														</select>
													</c:otherwise>
												</c:choose>	
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">定额支付金额</label>
                                         <div class="controls">
                                             <input type="text" class="span6" name="fixedPayAmt" value="${scanBoxDeviceInf.fixedPayAmt}"  readonly="readonly" />
                                             <span class="help-block"></span>
                                         </div>
                                        </div>

		                                <div class="control-group formSep">
		                                    <label class="control-label">打印文本</label>
		                                    <div class="controls">
                                             <input type="text" class="span6" name="print" value="${scanBoxDeviceInf.print}"  readonly="readonly" />
                                             <span class="help-block"></span>
                                         </div>
		                                </div>
		                                
		                                <div class="control-group formSep">
                                            <label class="control-label">打印二维码</label>
                                            <div class="controls">
                                             <input type="text" class="span6" id="printQr" name="printQr" value="${scanBoxDeviceInf.printQr}"  readonly="readonly"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">附加信息打印标记</label>
                                         <div class="controls">
                                         	<c:if test="${scanBoxDeviceInf.printType == 0}">
                                         		<select name="printType" id="printType"  class="span6" disabled="disabled">
                                         			<option  value="0">无效</option>
                                         		</select>
                                            </c:if>
                                            <c:if test="${scanBoxDeviceInf.printType == 1}">
                                            	<select name="printType" id="printType"  class="span6" disabled="disabled">
                                         			<option  value="1">追加</option>
                                         		</select>
                                            </c:if>
                                            <c:if test="${scanBoxDeviceInf.printType == 2}">
                                            	<select name="printType" id="printType"  class="span6" disabled="disabled">
                                         			<option  value="2">覆盖</option>
                                         		</select>
                                            </c:if>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                             <label class="control-label">附加信息</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="receipt"  value="${scanBoxDeviceInf.receipt}" readonly="readonly">${scanBoxDeviceInf.receipt}</textarea>
                                             </div>
                                        </div>
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks" value="${scanBoxDeviceInf.remarks}"  readonly="readonly">${scanBoxDeviceInf.remarks}</textarea>
                                             </div>
                                        </div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
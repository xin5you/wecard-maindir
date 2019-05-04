<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/init.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>

<link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css"
	media="all" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
<!-- colorbox -->
<link rel="stylesheet" href="${ctx}/resource/colorbox/colorbox.css" />

<script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
<script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
<script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
<script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
<script src="${ctx}/resource/js/module/channel/paymentChannelApi/editPaymentChannelApiInf.js"></script>

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
	                    <li>支付通道API</li>
	                    <li><a href="${ctx }/channel/paymentChannel/listPaymentChannel.do">支付通道管理</a></li>
	                     <li>支付通道API列表</li>
	                </ul>
				</div>
			</nav>
			<div class="row-fluid">
				<div class="span12">
					<form action="${ctx }/channel/paymentChannelApi/editPaymentChannelApiInfCommit.do" id="mainForm" class="form-horizontal form_validation_tip"
						method="post" enctype="multipart/form-data">
					    <h3 class="heading">编辑支付通道API</h3>
					    <input type="hidden" id="channelId"  name = "channelId" value="${channelId }"/>
					    <input type="hidden" id="id"  name = "id" value="${pci.id }"/>
                 	   	<div class="control-group">
			            <label class="control-label">通道API名称</label>
			            <div class="controls">
							<input type="text" class="span6" id="name" name="name" maxlength="256" value="${pci.name }"/>
			                 <span class="help-block"></span>
			            </div>
			     		</div>
			     		<div class="control-group">
			             <label class="control-label">链接</label>
			             <div class="controls">
								<input type="text" class="span6" id="url" name="url" maxlength="256" value = "${pci.url }"/>
			                 	<span class="help-block"></span>
			             </div>
			     		</div>
			     		<div class="control-group">
			            <label class="control-label">类型</label>
			            <div class="controls">
			               	<select name="apiType" id="apiType" data-placeholder="--请选择通道类型--" class="span6">
								<c:forEach var="mapType" items="${mapType}" varStatus="st">
								 	<option value="${mapType.key}" <c:if test="${mapType.value == pci.apiType }">selected="selected"</c:if> >${mapType.value}</option>
								</c:forEach>
                            </select>
			            </div>
			     		</div>
			     		<div class="control-group">
				             <label class="control-label">启用标识</label>
				             <div class="controls">
				               		<select name="enable" id="enable" data-placeholder="--请选择启用标识--" class="span6">
									 	<option value="1" <c:if test="${pci.enable == '1' }">selected="selected"</c:if> >启用</option>
									 	<option value="0" <c:if test="${pci.enable == '0' }">selected="selected"</c:if>>禁用</option>
	                                </select>
				             </div>
			     		</div>
                        <div class="control-group">
                             <label class="control-label">描述</label>
                             <div class="controls">
                                  <textarea  rows="4" class="span6" name="description" id = "description" maxlength="256">${pci.description }</textarea>
                             </div>
                        </div>
				        <div class="control-group">
							<div class="controls">
								<sec:authorize
									access="hasRole('ROLE_PAYMENT_CHANNELS_API_EDITCOMMIT')">
									<button class="btn btn-primary" type="submit" id="addSubmitBtn">保存</button>
								</sec:authorize>
								<button class="btn btn-search" type="button">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
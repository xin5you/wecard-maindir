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
      <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
      <script src="${ctx}/resource/js/module/merchant/scanBoxDeviceInf/addScanBoxDeviceInf.js"></script>
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
			                     <li>新增设备信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/merchant/scanBoxDeviceInf/addScanBoxDeviceInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								 <h3 class="heading">新增设备信息</h3>

				                 	   <div class="control-group formSep">
							             <label class="control-label">设备类型</label>
							             <div class="controls">
							               		<select name="deviceType" id="deviceType" data-placeholder="--请选择设备类型--" class="span6">
												<option value="PPBOX">PPBOX</option>
												</select>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">设备号</label>
							             <div class="controls">
												<input type="text" class="span6" id="deviceNo" name="deviceNo" maxlength="32"/>
							                 	<span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntCode" id="mercahnt_select" data-placeholder="--请选择商户--" class="span6">
												<option value=""></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntCode}">${rs.mchntName} (${rs.mchntCode})</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属一级门店</label>
							             <div class="controls">
							                 <select name="shopCode1"  id="shopCode1"  class="span6"></select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							     		 <label class="control-label">所属二级门店</label>
							             <div class="controls">
							                 <select name="shopCode"  id="shopCode"  class="span6"></select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">通道名称</label>
							             <div class="controls">
							               		<select name="channelNo" id="channelNo" data-placeholder="--请选择通道名称--" class="span6">
							               		<option value="">--请选择通道名称--</option>
												 <c:forEach var="pcList" items="${pcList}" varStatus="st">
												 		<option value="${pcList.channelNo}">${pcList.channelName}</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">定额支付状态</label>
							             <div class="controls">
							                 <select name="fixedPayFlag" id="fixedPayFlag"  class="span6">
							                 	<option value="1">是</option>
												<option selected="selected" value="0">否</option>
											 </select>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">定额支付金额</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="fixedPayAmt" name="fixedPayAmt" maxlength="12" value="0"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>

		                                <div class="control-group formSep">
		                                    <label class="control-label">打印文本</label>
		                                    <div class="controls">
                                             <input type="text" class="span6" id="print" name="print" maxlength="256"/>
                                             <span class="help-block"></span>
                                         </div>
		                                </div>
		                                
		                                <div class="control-group formSep">
                                            <label class="control-label">打印二维码</label>
                                            <div class="controls">
                                             <input type="text" class="span6" id="printQr" name="printQr" maxlength="128"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">附加信息打印标记</label>
                                         <div class="controls">
                                         		<select name="printType" id="printType"  class="span6">
								                 	<option selected="selected" value="0">无效</option>
													<option value="1">追加</option>
													<option value="2">覆盖</option>
												 </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                             <label class="control-label">附加信息</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="receipt" id = "receipt" maxlength="256"></textarea>
                                             </div>
                                        </div>
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks" id = "remarks" maxlength="256"></textarea>
                                             </div>
                                        </div>
								       <div class="control-group">
					                            <div class="controls">
					                            <sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_ADDCOMMIT')">
					                                <button class="btn btn-primary"  type="submit" id="addSubmitBtn" >保存</button>
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
    $('#mercahnt_select').chosen({
		no_results_text: "没有查询到您需要输入的数据：",
		placeholder_text : "请输入您需要查询的数据.", 
		search_contains: true,
		disable_search_threshold:10
	});
});
</script>
</html>
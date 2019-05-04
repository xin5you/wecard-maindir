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
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/provider/providerInf/listTelProviderInf.do">供应商信息管理</a></li>
			                    <li>供应商信息详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<h3 class="heading">供应商信息详情</h3>
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
					     		<div class="control-group formSep">
					             	<label class="control-label">供应商名称</label>
						             <div class="controls">
											<input type="text" class="span6" id="providerName" name="providerName" maxlength="32" value="${telProviderInf.providerName}" readonly="readonly"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">app_url</label>
						             <div class="controls">
											<input type="text" class="span6" name="appUrl" id="appUrl" maxlength="128" value="${telProviderInf.appUrl}" readonly="readonly"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">app_Secret</label>
						             <div class="controls">
											<input type="text" class="span6" name="appSecret" id="appSecret" maxlength="64" value="${telProviderInf.appSecret}" readonly="readonly"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">access_token</label>
						             <div class="controls">
											<input type="text" class="span6" name="accessToken" id="accessToken" maxlength="64" value="${telProviderInf.accessToken}" readonly="readonly"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">默认路由标识</label>
						             <div class="controls">
						               		<select name="defaultRoute" id="defaultRoute" class="span6" readonly="readonly" disabled="disabled">
											 <c:forEach var="drList" items="${defaultRouteList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${drList.code == telProviderInf.defaultRoute }">selected="selected""</c:if> >${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
	                                  <label class="control-label">供应商折扣</label>
	                                  <div class="controls">
	                                      <input type="text" class="span6" id="providerRate" name="providerRate" maxlength="8" value="${telProviderInf.providerRate}" readonly="readonly"/>
	                                      <span class="help-block"></span>
	                                  </div>
                                 </div>

                                <div class="control-group formSep">
                                    <label class="control-label">操作顺序</label>
                                    <div class="controls">
                                          <input type="text" class="span6" id="operSolr" name="operSolr" value="${telProviderInf.operSolr}" onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"/>
                                          <span class="help-block"></span>
                                    </div>
                                </div>
                                
                                <div class="control-group">
                                     <label class="control-label">备注</label>
                                     <div class="controls">
                                          <textarea  rows="4" class="span6" name="remarks" readonly="readonly" id = "remarks" maxlength="256">${telProviderInf.remarks}</textarea>
                                     </div>
                                </div>
                            </form>	
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
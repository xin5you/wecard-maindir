<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
     <script src="${ctx}/resource/js/module/phoneRecharge/telChannelInf/editTelChannelProductRate.js"></script>
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
			                    <li><a href="${ctx }/channel/channelInf/listTelChannelInf.do">分销商管理</a></li>
			                    <li>新增分销商充值产品信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/channel/product/editTelProviderInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								<input type = "hidden" name="itemId" id="itemId" value="${telProductInf.id }"/>
								<input type="hidden" id="channelId"  value="${telProductInf.channelId }"/>
								<h3 class="heading">编辑分销商充值产品折扣率</h3>
					     		<div class="control-group formSep">
					             	<label class="control-label">运营商产品名称</label>
						             <div class="controls">
		           			   	   			<input id="operName" name="operName" type="text" class="span6" value="${telProductInf.operName }" disabled="disabled" readonly="readonly" maxlength="32"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">运营商</label>
						             <div class="controls">
						               		<select name="operId" id="operId" disabled="disabled"  class="span6">
											 <c:forEach var="drList" items="${operIdList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${s.code==telProductInf.operId}">selected</c:if>>${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">区分地区标识</label>
						             <div class="controls">
						               		<select name="areaFlag" id="areaFlag" disabled="disabled"  class="span6">
											 <c:forEach var="drList" items="${areaFlagList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${s.code==telProductInf.areaFlag}">selected</c:if> >${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品面额（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productAmt" disabled="disabled" readonly="readonly" id="productAmt" value="${telProductInf.productAmt }"  maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品售价（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productPrice" disabled="disabled" readonly="readonly" id="productPrice" value="${telProductInf.productPrice }"  maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品类型</label>
						             <div class="controls">
						               		<select name="productType" id="productType" disabled="disabled" class="span6">
											 <c:forEach var="drList" items="${productTypeList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${s.code==telProductInf.productType}">selected</c:if>>${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
                               <div class="control-group formSep">
						             <label class="control-label">折扣率</label>
						             <div class="controls">
					            		 <input type="text" class="span6" name="channelRate" id="channelRate" value="${telProductInf.channelRate }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" maxlength="12"/>
					                 	 <span class="help-block"></span>
						             </div>
					     		</div>
						       <div class="control-group">
			                            <div class="controls">
			                            <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM_LIST_EDITCOMMIT')">
			                                <button class="btn btn-primary"  type="submit" id="addSubmitBtn" >保存</button>
			                             </sec:authorize>
			                                <button class="btn btn-back" type="button">返 回</button>
			                            </div>
			                  	</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
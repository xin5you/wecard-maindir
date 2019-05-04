<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
     <script src="${ctx}/resource/js/module/phoneRecharge/telChannelProduct/editTelChannelProduct.js"></script>
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
			                    <li><a href="${ctx }/channel/product/listTelChannelProduct.do">分销商充值产品管理</a></li>
			                     <li>新增分销商充值产品信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action=" " id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								<h3 class="heading">编辑分销商充值产品信息</h3>
					     		<div class="control-group formSep">
					             	<label class="control-label">运营商产品名称</label>
						             <div class="controls">
		           			   	   			<input id="operName" name="operName" type="text" class="span6" readonly="readonly" disabled="disabled" value="${telCPInf.operName }"  maxlength="32"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">运营商</label>
						             <div class="controls">
						               		<select name="operId" id="operId" disabled="disabled" class="span6">
											 <c:forEach var="drList" items="${operIdList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${drList.code==telCPInf.operId}">selected</c:if>>${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">区分地区标识</label>
						             <div class="controls">
						               		<select name="areaFlag" id="areaFlag" disabled="disabled" class="span6">
											 <c:forEach var="drList" items="${areaFlagList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${drList.code==telCPInf.areaFlag}">selected</c:if> >${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品面额（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productAmt" id="productAmt" readonly="readonly" disabled="disabled" value="${telCPInf.productAmt }"  onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品售价（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productPrice" id="productPrice" readonly="readonly" disabled="disabled" value="${telCPInf.productPrice }" onkeyup="checkProductPrice(this)" maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品类型</label>
						             <div class="controls">
						               		<select name="productType" id="productType" class="span6" disabled="disabled">
											 <c:forEach var="drList" items="${productTypeList}" varStatus="st">
											 		<option value="${drList.code}" <c:if test="${drList.code==telCPInf.productType}">selected</c:if>>${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
                                <div class="control-group">
                                     <label class="control-label">备注</label>
                                     <div class="controls">
                                          <textarea  rows="4" class="span6" name="remarks" id = "remarks"  readonly="readonly" disabled="disabled" maxlength="256">${telCPInf.remarks }</textarea>
                                     </div>
                                </div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
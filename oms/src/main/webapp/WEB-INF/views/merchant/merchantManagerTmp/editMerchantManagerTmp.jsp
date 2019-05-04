<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/merchant/merchantManagerTmp/editMerchantManagerTmp.js"></script>
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
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/merchant/managerTmp/listMerchantManagerTmp.do">商户员工管理</a></li>
			                     <li>编辑员工</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">编辑员工</h3>
				                 	   <input type="hidden" name="mangerId" id="mangerId" value="${merchantManagerTmp.mangerId }">
				                 	   <div class="control-group formSep">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntId" id="mercahnt_select" data-placeholder="请点击选择商户" class="chzn_a span6">
												<option value=""></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntId}"
												 				<c:if test="${merchantManagerTmp.mchntId==rs.mchntId}">selected="selected"</c:if>
												 		>${rs.mchntName } (${rs.mchntCode })</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							                 <select name="shopId" id="shopId"  class="span6">
							                 <option value="">==请选择所属门店==</option>
							                  <c:forEach var="rs" items="${shopInfList}" varStatus="ss">
							                 		<option value="${rs.shopId }" 
							                 		<c:if test="${merchantManagerTmp.shopId==rs.shopId}">selected="selected"</c:if>
							                 		>${rs.shopName }</option>
							                 	</c:forEach>
							                 </select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">所属角色</label>
							             <input id="roleType" value="${merchantManagerTmp.roleType}" type="hidden" />
							             <div class="controls">
							             	<label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="100" />商户boss
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="200" />商户财务
							                </label> 
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="300" />商户店长
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="400" />商户收银员
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="500" />商户服务员
							                </label>
							                 <%-- <select name="roleType" id="roleType"  class="span6">
							                 	<option value="100" <c:if test="${merchantManagerTmp.roleType=='100'}">selected="selected"</c:if>>商户boss</option>
							                 	<option value="200" <c:if test="${merchantManagerTmp.roleType=='200'}">selected="selected"</c:if>>商户财务</option>
							                 	<option value="300" <c:if test="${merchantManagerTmp.roleType=='300'}">selected="selected"</c:if>>商户店长</option>
							                 	<option value="400" <c:if test="${merchantManagerTmp.roleType=='400'}">selected="selected"</c:if>>商户收银员</option>
							                 	<option value="500" <c:if test="${merchantManagerTmp.roleType=='500'}">selected="selected"</c:if>>商户服务员</option>
							                 </select> --%>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">用户姓名</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="name" name="name" value="${merchantManagerTmp.name}"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
								             <label class="control-label">手机号</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="phoneNumber" name="phoneNumber" value="${merchantManagerTmp.phoneNumber}" />
								                 <span class="help-block"></span>
								             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
								             <label class="control-label">员工编号</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="remarks" name="remarks" value="${merchantManagerTmp.remarks}"/>
								                 <span class="help-block"></span>
								             </div>
							     		</div>
								       <div class="control-group ">
					                            <div class="controls">
					                                <button class="btn btn-primary" type="submit" id="addSubmitBtn" >保存</button>
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
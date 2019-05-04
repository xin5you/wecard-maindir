<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/merchant/merchantInf/listMerchantInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/merchant/merchantInf/listMerchantInf.do">商户管理</a></li>
			                     <li>商户列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/merchant/merchantInf/listMerchantInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus" value="${operStatus }"/>
						<h3 class="heading">商户列表</h3>
						<div class="row-fluid" id="h_search">
							<div class="span10">
								<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${merchantInf.mchntName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${merchantInf.mchntCode }"/>
		                       	</div>
								<div class="input-prepend">
		           			   	   	<span class="add-on">开户状态</span>
		           			   	   	<select name="accountStat" id="accountStat" class="input-medium">
                                    <option value="">--请选择--</option>
                                    <c:forEach var="dict" items="${dictList}" varStatus="st">
										 <option value="${dict.value}" <c:if test="${merchantInf.accountStat==dict.value}">selected="selected"</c:if>>${ dict.name }</option>
									 </c:forEach>
                                    </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">福利余额启用标识</span>
		           			   	   	<select name="mchntType1" id="mchntType1" class="input-medium">
                                    <option value="">--请选择--</option>
                                    <c:forEach var="mt" items="${mchntTypeList}" varStatus="st">
										 <option value="${mt.code}" <c:if test="${merchantInf.mchntType==mt.code}">selected="selected"</c:if>>${ mt.name }</option>
									 </c:forEach>
                                    </select>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_INTOADD')">
									<button type="button" class="btn btn-primary btn-add">新增商户</button>
								</sec:authorize>
							</div>
						</div>
						
				         <br/>       
						<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>商户号</th>
				               <th>商户名称</th>
				               <th>行业类型</th>
				               <th>开户状态</th>
				               <th>福利余额启用标识</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                    <td>${entity.mchntCode}</td>
				                    <td>${entity.mchntName}</td>
									<td>${entity.industryName1}</td>
									<td><c:if test="${entity.accountStat=='10'}">已开户</c:if><c:if test="${entity.accountStat=='00'}">未开户</c:if></td>
				                    <td>${entity.mchntType}</td>
				                    <td>
				                    	<sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_INTOEDIT')">
										<a mchntId="${entity.mchntId}" title="编辑" class="btn-mini btn-edit"  href="#"><i class="icon-edit"></i></a> 
										</sec:authorize>
<%-- 										<sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_DELETE')">   --%>
<%-- 										<a mchntId="${entity.mchntId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a> --%>
<%-- 										</sec:authorize> --%>
										<sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_VIEW')">
										<a mchntId="${entity.mchntId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
										</sec:authorize>
				                    </td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
				      </form>
			   </div>
	    </div>
</body>
</html>

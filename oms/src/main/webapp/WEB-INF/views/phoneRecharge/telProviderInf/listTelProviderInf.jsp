<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telProviderInf/listTelProviderInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/provider/providerInf/listTelProviderInf.do">供应商信息管理</a></li>
			                    <li>供应商信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/provider/providerInf/listTelProviderInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">供应商信息列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">供应商名称</span><input id="providerName" name="providerName" type="text" class="input-medium" maxlength="32" value="${telProviderInf.providerName }" />
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增供应商</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
					             <tr>
					               <th>供应商名称</th>
					               <th>app_url</th>
					               <th>app_Secret</th>
					               <th>access_token</th>
					               <th>默认路由标识</th>
					               <th>供应商折扣</th>
					               <th>操作顺序</th>
					               <th>操作</th>
					             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.providerName}</td>
				                 	<td>${entity.appUrl}</td>
									<td>${entity.appSecret}</td>
									<td>${entity.accessToken}</td>
				                    <td>${entity.defaultRoute}</td>
				                    <td>${entity.providerRate}</td>
				                    <td>${entity.operSolr}</td>
				                    <td>
				                    <sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF_INTOEDIT')">
									<a providerId="${entity.providerId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF_DELETE')">
									<a providerId="${entity.providerId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF_VIEW')">
									<a providerId="${entity.providerId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

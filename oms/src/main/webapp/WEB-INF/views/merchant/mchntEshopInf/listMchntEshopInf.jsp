<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/merchant/mchntEshopInf/listMchntEshopInf.js"></script>
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
			                    <li><a href="${ctx }/mchnteshop/eShopInf/listMchntEshopInf.do">商城管理</a></li>
			                     <li>商城列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/mchnteshop/eShopInf/listMchntEshopInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">商城列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商城名称</span><input id="eShopName" name="eShopName" type="text" class="input-medium" value="${eshop.eShopName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户名称</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${eshop.mchntName }"/>
		                       	</div>
		
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${eshop.mchntCode }" />
		                       	</div>
							</div>
							<div class="pull-right">
								
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 
								 <sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增商城</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" style="table-layout:fixed; ">
				             <thead>
				             <tr>
				                <th>商城名称</th>
				               <th>商户名称</th>
				               <th>商户号</th>
				               <th>门店名称</th>
				                <th>门店号</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.eShopName}</td>
				                 	<td>${entity.mchntName}</td>
									<td>${entity.mchntCode}</td>
									<td>${entity.shopName}</td>
				                    <td>${entity.shopCode}</td>
				                    <td>
				                    <sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_INTOEDIT')">
									<a eShopId="${entity.eShopId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_DELETE')">
									<a eShopId="${entity.eShopId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_VIEW')">
									<a eShopId="${entity.eShopId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

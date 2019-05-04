<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/merchant/shopInf/listShopInf.js"></script>
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
			                    <li><a href="${ctx }/merchant/shopInf/listShopInf.do">门店管理</a></li>
			                     <li>门店列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/merchant/shopInf/listShopInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">门店列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">门店名称</span><input id="shopName" name="shopName" type="text" class="input-medium" value="${shopInf.shopName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">门店号</span><input id="shopCode" name="shopCode" type="text" class="input-medium" value="${shopInf.shopCode }"/>
		                       	</div>
		
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${shopInf.mchntName }" />
		                       	</div>
		                       	
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${shopInf.mchntCode }"/>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增门店</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				                <th>门店号</th>
				               <th>门店名称</th>
				               <th>所属门店</th>
				               <th>门店级别</th>
				               <th>商户号</th>
				               <th>商户名称</th>
				               <th>售卡标志</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.shopCode}</td>
									<td>${entity.shopName}</td>
									<td>${entity.pShopName }</td>
									<td>${entity.shopLevel }</td>
				                    <td>${entity.mchntCode}</td>
				                    <td>${entity.mchntName}</td>
				                    <td>${entity.sellCardFlag=='0'?'允许售卡':'不允许售卡'}</td>
				                    <td style = "text-align: left;">
				                    	<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_INTOEDIT')">
											<a shopId="${entity.shopId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_DELETE')">
											<a shopId="${entity.shopId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_VIEW')">
											<a shopId="${entity.shopId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
										</sec:authorize>
										<c:if test="${entity.shopLevel == 2}">
										<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_DOWNLOAD')">
											<a shopId="${entity.shopId}" title="下载二维码" class="btn-mini btn-download" href="#"><i class="icon-qrcode"></i></a>
										</sec:authorize>
										</c:if>	
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

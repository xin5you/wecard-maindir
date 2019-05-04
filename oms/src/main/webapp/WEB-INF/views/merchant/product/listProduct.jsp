<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <%@ include file="/WEB-INF/views/common/init.jsp"%>
	    <%@ include file="/WEB-INF/views/common/head.jsp"%>
	    <script src="${ctx}/resource/js/module/merchant/product/listProduct.js"></script>
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
			                    <li><a href="${ctx }/merchant/product/listProduct.do">产品管理</a></li>
			                    <li>产品列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/merchant/product/listProduct.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">卡产品列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">产品名称</span><input id="productName" name="productName" type="text" class="input-medium" value="${product.productName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">产品号</span><input id="productCode" name="productCode" type="text" class="input-medium" value="${product.productCode }"/>
		                       	</div>
		
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">卡BIN</span><input id="cardBin" name="cardBin" type="text" class="input-medium" value="${product.cardBin }" />
		                       	</div>
		                       	
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">业务类型</span>
		           			   	   	<select id="businessType" name="businessType" class="input-medium">
										<option value="40">微信支付业务</option>
									</select>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_MCHNT_PRODUCT_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增产品</button>
								</sec:authorize>
							</div>
						</div>
						</br > 
						<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>产品号</th>
				               <th>产品名称</th>
				               <th>卡BIN</th>
				               <th>业务类型</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.productCode}</td>
									<td>${entity.productName}</td>
				                    <td>${entity.cardBin}</td>
				                    <td><c:if test="${entity.businessType=='40'}">微信支付业务</c:if><c:if test="${entity.businessType != '40'}">线下支付业务</c:if></td>
				                    <td>
										<sec:authorize access="hasRole('ROLE_MCHNT_PRODUCT_INTOEDIT')">
											<a productCode="${entity.productCode}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a> 
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_MCHNT_PRODUCT_VIEW')">
											<a productCode="${entity.productCode}" title="详情" class="btn-mini btn-view"  href="#"><i class="icon-search"></i></a> 
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

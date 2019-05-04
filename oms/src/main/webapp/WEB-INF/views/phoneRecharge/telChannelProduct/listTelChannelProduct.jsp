<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelProduct/listTelChannelProduct.js"></script>
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
			                    <li><a href="${ctx }/channel/product/listTelChannelProduct.do">分销商充值产品管理</a></li>
			                     <li>分销商充值产品信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/product/listTelChannelProduct.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">分销商充值产品信息列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">运营商产品名称</span>
		           			   	   	<input id="operName" name="operName" type="text" class="input-medium" value="${telCPInf.operName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">运营商</span>
		           			   	   	<select name="operId" id="operId" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${operIdList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telCPInf.operId}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
				                    </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">区分地区标识</span>
		           			   	   	<select name="areaFlag" id="areaFlag" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${areaFlagList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telCPInf.areaFlag}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
				                    </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">产品类型</span>
		           			   	   	<select name="productType" id="productType" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${productTypeList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telCPInf.productType}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
			                    	</select>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增产品</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				                <th>运营商产品名称</th>
				                <th>运营商</th>
				                <th>区分地区标识</th>
				                <th>产品面额（元）</th>
				                <th>产品售价（元）</th>
				                <th>产品类型</th>
				                <th>创建时间</th>
				                <th>修改时间</th>
				                <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td style="overflow: hidden; text-overflow:ellipsis;white-space: nowrap; ">${entity.operName}</td>
				                 	<td>${entity.operId}</td>
				                 	<td>${entity.areaFlag}</td>
				                 	<td>${entity.productAmt}</td>
				                 	<td>${entity.productPrice}</td>
				                 	<td>${entity.productType}</td>
				                 	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
				                    <td>
<%-- 				                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM_LIST_INTOADD')"> --%>
<%-- 									<a productId="${entity.productId}" title="添加费率" class="btn-grant-role" href="#"><i class="icon-plus"></i></a>  --%>
<%-- 									</sec:authorize> --%>
				                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT_INTOEDIT')">
									<a productId="${entity.productId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT_DELETE')">
									<a productId="${entity.productId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT_VIEW')">
									<a productId="${entity.productId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/phoneRechargeShop/listPhoneRechargeShop.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>福利管理</li>
			                    <li><a href="${ctx }/phone/phoneRecharge/listPhoneRechargeShop.do">手机充值商品</a></li>
			                     <li>商品列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">商品列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">供应商</span>
		           			   	   	<select name="supplier" id="supplier" class="input-medium">
                                                <option value="">--请选择--</option>
                                                 <c:forEach var="supplierType" items="${SupplierList}">
                                                        <option value="${supplierType.code}" <c:if test="${supplierType.code == pps.supplier }">selected="selected"</c:if>>${supplierType.value}</option>
                                                 </c:forEach>
                                                </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">运营商</span>
		           			   	   	<select name="oper" id="oper" class="input-medium">
                                                <option value="">--请选择--</option>
                                                 <c:forEach var="operType" items="${OperatorTypeList}">
                                                        <option value="${operType.code}" <c:if test="${operType.code == pps.oper }">selected="selected"</c:if>>${operType.value}</option>
                                                 </c:forEach>
                                                </select>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增商品</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
<!-- 				                <th>序号</th> -->
				               <th>供应商</th>
				               <th>运营商</th>
				               <th>商品面值</th>
				               <th>商品售价</th>
				               <th>商品类型</th>
				               <th>是否可用</th>
				               <th>创建时间</th>
				               <th>修改时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
<%-- 				                 	<td>${st.count}</td> --%>
									<td>${entity.supplier}</td>
									<td>${entity.oper }</td>
									<td>${entity.shopFace }${entity.resv1 }</td>
				                    <td>${entity.shopPrice}</td>
				                    <td>${entity.shopType}</td>
				                    <td>${entity.isUsable}</td>
				                    <td>
				                    <fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				                    </td>
				                    <td>
				                    <fmt:formatDate value="${entity.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				                    </td>
				                    <td style = "text-align: center;">
				                    	<sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP_INTOEDIT')">
											<a goodsID="${entity.id}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP_DELETE')">
											<a goodsID="${entity.id}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP_VIEW')">
											<a goodsID="${entity.id}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

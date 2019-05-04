<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/margin/merchantCashManage/listMerchantCashManage.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>保证金信息</li>
			                    <li><a href="${ctx }/margin/mchntCashManage/listMerchantCashManage.do">保证金管理</a></li>
			                     <li>保证金信息管理列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/margin/mchntCashManage/listMerchantCashManage.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">保证金信息管理列表</h3>
						<div class="row-fluid" id="h_search">
							<div class="span10">
								<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span>
		           			   	   	<input id="mchntName" name="mchntName" type="text" class="input-medium" value="${merchantCashManage.mchntName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span>
		           			   	   	<input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${merchantCashManage.mchntCode }"/>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
<!-- 								<button type="reset" class="btn btn-inverse btn-reset">重 置</button> -->
								<sec:authorize access="hasRole('ROLE_CASH_MARGIN_MANAGE_INTOADD')">
									<button type="button" class="btn btn-primary btn-add">新增商户保证金</button>
								</sec:authorize>
							</div>
						</div>
						<br/>
						<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>商户号</th>
				               <th>商户名称</th>
				               <th>保证金账户金额</th>
				               <th>保证金获取额度</th>
				               <th>累计充值金额</th>
				               <th>累计充值面额</th>
				               <th>押款标志</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                    <td>${entity.mchntCode}</td>
				                    <td>${entity.mchntName}</td>
									<td>${entity.mortgageAmt/100}</td>
									<td>${entity.getQuota/100}</td>
									<td>${entity.rechargeAmt/100}</td>
									<td>${entity.rechargeFaceAmt/100}</td>
									<td><c:if test="${entity.mortgageFlg=='0'}">平台给商户押款</c:if></td>
				                    <td>
<%-- 										<a chashId="${entity.chashId}" title="查看" class="btn-view" ><i class="icon-search"></i></a> --%>
										<sec:authorize access="hasRole('ROLE_CASH_MARGIN_MARGINLIST')">
										<a chashId="${entity.chashId}" title="追加保证金明细" class="btn-mini btn-add-margin" href="#"><i class="icon-plus"></i></a>
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

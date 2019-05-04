<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/margin/merchantCashManage/listMerchantMargin.js"></script>
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
			                    <li><a href="${ctx }/margin/mchntCashManage/listMerchantMargin.do?chashId=${chashId }">保证金流水列表</a></li>
			                     <li>商户追加保证金-新增</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/margin/mchntCashManage/listMerchantMargin.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<input type="hidden" type="chashId" id="chashId"  value="${chashId}"/>
						<h3 class="heading">保证金流水列表</h3>
						<div class="row-fluid" id="h_search">
						<div class="span10">
								<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${merchantInf.mchntName }" disabled="disabled" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${merchantInf.mchntCode }" disabled="disabled"/>
		                       	</div>
		                    </div>
							<div class="pull-right">
								<sec:authorize access="hasRole('ROLE_CASH_MARGIN_INTOADD')">
									<button type="button" class="btn btn-primary btn-add" id="intoAddMerchantMargin">追加保证金</button>
								</sec:authorize>
							</div>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>审核流水号</th>
				               <th>押款类型</th>
				               <th>保证金账户金额</th>
				               <th>累计充值金额</th>
				               <th>保证金追加金额</th>
				               <th>申请人</th>
				               <th>申请时间</th>
				               <th>状态</th>
				               <th>审批人</th>
				               <th>审批时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                    <td>${entity.marginListId}</td>
				                    <td><c:if test="${entity.mortgageFlg=='0'}">平台给商户押款</c:if></td>
									<td>${entity.mortgageAmt/100}</td>
									<td>${entity.rechargeAmt/100}</td>
									<td>${entity.addMortgageAmt/100}</td>
									<td>${entity.applyUserName}</td>
									<td><fmt:formatDate value="${entity.applyTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>
										 <c:forEach var="applyStat" items="${approveDictList}" varStatus="st">
										  <c:if test="${applyStat.value == entity.approveStat}">
										  	${applyStat.name }
										  </c:if>
										 </c:forEach>
									</td>
									<td>${entity.approveName}</td>
									<td><fmt:formatDate value="${entity.approveTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				                    <td>
				                    	<c:if test="${entity.approveStat=='10' }">
											<a marginListId="${entity.marginListId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>   
											<a marginListId="${entity.marginListId}" chashId="${entity.chashId}" title="删除" class="btn-delete" href="#" ><i class="icon-remove"></i></a>
										</c:if>
										<a marginListId="${entity.marginListId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

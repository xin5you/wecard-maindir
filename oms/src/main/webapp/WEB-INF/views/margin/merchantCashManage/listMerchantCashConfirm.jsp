<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/margin/merchantCashManage/listMerchantCashConfirm.js"></script>
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
			                    <li><a href="${ctx }/margin/mchntCashManage/listMerchantCashConfirm.do">商户保证金押款确认</a></li>
			                     <li>商户保证金押款确认列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/margin/mchntCashManage/listMerchantCashConfirm.do" class="form-inline"  method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">商户保证金押款确认列表</h3>
						<div class="row-fluid" id="h_search">
						<div class="span10">
								<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${merchantMargin.mchntName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${merchantMargin.mchntCode }" />
		                       	</div>
		                       	
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">押款标志</span>
		           			   	   	<select id="mortgageFlg" name="mortgageFlg" class="input-medium" >
										 <c:forEach var="dict" items="${dictList}" varStatus="st">
											 		<option value="${dict.value}" <c:if test="${merchantMargin.mortgageFlg==dict.value}">selected="selected"</c:if>>${ dict.name }</option>
										 </c:forEach>
									</select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">状态</span>
		           			   	   	  <select id="approveStat" name="approveStat" class="input-medium">
           			   	   	  			<option value="" >--审核状态--</option>
										 <c:forEach var="applyStat" items="${approveDictList}" varStatus="st">
										 			 <c:if test="${applyStat.value=='40' ||  applyStat.value=='50'}">
											 			<option value="${applyStat.value}" <c:if test="${applyStat.value == merchantMargin.approveStat}">selected="selected"</c:if>>${applyStat.name }</option>
											 		</c:if>
										 </c:forEach>
									  </select>
		                       	</div>
		                    </div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="button" class="btn btn-inverse btn-reset">重 置</button>
							</div>
						</div>
				         </br > 
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th width="7%">流水号</th>
				               <th width="7%">商户号</th>
				               <th width="7%">商户名称</th>
				               <th width="7%">押款标志</th>
				               <th width="7%">保证金账户金额</th>
				               <th width="7%">累计充值金额</th>
				               <th width="7%"width="7%">保证金追加金额</th>
				               <th width="7%">状态</th>
				               <th width="7%">押款确认人</th>
				               <th width="7%">押款确认时间</th>
				               <th width="10%">操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.marginListId}</td>
				                    <td>${entity.mchntCode}</td>
				                    <td>${entity.mchntName}</td>
				                    <td><c:if test="${entity.mortgageFlg=='0'}">平台给商户押款</c:if></td>
									<td>${entity.mortgageAmt/100}</td>
									<td>${entity.rechargeAmt/100}</td>
									<td>${entity.addMortgageAmt/100}</td>
									
									<td>
										 <c:forEach var="applyStat" items="${approveDictList}" varStatus="st">
										  <c:if test="${applyStat.value == entity.approveStat}">
										  	${applyStat.name }
										  </c:if>
										 </c:forEach>
									</td>
									<td>${entity.confirmPaymentName}</td>
									<td><fmt:formatDate value="${entity.confirmPaymentTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				                    <td>
				                    	<c:if test="${entity.approveStat=='40' }">
				                    		<sec:authorize access="hasRole('ROLE_CASH_MARGIN_CONFIRMATION_INTOADD')">
												<a marginListId="${entity.marginListId}" title="押款确认" class="btn-mini btn-approve" href="#"><i class="splashy-thumb_up"></i></a>
											</sec:authorize>
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

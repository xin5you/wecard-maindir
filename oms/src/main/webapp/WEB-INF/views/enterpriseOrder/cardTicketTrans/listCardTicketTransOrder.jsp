<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/cardTicketTrans/listCardTicketTransOrder.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/navbar.jsp"%>
    <div id="contentwrapper">
    	<div class="main_content">
    		<nav>
				<div id="jCrumbs" class="breadCrumb module">
					<ul>
						<li><a href="#"><i class="icon-home"></i></a></li>
						<li>交易管理</li>
						<li><a href="${ctx }/cardTicketTrans/listCardTicketTransOrder.do">卡券交易明细</a></li>
						<li>卡券交易订单列表</li>
					</ul>
				</div>
			</nav>
			<form id="searchForm" action="${ctx }/cardTicketTrans/listCardTicketTransOrder.do" class="form-inline" method="post">
				<input type="hidden" id="operStatus" value="${operStatus }"/>
				<h3 class="heading">卡券交易订单列表</h3>
				<div class="row-fluid">
					<div class="span12">
						<div class="input-prepend">
		    				<span class="add-on">订单号</span><input id="orderId" name="orderId" type="text" class="input-medium" value="${cardKeysOrderInf.orderId }" maxlength="22" />
		    			</div>
		    			<div class="input-prepend">
		    				<span class="add-on">用户名</span><input id="personalName" name="personalName" type="text" class="input-medium" value="${cardKeysOrderInf.personalName }" maxlength="22" />
		    			</div>
						<div class="input-prepend">
							<span class="add-on">订单类型</span> 
							<select id="orderType" name="orderType" class="input-135">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${orderTypeList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${dict.code==cardKeysOrderInf.type}">selected="selected"</c:if>>${dict.value }</option>
								</c:forEach>
							</select>
						</div>
						<div class="input-prepend">
							<span class="add-on">订单状态</span> 
							<select id="orderStat" name="orderStat" class="input-135">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${orderStatList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${dict.code==cardKeysOrderInf.stat}">selected="selected"</c:if> >${dict.value }</option>
								</c:forEach>
							</select>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-search"> 查 询 </button>
							<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="input-prepend">
							<span class="add-on">查询记录</span>
							<select name="queryType" id="queryType" class="input-medium" >
								<option value="">请选择</option>
				                <option value="cur" <c:if test="${cardKeysOrderInf.queryType=='cur'}">selected</c:if>>当天记录</option>
				                <option value="his" <c:if test="${cardKeysOrderInf.queryType=='his'}">selected</c:if> >历史记录</option>
		                    </select>
						</div>
						<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                            <span class="add-on">交易开始时间</span>
                            <input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${cardKeysOrderInf.startTime}" />
                            <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                        </div> 
                        <div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                            <span class="add-on">交易结束时间</span>
                            <input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${cardKeysOrderInf.endTime}" />
                            <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                        </div> 
					</div>
				</div>
							
				<br/>       
				<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
					<thead>
						<tr>
							<th>订单号</th>
							<th>用户名</th>
							<th>产品名称</th>
							<th>订单金额(元)</th>
							<th>订单类型</th>
							<th>订单状态</th>
							<th>订单数量</th>
							<th>银行卡号</th>
							<th>交易时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
						<tr>
							<td>${entity.orderId}</td>
							<td>${entity.personalName}</td>
							<td>${entity.productName}</td>
							<td>${entity.amount}</td>
							<td>${entity.type}</td>
							<td>${entity.stat}</td>
							<td>${entity.num}</td>
							<td>${entity.bankNo}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
							<td>
								<c:if test="${entity.orderStat=='31' or entity.orderStat=='21'}">
									<sec:authorize access="hasRole('ROLE_CARD_TICKET_TRANS_ORDER_RESET')">
					 					<a orderId="${entity.orderId}" title="订单重置" class="btn-mini btn-pencil" href="#"><i class="icon-pencil"></i></a>
					 				</sec:authorize>
					 			</c:if>
				 				<sec:authorize access="hasRole('ROLE_CARD_TICKET_TRANS_ORDER_VIEW')">
									<a orderId="${entity.orderId}" title="订单详情" class="btn-mini btn-view" href="#" style="float: right"><i class="icon-search"></i></a>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
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
						<li>卡券交易流水列表</li>
					</ul>
				</div>
			</nav>
			<form id="searchForm" action="${ctx }/cardTicketTrans/intoViewCardTicketTransLog.do" class="form-inline" method="post">
				<input type="hidden" id="operStatus" name="operStatus" value="${operStatus }"/>
				<input type="hidden" id="orderId" name="orderId" value="${orderId }"/>
				<h3 class="heading">卡券交易流水列表</h3>
				<%-- <div class="row-fluid">
					<div class="span12">
						<div class="input-prepend">
		    				<span class="add-on">订单号</span><input id="orderId" name="orderId" type="text" class="input-medium" value="${cardKeysOrderInf.orderId }" maxlength="22" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
		    			</div>
						<div class="input-prepend">
							<span class="add-on">订单类型</span> 
							<select id="orderType" name="orderType" class="input-135">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${orderTypeList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${cardKeysOrderInf.type==dict.code}">selected="selected"</c:if>>${dict.value }</option>
								</c:forEach>
							</select>
						</div>
						<div class="input-prepend">
							<span class="add-on">订单状态</span> 
							<select id="orderStat" name="orderStat" class="input-135">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${orderStatList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${cardKeysOrderInf.stat==dict.code}">selected="selected"</c:if>>${dict.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-search"> 查 询 </button>
						<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="input-prepend">
							<span class="add-on">查询记录</span>
							<select name="queryType" id="queryType" class="input-medium" >
		                     	<option value="cur">当天记录</option>
		                     	<option value="his" <c:if test="${cardKeysOrderInf.queryType=='his'}">selected</c:if> >历史记录</option>
		                    </select>
						</div>
						<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                            <span class="add-on">交易开始时间</span>
                            <input class="input-medium" id="beginTime" name="beginTime" readonly="readonly" type="text" value="${cardKeysOrderInf.startTime}" />
                            <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                        </div> 
                        <div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                            <span class="add-on">交易结束时间</span>
                            <input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${cardKeysOrderInf.endTime}" />
                            <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                        </div> 
					</div>
				</div>
							
				<br/> --%>       
				<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
					<thead>
						<tr>
							<th>交易流水号</th>
							<th>订单号</th>
							<th>交易类型</th>
							<th>卡密</th>
							<th>产品名称</th>
							<th>转入账户(黄牛)</th>
							<th>转出账户(用户名称)</th>
							<th>实际交易金额(元)</th>
							<th>原交易金额(元)</th>
							<th>手续费(%)</th>
							<th>手续费类型</th>
							<th>交易结果</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
						<tr>
							<td>${entity.txnPrimaryKey}</td>
							<td>${entity.orderId}</td>
							<td>${entity.transId}</td>
							<td>${entity.cardKey}</td>
							<td>${entity.productName}</td>
							<td>${entity.tfrInAcctNo}</td>
							<td>${entity.personalName}</td>
							<td>${entity.transAmt}</td>
							<td>${entity.orgTransAmt}</td>
							<td>${entity.transFee}</td>
							<td>${entity.transFeeType}</td>
							<td>${entity.transResult}</td>
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

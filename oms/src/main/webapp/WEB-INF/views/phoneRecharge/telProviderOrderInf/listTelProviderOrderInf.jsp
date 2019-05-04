<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
		<script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
		<script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="${ctx}/resource/js/module/trans/phoneRecharge/listPhoneRecharge.js"></script>
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
		                    <li><a href="${ctx }/provider/providerOrder/listTelProviderOrderInf.do">供应商订单</a></li>
		                     <li>供应商订单列表</li>
		                </ul>
					</div>
				</nav>
				<form id="searchForm" action="${ctx }/provider/providerOrder/listTelProviderOrderInf.do" class="form-inline" method="post">
					<input type="hidden" id="operStatus"  value="${operStatus }"/>
					<h3 class="heading">供应商订单列表</h3>
					<div class="row-fluid">
						<div class="span12">
							<div class="input-prepend">
	           			   	   	<span class="add-on">订单号</span>
	           			   	   	<input id="regOrderId" name="regOrderId" type="text" class="input-medium" value="${telProviderOrderInf.regOrderId }" />
	                       	</div>
	                       	<div class="input-prepend">
	           			   	   	<span class="add-on">渠道订单号</span>
	           			   	   	<input id="channelOrderId" name="channelOrderId" type="text" class="input-medium" value="${telProviderOrderInf.channelOrderId }"/>
	                       	</div>
	
	                       	<div class="input-prepend">
	           			   	   	<span class="add-on">供应商流水号</span>
	           			   	   	<input id="billId" name="billId" type="text" class="input-medium" value="${telProviderOrderInf.billId }" />
	                       	</div>
	                       	<div class="input-prepend">
								<span class="add-on">充值状态</span>
								<select name="rechargeState" id="rechargeState" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="s" items="${rechargeStateList}" varStatus="sta">
			                     		<option value="${s.code}"  <c:if test="${s.code==telProviderOrderInf.rechargeState}">selected</c:if> >${s.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
							</div>
						</div>
					</div>
					</br>
					<table class="table table-bordered dTableR table-hover" id="dt_gal">
						<thead>
							<tr>
								<th>订单号</th>
				                <th>渠道订单号</th>
				               	<th>订单金额</th>
				               	<th>支付状态</th>
				                <th>充值状态</th>
				                <th>成本价</th>
				                <th>撤销原因</th>
				                <th>供应商流水号</th>
				                <th>所属供应商</th>
				                <th>处理次数</th>
				                <th>订单处理时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
								<tr>
				                 	<td>${entity.regOrderId}</td>
				                 	<td>${entity.channelOrderId}</td>
									<td>${entity.regOrderAmt}</td>
				                    <td>${entity.payState}</td>
				                    <td>${entity.rechargeState}</td>
				                    <td>${entity.transCost}</td>
				                    <td>${entity.revokeMessage}</td>
				                    <td>${entity.billId}</td>
				                    <td>${entity.providerId}</td>
				                    <td>${entity.operNum}</td>
				                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.operateTime}"/></td>
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

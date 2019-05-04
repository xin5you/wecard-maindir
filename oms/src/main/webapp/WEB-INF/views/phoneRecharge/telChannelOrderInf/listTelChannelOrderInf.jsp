
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelOrderInf/listTelChannelOrderInf.js"></script>
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
			                    <li><a href="${ctx }/channel/channelOrder/listTelChannelOrderInf.do">分销商订单</a></li>
			                     <li>分销商订单列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/channelOrder/listTelChannelOrderInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">分销商订单列表</h3>
						<div class="row-fluid" id="h_search">
							<div class="span12">
								<div class="input-prepend">
		           			   	   	<span class="add-on">订单号</span>
		           			   	   	<input id="channelOrderId" name="channelOrderId" type="text" class="input-medium" value="${telChannelOrderInf.channelOrderId }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">外部订单号</span>
		           			   	   	<input id="outerTid" name="outerTid" type="text" class="input-medium" value="${telChannelOrderInf.outerTid }"/>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">分销商名称</span>
		           			   	   	<input id="channelName" name="channelName" type="text" class="input-medium" value="${telChannelOrderInf.channelName }" />
		                       	</div>
		                       	<div class="input-prepend">
									<span class="add-on">充值类型</span>
									<select name="rechargeType" id="rechargeType" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${rechargeTypeList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telChannelOrderInf.rechargeType}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
				                    </select>
								</div>
								<div class="input-prepend">
									<span class="add-on">订单状态</span>
									<select name="orderStat" id="orderStat" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${channelOrderStatList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telChannelOrderInf.orderStat}">selected</c:if> >${s.value }</option>
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
									<span class="add-on">通知状态</span>
									<select name="notifyStat" id="notifyStat" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${channelOrderNotifyStatList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telChannelOrderInf.notifyStat}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
				                    </select>
								</div>	
							</div>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				             	<th>订单号</th>
				                <th>分销商名称</th>
				                <th>外部订单号</th>
				                <th>充值手机</th>
				                <th>充值类型</th>
				                <th>充值面额</th>
				                <th>支付金额</th>
				                <th>订单状态</th>
				                <th>通知状态</th>
				                <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.channelOrderId}</td>
				                 	<td>${entity.channelName}</td>
									<td>${entity.outerTid}</td>
									<td>${entity.rechargePhone}</td>
				                    <td>${entity.rechargeType}</td>
				                    <td>${entity.rechargeValue}</td>
				                    <td>${entity.payAmt}</td>
				                    <td>${entity.orderStat}</td>
				                    <td>${entity.notifyStat}</td>
				                    <td>
<%-- 				                    <c:if test="${entity.notifyStat=='处理失败' }"> --%>
				                    <sec:authorize access="hasRole('ROLE_CALL_BACK_NOTIFYC_HANNEL')">
									<a channelOrderId="${entity.channelOrderId}" title="回调通知分销商" class="btn-mini btn-again-submit" href="#"><i class="icon-ok"></i></a>
									</sec:authorize>
<%-- 									</c:if> --%>
<%-- 									<sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_DELETE')"> --%>
<%-- 									<a eShopId="${entity.channelId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a> --%>
<%-- 									</sec:authorize> --%>
<%-- 									<sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_VIEW')"> --%>
<%-- 									<a eShopId="${entity.channelId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a> --%>
<%-- 									</sec:authorize> --%>
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
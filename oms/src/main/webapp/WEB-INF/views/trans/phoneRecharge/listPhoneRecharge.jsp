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
							<li>交易管理</li>
							<li><a href="${ctx }/phone/phoneRecharge/getPhoneRechargeList.do">手机充值交易明细</a></li>
							<li>手机充值交易明细列表</li>
						</ul>
					</div>
				</nav>
				<form id="searchForm" action="${ctx }/phone/phoneRecharge/getPhoneRechargeList.do" class="form-inline" method="post">
					<h3 class="heading">手机充值交易明细列表</h3>
					<div class="row-fluid">
						<div class="span12">
							<div class="input-prepend">
								<span class="add-on">订单号</span>
								<input id="rId" name="rId" type="text" class="input-small" value="${phoneRechargeOrder.rId }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">供应商订单号</span>
								<input id="supplierOrderNo" name="supplierOrderNo" type="text" class="input-medium" value="${phoneRechargeOrder.supplierOrderNo }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">交易流水号</span>
								<input id="channelOrderNo" name="channelOrderNo" type="text" class="input-medium" value="${phoneRechargeOrder.channelOrderNo }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">用户名</span>
								<input id="personalName" name="personalName" type="text" class="input-small" value="${phoneRechargeOrder.personalName }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">会员手机号</span>
								<input id="mobilePhoneNo" name="mobilePhoneNo" type="text" class="input-small" value="${phoneRechargeOrder.mobilePhoneNo }" />
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search">查 询</button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<button type="button" class="btn btn-primary btn-upload">导出表格</button>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span12">
							<div class="input-prepend">
								<span class="add-on">手机充值号码</span>
								<input id="phone" name="phone" type="text" class="input-small" value="${phoneRechargeOrder.phone }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">供应商</span>
								<select name="supplier" id="supplier" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="s" items="${supplierList}" varStatus="sta">
			                     		<option value="${s.code}"  <c:if test="${s.code==phoneRechargeOrder.supplier}">selected</c:if> >${s.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>	
							<div class="input-prepend">
								<span class="add-on">交易状态</span>
								<select name="transStat" id="transStat" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="tt" items="${transStatList}" varStatus="sta">
			                     		<option value="${tt.code}" <c:if test="${tt.code==phoneRechargeOrder.transStat}">selected</c:if> >${tt.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>
							<div class="input-prepend">
								<span class="add-on">订单类型</span>
								<select name="orderType" id="orderType" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="o" items="${orderTypeList}" varStatus="sta">
			                     		<option value="${o.code}" <c:if test="${o.code==phoneRechargeOrder.orderType}">selected</c:if> >${o.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>
							<div class="input-prepend">
								<span class="add-on">支付渠道</span>
								<select name="reqChannel" id="reqChannel" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="r" items="${reqChannelList}" varStatus="sta">
			                     		<option value="${r.code}" <c:if test="${r.code==phoneRechargeOrder.reqChannel}">selected</c:if> >${r.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span12">
							<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">交易开始时间</span>
                             	<input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${phoneRechargeOrder.startTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div> 
                         	<div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">交易结束时间</span>
                             	<input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${phoneRechargeOrder.endTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div>
						</div>
					</div>	
					</br>
					<table class="table table-bordered dTableR table-hover" id="dt_gal">
						<thead>
							<tr>
								<th width="5%">订单号</th>
								<th width="9%">供应商订单号</th>
								<th width="9%">交易流水号</th>
								<th width="6%">用户名</th>
								<th width="6%">会员手机号</th>
								<th width="6%">供应商</th>
								<th width="6%">手机充值号码</th>
								<th width="6%">充值金额（元）</th>
								<th width="6%">交易金额（元）</th>
								<th width="6%">优惠金额（元）</th>
								<th width="6%">供应商价格（元）</th>
								<th width="6%">流量面额（元）</th>
								<th width="7%">交易状态</th>
								<th width="6%">订单类型</th>
								<th width="7%">支付渠道</th>
								<th width="6%">交易时间</th>
								<th width="6%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
								<tr>
									<td>${entity.rId}</td>
									<td>${entity.supplierOrderNo}</td>
									<td>${entity.channelOrderNo}</td>
									<td>${entity.personalName}</td>
									<td>${entity.mobilePhoneNo}</td>
									<td>${entity.supplier}</td>
									<td>${entity.phone}</td>
									<td>${entity.telephoneFace}</td>
									<td>${entity.transAmt}</td>
									<td>${entity.discountsAmt}</td>
									<td>${entity.supplierAmt}</td>
									<td>${entity.fluxFace}</td>
									<td>${entity.transStat}</td>
									<td>${entity.orderType}</td>
									<td>${entity.reqChannel}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
									<td>
<%-- 										<sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_DELETE')"> --%>
											<c:if test="${entity.reqChannel == '福利余额' && entity.transStat =='充值失败' || entity.transStat =='退款失败'}">
												<a rId="${entity.rId}"  title="退款" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
											</c:if>
<%-- 										</sec:authorize> --%>
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

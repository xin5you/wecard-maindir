<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
		<script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
		<script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="${ctx}/resource/js/module/merchant/trans/listTrans.js"></script>
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
							<li><a href="${ctx }/merchant/transInf/listTrans.do">商户交易明细</a></li>
							<li>商户交易明细列表</li>
						</ul>
					</div>
				</nav>
				<form id="searchForm" action="${ctx }/merchant/transInf/listTrans.do" class="form-inline" method="post">
					<h3 class="heading">商户交易明细列表</h3>
					<div class="row-fluid">
						<div class="span12">
							<div class="input-prepend">
								<span class="add-on">商户名称</span>
								<input id="mchntName" name="mchntName" type="text" class="input-medium" value="${transInf.mchntName }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">商户号</span>
								<input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${transInf.mchntCode }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">门店名称</span>
								<input id="shopName" name="shopName" type="text" class="input-medium" value="${transInf.shopName }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">门店号</span>
								<input id="shopCode" name="shopCode" type="text" class="input-medium" value="${transInf.shopCode }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">用户名</span>
								<input id="userName" name="userName" type="text" class="input-small" value="${transInf.userName }" />
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
								<span class="add-on">渠道号</span>
								<select name="channelCode" id="channelCode" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="ccl" items="${ChannelCodeList}" varStatus="sta">
			                     		<option value="${ccl.code}"  <c:if test="${ccl.code==transInf.omschannelCode}">selected</c:if> >${ccl.name }</option>
			                     	</c:forEach>
			                    </select>
							</div>	
							<div class="input-prepend">
								<span class="add-on">交易类型</span>
								<select name="transType" id="transType" class="input-medium">
			                     	<option value="">--全部--</option>
			                     	<c:forEach var="tt" items="${transTypeList}" varStatus="sta">
			                     		<option value="${tt.code}" <c:if test="${tt.code==transInf.transType}">selected</c:if> >${tt.value }</option>
			                     	</c:forEach>
			                    </select>
							</div>
							<div class="input-prepend">
								<span class="add-on">查询记录</span>
								<select name="queryType" id="queryType" class="input-medium" >
			                     	<option value="cur">当天记录</option>
			                     	<option value="his" <c:if test="${transInf.queryType=='his'}">selected</c:if> >历史记录</option>
			                    </select>
							</div>
							<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">交易开始时间</span>
                             	<input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${transInf.startTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div> 
                         	<div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">交易结束时间</span>
                             	<input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${transInf.endTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div> 
						</div>
					</div>	
					</br>
					<table class="table table-bordered dTableR table-hover" id="dt_gal">
						<thead>
							<tr>
								<th width="9%">流水号</th>
								<th width="7%">清算日</th>
								<th width="7%">渠道</th>
								<th width="6%">用户名</th>
								<th width="9%">卡号</th>
								<th width="9%">账户号</th>
								<th width="9%">商户名称</th>
								<th width="6%">门店名称</th>
								<th width="9%">交易类型</th>
								<th width="7%">交易金额</th>
								<th width="7%">交易状态</th>
								<th width="6%">交易时间</th>
								<th width="8%">参考号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
								<tr>
									<td>${entity.dmsRelatedKey}</td>
									<td>${entity.settleDate}</td>
									<td>
									${entity.chnlName }
									</td>
									<td>${entity.personName}</td>
									<td>${entity.cardNo}</td>
									<td>${entity.priAcctNo}</td>
									<td>${entity.mchntName}</td>
									<td>${entity.shopName}</td>
									<td>${entity.transId}</td>
									<td>${entity.transAmt}</td>
									<td>${entity.respCode}</td>
									<td>${entity.transTime}</td>
									<td>${entity.referenceNo}</td>
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

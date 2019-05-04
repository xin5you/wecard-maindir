<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<%@ include file="/WEB-INF/views/common/init.jsp"%>
	<%@ include file="/WEB-INF/views/common/head.jsp"%>
	</head>
	<body>
		<%@ include file="/WEB-INF/views/common/navbar.jsp"%>
		<!-- main content -->
		<div id="contentwrapper">
			<div class="main_content">
				<div class="row-fluid">
					<div class="span12">
						<form id="mainForm" action="#" class="form-horizontal form_validation_tip" method="post">
							<input type="hidden" name="marginListId" id="marginListId" value="${merchantMargin.marginListId }">
							<h3 class="heading">商户保证金-详细</h3>
							<div class="control-group">
								<label class="control-label">商户名称</label>
								<div class="controls">
									<input type="text" class="span6" id="mchntName" name="mchntName" disabled value="${merchantInf.mchntName }" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">商户号</label>
								<div class="controls">
									<input type="text" class="span6" id="mchntCode" name="mchntCode" disabled value="${merchantInf.mchntCode }" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">押款标志</label>
								<div class="controls">
									<select id="mortgageFlg" name="mortgageFlg" class="input-135" disabled>
										<c:forEach var="dict" items="${dictList}" varStatus="st">
											<option value="${dict.value}"
												<c:if test="${merchantMargin.mortgageFlg==dict.value}">selected="selected"</c:if>>${ dict.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">保证金账户金额</label>
								<div class="controls">
									<input type="text" class="span6" id="mortgageAmt" name="mortgageAmt" value="${merchantMargin.mortgageAmt/100}" disabled />
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label">获取总额度</label>
								<div class="controls">
									<input type="text" class="span6" id="getQuota" name="getQuota" value="${merchantMargin.getQuota/100}" disabled />
	
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">累计充值金额</label>
								<div class="controls">
									<input type="text" class="span6" id="rechargeAmt" name="rechargeAmt" value="${merchantMargin.rechargeAmt/100}" disabled />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">累计充值面额</label>
								<div class="controls">
									<input type="text" class="span6" id="rechargeFaceAmt" name="rechargeFaceAmt" value="${merchantMargin.rechargeFaceAmt/100}" disabled />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">保证金追加金额</label>
								<div class="controls">
									<input type="text" class="span6" id="addMortgageAmt" name="addMortgageAmt" value="${merchantMargin.addMortgageAmt/100 }" disabled />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">追加获取总额度</label>
								<div class="controls">
									<input type="text" class="span6" id="addGetQuota" name="addGetQuota" value="${merchantMargin.addGetQuota/100 }" disabled />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">申请人</label>
								<div class="controls">
									<input type="text" class="span6" id="applyUserName" name="applyUserName" value="${merchantMargin.applyUserName }" disabled />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">申请时间</label>
								<div class="controls">
									<td><input type="text" class="span6" id="applyTime" name="applyTime" value="<fmt:formatDate value='${merchantMargin.applyTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled /></td>
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label">审批人</label>
								<div class="controls">
									<td><input type="text" class="span6" id="approveName" name="approveName" value="${merchantMargin.approveName }" disabled /></td>
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label">审批时间</label>
								<div class="controls">
									<input type="text" class="span6" id="approveTime" name="approveTime" value="<fmt:formatDate value='${merchantMargin.approveTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label">押款确认人</label>
								<div class="controls">
									<td><input type="text" class="span6" id="confirmPaymentName" name="confirmPaymentName" value="${merchantMargin.confirmPaymentName }" disabled /></td>
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label">押款确认时间</label>
								<div class="controls">
									<input type="text" class="span6" id="confirmPaymentTime" name="confirmPaymentTime" value="<fmt:formatDate value='${merchantMargin.confirmPaymentTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled />
								</div>
							</div>
	
							<div class="control-group">
								<label class="control-label">审批状态</label>
								<div class="controls">
									<td>
										<select id="approveStat" name="approveStat" class="input-135" disabled>
											<c:forEach var="applyStat" items="${approveStatList}" varStatus="st">
												<option value="${applyStat.value}" <c:if test="${applyStat.value == merchantMargin.approveStat}">selected="selected"</c:if>>${applyStat.name }</option>
											</c:forEach>
										</select>
									</td>
								</div>
							</div>
	
							<div class="control-group ">
								<div class="controls">
									<a href="#" class="btn btn-inverse btn-primary" onClick="javascript:history.back(-1);">返 回</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/margin/merchantCashManage/editMerchantMargin.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
          <!-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>保证金信息</li>
			                    <li><a href="${ctx }/margin/mchntCashManage/listMerchantMargin.do?chashId=${merchantMargin.chashId}">保证金流水列表</a></li>
			                     <li>商户追加保证金-新增</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip" method="post">
								<input type="hidden" name="marginListId" id="marginListId" value="${merchantMargin.marginListId }">
								<input type="hidden" name="chashId" id="chashId" value="${merchantMargin.chashId }">
								 <h3 class="heading">商户保证金追加金额-编辑</h3>
				                 	   <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							             	<input type="text" class="span6" id="mchntName" name="mchntName" disabled="true" value="${merchantInf.mchntName }"/>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">商户号</label>
							             <div class="controls">
							                  <input type="text" class="span6" id="mchntCode" name="mchntCode" disabled="true" value="${merchantInf.mchntCode }"/>
							             </div>
							     		</div>
<!-- 							     		<div class="control-group"> -->
<!-- 							             <label class="control-label">审核状态</label> -->
<!-- 							             <div class="controls"> -->
<!-- 							                  <select id="applyStat" name="applyStat" class="input-135"> -->
<%-- 												 <c:forEach var="applyStat" items="${applyStatList}" varStatus="st"> --%>
<%-- 												 			 <c:if test="${applyStat.value=='10' || applyStat.value=='20'}"> --%>
<%-- 													 			<option value="${applyStat.value}">${applyStat.name }</option> --%>
<%-- 													 		</c:if> --%>
<%-- 												 </c:forEach> --%>
<!-- 											  </select> -->
<!-- 							             </div> -->
<!-- 							     		</div> -->
							     		<div class="control-group">
							             <label class="control-label">押款标志</label>
							             <div class="controls">
							                  <select id="mortgageFlg" name="mortgageFlg" class="input-135" disabled="true">
												 <c:forEach var="dict" items="${dictList}" varStatus="st">
													 		<option value="${dict.value}" <c:if test="${merchantMargin.mortgageFlg==dict.value}">selected="selected"</c:if>>${ dict.name }</option>
												 </c:forEach>
											  </select>
							             </div>
							     		</div>
			       						<div class="control-group">
							             <label class="control-label">保证金账户金额</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mortgageAmt" name="mortgageAmt" value="${merchantMargin.mortgageAmt/100}" disabled="true"/>
							             </div>
							     		</div>
	
							     		<div class="control-group">
							             <label class="control-label">获取总额度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="getQuota" name="getQuota"  value="${merchantMargin.getQuota/100}" disabled="true"/>
							                 
							             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">累计充值金额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="rechargeAmt" name="rechargeAmt" value="${merchantMargin.rechargeAmt/100}" disabled="true"/>
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">累计充值面额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="rechargeFaceAmt" name="rechargeFaceAmt" value="${merchantMargin.rechargeFaceAmt/100}" disabled="true"/>
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">保证金追加金额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="addMortgageAmt" name="addMortgageAmt" value="${merchantMargin.addMortgageAmt/100 }" />
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">追加获取总额度</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="addGetQuota" name="addGetQuota"  value="${merchantMargin.addGetQuota/100 }"/>
								                 <span class="help-block">例如：当前追加金额是10000元,商户给12000的额度，当前输入值是12000</span>
								             </div>
							     		</div>
								       <div class="control-group ">
					                            <div class="controls">
					                            	<button class="btn btn-primary" type="button" id="submitApplyBtn" >提交审批</button>
					                                <button class="btn btn-primary" type="button" id="addSaveBtn" >保 存</button>
					                                <button class="btn btn-inverse btn-reset" type="button" id="backBtn">返 回</button>
					                            </div>
					                  	</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
    <script src="${ctx}/resource/js/module/margin/merchantCashManage/addMerchantCashManage.js"></script>
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
			                    <li><a href="${ctx }/margin/mchntCashManage/listMerchantCashManage.do">保证金管理</a></li>
			                     <li>商户保证金新增</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx }/margin/mchntCashManage/addMerchantCashManageCommit.do" class="form-horizontal form_validation_tip" method="post">
								 <h3 class="heading">商户保证金新增</h3>
				                 	   <div class="control-group">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntId" id="mercahnt_select" data-placeholder="请点击选择商户" class="chzn_a span6">
												<option value=""></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntId}">${rs.mchntName } (${rs.mchntCode })</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">押款标志</label>
							             <div class="controls">
							                  <select id="mortgageFlg" name="mortgageFlg" class="input-135">
												 <c:forEach var="dict" items="${dictList}" varStatus="st">
													 		<option value="${dict.value}">${ dict.name }</option>
												 </c:forEach>
											  </select>
							             </div>
							     		</div>
							       		<div class="control-group">
							             <label class="control-label">保证金账户金额</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mortgageAmt" name="mortgageAmt" disabled="true"/>
							                 
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">获取总额度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="getQuota" name="getQuota" disabled="true"/>
							                 
							             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">累计充值金额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="rechargeAmt" name="rechargeAmt" disabled="true"/>
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">累计充值面额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="rechargeFeecAmt" name="rechargeFeecAmt" disabled="true"/>
								             </div>
							     		</div>
								       <div class="control-group ">
					                            <div class="controls">
					                            	<sec:authorize access="hasRole('ROLE_CASH_MARGIN_MANAGE_ADDCOMMIT')">
					                                <button class="btn btn-primary" type="button" id="addSubmitBtn" >保存</button>
					                                </sec:authorize>
					                                <button class="btn btn-inverse btn-reset" type="reset" id="resetBtn">重 置</button>
					                            </div>
					                  	</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
<script type="text/javascript">
$(function(){
    $('#mercahnt_select').chosen();
});
</script>
</html>
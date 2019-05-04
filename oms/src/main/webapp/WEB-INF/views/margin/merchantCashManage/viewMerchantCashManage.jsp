<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
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
								 <h3 class="heading">商户保证金-明细</h3>
				                 	   <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							               		 <input type="text" class="span6" id="mchntName" name="mchntName" value="${merchantInf.mchntName }" disabled="true"/>
							             </div>
							     		</div>
							     		
							     		   <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							               		 <input type="text" class="span6" id="mchntCode" name="mchntCode" value="${merchantInf.mchntCode }" disabled="true"/>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">押款标志</label>
							             <div class="controls">
							                  <select id="mortgageFlg" name="mortgageFlg" class="input-135" disabled="true">
												 <c:forEach var="dict" items="${dictList}" varStatus="st">
													 		<option value="${dict.value}" <c:if test="${merchantCashManage.mortgageFlg==dict.value}">selected="selected"</c:if>>${ dict.name }</option>
												 </c:forEach>
											  </select>
							             </div>
							     		</div>
							       		<div class="control-group">
							             <label class="control-label">保证金账户金额</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mortgageAmt" name="mortgageAmt" value="${merchantCashManage.mortgageAmt/100 }" disabled="true"/>
							             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">累计销售额度</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="rechargeAmt" name="rechargeAmt" value="${merchantCashManage.rechargeAmt/100 }"  disabled="true"/>
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">保证金追加金额</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="mortgageAmt" name="mortgageAmt" value="${merchantCashManage.mortgageAmt/100 }" disabled="true"/>
								             </div>
							     		</div>
							     		<div class="control-group">
								             <label class="control-label">追加获取总额度</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="getQuota" name="getQuota"  value="${merchantMargin.getQuota/100 }" disabled="true"/>
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
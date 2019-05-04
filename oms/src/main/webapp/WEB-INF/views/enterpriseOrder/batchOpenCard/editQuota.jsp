<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchOpenCard/editQuota.js"></script>
    <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>订单管理</li>
			                    <li><a href="${ctx }/enterpriseOrder/batchOpenCard/listOpenCard.do">批量开卡</a></li>
			                     <li>编辑账户限额</li>
			                </ul>
			            </div>
			        </nav>
					<form id="pageMainForm" action="" class="form-inline form_validation_tip" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<input type="hidden" id="orderId"  value="${orderId }"/>
						<h3 class="heading">编辑账户限额</h3>
						
						<div class="row-fluid" >
							 <div class="span12">
		                       	
		                       	<div class="control-group formSep">
		           			   	   	<span class="add-on">网上交易限额：</span><input id="maxQuota" name="maxQuota" type="text" class="input-medium" value="" maxlength="10" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" />&nbsp;
		                       	</div>
							
						  </div>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				               <th>序号</th>
				               <th>姓名</th>
				               <th>身份证号码</th>
				               <th>手机号</th>
				               <th>状态</th>
				               <th>备注</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${st.index+1 }</td>
                                    <td>${entity.userName}</td>
                                    <td>${entity.userCardNo}</td>
                                    <td>${entity.phoneNo}</td>
                                    <td>${entity.orderStat}</td>
                                    <td>${entity.remarks}</td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
				      
				      <br/>
				      <sec:authorize access="hasRole('ROLE_BATCH_QUOTA_COMMIT')">
				      <button class="btn btn-primary btn-sub" type="submit">保存</button> 
				      </sec:authorize>
                       <a href="${ctx }/enterpriseOrder/batchOpenCard/listOpenCard.do"><button class="btn btn-primary" type="button">返回</button></a>
			   	</form>
			   </div>
	    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/statement/financingStatement/listFinancingSummarizing.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>报表管理</li>
			                    <li>财务报表</li>
			                    <li><a href="${ctx }/statement/financingStatement/listFinancingSummarizing.do">财务结算汇总列表</a></li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/statement/financingStatement/listFinancingSummarizing.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">财务结算汇总列表</h3>
						
						<div class="row-fluid">
                            <div class="span12">
                                <div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                                    <span class="add-on">开始时间</span>
                                    <input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${condition.startTime }" />
                                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                                </div> 
                                <div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                                    <span class="add-on">结束时间</span>
                                    <input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${condition.endTime }" />
                                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                                </div>
							 
							<div class="pull-right">
								
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<button type="button" class="btn btn-primary btn-upload">导出表格</button>
							</div>
						  </div>
						</div>
					<br/>    
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				               <th rowspan="2" valign="center">商户名称</th>
				               <th colspan="2">快捷消费金额</th>
				               <th colspan="3">会员卡充值金额</th>
				               <th rowspan="2">平台补贴金额</th>
				               <th rowspan="2">服务费</th>
				               <th rowspan="2">结算金额</th>
				             </tr>
				             <tr>
                               <th>知了企服</th>
                               <th>嘉福平台</th>
                               <th>知了企服</th>
                               <th>嘉福平台</th>
                               <th>平台充值</th>
                             </tr>
				             </thead>
				             <tbody>
					             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
						             <tr>
		                               <td>${entity.mchntName }</td>
		                               <td>${entity.wxConsumeAmt }</td>
		                               <td>${entity.jfConsumeAmt }</td>
		                               <td>${entity.wxRechargeUploadAmt }</td>
	                                   <td>${entity.jfRechargeUploadAmt }</td>
	                                   <td>${entity.ptRechargeUploadAmt }</td>
	                                   <td>${entity.ptSubsidyAmt }</td>
	                                   <td>${entity.serviceCharge }</td>
	                                   <td>${entity.settleAmt }</td>
		                             </tr>
	                             </c:forEach>
				             </tbody>
				         </table>
				         <c:if test="${pageInfo.list!=null}">
                         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
                         </c:if>
				      </form>
			   </div>
	    </div>
</body>
</html>

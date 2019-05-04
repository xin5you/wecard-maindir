<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/statement/operationStatement/listOperationDetail.js"></script>
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
			                    <li>运营报表</li>
			                    <li><a href="${ctx }/statement/operationStatement/listOperationDetail.do">运营明细列表</a></li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/statement/operationStatement/listOperationDetail.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">运营明细列表</h3>
						
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
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal"  style="font-size: 12px;">
				             <thead>
				             <tr>
				               <th rowspan="2" valign="center">商户名称</th>
				               <th colspan="2">会员卡消费</th>
				               <th colspan="4">快捷消费</th>
				               <th colspan="9">会员卡充值</th>
				               <th rowspan="2">会员卡余额</th>
				             </tr>
				             <tr>
				                <th>消费总额</th>
                               <th>消费笔数</th>
                               <th>微信<br/>消费金额</th>
                               <th>微信<br/>消费笔数</th>
                               <th>嘉福平台<br/>消费金额</th>
                               <th>嘉福平台<br/>消费笔数</th>
                               <th>微信<br/>充值金额</th>
                               <th>微信<br/>充值面额</th>
                               <th>微信<br/>充值笔数</th>
                               <th>嘉福平台<br/>充值金额</th>
                               <th>嘉福平台<br/>充值面额</th>
                               <th>嘉福平台<br/>充值笔数</th>
                               <th>平台<br/>充值金额</th>
                               <th>平台<br/>充值面额</th>
                               <th>平台<br/>充值笔数</th>
                             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
					             <tr>
	                               <td>${entity.mchntName }</td>
	                               <td>${entity.memberCardConsumeAmt }</td>
	                               <td>${entity.memberCardConsumeCount }</td>
	                               <td>${entity.wxConsumeAmt }</td>
                                   <td>${entity.wxConsumeCount }</td>
                                   <td>${entity.jfConsumeAmt }</td>
                                   <td>${entity.jfConsumeCount }</td>
                                   <td>${entity.wxRechargeUploadAmt }</td>
                                   <td>${entity.wxRechargeTransAmt }</td>
                                   <td>${entity.wxRechargeCount }</td>
                                   <td>${entity.jfRechargeUploadAmt }</td>
                                   <td>${entity.jfRechargeTransAmt }</td>
                                   <td>${entity.jfRechargeCount }</td>
                                   <td>${entity.ptRechargeUploadAmt }</td>
                                   <td>${entity.ptRechargeTransAmt }</td>
                                   <td>${entity.ptRechargeCount }</td>
                                   <td>${entity.memberCardBal }</td>
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

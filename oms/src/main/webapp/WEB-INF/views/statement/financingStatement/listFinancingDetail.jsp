<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/statement/financingStatement/listFinancingDetail.js"></script>
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
			                    <li><a href="${ctx }/statement/financingStatement/listFinancingDetail.do">财务结算明细列表</a></li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/statement/financingStatement/listFinancingDetail.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">财务结算明细列表</h3>
						
						<div class="row-fluid" >
                             <div class="span12">
                                <div class="input-prepend">
                                    <span class="add-on">选择商户:</span>
                                    <select name="mchntCode" id="mercahnt_select" class="input-medium">
                                    <option value="">--请选择--</option>
                                        <c:forEach var="rs" items="${mchntList}" varStatus="st">
                                                <option value="${rs.mchntCode}"  <c:if test="${rs.mchntCode==condition.mchntCode}">selected</c:if> >${rs.mchntName} </option>
                                         </c:forEach>
                                    </select>
                                </div>
                                
                                <div class="input-prepend">
                                    <span class="add-on">选择门店:</span>
                                    <select name="shopCode" id="shopCode" class="input-medium">
                                    <c:if test="${condition.mchntCode!=null }">
                                         <c:forEach var="shop" items="${shopInfList}" varStatus="st">
                                                <option value="${shop.shopCode}"  <c:if test="${shop.shopCode==condition.shopCode}">selected</c:if> >${shop.shopName} </option>
                                         </c:forEach>
                                    </c:if>
                                    </select>
                                </div>
                             
                            <div class="pull-right">
                                
                                <button type="submit" class="btn btn-search"> 查 询 </button>
                                <button type="reset" class="btn btn-inverse btn-reset">重 置</button>
                                <button type="button" class="btn btn-primary btn-upload">导出表格</button>
                            </div>
                          </div>
                        </div>
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
                            </div>
                          </div>
                         </br >
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
                               <th>流水号</th>
                               <th>外部流水号</th>
                               <th>清算日期</th>
                               <th>卡号</th>
                               <th>账户号</th>
                               <th>交易金额</th>
                               <th>交易类型</th>
                               <th>交易时间</th>
                             </tr>
				             </thead>
				             <tbody>
					             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
                                     <tr>
                                       <td>${entity.dmsRelatedKey }</td>
                                       <td>${entity.orgDmsRelatedKey }</td>
                                       <td>${entity.settleDate }</td>
                                       <td>${entity.cardNo }</td>
                                       <td>${entity.priAcctNo }</td>
                                       <td>${entity.transAmt }</td>
                                       <td>${entity.transType }</td>
                                       <td>${entity.transTime }</td>
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

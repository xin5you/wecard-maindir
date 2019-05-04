<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/statement/merchantStatement/listMerchantDetail.js"></script>
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
			                    <li>商户报表</li>
			                    <li><a href="${ctx }/statement/merchantStatement/listMerchantDetail.do">门店数据统计列表</a></li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/statement/merchantStatement/listMerchantDetail.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">门店数据统计列表</h3>
						
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
				               <th rowspan="2" valign="center">档口名</th>
				               <th colspan="2">会员卡</th>
				               <th colspan="2">快捷消费</th>
				               <th rowspan="2">总消费额</th>
				             </tr>
				             <tr>
                               <th>消费总额</th>
                               <th>消费笔数</th>
                               <th>消费总额</th>
                               <th>消费笔数</th>
                             </tr>
				             </thead>
				             <tbody>
					             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
						             <tr>
		                               <td>${entity.shopName }</td>
		                               <td>${entity.memberCardConsumeAmt }</td>
		                               <td>${entity.memberCardConsumeCount }</td>
		                               <td>${entity.speedyConsumeAmt }</td>
	                                   <td>${entity.speedyConsumeCount }</td>
	                                   <td>${entity.consumeAmt }</td>
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

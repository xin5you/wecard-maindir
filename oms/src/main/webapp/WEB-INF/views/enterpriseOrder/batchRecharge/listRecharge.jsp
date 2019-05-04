<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchRecharge/listRecharge.js"></script>
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
			                    <li><a href="${ctx }/enterpriseOrder/batchRecharge/listRecharge.do">批量充值</a></li>
			                     <li>批量充值列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/enterpriseOrder/batchRecharge/listRecharge.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">批量充值列表</h3>
						
						<div class="row-fluid" >
							 <div class="span12">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">订单号:</span><input id="orderId" name="orderId" type="text" class="input-medium" value="${order.orderId }"  maxlength="22" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">订单名称:</span><input id="orderName" name="orderName" type="text" class="input-medium" value="${order.orderName }"/>
		                       	</div>
		
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">订单状态:</span>
		           			   	   	<select name="orderStat" id="orderStat" class="input-medium">
                                    <option value="">--请选择--</option>
                                    <c:forEach var="mapStat" items="${mapOrderStat}" varStatus="sta">
                                        <option value="${mapStat.key}"  <c:if test="${mapStat.key==order.orderStat}">selected</c:if>   >${mapStat.value }</option>
                                    </c:forEach>
                                    </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">开票状态:</span>
		           			   	   	<select name="invoiceStat" id="invoiceStat" class="input-medium">
                                    <option value="100">--请选择--</option>
                                    <option value="0" <c:if test="${order.invoiceStat == '0'}">selected</c:if> >可开票</option>
                    				<option value="1" <c:if test="${order.invoiceStat=='1'}">selected</c:if> >已开票</option>
                    				<option value="2" <c:if test="${order.invoiceStat=='2'}">selected</c:if> >不可开票</option>
                                    </select>
		                       	</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增</button>
								</sec:authorize>
							</div>
						  </div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<div class="input-prepend">
		           			   	   	<span class="add-on">业务类型:</span>
		           			   	   	<select name="bizType" id="bizType" class="input-medium">
                                    <option value="">--请选择--</option>
                                    <c:forEach var="rechaege" items="${rechargeTypeList}" varStatus="sta">
										<option value="${rechaege.code}"   >${rechaege.type}</option>
									</c:forEach>
                                    </select>
		                       	</div>
								<div class="input-prepend">
                                    <span class="add-on">充值产品</span>
                                    <select name="productCode" id="productCode" class="input-medium" style="width: 180px">
                                       <option value="">--请选择--</option>
                                       <c:forEach var="product" items="${productList}" varStatus="sta">
                                           <option value="${product.productCode}"  <c:if test="${product.productCode==order.productCode}">selected</c:if>  >${product.productName}</option>
                                       </c:forEach>
                                       </select>
                                </div>
	                            <div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
	                                <span class="add-on">开始时间</span>
	                                <input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${order.startTime }" />
	                                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
	                            </div> 
	                            <div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
	                                <span class="add-on">结束时间</span>
	                                <input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${order.endTime }" />
	                                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
	                            </div>
	                        </div>
                          </div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				               <th>订单号码</th>
				               <th>订单名称</th>
				               <th>业务类型</th>
				               <th>订单数量</th>
				               <th>订单总金额</th>
				               <th>订单状态</th>
				               <th>创建人</th>
				               <th>创建时间</th>
                               <th>修改人</th>
                               <th>修改时间</th>
                               <th>开票状态</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
                                 <tr>
                                    <td>${entity.orderId }</td>
                                    <td>${entity.orderName }</td>
                                    <td>${entity.bizType }</td>
                                    <td>${entity.orderCount }</td>
                                    <td>${entity.sumAmount }</td>
                                    <td>${entity.orderStat }</td>
                                    <td>${entity.createUser }</td>
                                    <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                                    <td>${entity.updateUser }</td>
                                    <td><fmt:formatDate value="${entity.updateTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                                    <td>
                                    	<c:if test="${entity.orderStat =='处理成功' || entity.orderStat =='部分成功'}"><c:if test="${entity.invoiceStat == '0'}">可开票</c:if></c:if>
                                    	<c:if test="${entity.orderStat =='处理成功' || entity.orderStat =='部分成功'}"><c:if test="${entity.invoiceStat != '0'}">已开票</c:if></c:if>
                                    	<c:if test="${entity.orderStat !='处理成功' && entity.orderStat !='部分成功'}">不可开票</c:if>
                					</td>
                                    <td style = "text-align: left;">
                                    <c:if test="${entity.orderStat=='草稿' }">
                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_ORDERCOMMIT')">
                                    <a orderId="${entity.orderId }" title="提交" class="btn-mini btn-submit" href="#"><i class="icon-ok"></i></a>
                                   </sec:authorize>
                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_INTOEDIT')">
                                    <a orderId="${entity.orderId }" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
                                   </sec:authorize>
                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_DELETE')">
                                    <a orderId="${entity.orderId }" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
                                   </sec:authorize>
                                    </c:if>
                                    <c:if test="${entity.orderStat=='部分成功' }">
                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_ORDERAGAINCOMMIT')">
                                    <a orderId="${entity.orderId }" title="重新提交" class="btn-mini btn-again-submit" href="#"><i class="icon-ok"></i></a>
                                   </sec:authorize>
                                    </c:if>
                                    <c:if test="${entity.orderStat=='处理成功' || entity.orderStat =='部分成功'}">
	                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_ORDERAGAINCOMMIT')">
	                                    <c:if test="${entity.invoiceStat == '0'}">
	                                    	<sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_ORDERCOMMIT')">
	                                    	<a orderId="${entity.orderId }" title="开票" class="btn-mini btn-edit a" href="#"><i class="icon-pencil"></i></a>
	                                    	</sec:authorize>
	                                    </c:if>
	                                    <c:if test="${entity.invoiceStat != '0'}">
	                                    	<sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_ORDERCOMMIT')">
	                                    	<a orderId="${entity.orderId }" title="开票详情" class="btn-mini btn-edit b" href="#"><i class="icon-signal"></i></a>
	                                    	</sec:authorize>
	                                    </c:if>
	                                   </sec:authorize>
                                    </c:if>
                                   <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE_VIEW')">
                                    <a orderId="${entity.orderId }" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
                                   </sec:authorize>
                                    </td>
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

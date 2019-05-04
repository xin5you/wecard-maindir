<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
		<script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
		<script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="${ctx}/resource/js/module/active/activity/listMerchantActives.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/views/common/navbar.jsp"%>
		<div id="contentwrapper">
			<div class="main_content">
				<nav>
		            <div id="jCrumbs" class="breadCrumb module">
		                <ul>
		                    <li><a href="#"><i class="icon-home"></i></a></li>
		                    <li>活动信息</li>
		                    <li>优惠活动列表</li>
		                </ul>
		            </div>
		        </nav>
				<form id="searchForm" action="${ctx }/active/activity/listMerchantActives.do" class="form-inline" method="post">
					<h3 class="heading">优惠活动列表</h3>
					<div class="row-fluid">
						<div class="span12">
							<div class="input-prepend">
								<span class="add-on">活动号</span> 
								<input id="activeId" name="activeId" type="text" class="input-medium" value="${entity.activeId }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">活动名称</span> 
								<input id="activeName" name="activeName" type="text" class="input-medium" value="${entity.activeName }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">商户号</span> 
								<input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${entity.mchntCode }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">商户名称</span> 
								<input id="mchntName" name="mchntName" type="text" class="input-medium" value="${entity.mchntName }" />
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search">查 询</button>
								<button type="button" class="btn btn-inverse btn-reset">重 置</button>
								<sec:authorize access="hasRole('ROLE_ACTIVES_INTOADD')">
									<button type="button" class="btn btn-primary btn-add">新 增</button>
								</sec:authorize>
							</div>
						</div>
					</div>	
					<div class="row-fluid">
						<div class="span12">	
							<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">活动开始时间</span>
                             	<input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${entity.startTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div> 
                         	<div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                             	<span class="add-on">活动结束时间</span>
                             	<input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${entity.endTime}" />
                             	<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                         	</div> 
                         	<div class="input-prepend">
			                	<span class="add-on">活动状态</span>
			                    <select name="activeStat" id="activeStat" class="input-medium" >
			                     	<option value="">---请选择---</option>
			                     	<c:forEach var="status" items="${activeStatList}" varStatus="sta">
			                     		<option value="${status.code}" <c:if test="${status.code==entity.activeStat}">selected</c:if> >${status.name }</option>
			                     	</c:forEach>
			                    </select>
			                </div>  
						</div>
					</div>
					</br>
					<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
						<thead>
							<tr>
								<th>活动号</th>
								<th>活动名称</th>
								<th>商户号</th>
								<th>商户名称</th>
								<th>活动状态</th>
								<th>活动开始时间</th>
								<th>活动结束时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
								<tr>
									<td>${entity.activeId}</td>
									<td>${entity.activeName}</td>
									<td>${entity.mchntCode}</td>
									<td>${entity.mchntName}</td>
									<td>
										<c:if test="${entity.activeStat==0}">草稿</c:if>
										<c:if test="${entity.activeStat==1}">生效中</c:if>
										<c:if test="${entity.activeStat==2}">失效</c:if>
									</td>
									<td>${entity.startTime}</td>
									<td>${entity.endTime}</td>
									<td>
										<sec:authorize access="hasRole('ROLE_ACTIVES_INTOEDIT')">
											<a activeId="${entity.activeId}" title="编辑" href="#" class="btn-mini btn-edit"><i class="icon-edit"></i></a> 
										</sec:authorize>
										<c:if test="${entity.activeStat!=1}">
											<sec:authorize access="hasRole('ROLE_ACTIVES_DELETE')">
												<a activeId="${entity.activeId}" title="删除" href="#" class="btn-mini btn-delete"><i class="icon-remove"></i></a> 
											</sec:authorize>
										</c:if>
										<sec:authorize access="hasRole('ROLE_ACTIVES_VIEW')">
											<a activeId="${entity.activeId}" title="详情" href="#" class="btn-mini btn-view"><i class="icon-search"></i></a>
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

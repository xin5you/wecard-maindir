<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/channel/paymentChannelApi/listPaymentChannelApiInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>支付通道API</li>
			                    <li><a href="${ctx }/channel/paymentChannel/listPaymentChannel.do">支付通道管理</a></li>
			                     <li>支付通道API列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/paymentChannelApi/listPaymentChannelApi.do" class="form-inline" method="post">
						<input type="hidden" id="channelId" name = "channelId"  value="${pci.channelId }"/>
						<h3 class="heading">支付通道API列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
							 	<div class="input-prepend">
		           			   	   	<span class="add-on">通道API名称</span><input id="name" name="name" value = "${pci.name }" type="text" class="input-medium"/>
		                       	</div>
		                       	<div class="input-prepend">
									<span class="add-on">类型</span> 
									<select id="apiType" name="apiType" class="input-135">
										<option value="">--请选择--</option>
										 <c:forEach var="apiTypeMap" items="${apiTypeMap}" varStatus="st">
											 	<option value="${apiTypeMap.key}" <c:if test="${apiTypeMap.key==apiType}">selected="selected"</c:if>>${apiTypeMap.value}</option>
										 </c:forEach>
									</select>
								</div>
							</div>
							
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_API_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增</button>
								</sec:authorize>
							</div>
						</div>
				         </br >      
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" style="table-layout:fixed; ">
				             <thead>
				             <tr>
				               <th>支付通道名称</th>
				               <th>通道API名称</th>
				               <th>类型</th>
<!-- 				               <th>描述信息</th> -->
				               <th>状态</th>
				               <th>创建时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.channelName}</td>
				                 	<td>${entity.name}</td>
				                 	<td>${entity.apiType}</td>
<%-- 									<td>${entity.description}</td> --%>
									<td>
										<c:if test="${entity.enable=='1'}">启用</c:if>
										<c:if test="${entity.enable=='0'}">禁用</c:if>
									</td>
									<td><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				                    <td>
									<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_API_INTOEDIT')">
											<a id="${entity.id}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_API_DELETE')">
											<a id="${entity.id}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_API_VIEW')">
											<a id="${entity.id}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
										</sec:authorize>
				                    </td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
				      </form>
			   </div>
</body>
</html>

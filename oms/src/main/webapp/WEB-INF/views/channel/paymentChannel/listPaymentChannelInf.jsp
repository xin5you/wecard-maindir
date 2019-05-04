<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/channel/paymentChannel/listPaymentChannelInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>支付通道信息</li>
			                    <li><a href="${ctx }/channel/paymentChannel/listPaymentChannel.do">支付通道管理</a></li>
			                     <li>支付通道信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/paymentChannel/listPaymentChannel.do" class="form-inline" method="post">
<%-- 						<input type="hidden" id="operStatus"  value="${operStatus }"/> --%>
						<h3 class="heading">支付通道信息列表</h3>
						
						<div class="row-fluid" id="h_search">
							 <div class="span10">
							 	<div class="input-prepend">
		           			   	   	<span class="add-on">通道名称</span><input id="channelName" name="channelName" value = "${pc.channelName }" type="text" class="input-medium"/>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">启用标识</span>
		           			   	   	<select id="enable" name="enable" class="input-medium">
		           			   	   		<option value="">--请选择--</option>
										<option value="1" <c:if test="${pc.enable == '1' }">selected="selected"</c:if>>启用</option>
										<option value="0" <c:if test="${pc.enable == '0' }">selected="selected"</c:if>>禁用</option>
									</select>
		                     	</div>
							 </div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增</button>
								</sec:authorize>
							</div>
						</div>
				         </br >      
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" style="table-layout:fixed; ">
				             <thead>
				             <tr>
				               <th>通道号</th>
				               <th>通道名称</th>
				               <th>费率(万分比)</th>
				               <th>类型</th>
				               <th>描述信息</th>
				               <th>启用标识</th>
				               <th>创建时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.channelNo}</td>
				                 	<td>${entity.channelName}</td>
				                 	<td>${entity.rate}</td>
				                 	<td>${entity.channelType}</td>
									<td>${entity.description}</td>
									<td>
										<c:if test="${entity.enable=='1'}">启用</c:if>
										<c:if test="${entity.enable=='0'}">禁用</c:if>
									</td>
									<td><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				                    <td>
				                    	<c:if test="${entity.channelNo != '101' }">
										<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_INTOEDIT')">
											<a id="${entity.id}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_DELETE')">
											<a id="${entity.id}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS_VIEW')">
											<a id="${entity.id}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
										</sec:authorize>
										</c:if>
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

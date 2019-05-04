<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelReserve/listTelChannelReserve.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/channel/reserve/listTelChannelReserve.do">备付金管理</a></li>
			                     <li>备付金信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/reserve/listTelChannelReserve.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">备付金信息列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">分销商名称</span><input id="channelName" name="channelName" type="text" class="input-medium" value="${reserve.channelName }" />
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_RESERVE_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增备付金</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				                <th>分销商名称</th>
				                <th>备付金</th>
				                <th>备付金类型</th>
				               <th>创建时间</th>
				               <th>修改时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.channelName}</td>
				                 	<td>${entity.reserveAmt}</td>
				                 	<td>${entity.reserveType}</td>
				                 	<td>
				                    <fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				                    </td>
				                    <td>
				                    <fmt:formatDate value="${entity.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
				                    </td>
				                    <td>
<%-- 				                    <sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_INTOEDIT')"> --%>
<%-- 									<a eShopId="${entity.id}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a> --%>
<%-- 									</sec:authorize> --%>
<%-- 									<sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_DELETE')"> --%>
<%-- 									<a eShopId="${entity.id}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a> --%>
<%-- 									</sec:authorize> --%>
									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_RESERVE_VIEW')">
									<a reserveId="${entity.id}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

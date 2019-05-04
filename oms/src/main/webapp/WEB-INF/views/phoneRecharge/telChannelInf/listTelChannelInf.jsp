<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelInf/listTelChannelInf.js"></script>
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
			                    <li><a href="${ctx }/channel/channelInf/listTelChannelInf.do">分销商管理</a></li>
			                     <li>分销商信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/channelInf/listTelChannelInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">分销商信息列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">分销商名称</span><input id="channelName" name="channelName" type="text" class="input-medium" value="${telChannelInf.channelName }" />
		                       	</div>
							</div>
							<div class="pull-right">
								
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 
								 <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_INF_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增分销商</button>
								</sec:authorize>
							</div>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				             	<th>分销商ID</th>
				                <th>分销商名称</th>
				               	<th>分销商编号</th>
				               	<th>分销商备付金额(元)</th>
				                <th>分销商预警金额(元)</th>
				                <th>管理员手机号</th>
				                <th>邮箱</th>
				               	<th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.channelId}</td>
				                 	<td>${entity.channelName}</td>
				                 	<td>${entity.channelCode}</td>
									<td>${entity.channelReserveAmt}</td>
				                    <td>${entity.channelPrewarningAmt}</td>
				                    <td>${entity.phoneNo}</td>
				                    <td>${entity.email}</td>
				                    <td>
				                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM_LIST_INTOADD')">
									<a channelId="${entity.channelId}" title="添加产品折扣率" class="btn-mini btn-edit a" href="#"><i class="icon-pencil"></i></a> 
									</sec:authorize>
				                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_RESERVE_INTOLIST')">
									<a channelId="${entity.channelId}" title="追加备付金" class="btn-grant-role"  href="#"><i class="icon-plus"></i></a> 
									</sec:authorize>
				                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_INF_INTOEDIT')">
									<a channelId="${entity.channelId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_INF_DELETE')">
									<a channelId="${entity.channelId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_INF_VIEW')">
									<a channelId="${entity.channelId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
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

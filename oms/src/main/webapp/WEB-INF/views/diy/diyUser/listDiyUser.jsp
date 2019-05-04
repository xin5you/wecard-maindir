<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/init.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script src="${ctx}/resource/js/module/diy/diyUser/listDiyUser.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/navbar.jsp"%>

	<div class="main_content">
		<nav>
			<div id="jCrumbs" class="breadCrumb module">
				<ul>
					<li><a href="#"><i class="icon-home"></i></a></li>
					<li>商户自助平台管理</li>
					<li><a href="${ctx }/diy/diyUser/listDiyUser.do">商户用户管理</a></li>
					<li>用户列表查询</li>
				</ul>
			</div>
		</nav>
		<form id="searchForm" action="${ctx }/diy/diyUser/listDiyUser.do"
			class="form-inline" method="post">
			<input type="hidden" id="operStatus" value="${operStatus }" />
			<h3 class="heading">用户列表查询</h3>
			<div class="row-fluid" id="h_search">
				<div class="span12">
					<div class="input-prepend">
						<span class="add-on">所属商户</span> <input id="mchntName"
							name="mchntName" type="text" class="input-medium"
							value="${bizUser.mchntName }" />
					</div>
					<div class="input-prepend">
						<span class="add-on">用户名</span> <input id="userName"
							name="userName" type="text" class="input-medium"
							value="${bizUser.userName }" />
					</div>
					<div class="input-prepend">
						<span class="add-on">手机号码</span> <input id="phoneNo"
							name="phoneNo" type="text" class="input-medium"
							value="${bizUser.phoneNo }" />
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-search">查 询</button>
						<button type="reset" class="btn btn-inverse btn-reset">重
							置</button>
						<sec:authorize access="hasRole('ROLE_DIY_USER_INTOADD')">
							<button type="button" class="btn btn-primary btn-add">新增用户</button>
						</sec:authorize>
					</div>
				</div>
			</div>
			</br>
			<table class="table table-striped table-bordered dTableR table-hover"
				id="dt_gal">
				<thead>
					<tr>
						<th>手机号码</th>
						<th>用户名</th>
						<th>角色</th>
						<th>所属商户</th>
						<th>所属门店</th>
						<th>创建时间</th>
						<th>是否默认</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="diyUser" items="${pageInfo.list}" varStatus="st">
						<tr>
							<td>${diyUser.phoneNo}</td>
							<td>${diyUser.userName}</td>
							<td>${diyUser.roleName}</td>
							<td>${diyUser.mchntName}</td>
							<td>${diyUser.shopName}</td>
							<td><fmt:formatDate value="${diyUser.createTime}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><c:if test="${diyUser.isdefault =='0'}">是</c:if>
								<c:if test="${diyUser.isdefault !='0'}">否</c:if></td>
							<td><c:if test="${diyUser.dataStat =='0'}">正常</c:if>
								<c:if test="${diyUser.dataStat !='0'}">停用</c:if></td>
							<td><c:if
									test="${diyUser.isdefault =='0' && diyUser.dataStat =='0'}">
									<c:if test="${diyUser.roleCheckflag =='1'}">
										<sec:authorize access="hasRole('ROLE_DIY_USER_INTOEDIT')">
											<a userId="${diyUser.id}" title="编辑" class="btn-edit"
												href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
									</c:if>
									<sec:authorize access="hasRole('ROLE_DIY_USER_DELETE')">
										<a userId="${diyUser.id}" title="删除" class="btn-delete"
											href="#"><i class="icon-remove"></i></a>
									</sec:authorize>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@ include file="/WEB-INF/views/common/pagination.jsp"%>
		</form>
	</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/sys/user/listUser.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
 		
 		<div class="main_content">
 		<nav>
            <div id="jCrumbs" class="breadCrumb module">
                <ul>
                    <li><a href="#"><i class="icon-home"></i></a></li>
                    <li>系统信息</li>
                    <li><a href="${ctx }/sys/user/listUser.do">用户管理</a></li>
                     <li>用户列表查询</li>
                </ul>
            </div>
        </nav>
		<form id="searchForm" action="${ctx }/sys/user/listUser.do" class="form-inline" method="post">
			<input type="hidden" id="operStatus"  value="${operStatus }"/>
			<h3 class="heading">用户列表查询</h3>
			<div class="row-fluid" id="h_search">
			<div class="span12">
				<div class="input-prepend">
						<span class="add-on">用户名</span> 
						<input id="name" name="name" type="text" class="input-medium" value="${bizUser.name }" />
				</div>
				<div class="input-prepend">
						<span class="add-on">登录名</span> 
						<input id="loginname" name="loginname" type="text" class="input-medium" value="${bizUser.loginname }" />
				</div>
				<div class="pull-right">
						 <button type="submit" class="btn btn-search">查 询</button>
						<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
						<sec:authorize access="hasRole('ROLE_SYS_USER_INTOADD')">
							<button type="button" class="btn btn-primary btn-add">新增用户</button>
						</sec:authorize>
				</div>
			</div>
			</div>
	         </br >       
	         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
	             <thead>
	             <tr>
	               <th>登录名</th>
	               <th>姓名</th>
	               <th>所属部门</th>
	               <th>创建时间</th>
	               <th>是否默认</th>
	               <th>状态</th>
	               <th>操作</th>
	             </tr>
	             </thead>
	             <tbody>
	             <c:forEach var="user" items="${pageInfo.list}" varStatus="st">
	                 <tr>
	                    <td>${user.loginname}</td>
	                    <td>${user.name}</td>
						<td>${user.organizationName}</td>
						<td><fmt:formatDate value="${user.createdatetime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><c:if test="${user.isdefault =='0'}">是</c:if><c:if test="${user.isdefault !='0'}">否</c:if></td>
						<td><c:if test="${user.state =='0'}">正常</c:if><c:if test="${user.state !='0'}">停用</c:if></td>
	                    <td>
	                    	<c:if test="${user.isdefault !='0' && user.state !='1'}">
	                    		<sec:authorize access="hasRole('ROLE_SYS_USER_INTOEDIT')">
									<a userId="${user.id}" title="编辑" class="btn-edit" href="#"><i class="icon-edit"></i></a>
								</sec:authorize>
								
								<sec:authorize access="hasRole('ROLE_SYS_USER_DELETE')">
									<a userId="${user.id}" title="删除" class="btn-delete" href="#"><i class="icon-remove"></i></a>
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

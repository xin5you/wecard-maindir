<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
        <script src="${ctx}/resource/js/module/sys/user/editUser.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
          <!-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>系统信息</li>
			                    <li><a href="${ctx }/sys/user/listUser.do">用户管理</a></li>
			                     <li>编辑用户</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" class="form-horizontal form_validation_tip" method="post">
								<input type="hidden" id="userId" name="userId" value="${currUser.id }">
								 <h3 class="heading">编辑用户</h3>
						         <div class="control-group">
						             <label class="control-label">登录名</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="loginname" name="loginname" value="${currUser.loginname }" />
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">姓名</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="name" name="name" value="${currUser.name }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						
<!-- 						         <div class="control-group"> -->
<!-- 						             <label class="control-label">用户类型</label> -->
<!-- 						             <div class="controls"> -->
<%-- 						                 <input type="text" class="span6" id="usertype" name="usertype" value="${user.usertype }"/> --%>
<!-- 						                 <span class="help-block"></span> -->
<!-- 						             </div> -->
<!-- 						         </div> -->
						         
						         <div class="control-group">
						             <label class="control-label">部门</label>
						             <div class="controls">
						                 <select class="form-control span6" id="organizationId" name="organizationId">
											 <c:forEach var="organ" items="${organizationList}" varStatus="st">
											 		<option value="${organ.id}" <c:if test="${currUser.organizationId == organ.id}">selected="selected"</c:if>>${organ.name }</option>
											 </c:forEach>
										</select>
										 <span class="help-block"></span>
						             </div>
						         </div>
						         
						         <div class="control-group">
						             <label class="control-label">角色</label> 
						             <div class="controls">
						                  <select  multiple class="form-control span6" id="roleIds" name="roleIds">
											 <c:forEach var="role" items="${roleList}" varStatus="st">
											 		<option value="${role.id}"
											 		 	<c:forEach var="usRoles" items="${userRoleList}" varStatus="us">
											 		 		<c:if test="${role.id == usRoles.id}">selected="selected"</c:if>
											 		 	</c:forEach>
											 		 >
											 		 ${role.name }
											 		 </option>
											 </c:forEach>
											</select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_SYS_USER_EDITCOMMIT')">
				                                	<button class="btn btn-primary btn-submit" type="submit">提 交</button>
				                                </sec:authorize>
				                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
				                            </div>
				                  </div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
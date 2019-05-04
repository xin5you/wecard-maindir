<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
        <script src="${ctx}/resource/js/module/diy/diyUser/addDiyUser.js"></script>
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
					                    <li>商户自助平台管理</li>
					                    <li><a href="${ctx }/diy/diyUser/listDiyUser.do">商户用户管理</a></li>
					                     <li>新增商户用户</li>
					                </ul>
					            </div>
					        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" class="form-horizontal form_validation_tip" method="post">
							<input type="hidden" name = "roleIdTmp" id="roleIdTmp" value="${roleIdTmp }"/>
								 <h3 class="heading">新增商户用户</h3>
						         <div class="control-group">
						             <label class="control-label">用户名</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="userName" name="userName" maxlength="32" />
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">手机号码</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="phoneNo" name="phoneNo" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						
						         <div class="control-group">
						             <label class="control-label">密码</label>
						             <div class="controls">
						                 <input type="password" class="span6" id="password" name="password" maxlength="16" onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">确认密码</label>
						             <div class="controls">
						                 <input type="password" class="span6" id="upassword" name="upassword" maxlength="16" onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">所属商户</label>
						             <div class="controls">
						                 <select class="form-control span6" id="mchntCode" data-placeholder="--请选择商户--" name="mchntCode">
						                 	<option value="0" selected="selected">请选择</option>
											 <c:forEach var="mchnt" items="${mchntList}" varStatus="st">
											 		<option value="${mchnt.mchntCode}">${mchnt.mchntName}</option>
											 </c:forEach>
										 </select>
										 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">角色</label> 
						             <div class="controls">
						                  <select  class="form-control span6" id="roleIds" name="roleIds">
											 <c:forEach var="role" items="${diyRoleList}" varStatus="st">
											 		<option value="${role.id}">${role.roleName }</option>
											 </c:forEach>
										  </select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group" id ="channel" >
						             <label class="control-label">所属分销商</label>
						             <div class="controls">
						                 <select class="form-control span6" id="channelId" data-placeholder="--请选择分销商--" name="channelId">
						                 	<option value="" selected="selected">请选择</option>
											 <c:forEach var="c" items="${telChannelList}" varStatus="st">
											 		<option value="${c.channelId}">${c.channelName}</option>
											 </c:forEach>
										 </select>
										 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group ">
		                            <div class="controls">
		                            	<sec:authorize access="hasRole('ROLE_DIY_USER_ADDCOMMIT')">
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
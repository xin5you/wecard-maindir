<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
        <script src="${ctx}/resource/js/module/sys/role/editRole.js"></script>
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
			                    <li><a href="${ctx }/sys/role/listRole.do">角色列表查询</a></li>
			                     <li>编辑角色</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" class="form-horizontal form_validation_tip" method="post">
								<input type="hidden" class="span6" name="id" id="roleId" value="${role.id}"/>
								 <h3 class="heading">编辑角色</h3>
						         <div class="control-group">
						             <label class="control-label">角色名称</label>
						             <div class="controls">
						                 <input type="text" class="span6" name="name" id="roleName" value="${role.name }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">排序</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="seq" name="seq" value="${role.seq }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						
						         <div class="control-group">
						             <label class="control-label">备注</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="description" name="description" value="${role.description }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         
						         <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_SYS_ROLE_EDITCOMMIT')">
				                                	<button class="btn btn-primary btn-submit" type="submit">确定</button>
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
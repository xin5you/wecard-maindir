<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
        <script src="${ctx}/resource/js/module/diy/diyResource/editDiyResource.js"></script>
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
			                    <li><a href="${ctx }/diy/diyResource/listDiyResource.do">商户资源管理</a></li>
			                    <li>编辑商户资源</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" class="form-horizontal form_validation_tip" method="post">
								<input type="hidden" id="resourceId" name="resourceId" value="${diyResource.id }">
							
								 <h3 class="heading">编辑商户资源</h3>
						         <div class="control-group">
						             <label class="control-label">资源名称</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="resourceName" name="resourceName" value="${diyResource.resourceName }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">资源KEY</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="key" name="key" value="${diyResource.key }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">资源类型${diyResource.resourceType }</label>
						             <div class="controls">
						                 <select class="form-control span6" id="resourceType" name="resourceType">
						                 	 <option value="1" <c:if test="${diyResource.resourceType=='1' }">selected="selected"</c:if>>button</option>
						                 	 <option value="0" <c:if test="${diyResource.resourceType=='0' }">selected="selected"</c:if>>菜单</option>
						                 </select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						
						         <div class="control-group">
						             <label class="control-label">资源路径</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="url" name="url" value="${diyResource.url }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">排序</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="seq" name="seq" value="${diyResource.seq }" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
						                 <span class="help-block" style="color: red;">请输入整数</span>
						             </div>
						         </div>
						         
						         <div class="control-group">
						             <label class="control-label">菜单图标</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="icon" name="icon" value="${diyResource.icon }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         
						         <div class="control-group">
						             <label class="control-label">状态</label>
						             <div class="controls">
						                 <select class="form-control span6" id="dataStat" name="dataStat">
						                 	 <option value="0" <c:if test="${diyResource.dataStat=='0' }">selected="selected"</c:if>>正常</option>
						                 	 <option value="1" <c:if test="${diyResource.dataStat=='1' }">selected="selected"</c:if>>禁用</option>
						                 </select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         
						         <div class="control-group">
						             <label class="control-label">上级资源</label>
						             <div class="controls">
						                  <select class="form-control span6" id="pid" name="pid">
						                 	 <option value="0" selected="selected">请选择资源目录</option>
											 <c:forEach var="rs" items="${diyResourceList}" varStatus="st">
											 		<option value="${rs.id}" <c:if test="${diyResource.pid==rs.id }">selected="selected"</c:if>>${rs.resourceName }</option>
											 </c:forEach>
										</select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						       
						         <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_DIY_RESOURCE_EDITCOMMIT')">
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
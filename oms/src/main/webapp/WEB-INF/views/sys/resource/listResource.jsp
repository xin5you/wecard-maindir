<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link href="${ctx}/resource/css/master.css" rel="stylesheet" type="text/css" />
  	<link href="${ctx}/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/resource/js/jquery/jquery.treeTable.min.js"></script>
    <script src="${ctx}/resource/js/module/sys/resource/listResource.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	 <nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>系统信息</li>
			                    <li><a href="${ctx }/sys/resource/listResource.do">资源管理</a></li>
			                     <li>资源列表查询</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="#" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">资源列表查询</h3>
						<div class="pull-right">
							<sec:authorize access="hasRole('ROLE_SYS_RESOURCE_INTOADD')">
								<button type="button" class="btn btn-primary btn-add"  >新增资源</button>
							</sec:authorize>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>资源名称</th>
				               <th>资源KEY</th>
				               <th>资源路径</th>
				               <th>排序</th>
				               <th>图标</th>
				               <th>状态</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="resource" items="${pageInfo}" varStatus="st">
				                 <tr id="${resource.id}" 
				                 	<c:if test="${resource.pid =='0' && resource.resourcetype=='0'}">class="parent"</c:if> 
				                 	<c:if test="${resource.pid !='0' && resource.resourcetype=='0'}">class="parent child-of-${resource.pid }"</c:if>
				                 	<c:if test="${resource.pid !='0' && resource.resourcetype!='0'}">class="child-of-${resource.pid }"</c:if> 
				                 	>
				                    <td style="text-align:left;"><span <c:if test="${resource.resourcetype =='0'}">class='folder'</c:if> <c:if test="${resource.resourcetype =='1'}">class='file'</c:if>>${resource.name}</span></td>
				                     <td>${resource.key}</td>
				                    <td>${resource.url}</td>
									<td>${resource.seq}</td>
									<td>${resource.icon}</td>
									<td><c:if test="${resource.state =='0'}">正常</c:if><c:if test="${resource.state !='0'}">停用</c:if></td>
				                    <td>
				                   	 <c:if test="${resource.resourcetype=='0'}">
				                    	<sec:authorize access="hasRole('ROLE_SYS_RESOURCE_INTOADD')">
											<a resourceId="${resource.id}" title="添加" class="btn-add" href="#"><i class="icon-plus"></i></a> 
										</sec:authorize>
										</c:if>
				                    	<sec:authorize access="hasRole('ROLE_SYS_RESOURCE_INTOEDIT')">
											<a resourceId="${resource.id}" title="编辑" class="btn-edit" href="#"><i class="icon-edit"></i></a> 
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_SYS_RESOURCE_DELETE')">  
											<a resourceId="${resource.id}" title="删除" class="btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
				                    </td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				      </form>
			   </div>
	    </div>
</body>
</html>

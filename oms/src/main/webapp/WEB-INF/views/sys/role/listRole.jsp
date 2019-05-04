<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/sys/role/listRole.js"></script>
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
			                    <li><a href="${ctx }/sys/role/listRole.do">角色列表查询</a></li>
			                     <li>角色列表查询</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="#" class="form-inline" method="post">
						<input type="hidden" id="isSearch" name="isSearch" value="1"/>
						<h3 class="heading">角色列表查询</h3>
						<div class="row-fluid" id="h_search">
							<sec:authorize access="hasRole('ROLE_SYS_ROLE_INTOADD')">
								<div class="pull-right"><button type="button" class="btn btn-primary btn-add">新增角色</button></div>
							</sec:authorize>
				        </div>
				         </br >   
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>角色名称</th>
				               <th>排序号</th>
				               <th>是否默认</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="role" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                    <td>${role.name}</td>
				                    <td>${role.seq}</td>
									<td>${role.isdefault}</td>
				                    <td>
				                    	<sec:authorize access="hasRole('ROLE_SYS_ROLE_INTOAUTHO')">
				                    	<a orderId="${role.id}" title="授权" class="btn-grant-role" href="#"><i class="icon-plus"></i></a>&nbsp;
				                    	</sec:authorize>
				                    	<sec:authorize access="hasRole('ROLE_SYS_ROLE_INTOEDIT')">
										<a orderId="${role.id}" title="编辑" class="btn-edit-role" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_SYS_ROLE_DELETE')">  
										<a orderId="${role.id}" title="删除" class="btn-delete" href="#"><i class="icon-remove"></i></a>
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
    <div class="modal hide fade"  id="grantRoleModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-header">
              <button class="close" data-dismiss="modal">&times;</button>
              <h3>授权</h3>
          </div>
          <div class="modal-body" >
				<iframe id="grantRoleIframe"  height="99%" src="" frameBorder="0" width="99%"></iframe>  
          </div>
           <div class="modal-footer">
               <a href="#" class="btn" data-dismiss="modal"> 关闭 </a>
           </div>                  
     </div>
 </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link href="${ctx}/resource/css/master.css" rel="stylesheet" type="text/css" />
  	<link href="${ctx}/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/resource/js/jquery/jquery.treeTable.min.js"></script>
    <script src="${ctx}/resource/js/module/sys/dictionary/listDictionary.js"></script>
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
			                    <li><a href="${ctx }/sys/dictionary/listDictionary.do">字典管理</a></li>
			                     <li>字典列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="mainForm" action="#" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">字典列表</h3>
						<div class="pull-right">
							<sec:authorize access="hasRole('ROLE_SYS_DICT_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新增字典</button>
							</sec:authorize>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>名称</th>
				               <th>编码</th>
				               <th>排序</th>
				               <th>资源类型</th>
				               <th>资源值</th>
				               <th>状态</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="dict" items="${pageInfo}" varStatus="st">
				                 <tr id="${dict.id}" 
				                 	<c:if test="${dict.pid =='0' && dict.type=='0'}">class="parent"</c:if> 
				                 	<c:if test="${dict.pid !='0' && dict.type=='0'}">class="parent child-of-${dict.pid }"</c:if>
				                 	<c:if test="${dict.pid !='0' && dict.type!='0'}">class="child-of-${dict.pid }"</c:if> 
				                 	>
				                    <td style="text-align:left;"><span <c:if test="${dict.type =='0'}">class='folder'</c:if> <c:if test="${dict.type =='1'}">class='file'</c:if>>${dict.name}</span></td>
				                    <td>${dict.code}</td>
									<td>${dict.seq}</td>
									<td><c:if test="${dict.type =='0'}">类型</c:if> <c:if test="${dict.type =='1'}">字典</c:if></td>
									<td>${dict.value}</td>
									<td><c:if test="${dict.state =='0'}">正常</c:if><c:if test="${dict.state !='0'}">停用</c:if></td>
				                    <td>
				                    	<sec:authorize access="hasRole('ROLE_SYS_DICT_INTOEDIT')">
											<a dictId="${dict.id}" title="编辑" class="btn-edit" href="#"><i class="icon-edit"></i></a> 
										</sec:authorize>  
										<sec:authorize access="hasRole('ROLE_SYS_DICT_DELETE')">
											<a dictId="${dict.id}" title="删除" class="btn-delete" href="#"><i class="icon-remove"></i></a>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/merchant/merchantManagerTmp/listMerchantManagerTmp.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/merchant/managerTmp/listMerchantManagerTmp.do">商户员工管理</a></li>
			                     <li>员工管理列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/merchant/managerTmp/listMerchantManagerTmp.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">员工管理列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">姓名</span><input id="name" name="name" type="text" class="input-medium" value="${merchantManagerTmp.name }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">手机号</span><input id="phoneNumber" name="phoneNumber" type="text" class="input-medium" value="${merchantManagerTmp.phoneNumber }"/>
		                       	</div>
		
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户名</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${merchantManagerTmp.mchntName }" />
		                       	</div>
		                       	
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">门店名称</span><input id="shopName" name="shopName" type="text" class="input-medium" value="${merchantManagerTmp.shopName }"/>
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								<button type="button" class="btn btn-primary btn-add">新增员工</button>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
				             <thead>
				             <tr>
				               <th>员工姓名</th>
				               <th>手机号</th>
				               <th>工号</th>
				               <th>角色</th>
				               <th>商户</th>
				               <th>门店</th>
				               <th>状态</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.name}</td>
									<td>${entity.phoneNumber}</td>
				                    <td>${entity.remarks}</td>
				                    <td>${entity.roleName}</td>
				                    <td>${entity.mchntName}</td>
				                    <td>${entity.shopName}</td>
				                    <td>
				                    	<c:if test="${entity.dataStat=='1'}">已注册</c:if>
				                    	<c:if test="${entity.dataStat=='0'}">未注册</c:if>
				                    </td>
				                    <td>
											<a mangerId="${entity.mangerId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
											<a mangerId="${entity.mangerId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
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

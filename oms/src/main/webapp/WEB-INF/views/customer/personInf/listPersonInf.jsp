<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/customer/personInf/listPersonInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
 		
 		<div class="main_content">
 		<nav>
            <div id="jCrumbs" class="breadCrumb module">
                <ul>
                    <li><a href="#"><i class="icon-home"></i></a></li>
                    <li>用户会员卡管理</li>
                    <li><a href="${ctx }/customer/personInf/getPersonInfList.do">用户会员卡管理</a></li>
                     <li>用户会员卡列表查询</li>
                </ul>
            </div>
        </nav>
		<form id="searchForm" action="${ctx }/customer/personInf/getPersonInfList.do" class="form-inline" method="post">
			<h3 class="heading">用户会员卡列表查询</h3>
			<div class="row-fluid" id="h_search">
			<div class="span12">
				<div class="input-prepend">
						<span class="add-on">用户名</span> 
						<input id="personalName" name="personalName" type="text" class="input-medium" value="${personInf.personalName }" />
				</div>
				<div class="input-prepend">
						<span class="add-on">手机号码</span> 
						<input id="mobilePhoneNo" name="mobilePhoneNo" type="text" class="input-medium" value="${personInf.mobilePhoneNo }" />
				</div>
				<div class="pull-right">
						<button type="submit" class="btn btn-search">查 询</button>
						<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
				</div>
			</div>
			</div>
	         </br >       
	         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
	             <thead>
	             <tr>
	               <th>用户名</th>
	               <th>手机号码</th>
	               <th>修改人</th>
	               <th>创建时间</th>
	               <th>修改时间</th>
	               <th>注销状态</th>
	               <th>操作</th>
	             </tr>
	             </thead>
	             <tbody>
	             <c:forEach var="per" items="${pageInfo.list}" varStatus="st">
	                 <tr>
	                    <td>${per.personalName}</td>
	                    <td>${per.mobilePhoneNo}</td>
	                    <td>${per.remarks}</td>
						<td><fmt:formatDate value="${per.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${per.updateTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><c:if test="${per.dataStat == 0 }">未注銷</c:if><c:if test="${per.dataStat != 0 }"><span style = "color: red;">已注銷</span></c:if></td>
	                    <td style = "text-align: left;">
	                    	<c:if test="${per.dataStat == 0 }">
								<sec:authorize access="hasRole('ROLE_CANCEL_PHONENO')">
									<a userId="${per.userId}" title="注销手机号码" class="btn-mini btn-delete" href="#"><i class="icon-trash"></i></a>
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

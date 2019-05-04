<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchOpenWBAccountlist/listOpenWBAccount.js"></script>
</head>
<body>
 <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
         <div id="contentwrapper">
             <div class="main_content">
             	<nav>
            <div id="jCrumbs" class="breadCrumb module">
                <ul>
                    <li><a href="#"><i class="icon-home"></i></a></li>
                    <li>福利管理</li>
                    <li><a href="${ctx }/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do">批量提现黑名单</a></li>
                     <li>批量提现黑名单列表</li>
                </ul>
            </div>
        </nav>
		<form id="searchForm" action="${ctx }/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do" class="form-inline" method="post">
			<input type="hidden" id="operStatus"  value="${operStatus }"/>
			<h3 class="heading">批量提现黑名单列表</h3>
			
			<div class="row-fluid" >
				 <div class="span12">
                      	<div class="input-prepend">
          			   	   	<span class="add-on">用户姓名：</span><input id="userName" name="userName" type="text" class="input-medium" value="${withdrawBlacklist.userName }"/>
                      	</div>
                      	<div class="input-prepend">
          			   	   	<span class="add-on">用户手机号：</span><input id="userPhone" name="userPhone" type="text" class="input-medium" value="${withdrawBlacklist.userPhone }"/>
                      	</div>
                      	<div id="datetimepicker1" class="input-prepend input-append date date-time-picker">
                              <span class="add-on">开始时间</span>
                              <input class="input-medium" id="startTime" name="startTime" readonly="readonly" type="text" value="${withdrawBlacklist.startTime }" />
                              <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                          </div> 
                          <div id="datetimepicker2" class="input-prepend input-append date date-time-picker">
                              <span class="add-on">结束时间</span>
                              <input class="input-medium" id="endTime" name="endTime" readonly="readonly" type="text" value="${withdrawBlacklist.endTime }" />
                              <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                          </div>
					<div class="pull-right">
						<button type="submit" class="btn btn-search"> 查 询 </button>
						<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
						<sec:authorize access="hasRole('ROLE_WITHDRAW_BLACKLIST_INTOADD')">
						<button type="button" class="btn btn-primary btn-add">新增</button>
						</sec:authorize>
					</div>
			  	</div>
			</div>
	         </br >       
	         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
	             <thead>
	             <tr>
	               <th>用户姓名</th>
	               <th>用户手机号</th>
	               <th>创建人</th>
	               <th>创建时间</th>
                   <th>修改人</th>
                   <th>修改时间</th>
	               <th>操作</th>
	             </tr>
	             </thead>
	             <tbody>
	             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
	                 <tr>
	                 	<td>${entity.userName }</td>
	                 	<td>${entity.userPhone }</td>
	                    <td>${entity.createUser }</td>
	                    <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                        <td>${entity.updateUser }</td>
                        <td><fmt:formatDate value="${entity.updateTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
	                    <td>
						<sec:authorize access="hasRole('ROLE_WITHDRAW_BLACKLIST_DELETE')">
						<a id="${entity.id }" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
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
</body>
</html>

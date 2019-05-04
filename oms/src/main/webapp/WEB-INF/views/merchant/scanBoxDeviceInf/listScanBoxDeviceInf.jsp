<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>设备信息</li>
			                    <li><a href="${ctx }/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do">设备管理</a></li>
			                     <li>设备信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">设备信息列表</h3>
						
						<div class="row-fluid" id="h_search">
							 <div class="span10">
							 	<div class="input-prepend">
		           			   	   	<span class="add-on">商户号</span><input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${scanBoxDeviceInf.mchntCode }"/>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">商户名称</span><input id="mchntName" name="mchntName" type="text" class="input-medium" value="${scanBoxDeviceInf.mchntName }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">门店号</span><input id="shopCode" name="shopCode" type="text" class="input-medium" value="${scanBoxDeviceInf.shopCode }" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">门店名称</span><input id="shopName" name="shopName" type="text" class="input-medium" value="${scanBoxDeviceInf.shopName }" />
		                       	</div>
							</div>
							<div class="pull-right">
								
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_INTOADD_01')">
								<button type="button" class="btn btn-primary btn-add">新增设备</button>
								</sec:authorize>
								
							</div>
						</div>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
							 	<div class="input-prepend">
		           			   	   	<span class="add-on">设备号</span><input id="deviceNo" name="deviceNo" type="text" class="input-medium" value="${scanBoxDeviceInf.deviceNo }"/>
		                       	</div>
							</div>
						</div>
						
				         </br >      
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" style="table-layout:fixed; ">
				             <thead>
				             <tr>
				               <th>设备类型</th>
				               <th>设备号</th>
				               <th>机构号</th>
				               <th>商户号</th>
				               <th>商户名称</th>
				               <th>门店号</th>
				               <th>门店名称</th>
				               <th>通道名称</th>
				               <th>状态</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.deviceType}</td>
				                 	<td style="overflow: hidden; text-overflow:ellipsis;white-space: nowrap; ">${entity.deviceNo}</td>
				                 	<td>${entity.insCode}</td>
									<td>${entity.mchntCode}</td>
									<td>${entity.mchntName}</td>
									<td>${entity.shopCode}</td>
									<td>${entity.shopName}</td>
									<td>${entity.channelName}</td>
									<td>
										<c:if test="${entity.dataStat==0}">正常</c:if>
										<c:if test="${entity.dataStat==1}">不正常</c:if>
									</td>
				                    <td>
									<sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_INTOEDIT')">
											<a deviceId="${entity.deviceId}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_DELETE')">
											<a deviceId="${entity.deviceId}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF_VIEW')">
											<a deviceId="${entity.deviceId}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
										</sec:authorize>
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

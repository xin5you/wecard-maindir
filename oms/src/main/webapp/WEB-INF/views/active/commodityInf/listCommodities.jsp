<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<script src="${ctx}/resource/js/module/active/commodityInf/listCommodities.js"></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/views/common/navbar.jsp"%>
		<div id="contentwrapper">
			<div class="main_content">
				<nav>
		            <div id="jCrumbs" class="breadCrumb module">
		                <ul>
		                    <li><a href="#"><i class="icon-home"></i></a></li>
		                    <li>活动信息</li>
		                    <li>商品列表</li>
		                </ul>
		            </div>
		        </nav>
				<form id="mainForm" action="${ctx }/active/commodityInf/listCommodities.do" class="form-inline" method="post">
					<h3 class="heading">商品列表</h3>
					<div class="row-fluid" id="h_search">
						<div class="span10">
							<div class="input-prepend">
								<span class="add-on">商品号</span> 
								<input id="commodityId" name="commodityId" type="text" class="input-medium" value="${commodityInf.commodityId }" />
							</div>
							<div class="input-prepend">
								<span class="add-on">商品名称</span> 
								<input id="commodityName" name="commodityName" type="text" class="input-small" value="${commodityInf.commodityName }" />
							</div>

							<div class="input-prepend">
								<span class="add-on">商户号</span> 
								<input id="mchntCode" name="mchntCode" type="text" class="input-medium" value="${commodityInf.mchntCode }" />
							</div>

							<div class="input-prepend">
								<span class="add-on">商户名称</span> <input id="mchntName" name="mchntName" type="text" class="input-small" value="${commodityInf.mchntName }" />
							</div>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-search">查 询</button>
							<button type="button" class="btn btn-inverse btn-reset">重 置</button>
							<sec:authorize access="hasRole('ROLE_COMMODITYINF_INTOADD')">
								<button type="button" class="btn btn-primary btn-add">新 增</button>
							</sec:authorize>
						</div>
					</div>
					</br>
					<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
						<thead>
							<tr>
								<th>商品号</th>
								<th>商品名称</th>
								<th>商品面额</th>
								<th>商品成本</th>
								<th>商户名称</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
								<tr>
									<td>${entity.commodityId}</td>
									<td>${entity.commodityName}</td>
									<td>${entity.commodityFacevalue/100}</td>
									<td>${entity.commodityCost/100}</td>
									<td>${entity.mchntName}</td>
									<td>
										<sec:authorize access="hasRole('ROLE_COMMODITYINF_INTOEDIT')">
											<a commId="${entity.commodityId}" title="编辑" href="#" class="btn-mini btn-edit"><i class="icon-edit"></i></a> 
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_COMMODITYINF_DELETE')">
											<a commId="${entity.commodityId}" title="删除" href="#" class="btn-mini btn-delete"><i class="icon-remove"></i></a> 
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_COMMODITYINF_VIEW')">
											<a commId="${entity.commodityId}" title="详情" href="#" class="btn-mini btn-view"><i class="icon-search"></i></a>
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
		<div id="commodityInfModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<form class="form-horizontal">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h3 id="commodityInfModal_h">商品编辑</h3>
				</div>
				<div class="modal-body">
					<input type="hidden" id="commodity_id" />
					<fieldset>
						<div class="control-group">
							<label class="control-label">所属商户：</label>
							<div class="controls">
								<input type="text" class="span3" id="mchnt_name" readonly="readonly"/>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">商品名称：</label>
							<div class="controls">
								<input type="text" class="span3" id="commodity_name" />
								<span class="help-block"></span>
							</div>	
						</div>
						<div class="control-group">
							<label class="control-label">商品面额：</label>
							<div class="controls">
								<input type="text" class="span3" id="commodity_facevalue" readonly="readonly" />
								<span class="help-block"></span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">商品成本：</label>
							<div class="controls">
								<input type="text" class="span3" id="commodity_cost" readonly="readonly" />
								<span class="help-block"></span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">商品状态：</label>
							<div class="controls">
								<select name="commodity_dataStat" id="commodity_dataStat" class="span3">
									<c:forEach var="rs" items="${commStatus}" varStatus="st">
										<option value="${rs.code}">${rs.name }</option>
									</c:forEach>
		                        </select>
								<span class="help-block"></span>
							</div>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="modal-footer" style="text-align: center;">
	            <button class="btn btn-primary btn-submit">提 交  </button>
	            <button class="btn" data-dismiss="modal" aria-hidden="true">关 闭</button>
	        </div>
		</div>         
	</body>
</html>

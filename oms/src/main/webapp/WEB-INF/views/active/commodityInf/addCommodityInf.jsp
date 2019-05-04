<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
		<script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
		<script src="${ctx}/resource/js/module/active/commodityInf/addCommodityInf.js"></script>
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
		                    <li>活动信息</li>
		                    <li><a href="${ctx }/active/commodityInf/listCommodities.do">商品列表</a></li>
		                    <li>新增商品</li>
		                </ul>
		            </div>
		        </nav>
				<div class="row-fluid">
					<div class="span12">
						<form id="myForm" class="form-horizontal form_validation_tip" method="post">
							<h3 class="heading">新增商品</h3>
							<div class="tabbable">
								<div class="tab-content">
									<div class="tab-pane active">
										<div class="control-group formSep">
							             	<label class="control-label">所属商户：</label>
							             	<div class="controls">
							               		<select name="mchntId" id="mchntId" class="span6">
													<c:forEach var="rs" items="${mchntList}" varStatus="st">
														<option value="${rs.mchntId},${rs.productCode}">${rs.mchntName }</option>
													</c:forEach>
				                                </select>
				                                <button class="btn btn-mchnt-list" type="button" style="margin-bottom: 23px;">查看商品列表</button>
							             	</div>
							     		</div>
										<div class="control-group formSep">
											<label class="control-label">商品名称<span class="f_req">*</span>：</label>
											<div class="controls">
												<input type="text" class="span6" id="commodityName" name="commodityName"/>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group formSep">
											<label class="control-label">商品面额<span class="f_req">*</span>：</label>
											<div class="controls">
												<input type="text" class="span6" id="commodityFacevalue" name="commodityFacevalue"/>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group formSep">
											<label class="control-label">商品成本<span class="f_req">*</span>：</label>
											<div class="controls">
												<input type="text" class="span6" id="commodityCost" name="commodityCost"/>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group formSep">
											<label class="control-label">商品状态：</label>
											<div class="controls">
												<select name="dataStat" id="dataStat" class="span6">
													<c:forEach var="rs" items="${commStatus}" varStatus="st">
														<option value="${rs.code}">${rs.name }</option>
													</c:forEach>
				                                </select>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group ">
											<div class="controls">
												<button class="btn btn-primary btn-add" type="submit">确 定</button>
												<button class="btn btn-inverse btn-return" type="button">返 回</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="mchntListModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-header">
				<button class="close" data-dismiss="modal">&times;</button>
				<h3>【<span id="mchnt_name"></span>】商品列表</h3>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
					<thead>
						<tr>
							<th>商品号</th>
							<th>商品名称</th>
							<th>商品面额</th>
							<th>商品成本</th>
						</tr>
					</thead>
					<tbody id="mchnt_commodities"></tbody>
				</table>
				<div style="text-align: center;">
					<button class="btn btn-primary" data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</div>	
	</body>
</html>
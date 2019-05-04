<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/enterpriseOrder/cardKeys/listCardKeysProduct.js"></script>
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
						<li><a href="${ctx }/cardKeys/listCardKeysProduct.do">卡密产品信息</a></li>
						<li>卡密产品列表</li>
					</ul>
				</div>
			</nav>
			<form id="searchForm" action="${ctx }/cardKeys/listCardKeysProduct.do" class="form-inline" method="post">
				<input type="hidden" id="operStatus" value="${operStatus }"/>
				<h3 class="heading">卡密产品列表</h3>
				<div class="row-fluid" id="h_search">
					<div class="span12">
						<div class="input-prepend">
		    				<span class="add-on">产品号</span><input id="productCode" name="productCode" type="text" class="input-medium" value="${cardKeysProduct.productCode }" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
		    			</div>
		    			<div class="input-prepend">
		    				<span class="add-on">产品名称</span><input id="productName" name="productName" type="text" class="input-medium" value="${cardKeysProduct.productName }"/>
		    			</div>
					    <%-- <div class="input-prepend">
					    	<span class="add-on">面额</span><input id="orgAmount" name="orgAmount" type="text" class="input-medium" value="${cardKeysProduct.orgAmount }" onkeyup="this.value=this.value.replace(/\D/g,'')" />
					    </div> --%>
						<div class="input-prepend">
							<span class="add-on">产品类型</span> 
							<select id="productType" name="productType" class="input-medium">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${productTypeList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${cardKeysProduct.productType==dict.code}">selected="selected"</c:if>>${ dict.value }</option>
								</c:forEach>
							</select>
						</div>
						<div class="input-prepend">
							<span class="add-on">上架状态</span> 
							<select id="isPutaway" name="isPutaway" class="input-medium">
								<option value="">--请选择--</option>
								<c:forEach var="dict" items="${isPutawayList}" varStatus="st">
								<option value="${dict.code}" <c:if test="${cardKeysProduct.isPutaway==dict.code}">selected="selected"</c:if>>${ dict.value }</option>
								</c:forEach>
							</select>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-search"> 查 询 </button>
							<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
							<sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_INTOADD')">
							<button type="button" class="btn btn-primary btn-add">新增卡密产品</button>
							</sec:authorize>
						</div>
					</div>
				</div>
						
				<br/>       
				<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
					<thead>
						<tr>
							<th>产品号</th>
							<th>产品名称</th>
							<th>产品类型</th>
							<th>面额</th>
							<th>单位</th>
							<th>金额(元)</th>
							<th>已发总数</th>
							<th>供应商</th>
							<th>上架状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
						<tr>
							<td>${entity.productCode}</td>
							<td>${entity.productName}</td>
							<td>${entity.productType}</td>
							<td>${entity.orgAmount}</td>
							<td>${entity.productUnit}</td>
							<td>${entity.amount}</td>
							<td>${entity.totalNum}</td>
							<td>${entity.supplier}</td>
							<td>${entity.isPutaway}</td>
							<td>
								<sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_INTOEDIT')">
									<a productId="${entity.productCode}" title="编辑" class="btn-mini btn-edit"  href="#"><i class="icon-edit"></i></a> 
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_PENCIL')">
				 					<a productId="${entity.productCode}" title="上下架" class="btn-mini btn-pencil" href="#"><i class="icon-pencil"></i></a>
				 				</sec:authorize>
				 				<sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_VIEW')">
									<a productId="${entity.productCode}" title="详情" class="btn-mini btn-view" href="#"><i class="icon-search"></i></a>
								</sec:authorize>
				 				<sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_DELETE')">
				 					<a productId="${entity.productCode}" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
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
	<div id="cardKeysProductModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<form class="form-horizontal">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
					<h3 id="cardKeysProductModal_h">卡密产品上下架</h3>
				</div>
				<div class="modal-body">
					<input type="hidden" id="pId" />
					<fieldset>
						<div class="control-group">
							<label class="control-label">是否上架：</label>
							<div class="controls">
								<select name="ckp_dataStat" id="ckp_dataStat" class="span3">
									<c:forEach var="rs" items="${dataStatList}" varStatus="st">
										<option value="${rs.code}">${rs.value }</option>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/navbar.jsp"%>
	<-- main content -->
	<div id="contentwrapper">
		<div class="main_content">
			<nav>
				<div id="jCrumbs" class="breadCrumb module">
					<ul>
						<li><a href="#"><i class="icon-home"></i></a></li>
						<li>福利管理</li>
						<li><a href="${ctx }/cardKeys/listCardKeysProduct.do">卡密产品信息</a></li>
						<li>卡密产品详情</li>
					</ul>
				</div>
			</nav>
			<div class="row-fluid">
				<div class="span12">
					<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
						<%-- <input type="hidden" id="productCode" name="productCode"  value="${cardKeysProduct.productCode }" > --%>
						<h3 class="heading">卡密产品详情</h3>
						<div class="control-group">
							<label class="control-label">产品号</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.productCode}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品名称</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.productName}" readonly="readonly" />
							</div>
						</div>
                        <div class="control-group">
							<label class="control-label">产品类型</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.productType}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">是否上架</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.isPutaway}" readonly="readonly" />
							</div>
						</div>
                        <div class="control-group">
							<label class="control-label">面额</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.orgAmount}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">产品单位</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.productUnit}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">金额（元）</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.amount}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">已发总数</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.totalNum}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">可购数量</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.availableNum}" readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">供应商</label>
							<div class="controls">
								<input type="text" class="span6"  value="${cardKeysProduct.supplier}" readonly="readonly" />
							</div>
						</div>
                        <div class="control-group">
	                        <label class="control-label">LOGO</label>
	                        <div class="controls">
	                        	<img alt="卡密产品LOGO" src="${cardKeysProduct.logoUrl }" width="260" height="300">
	                        </div>
                        </div>
						<div class="control-group">
							<label class="control-label">产品描述</label>
							<div class="controls">
								<textarea  rows="4" class="span6" name="productDesc" readonly="readonly" >${cardKeysProduct.productDesc} </textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea  rows="4" class="span6" name="remarks" readonly="readonly" >${cardKeysProduct.remarks} </textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
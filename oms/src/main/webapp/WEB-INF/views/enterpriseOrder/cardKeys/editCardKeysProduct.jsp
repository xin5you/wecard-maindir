<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <!-- colorbox -->
      <link rel="stylesheet" href="${ctx}/resource/colorbox/colorbox.css" />
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
      <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/enterpriseOrder/cardKeys/editCardKeysProduct.js"></script>
      
      <script src="${ctx}/resource/colorbox/jquery.colorbox.min.js"></script>
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
						<li>修改卡密产品</li>
					</ul>
				</div>
			</nav>
			<div class="row-fluid">
				<div class="span12">
					<form action="${ctx }/cardKeys/editCardKeysProductCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
						<input type="hidden" id="productType" name="productType"  value="${cardKeysProduct.productType}" />
						<h3 class="heading">修改卡密产品</h3>
						<input type="hidden" name="check" id="check" value="update" />
						<div class="control-group formSep">
							<label class="control-label">产品类型</label>
							<div class="controls">
								<select name="productType" id="productType_select" class="chzn_a span6" disabled="disabled">
									<c:forEach var="pt" items="${productTypeList}" varStatus="st">
										<option value="${pt.code}" <c:if test="${pt.value==cardKeysProduct.productType }">selected="selected"</c:if> >${pt.value }</option>
									</c:forEach>
						        </select>
							</div>
						</div>
						<div class="control-group formSep">
							<label class="control-label">产品号</label>
							<div class="controls">
								<input type="text" class="span6" readonly="readonly" name="productCode" id="productCode"  value="${cardKeysProduct.productCode}" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')" />
							</div>
						</div>
						<div class="control-group formSep">
							<label class="control-label">产品名称</label>
							<div class="controls">
								<input type="text" class="span6" name="productName" id="productName"  value="${cardKeysProduct.productName}"/>
							</div>
						</div>
                        <div class="control-group formSep">
	                        <label class="control-label">面额</label>
	                        <div class="controls">
		                        <input type="text" class="span6" id="orgAmount" name="orgAmount" value="${cardKeysProduct.orgAmount}" />
		                        <span class="help-block"></span>
	                        </div>
                        </div>
                        <div class="control-group formSep">
							<label class="control-label">产品单位</label>
							<div class="controls">
								<select name="productUnit" id="productUnit_select" class="chzn_a span6">
									<c:forEach var="pt" items="${productUnitList}" varStatus="st">
										<option value="${pt.code}" <c:if test="${pt.value==cardKeysProduct.productUnit }">selected="selected"</c:if> >${pt.value }</option>
									</c:forEach>
						        </select>
							</div>
						</div>
                        <div class="control-group formSep">
	                        <label class="control-label">金额（元）</label>
	                        <div class="controls">
		                        <input type="text" class="span6" id="amount" name="amount" value="${cardKeysProduct.amount}"  />
		                        <span class="help-block"></span>
	                        </div>
                        </div>
                        <div class="control-group formSep">
	                        <label class="control-label">已发总数</label>
	                        <div class="controls">
		                        <input type="text" class="span6" id="totalNum" name="totalNum" value="${cardKeysProduct.totalNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" readonly="readonly"/>
		                        <span class="help-block"></span>
	                        </div>
                        </div>
                        <div class="control-group formSep">
	                        <label class="control-label">可购数量</label>
	                        <div class="controls">
		                        <input type="text" class="span6" id="availableNum" name="availableNum" value="${cardKeysProduct.availableNum}" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')" />
		                        <span class="help-block"></span>
	                        </div>
                        </div>
                        <div class="control-group formSep">
	                        <label class="control-label">供应商</label>
	                        <div class="controls">
		                        <input type="text" class="span6" id="supplier" name="supplier" value="${cardKeysProduct.supplier}" />
		                        <span class="help-block"></span>
	                        </div>
                        </div>
						<div class="control-group formSep">
	                        <label class="control-label">LOGO</label>
	                        <div class="controls">
	                        <table class="table table-bordered table-striped table_vam cols-6" id="dt_gal">
		                        <thead>
			                        <tr>
				                        <th>图片</th>
				                        <th>日期</th>
			                        </tr>
		                        </thead>
		                        <tbody>
			                        <tr>
				                        <td style="width:60px" >
					                        <a href="${cardKeysProduct.logoUrl }" title="Image" class="cbox_single thumbnail">
					                        	<img alt="" src="${cardKeysProduct.logoUrl }" style="height:50px;width:50px">
					                        </a>
				                        </td>
				                        <td ><fmt:formatDate value="${cardKeysProduct.updateTime }" pattern="yyyy-MM-dd HH-mm-ss" /> </td>
			                        </tr>
		                        </tbody>
	                        </table>
	                        </div>
	                        <div class="controls">
		                        <div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
			                        <div class="input-append">
				                        <div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div>
				                        <span class="btn btn-file">
					                        <span class="fileupload-new">选择文件</span>
					                        <span class="fileupload-exists">重新选择</span>
					                        <input type="file"  id="logoUrl" name="logoUrl" />
				                        </span>
				                        <a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
			                        </div>
		                        </div>
	                        </div>
				        </div>
						<div class="control-group">
							<label class="control-label">产品描述</label>
							<div class="controls">
								<textarea  rows="4" class="span6" name="productDesc" id="productDesc" >${cardKeysProduct.productDesc} </textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注</label>
							<div class="controls">
								<textarea  rows="4" class="span6" name="remarks" id="remarks">${cardKeysProduct.remarks} </textarea>
							</div>
						</div>
						<div class="control-group ">
					        <div class="controls">
						        <sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT_EDITCOMMIT')">
						        	<button class="btn btn-primary" type="submit" id="editSubmitBtn" >保存</button>
						        </sec:authorize>
						        <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
					        </div>
				        </div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script>

/* $(function(){
    $('#productType_select').chosen();
}); */
</script>
</html>
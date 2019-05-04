<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/merchant/product/addProduct.js"></script>
</head>

<script type="text/javascript">

</script>

<body>
	<div id="maincontainer" class="clearfix">
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
          <!-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                	 <nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/merchant/product/listProduct.do">产品管理</a></li>
			                    <li>新增产品</li>
			                </ul>
			            </div>
			         </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx }/merchant/product/addProductCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								<h3 class="heading">新增产品</h3>
								<div class="row-fluid">
									<div class="span12">
										<label class="control-label">商户名称</label>
										 <div class="controls">
											<select name="mchntId" id="mercahnt_select" data-placeholder="请点击选择商户" class="chzn_a span6">
													<option value=""></option>
													 <c:forEach var="rs" items="${mchntList}" varStatus="st">
													 		<option value="${rs.mchntId}">${rs.mchntName } (${rs.mchntCode })</option>
													 </c:forEach>
					                        </select>
				                        </div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">产品名称</label>
										<div class="controls">
											<input class="span6" type="text" id="productName" name="productName" value="${merchantInf.productName }">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">产品号</label>
										<div class="controls">
											<input class="span6" type="text" id="productCode" name="productCode">
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">卡BIN</label>
										<div class="controls">
										<input class="span6" type="text" id="cardBin" name="cardBin">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">署名类型</label>
										<div class="controls">
										<select id="onymousStat" name="onymousStat" class="span6">
											<option value="00">非记名卡</option>
											<option value="10">记名卡</option>
										</select>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">业务类型</label> 
										<div class="controls">
										<select id="businessType" name="businessType" class="span6">
											<option value="40">微信支付业务</option>
										</select>
										</div>
									</div>
									<div class="span6">
										<label class="control-label">产品类型</label>
										<div class="controls">
											<select id="productType" name="productType" class="span6">
												<option value="00">充值卡</option>
												<option value="10">礼品卡</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">最大消费次数</label>
										<div class="controls">
											<input class="span6" type="text" id="consumTimes" name="consumTimes" oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="6">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">最大充值次数</label> 
										<div class="controls">
											<input class="span6" type="text" id="rechargeTimes" name="rechargeTimes" oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="6">
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">最大余额</label>
										<div class="controls">
											<input class="span6" type="text" id="maxBalance" name="maxBalance" oninput='this.value=this.value.replace(/[^\d.]/g,"")' maxlength="10">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">最终卡序号 </label>
										<div class="controls">
											<input class="span6" type="text" id="lastCardNum" name="lastCardNum">
										</div>
									</div>
								</div>
							    <div class="row-fluid">
									<label class="control-label">卡面</label>
								    <div class="controls">
										<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
											<div class="input-append">
												<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div>
												<span class="btn btn-file">
													<span class="fileupload-new">选择文件</span>
													<span class="fileupload-exists">重新选择</span>
													<input type="file"  name="cardFaceImgs[]"/>
												</span>
												<a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<label class="control-label">备注</label>
									<div class="controls">
										<textarea rows="6" class="span6" name="remarks"></textarea>
										<span class="help-block"></span>
									</div>
								</div>
								<div class="control-group ">
									<div class="controls">
										<sec:authorize access="hasRole('ROLE_MCHNT_PRODUCT_ADDCOMMIT')">
											<button class="btn btn-primary" type="button" id="addSubmitBtn">保 存</button>
										</sec:authorize>
										<button class="btn btn-inverse btn-primary" type="button" id="returnBtn">返 回</button>
									</div>
								</div>
							</form>
					     </div>
				     </div>
				</div>
		</div>
 </div>				
</body>
<script type="text/javascript">
$(function(){
    $('#mercahnt_select').chosen();
});
$('#cardFaceImgs').fileinput({
	showUpload: false,
	showCaption: false,
    language: 'fr',
    uploadUrl: '${ctx }/merchant/product/addProductUpload.do', 
    maxFileSize: 1024,
    maxFilesNum: 10,
    allowedFileExtensions : ['jpg', 'png','gif'],
});
</script>
</html>
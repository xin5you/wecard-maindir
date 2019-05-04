<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>

      <script src="${ctx}/resource/js/module/merchant/merchantInf/addMerchantInf.js"></script>
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
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/merchant/merchantInf/listMerchantInf.do">商户管理</a></li>
			                     <li>新增商户</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="merchantForm" action="${ctx }/merchant/merchantInf/addmerchantInfCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">新增商户</h3>
								 <div class="tabbable">
	                                <ul class="nav nav-tabs">
	                                    <li class="active"><a id="addMerchantInf_step1_btn"  href="#addMerchantInf_step1" data-toggle="tab">商户管理</a></li>
	                                    <li><a id="addMerchantInf_step2_btn" href="#addMerchantInf_step2" data-toggle="tab">默认门店</a></li>
	                                    <li><a id="addMerchantInf_step3_btn" href="#addMerchantInf_step3" data-toggle="tab">商户明细</a></li>
	                                </ul>
                                 <div class="tab-content">
                                    <div class="tab-pane active" id="addMerchantInf_step1">
							         <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mchntName" name="mchntName"/>
							                 <span class="help-block"></span>
							             </div>
							     	</div>
							         <div class="control-group">
							             <label class="control-label">行业类型</label>
							             <div class="controls">
							                  <select class="form-control span6" id="industryType1" name="industryType1">
												 <c:forEach var="rs" items="${industryList}" varStatus="st">
												 		<option value="${rs.id}">${rs.industryName }</option>
												 </c:forEach>
											</select>
							                 <span class="help-block"></span>
							             </div>
							         </div>
							         <div class="control-group">
							             <label class="control-label">福利余额启用标识</label>
							             <div class="controls">
							                  <select class="form-control span6" id="mchntType1" name="mchntType1">
												 <c:forEach var="mt" items="${mchntTypeList}" varStatus="st">
												 		<option value="${mt.code}">${mt.name }</option>
												 </c:forEach>
											</select>
							                 <span class="help-block"></span>
							             </div>
							         </div>
							         <div class="control-group ">
					                            <div class="controls">
					                                <button class="btn btn-primary" type="button" id="addMerchantInfBtn1" >下一步</button>
					                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
					                            </div>
					                  </div>
				                 </div>
				                 <div class="tab-pane" id="addMerchantInf_step2">
				                 
				                 	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 
							     		<div class="control-group">
							             <label class="control-label">旗舰店名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopName" name="shopName"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">所在区域</label>
							             <div class="controls">
							                  <div data-toggle="distpicker">
										          <select class="form-control" id="provincePage" name="provincePage" data-province="---- 选择省 ----"></select>
										          <select class="form-control" id="cityPage" name="cityPage" data-city="---- 选择市 ----"></select>
										          <select class="form-control" id="districtPage" name="districtPage" data-district="---- 选择区 ----"></select>
										      </div>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">详细地址</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="address" name="address"/>
							                  <button class="btn btn-primary btn-map-address" type="button">获取经纬度</button>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">经度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="longitude" name="longitude" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">纬度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="latitude" name="latitude" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">旗舰店客服电话</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="telephone" name="telephone"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							             <div class="control-group formSep">
                                               <label class="control-label">店铺照</label>
                                                <div class="controls">
                                               		<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
														<div class="input-append">
															<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file"  name="fileShopInfImgs[]"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
														</div>
													</div>
												</div>
                                           </div>
<!-- 							         	<div class="control-group formSep"> -->
<!-- 							             <label class="control-label">商品照片</label> -->
<!-- 							              <div  class="controls"> -->
<!-- 								                 <input id="fileShopMenuImgs" name="fileShopMenuImgs[]" type="file" multiple> -->
<!--               									  <span class="help-block"></span> -->
<!-- 							             </div> -->
<!-- 							        </div> -->
							        <div class="control-group formSep">
							             <label class="control-label">店内照</label>
							              <div  class="controls">
								                  <input id="fileShopInStoreImgs" name="fileShopInStoreImgs[]" type="file" multiple>
              									  <span class="help-block"></span>
							             </div>
							        </div>
							        <div class="control-group">
							             <label class="control-label">营业时间</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="businessHours" name="businessHours"/>
							                 <span class="help-block"></span>
							             </div>
							     	</div>
							     	<div class="control-group">
							             <label class="control-label">星级评价</label>
							             <div class="controls">
							                 <select class="form-control span6" id="evaluate" name="evaluate" >
											 		<option value="20">1</option>
											 		<option value="40">2</option>
											 		<option value="60">3</option>
											 		<option value="80">4</option>
											 		<option value="100">5</option>
											</select>
							             </div>
							     	</div>
							     	<div class="control-group">
							             <label class="control-label">备注</label>
							             <div class="controls">
							                  <textarea  rows="6" class="span6" ></textarea>
							                 <span class="help-block"></span>
							             </div>
							     	</div>
							       <div class="control-group ">
				                            <div class="controls">
				                                <button class="btn btn-primary" type="button" id="addMerchantInfBtn2" >下一步</button>
				                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
				                            </div>
				                  	</div>
				                 </div>
				                 <div class="tab-pane" id="addMerchantInf_step3">
				                 		<div class="formSep">
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业名称</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntName" name="mListmchntName">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">组织机构</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntCode" name="mListmchntCode">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">营业执照</label>
													<div class="controls">
														<input class="span6" type="text" id="busLicenceCode" name="busLicenceCode">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人姓名</label>
													<div class="controls">
														<input class="span6" type="text" id="name" name="name">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">法人身份证</label>
													<div class="controls">
														<input class="span6" type="text" id="idCardNo" name="idCardNo">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人手机号</label>
													<div class="controls">
														<input class="span6" type="text" id="phoneNumber" name="phoneNumber" >
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户银行</label>
													<div class="controls">
														<input class="span6" type="text" id="backName" name="backName">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业开户银行账号</label>
													<div class="controls">
														<input class="span6" type="text" id="backAct" name="backAct" >
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户名称</label>
													<div class="controls">
														<input class="span6" type="text" id="backActName" name="backActName">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业规模</label>
													<div class="controls">
													<select  id="insScale"  name="insScale">
														<option value="50">50人以下</option>
														<option value="50">50人至150人</option>
														<option value="500">150人至500人</option>
														<option value="501">500人 以上</option>
													</select>
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">商业品牌</label>
													<div class="controls">
													<input class="span6" type="text" id="brandName" name="brandName" >
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业类型</label>
													<div class="controls">
													<select  id="mchntType"  name="mchntType">
														<option value="1">民营</option>
														<option value="2">国企</option>
														<option value="3">外企</option>
													</select>
													</div>
												</div>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">组织机构文件代码文件</label>
								             <div class="controls">
													<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
														<div class="input-append">
															<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file"  name="insCodeCards[]"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
														</div>
													</div>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">企业工商营业执照文件</label>
								             <div class="controls">
													<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
														<div class="input-append">
															<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file" name="businessLicences[]"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
														</div>
													</div>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">法人身份证正面照片</label>
								             <div class="controls">
													<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
														<div class="input-append">
															<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file"  name="idCards[]"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
														</div>
													</div>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">商户品牌LOG照片</label>
								             <div class="controls">
													<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" class="span6"/>
														<div class="input-append">
															<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file" name="brandLogos[]"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
														</div>
													</div>
											</div>
										</div>
										<div class="control-group">
							             <label class="control-label">邀请码</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="inviteCode" name="inviteCode" maxlength="15"/>
							                 <span class="help-block">请输入15位邀请码</span>
							             </div>
							     		</div>
										<div class="control-group">
								             <label class="control-label">备注</label>
								             <div class="controls">
								                  <textarea  rows="6" class="span6" id="mListRemarks" name="mListRemarks" ></textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
								     	
								     	       <div class="control-group ">
				                            <div class="controls">
					                            <sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_ADDCOMMIT')">
					                                <button class="btn btn-primary" type="button" id="addMerchantInfBtn3" >保存</button>
				                                </sec:authorize>
				                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
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
</body>
<script>
// $('#fileShopInfImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
//     language: 'fr',
//     uploadUrl: '${ctx }/merchant/merchantInf/addmerchantInfUpload.do', 
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
// $('#fileShopMenuImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
// 	uploadUrl: '${ctx }/merchant/merchantInf/addmerchantInfUpload.do', 
//     language: 'fr',
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
$('#fileShopInStoreImgs').fileinput({
	language: 'zh', //设置语言
	uploadUrl: '${ctx }/merchant/merchantInf/addmerchantInfUpload.do', 
	allowedFileExtensions : ['jpg', 'png','gif','jpeg'],//接收的文件后缀,
	maxFileCount:10,
	enctype: 'multipart/form-data',
	showUpload: false, //是否显示上传按钮
	showCaption: false,//是否显示标题
	browseClass: "btn btn-primary", //按钮样式 
	previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
	msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
});
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

	

      <link href="${ctx }/resource/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
      
      <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.data.js"></script>
	  <script src="${ctx}/resource/jQueryDistpicker/js/distpicker.js"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
      
      <script src="${ctx}/resource/js/module/merchant/merchantInf/editMerchantInf.js"></script>
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
			                     <li>编辑商户</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="merchantForm" action="${ctx }/merchant/merchantInf/editMerchantInfCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <input type="hidden" id="mchntId" name="mchntId" value="${merchantInf.mchntId }">
								 <h3 class="heading">编辑商户</h3>
								 <div class="tabbable">
	                                <ul class="nav nav-tabs">
	                                    <li class="active"><a id="editMerchantInf_step1_btn"  href="#editMerchantInf_step1" data-toggle="tab">商户管理</a></li>
	                                    <li><a id="editMerchantInf_step3_btn" href="#editMerchantInf_step3" data-toggle="tab">商户明细</a></li>
	                                </ul>
                                 <div class="tab-content">
                                    <div class="tab-pane active" id="editMerchantInf_step1">
							         <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mchntName" name="mchntName" value="${merchantInf.mchntName }"/>
							                 <span class="help-block"></span>
							             </div>
							     	</div>
							         <div class="control-group">
							             <label class="control-label">行业类型</label>
							             <div class="controls">
							                  <select class="form-control span6" id="industryType1" name="industryType1">
												 <c:forEach var="rs" items="${industryList}" varStatus="st">
												 		<option value="${rs.id}"<c:if test="${merchantInf.industryType1==rs.id }">selected="selected"</c:if>  >${rs.industryName }</option>
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
												 		<option value="${mt.code}" <c:if test="${mt.code==merchantInf.mchntType }">selected="selected"</c:if>>${mt.name }</option>
												 </c:forEach>
											</select>
							                 <span class="help-block"></span>
							             </div>
							         </div>
							         <div class="control-group ">
					                            <div class="controls">
					                                <button class="btn btn-primary" type="button" id="editMerchantInfBtn1" >下一步</button>
					                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
					                            </div>
					                  </div>
				                 </div>
				                 <div class="tab-pane" id="editMerchantInf_step3">
				                 		<div class="formSep">
											<div class="row-fluid">
												<div class="span6">
												
													<label class="control-label">企业名称</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntName" name="mListmchntName" value="${mInflist.mchntName }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">组织机构</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntCode" name="mListmchntCode" value="${mInflist.mchntCode }">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">营业执照</label>
													<div class="controls">
														<input class="span6" type="text" id="busLicenceCode" name="busLicenceCode" value="${mInflist.busLicenceCode }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人姓名</label>
													<div class="controls">
														<input class="span6" type="text" id="name" name="name" value="${mInflist.name }">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">法人身份证</label>
													<div class="controls">
														<input class="span6" type="text" id="idCardNo" name="idCardNo" value="${mInflist.idCardNo }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人手机号</label>
													<div class="controls">
														<input class="span6" type="text" id="phoneNumber" name="phoneNumber" value="${mInflist.phoneNumber }">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户银行</label>
													<div class="controls">
														<input class="span6" type="text" id="backName" name="backName" value="${mInflist.backName }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业开户银行账号</label>
													<div class="controls">
														<input class="span6" type="text" id="backAct" name="backAct" value="${mInflist.backAct }">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户名称</label>
													<div class="controls">
														<input class="span6" type="text" id="backActName" name="backActName" value="${mInflist.backActName }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业规模</label>
													<div class="controls">
													<select  id="insScale"  name="insScale">
														<option value="49" <c:if test="${mInflist.insScale=='49' }">selected="selected"</c:if> >50人以下</option>
														<option value="150" <c:if test="${mInflist.insScale=='150' }">selected="selected"</c:if> >50人至150人</option>
														<option value="500" <c:if test="${mInflist.insScale=='500' }">selected="selected"</c:if> >150人至500人</option>
														<option value="501" <c:if test="${mInflist.insScale=='501' }">selected="selected"</c:if> >500人 以上</option>
													</select>
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">商业品牌</label>
													<div class="controls">
													<input class="span6" type="text" id="brandName" name="brandName" value="${mInflist.brandName }">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业类型</label>
													<div class="controls">
													<select  id="mchntType"  name="mchntType">
														<option value="1" <c:if test="${mInflist.mchntType=='1' }">selected="selected"</c:if> >民营</option>
														<option value="2" <c:if test="${mInflist.mchntType=='2' }">selected="selected"</c:if> >国企</option>
														<option value="3" <c:if test="${mInflist.mchntType=='3' }">selected="selected"</c:if> >外企</option>
													</select>
													</div>
												</div>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">组织机构文件代码文件</label>
								             <div class="controls">
								             		<c:forEach var="entity" items="${imgList1002}" varStatus="st">
		                                           		<img src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
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
								             		<c:forEach var="entity" items="${imgList1003}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
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
								             		<c:forEach var="entity" items="${imgList1004}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
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
								             		<c:forEach var="entity" items="${imgList1001}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
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
							                 <input type="text" class="span6" id="inviteCode" name="inviteCode" maxlength="15" value="${mInflist.inviteCode }" readonly="readonly"/>
							             </div>
							     		</div>
										<div class="control-group">
								             <label class="control-label">备注</label>
								             <div class="controls">
								                  <textarea  rows="6" class="span6" id="mListRemarks" name="mListRemarks">${mInflist.remarks }</textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
								     	
								     	       <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF_EDITCOMMIT')">
				                                	<button class="btn btn-primary" type="button" id="editMerchantInfBtn3" >保存</button>
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
</html>
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
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
      <script src="${ctx }/resource/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/merchant/shopInf/addShopInf.js"></script>
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
			                    <li><a href="${ctx }/merchant/shopInf/listShopInf.do">门店管理</a></li>
			                     <li>新增门店</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx }/merchant/shopInf/addShopInfCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">新增门店</h3>
						         <input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 	   <div class="control-group">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntId" id="mercahnt_select" data-placeholder="请点击选择商户" class="chzn_a span6">
												<option value=""></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntId}">${rs.mchntName } (${rs.mchntCode })</option>
												 </c:forEach>
				                                </select>
							            </div>
							     		</div>
							     		
							     		   <div class="control-group">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							               		<select name="pShopCode" id="shop_select" data-placeholder="请点击选择门店" class="chzn_a span6">
												
				                                </select>
							            </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">旗舰店名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopName" name="shopName"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">门店类型</label>
                                         <div class="controls">
                                                <select name="shopType" id="shopType" class="chzn_a span6">
                                                 <c:forEach var="mapType" items="${mapType}">
                                                        <option value="${mapType.key}" <c:if test="${mapType.key == '10' }">selected="selected"</c:if> >${mapType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">是否售卡</label>
                                         <div class="controls">
                                                <select name="sellCardFlag" id="sellCardFlag" class="chzn_a span6">
                                                 <c:forEach var="mapCardFlag" items="${mapCardFlag}">
                                                        <option value="${mapCardFlag.key}"  <c:if test="${mapCardFlag.key == '0' }">selected="selected"</c:if> >${mapCardFlag.value}</option>
                                                 </c:forEach>
                                                </select>
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
							                  <textarea  rows="6" class="span6" name="remarks"></textarea>
							                 <span class="help-block"></span>
							             </div>
							     	</div>
							       <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF_ADDCOMMIT')">
				                                <button class="btn btn-primary" type="button" id="addSubmitBtn" >保存</button>
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


$(function(){
    $('#mercahnt_select').chosen();
});

// $('#fileShopInfImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
//     language: 'fr',
//     uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
// $('#fileShopMenuImgs').fileinput({
// 	showUpload: false,
// 	showCaption: false,
// 	uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
//     language: 'fr',
//     maxFileSize: 1024,
//     maxFilesNum: 10,
//     allowedFileExtensions : ['jpg', 'png','gif'],
// });
$('#fileShopInStoreImgs').fileinput({
	language: 'zh', //设置语言
	uploadUrl: '${ctx }/merchant/shopInf/addShopInfUpload.do', 
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
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
      <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
      <script src="${ctx}/resource/js/module/merchant/mchntEshopInf/addMchntEshopInf.js"></script>
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
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/mchnteshop/eShopInf/listMchntEshopInf.do">商城管理</a></li>
			                     <li>新增商城</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/mchnteshop/eShopInf/addMchntEshopInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								 <h3 class="heading">新增商城</h3>

				                 	   <div class="control-group formSep">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<select name="mchntCode" id="mercahnt_select" data-placeholder="--请选择商户--" class="chzn_a span6">
												<option value=""></option>
												 <c:forEach var="rs" items="${mchntList}" varStatus="st">
												 		<option value="${rs.mchntCode}">${rs.mchntName} (${rs.mchntCode})</option>
												 </c:forEach>
				                                </select>
							             </div>
							     		</div>
							     		<div class="control-group formSep">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							                 <select name="shopCode"  id="shopCode"  class="span6"></select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">商城名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="eShopName" name="eShopName"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">渠道Code</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="channelCode" name="channelCode"/>
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		
                                       
		                                <div class="control-group formSep">
		                                    <label class="control-label">商城LOGO</label>
		                                    <div class="controls">
		                                        <div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
		                                            <div class="input-append">
		                                                <div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div>
		                                                <span class="btn btn-file">
		                                                    <span class="fileupload-new">选择文件</span>
		                                                    <span class="fileupload-exists">重新选择</span>
		                                                    <input type="file"  id="eshopLogos" name="eshopLogos" />
		                                                </span>
		                                                <a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
		                                            </div>
		                                        </div>
		                                    </div>
		                                </div>
		                                
		                                <div class="control-group formSep">
                                            <label class="control-label">商城背景图</label>
                                            <div class="controls">
                                                <div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
                                                    <div class="input-append">
                                                        <div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div>
                                                        <span class="btn btn-file">
                                                            <span class="fileupload-new">选择文件</span>
                                                            <span class="fileupload-exists">重新选择</span>
                                                            <input type="file"  id="eshopBackGrounds" name="eshopBackGrounds" />
                                                        </span>
                                                        <a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
							     		
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks" ></textarea>
                                             </div>
                                        </div>
							     		
								       <div class="control-group">
					                            <div class="controls">
					                            <sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_ADDCOMMIT')">
					                                <button class="btn btn-primary"  type="submit" id="addSubmitBtn" >保存</button>
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
    $('#mercahnt_select').chosen({
		no_results_text: "没有查询到您需要输入的数据：",
		placeholder_text : "请输入您需要查询的数据.", 
		search_contains: true,
		disable_search_threshold:10
	});
});
</script>
</html>
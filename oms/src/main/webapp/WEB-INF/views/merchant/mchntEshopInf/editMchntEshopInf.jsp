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
      <script src="${ctx}/resource/js/module/merchant/mchntEshopInf/editMchntEshopInf.js"></script>
      
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
			                    <li>商户信息</li>
			                    <li><a href="${ctx }/mchnteshop/eShopInf/listMchntEshopInf.do">商城管理</a></li>
			                     <li>编辑商城</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/mchnteshop/eShopInf/editMchntEshopInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								 <input type="hidden" id="eShopId" name="eShopId"  value="${mchntEshopInf.eShopId }" />
								 <h3 class="heading">编辑商城</h3>
						         	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 	   <div class="control-group formSep">
							             <label class="control-label">商城名</label>
							             <div class="controls">
							               		<input type="text" class="span6" name="eShopName" id="eShopName"  value="${mchntEshopInf.eShopName}"/>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
                                         <label class="control-label">渠道Code</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="channelCode" name="channelCode" value="${mchntEshopInf.channelCode}" />
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group formSep">
                                               <label class="control-label">商城logo</label>
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
                                                                    <a href="${mchntEshopInf.eShopLogo }" title="Image" class="cbox_single thumbnail">
                                                                        <img alt="" src="${mchntEshopInf.eShopLogo }" style="height:50px;width:50px">
                                                                    </a>
                                                                </td>
                                                                <td ><fmt:formatDate value="${mchntEshopInf.updateTime }" pattern="yyyy-MM-dd HH-mm-ss" /> </td>
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
                                                                    <a href="${mchntEshopInf.bgUrl }" title="Image" class="cbox_single thumbnail">
                                                                        <img alt="" src="${mchntEshopInf.bgUrl }" style="height:50px;width:50px">
                                                                    </a>
                                                                </td>
                                                                <td ><fmt:formatDate value="${mchntEshopInf.updateTime }" pattern="yyyy-MM-dd HH-mm-ss" /> </td>
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
                                                            <input type="file"  id="eshopBackGrounds" name="eshopBackGrounds" />
                                                        </span>
                                                        <a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
                                                    </div>
                                                </div>
                                            </div>
				                 		</div>
				                 		
				                 	   <div class="control-group">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<input type="text" class="span6"  name="mchntName" id="mchntName"  value="${mchntEshopInf.mchntName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		 <div class="control-group">
							             <label class="control-label">商户CODE</label>
							             <div class="controls">
							               		<input type="text" class="span6" name="mchntCode" id="mchntCode"  value="${mchntEshopInf.mchntCode}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							               		<input type="text" class="span6" name="shopName" id="shopName"  value="${mchntEshopInf.shopName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		 <div class="control-group">
							             <label class="control-label">门店CODE</label>
							             <div class="controls">
							               		<input type="text" class="span6"  name="shopCode" id="shopCode" value="${mchntEshopInf.shopCode}" readonly="readonly" />
							             </div>
							     		</div>
							     		
								     	<div class="control-group">
								             <label class="control-label">备注</label>
								             <div class="controls">
								                  <textarea  rows="4" class="span6" name="remarks" id="remarks">${mchntEshopInf.remarks} </textarea>
								             </div>
								     	</div>
							     	
							       <div class="control-group ">
				                            <div class="controls">
				                            <sec:authorize access="hasRole('ROLE_MCHNT_ESHOPINF_EDITCOMMIT')">
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

$(function(){
    $('#mercahnt_select').chosen();
});
</script>
</html>
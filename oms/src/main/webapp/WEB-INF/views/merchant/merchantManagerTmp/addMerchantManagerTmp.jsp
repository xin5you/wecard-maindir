<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>

      <link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
      <script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
      <script src="${ctx}/resource/js/module/merchant/merchantManagerTmp/addMerchantManagerTmp.js"></script>
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
			                    <li><a href="${ctx }/merchant/managerTmp/listMerchantManagerTmp.do">商户员工管理</a></li>
			                     <li>新增员工</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">新增员工</h3>

				                 	   <div class="control-group formSep">
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
							     		<div class="control-group formSep">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							                 <select name="shopId" id="shopId"  class="span6"></select>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">所属角色</label>
							             <div class="controls">
							                 <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="100" />商户boss
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="200" />商户财务
							                </label> 
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="300" />商户店长
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="400" />商户收银员
							                </label>
							                <label class="checkbox">
							                 	<input name="roleType" type="checkbox" value="500" />商户服务员
							                </label>
							                 <!-- <select name="roleType" id="roleType"  class="span6">
							                 	<option value="100">商户boss</option>
							                 	<option value="200">商户财务</option>
							                 	<option value="300">商户店长</option>
							                 	<option value="400">商户收银员</option>
							                 	<option value="500">商户服务员</option>
							                 </select> -->
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
							             <label class="control-label">用户姓名</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="name" name="name" />
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
								             <label class="control-label">手机号</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="phoneNumber" name="phoneNumber" />
								                 <span class="help-block"></span>
								             </div>
							     		</div>
							     		
							     		<div class="control-group formSep">
								             <label class="control-label">员工编号</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="remarks" name="remarks"/>
								                 <span class="help-block"></span>
								             </div>
							     		</div>
								       <div class="control-group">
					                            <div class="controls">
					                                <button class="btn btn-primary" type="submit" id="addSubmitBtn" >保存</button>
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
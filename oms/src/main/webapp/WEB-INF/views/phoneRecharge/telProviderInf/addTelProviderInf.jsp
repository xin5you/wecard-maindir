<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
     <script src="${ctx}/resource/js/module/phoneRecharge/telProviderInf/addTelProviderInf.js"></script>
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
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/provider/providerInf/listTelProviderInf.do">供应商信息管理</a></li>
			                    <li>新增供应商信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/provider/providerInf/addTelProviderInfCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								<h3 class="heading">新增供应商信息</h3>
					     		<div class="control-group formSep">
					             	<label class="control-label">供应商名称</label>
						             <div class="controls">
											<input type="text" class="span6" id="providerName" name="providerName" maxlength="32"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">app_url</label>
						             <div class="controls">
											<input type="text" class="span6" name="appUrl" id="appUrl" maxlength="128"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">app_Secret</label>
						             <div class="controls">
											<input type="text" class="span6" name="appSecret" id="appSecret" maxlength="64"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">access_token</label>
						             <div class="controls">
											<input type="text" class="span6" name="accessToken" id="accessToken" maxlength="64"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">默认路由标识</label>
						             <div class="controls">
						               		<select name="defaultRoute" id="defaultRoute" class="span6" <c:if test="${defaultRouteState == '0' }"> readonly="readonly" disabled="disabled" </c:if> >
											 <c:forEach var="drList" items="${defaultRouteList}" varStatus="st">
											 		<option value="${drList.code}">${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
	                                  <label class="control-label">供应商折扣</label>
	                                  <div class="controls">
	                                      <input type="text" class="span6" id="providerRate" name="providerRate" onkeyup="checkPrice(this)" maxlength="8"/>
	                                      <span class="help-block"></span>
	                                  </div>
                                 </div>

                                <div class="control-group formSep">
                                    <label class="control-label">操作顺序</label>
                                    <div class="controls">
                                          <input type="text" class="span6" id="operSolr" name="operSolr" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
                                          <span class="help-block"></span>
                                    </div>
                                </div>
                                
                                <div class="control-group">
                                     <label class="control-label">备注</label>
                                     <div class="controls">
                                          <textarea  rows="4" class="span6" name="remarks" id = "remarks" maxlength="256"></textarea>
                                     </div>
                                </div>
						       <div class="control-group">
			                            <div class="controls">
			                            <sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF_ADDCOMMIT')">
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
<script type="text/javascript">
//验证价格（带有小数点，小数点最多是三位）
function checkPrice(obj){
      obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
	  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
	  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
	  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');//只能输入三个小数   
	  if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
	   obj.value= parseFloat(obj.value);  
	  }  
}
</script>
</html>
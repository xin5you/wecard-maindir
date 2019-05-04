<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
     <script src="${ctx}/resource/js/module/phoneRecharge/telChannelProduct/addTelChannelProduct.js"></script>
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
			                    <li><a href="${ctx }/channel/product/listTelChannelProduct.do">分销商充值产品管理</a></li>
			                     <li>新增分销商充值产品信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form action="${ctx }/channel/product/addTelChannelProductCommit.do" id="mainForm"  class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data" >
								<h3 class="heading">新增分销商充值产品信息</h3>
		                       	<div class="control-group formSep">
					             	<label class="control-label">运营商产品名称</label>
						             <div class="controls">
		           			   	   			<input id="operName" name="operName" type="text" class="span6" maxlength="32"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">运营商</label>
						             <div class="controls">
						               		<select name="operId" id="operId" class="span6">
											 <c:forEach var="drList" items="${operIdList}" varStatus="st">
											 		<option value="${drList.code}">${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">区分地区标识</label>
						             <div class="controls">
						               		<select name="areaFlag" id="areaFlag" disabled="disabled" class="span6">
											 <c:forEach var="drList" items="${areaFlagList}" varStatus="st">
											 		<option value="${drList.code}"  <c:if test="${drList.code== '1'}">selected</c:if>>${drList.value}</option>
											 </c:forEach>
			                                </select>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品面额（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productAmt" id="productAmt" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品售价（元）</label>
						             <div class="controls">
											<input type="text" class="span6" name="productPrice" id="productPrice" onkeyup="checkProductPrice(this)" maxlength="12"/>
						                 	<span class="help-block"></span>
						             </div>
					     		</div>
					     		<div class="control-group formSep">
						             <label class="control-label">产品类型</label>
						             <div class="controls">
						               		<select name="productType" id="productType" class="span6">
											 <c:forEach var="drList" items="${productTypeList}" varStatus="st">
											 		<option value="${drList.code}">${drList.value}</option>
											 </c:forEach>
			                                </select>
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
			                            <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT_ADDCOMMIT')">
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
<script type="text/javascript">
//验证产品售价
function checkProductPrice(obj){
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
</body>
</html>
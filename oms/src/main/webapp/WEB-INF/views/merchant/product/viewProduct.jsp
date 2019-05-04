<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
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
			                    <li>产品详情</li>
			                </ul>
			            </div>
			         </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
								 <input type="hidden" id="productCode" name="productCode"  value="${product.productCode }" >
								 <h3 class="heading">产品详情</h3>
						         <input type="hidden" name="province" id="province">
				                 <input type="hidden" name="city" id="city">
				                 <input type="hidden" name="district" id="district">
				                 <div class="row-fluid">
							     	<label class="control-label">所属商户</label>
							     	<div class="controls">
							        	<input type="text" class="span6"  value="${merchantInf.mchntName}(${merchantInf.mchntCode})" disabled="disabled" />
							     	</div>
							    </div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">产品名称</label>
										<div class="controls">
											<input class="span6" type="text" id="productName" name="productName" value="${product.productName}" disabled="disabled">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">产品号</label>
										<div class="controls">
											<input class="span6" type="text" id="productCode" name="productCode" value="${product.productCode}" disabled="disabled"> 
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">卡BIN</label>
										<div class="controls">
											<input class="span6" type="text" id="cardBin" name="cardBin" value="${product.cardBin}" disabled="disabled">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">署名类型</label>
										<div class="controls">
											<select id="onymousStat" name="onymousStat" class="span6" value="${product.onymousStat}" disabled="disabled"> 
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
											<select id="businessType" name="businessType" class="span6" value="${product.businessType}" disabled="disabled">
												<option value="40">微信支付业务</option>
											</select>
										</div>
									</div>
									<div class="span6">
										<label class="control-label">产品类型</label>
										<div class="controls">
											<select id="productType" name="productType" class="span6" value="${product.productType}"  disabled="disabled">
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
											<input class="span6" type="text" id="consumTimes" name="consumTimes" oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="6" value="${product.consumTimes}" disabled="disabled">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">最大充值次数</label>
										<div class="controls">
											<input class="span6" type="text" id="rechargeTimes" name="rechargeTimes" oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="6" value="${product.rechargeTimes}" disabled="disabled">
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<label class="control-label">最大余额</label>
										<div class="controls">
											<input class="span6" type="text" id="maxBalance" name="maxBalance" oninput='this.value=this.value.replace(/\D/gi,"")' maxlength="10" value="${product.maxBalance/100}" disabled="disabled">
										</div>
									</div>
									<div class="span6">
										<label class="control-label">最终卡序号 </label>
										<div class="controls">
											<input class="span6" type="text" id="lastCardNum" name="lastCardNum" value="${product.lastCardNum}"  disabled="disabled">
										</div>
									</div>
								</div>
							    <div class="row-fluid">
                                	<label class="control-label">卡面</label>
                                	<div class="controls">
	                                	<c:forEach var="entity" items="${imgList3001}" varStatus="st">
		                                	<img alt="卡面" src="${entity.imageUrl }"  width="260" height="300">
										</c:forEach>
									</div>
                               	</div>
								<div class="row-fluid">
									<label class="control-label">备注</label>
									<div class="controls">
										<textarea rows="6" class="span6" name="remarks" disabled="disabled">${product.remarks}</textarea>
										<span class="help-block"></span>
									</div>
								</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
 </div>				
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
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
			                     <li>商户详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">

								 <h3 class="heading">商户详情</h3>
				                 	    <div class="control-group">
							             <label class="control-label">商户名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="mchntName" name="mchntName" value="${merchantInf.mchntName }" readonly="readonly"/>
							             </div>
							     	</div>
							         <div class="control-group">
							             <label class="control-label">行业类型</label>
							             <div class="controls">
							                  <select class="form-control span6" id="industryType1" name="industryType1" readonly="readonly" disabled="disabled">
												 <c:forEach var="rs" items="${industryList}" varStatus="st">
												 		<option value="${rs.id}"<c:if test="${merchantInf.industryType1==rs.id }">selected="selected"</c:if>  >${rs.industryName }</option>
												 </c:forEach>
											</select>
							             </div>
							         </div>
							         <div class="control-group">
							             <label class="control-label">是否启用福利余额</label>
							             <div class="controls">
							                  <select class="form-control span6" id="mchntType1" name="mchntType1" readonly="readonly" disabled="disabled">
												 <c:forEach var="mt" items="${mchntTypeList}" varStatus="st">
												 		<option value="${mt.code}" <c:if test="${mt.code==merchantInf.mchntType }">selected="selected"</c:if>>${mt.name }</option>
												 </c:forEach>
											</select>
							                 <span class="help-block"></span>
							             </div>
							         </div>
							         	<div class="formSep">
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业名称</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntName" name="mListmchntName" value="${mInflist.mchntName }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">组织机构</label>
													<div class="controls">
														<input class="span6" type="text" id="mListmchntCode" name="mListmchntCode" value="${mInflist.mchntCode }" readonly="readonly">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">营业执照</label>
													<div class="controls">
														<input class="span6" type="text" id="busLicenceCode" name="busLicenceCode" value="${mInflist.busLicenceCode }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人姓名</label>
													<div class="controls">
														<input class="span6" type="text" id="name" name="name" value="${mInflist.name }" readonly="readonly">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">法人身份证</label>
													<div class="controls">
														<input class="span6" type="text" id="idCardNo" name="idCardNo" value="${mInflist.idCardNo }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">法人手机号</label>
													<div class="controls">
														<input class="span6" type="text" id="phoneNumber" name="phoneNumber" value="${mInflist.phoneNumber }" readonly="readonly">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户银行</label>
													<div class="controls">
														<input class="span6" type="text" id="backName" name="backName" value="${mInflist.backName }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业开户银行账号</label>
													<div class="controls">
														<input class="span6" type="text" id="backAct" name="backAct" value="${mInflist.backAct }" readonly="readonly">
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span6">
													<label class="control-label">企业开户名称</label>
													<div class="controls">
														<input class="span6" type="text" id="backActName" name="backActName" value="${mInflist.backActName }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业规模</label>
													<div class="controls">
													<select  id="insScale"  name="insScale" readonly="readonly" disabled="disabled">
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
														<input class="span6" type="text" id="brandName" name="brandName" value="${mInflist.brandName }" readonly="readonly">
													</div>
												</div>
												<div class="span6">
													<label class="control-label">企业类型</label>
													<div class="controls">
													<select  id="mchntType"  name="mchntType" readonly="readonly" disabled="disabled">
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
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">企业工商营业执照文件</label>
								             <div class="controls">
													<c:forEach var="entity" items="${imgList1003}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">法人身份证正面照片</label>
								             <div class="controls">
													<c:forEach var="entity" items="${imgList1004}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
											</div>
										</div>
										<div class="control-group">
											 <label class="control-label">商户品牌LOG照片</label>
								             <div class="controls">
													<c:forEach var="entity" items="${imgList1001}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
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
								                  <textarea  rows="6" class="span6" id="mListRemarks" name="mListRemarks" readonly="readonly">${mInflist.remarks }</textarea>
								             </div>
								     	</div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
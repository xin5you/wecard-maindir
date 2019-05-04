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
      <script src="${ctx}/resource/js/module/phoneRecharge/phoneRechargeShop/editPhoneRechargeShop.js"></script>
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
			                    <li>福利管理</li>
			                    <li><a href="${ctx }/phone/phoneRecharge/listPhoneRechargeShop.do">手机充值商品</a></li>
			                     <li>编辑商品信息</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx}/phone/phoneRecharge/editPhoneRechargeShopCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">编辑商品信息</h3>
							     		<input type="hidden"  id="goodsId" name="goodsId" value="${prs.id }"/>
							     		<div class="control-group">
                                         <label class="control-label">供应商</label>
                                         <div class="controls">
							                 <input type="text" class="span6" id="supplierType" name="supplierType" value="${prs.supplierType }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">运营商</label>
                                         <div class="controls">
							                 <input type="text" class="span6" id="operType" name="operType" value="${prs.operType }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
                                        </div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商品编号</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopNo" name="shopNo" value="${prs.shopNo }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商品面值</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopFace" name="shopFace" value="${prs.shopFace }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商品售价</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopPrice" name="shopPrice" value="${prs.shopPrice }"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">是否可用</label>
                                         <div class="controls">
                                                <select name="isUsable" id="isUsable" class="chzn_a span6" >
                                                 <c:forEach var="IsUsableType" items="${IsUsableTypeList}">
                                                        <option value="${IsUsableType.code}" <c:if test="${IsUsableType.code == prs.isUsable }">selected="selected"</c:if>>${IsUsableType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">商品类型</label>
                                         <div class="controls">
                                                <select name="shopType" id="shopType" class="chzn_a span6">
                                                 <c:forEach var="shopType" items="${ShopTypeList}">
                                                        <option value="${shopType.code}"  <c:if test="${shopType.code == prs.shopType }">selected="selected"</c:if> >${shopType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">商品单位</label>
                                         <div class="controls">
                                                <select name="resv1" id="resv1" class="chzn_a span6">
                                                	<c:forEach var="ShopUnitType" items="${ShopUnitTypeList}">
                                                        <option value="${ShopUnitType.code}" <c:if test="${ShopUnitType.code == prs.resv1 }">selected="selected"</c:if>>${ShopUnitType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks"  id="remarks">${prs.remarks }</textarea>
                                             </div>
                                        </div>
							     		
							       <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP_EDITCOMMIT')">
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
</script>
</html>
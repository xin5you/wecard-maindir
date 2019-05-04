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
      <script src="${ctx}/resource/js/module/phoneRecharge/phoneRechargeShop/addPhoneRechargeShop.js"></script>
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
			                     <li>商品详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
								 <h3 class="heading">商品详情</h3>
							     		<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
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
							                 <input type="text" class="span6" id="shopNo" name="shopNo" readonly="readonly" value="${prs.shopNo }"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商品面值</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopFace" name="shopFace" readonly="readonly" value="${prs.shopFace }"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商品售价</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopPrice" name="shopPrice" readonly="readonly" value="${prs.shopPrice }"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">是否可用</label>
                                         <div class="controls">
                                                <select name="isUsable" id="isUsable" class="chzn_a span6" readonly="readonly">
                                                 <c:forEach var="IsUsableType" items="${IsUsableTypeList}">
                                                        <option value="${IsUsableType.code}" <c:if test="${IsUsableType.code == prs.isUsable }">selected="selected"</c:if>>${IsUsableType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">商品类型</label>
                                         <div class="controls">
                                                <select name="shopType" id="shopType" class="chzn_a span6" readonly="readonly">
                                                 <c:forEach var="shopType" items="${ShopTypeList}">
                                                        <option value="${shopType.code}" <c:if test="${shopType.code == prs.shopType }">selected="selected"</c:if>>${shopType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">商品单位</label>
                                         <div class="controls">
                                                <select name=resv1 id="resv1" class="chzn_a span6" readonly="readonly">
                                                	<c:forEach var="ShopUnitType" items="${ShopUnitTypeList}">
                                                        <option value="${ShopUnitType.code}" <c:if test="${ShopUnitType.code == prs.resv1 }">selected="selected"</c:if>>${ShopUnitType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea   rows="4" class="span6" name="remarks"  id="remarks" readonly="readonly">${prs.remarks }</textarea>
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
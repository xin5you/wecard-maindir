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
			                    <li><a href="${ctx }/merchant/shopInf/listShopInf.do">门店管理</a></li>
			                     <li>门店详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
								 <input type="hidden" id="shopId" name="shopId"  value="${shop.shopId }" >
								 <h3 class="heading">门店详情</h3>
						         	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 	   <div class="control-group">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${merchantInf.mchntName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${shop.pShopName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		   <div class="control-group">
							             <label class="control-label">商户CODE</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${merchantInf.mchntCode}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">旗舰店名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="shopName" name="shopName" value="${shop.shopName }" readonly="readonly" />
							                 
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">门店类型</label>
                                         <div class="controls">
                                                <select name="shopType" id="shopType" class="chzn_a span6" readonly="readonly" >
                                                 <c:forEach var="mapType" items="${mapType}">
                                                        <option value="${mapType.key}" <c:if test="${mapType.key == shop.shopType }">selected="selected"</c:if> >${mapType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">售卡标志</label>
                                         <div class="controls">
                                                <select name="sellCardFlag" id="sellCardFlag" class="chzn_a span6" readonly="readonly" >
                                                 <c:forEach var="mapCardFlag" items="${mapCardFlag}">
                                                        <option value="${mapCardFlag.key}"  <c:if test="${mapCardFlag.key == shop.sellCardFlag }">selected="selected"</c:if> >${mapCardFlag.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
                                        </div>
							     		
							     		<div class="control-group">
							             <label class="control-label">所在区域</label>
							             <div class="controls">
							                  <input type="text" class="span6"  value="${city}" readonly="readonly" />
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">详细地址</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="address" name="address" value="${shop.shopAddr }" readonly="readonly" />
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">经度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="longitude" name="longitude" value="${shop.longitude }" readonly="readonly" />
							                 
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">纬度</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="latitude" name="latitude" readonly="readonly" value="${shop.latitude }"/>
							                 
							             </div>
							     		</div>
							     		<div class="control-group">
							             <label class="control-label">旗舰店客服电话</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="telephone" name="telephone" value="${shop.telephone }" readonly="readonly" />
							             </div>
							     		</div>
							     		 <div class="control-group formSep">
                                               <label class="control-label">店铺照</label>
                                              	<div class="controls">
	                                              	<c:forEach var="entity" items="${imgList2001}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
												 </div>
                                           </div>
<!-- 							         	<div class="control-group formSep"> -->
<!-- 								             <label class="control-label">菜品照片</label> -->
<!-- 								              <div  class="controls"> -->
<%-- 									                <c:forEach var="entity" items="${imgList2003}" varStatus="st"> --%>
<%-- 		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300"> --%>
<%-- 													</c:forEach> --%>
<!-- 								             </div> -->
<!-- 								        </div> -->
								        <div class="control-group formSep">
								             <label class="control-label">店内照片</label>
								              <div  class="controls">
									                <c:forEach var="entity" items="${imgList2002}" varStatus="st">
		                                           		<img alt="店铺照" src="${entity.imageUrl }"  width="260" height="300">
													</c:forEach>
								             </div>
								        </div>
							        	<div class="control-group">
								             <label class="control-label">营业时间</label>
								             <div class="controls">
								                 <input type="text" class="span6" id="businessHours" name="businessHours" value="${shop.businessHours }" readonly="readonly" />
								                 
								             </div>
								     	</div>
								     	<div class="control-group">
								             <label class="control-label">星级评价</label>
								             <div class="controls">
								                 <select class="form-control span6" id="evaluate" name="evaluate"  readonly="readonly" >
												 		<option value="20" <c:if test="${shop.evaluate=='20' }">selected="selected"</c:if>>1</option>
												 		<option value="40" <c:if test="${shop.evaluate=='40' }">selected="selected"</c:if>>2</option>
												 		<option value="60" <c:if test="${shop.evaluate=='60' }">selected="selected"</c:if>>3</option>
												 		<option value="80" <c:if test="${shop.evaluate=='80' }">selected="selected"</c:if>>4</option>
												 		<option value="100" <c:if test="${shop.evaluate=='100' }">selected="selected"</c:if>>5</option>
												</select>
								             </div>
								     	</div>
								     	<div class="control-group">
								             <label class="control-label">备注</label>
								             <div class="controls">
								                  <textarea  rows="6" class="span6" name="remarks" readonly="readonly" >${shop.remarks } </textarea>
								             </div>
								     	</div>
							  
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
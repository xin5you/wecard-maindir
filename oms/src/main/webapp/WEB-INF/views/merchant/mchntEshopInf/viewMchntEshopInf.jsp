<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
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
			                     <li>商城详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="#" class="form-horizontal form_validation_tip">
								 <input type="hidden" id="eShopId" name="eShopId"  value="${mchntEshopInf.eShopId }" >
								 <h3 class="heading">商城详情</h3>
						         	<input type="hidden" name="province" id="province">
				                 	<input type="hidden" name="city" id="city">
				                 	<input type="hidden" name="district" id="district">
				                 		
				                 		<div class="control-group">
							             <label class="control-label">商城名</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${mchntEshopInf.eShopName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
                                         <label class="control-label">渠道Code</label>
                                         <div class="controls">
                                             <input type="text" class="span6" id="channelCode" name="channelCode" value="${mchntEshopInf.channelCode}"  readonly="readonly"  />
                                             <span class="help-block"></span>
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">商城logo</label>
                                         <div class="controls">
                                                <img alt="店铺照" src="${mchntEshopInf.eShopLogo }" width="260" height="300">
                                         </div>
                                        </div>
                                        
                                        <div class="control-group">
                                         <label class="control-label">商城背景图</label>
                                         <div class="controls">
                                                <img alt="店铺照" src="${mchntEshopInf.bgUrl }" width="260" height="300">
                                         </div>
                                        </div>
				                 		
				                 	   <div class="control-group">
							             <label class="control-label">所属商户</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${mchntEshopInf.mchntName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		 <div class="control-group">
							             <label class="control-label">商户CODE</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${mchntEshopInf.mchntCode}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">所属门店</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${mchntEshopInf.shopName}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		 <div class="control-group">
							             <label class="control-label">门店CODE</label>
							             <div class="controls">
							               		<input type="text" class="span6"  value="${mchntEshopInf.shopCode}" readonly="readonly" />
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">商城地址</label>
							             <div class="controls">
							                    <textarea  rows="4" class="span6" name="eShopUrl" readonly="readonly" >${mchntEshopInf.eShopUrl} </textarea>
							             </div>
							     		</div>
							     		
								     	<div class="control-group">
								             <label class="control-label">备注</label>
								             <div class="controls">
								                  <textarea  rows="4" class="span6" name="remarks" readonly="readonly" >${mchntEshopInf.remarks} </textarea>
								             </div>
								     	</div>
							  
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchWithDraw/listBatchWithDrawOrder.js?v=1.7"></script>
	<script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>订单管理</li>
			                    <li><a href="${ctx }/batchWithdrawOrder/listBatchWithdrawOrder.do">批量代付</a></li>
			                     <li>批量开卡列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/batchWithdrawOrder/listBatchWithdrawOrder.do" class="form-inline" method="post">
						<h3 class="heading">代付订单列表</h3>
						<div class="row-fluid" >
							 <div class="span12">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">订单号:</span><input id="orderId" name="orderId" type="text" class="input-medium" value="${order.orderId}"  maxlength="22" />
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">订单名称:</span><input id="orderName" name="orderName" type="text" class="input-medium" value="${order.orderName}"/>
		                       	</div>

								<div class="pull-right">
									<button type="submit" class="btn btn-search"> 查 询 </button>
									<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
									<button type="button" class="btn btn-primary btn-into-export-order">批量代付</button>
								</div>
						  </div>
						</div>
						<div class="row-fluid">
							<div class="pull-right">
								<span class="add-on" style="color: #a94442">账户余额: ${balObj.balSign}${balObj.balance}</span>
							</div>
                          </div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				               <th>订单号码</th>
				               <th>订单名称</th>
								<th>代付总额（元）</th>
				               <th>代付数量</th>
				               <th>订单状态</th>
				               <th>创建人</th>
				               <th>创建时间</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${entity.orderId }</td>
				                 	<td>${entity.orderName }</td>
									 <td>${entity.totalAmount }</td>
									<td>${entity.totalNum }</td>
									<td>${entity.stat }</td>
				                    <td>${entity.createUser }</td>
									 <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                                    <td>
										<c:if test="${entity.stat=='00' }">
											<a orderId="${entity.orderId }" title="代付提交" class="btn-mini btn-submit" href="#">代付提交</a>
											<a orderId="${entity.orderId }" title="删除订单" class="btn-mini btn-delete" href="#">删除订单</a>
										</c:if>
										<a orderId="${entity.orderId }" title="代付详情" class="btn-mini btn-view-list" href="#">代付列表</a>
									</td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
				      </form>
			   </div>
	    </div>


	   <div id="batchExportOrderModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
		   <div class="modal-header">
			   <button class="close" data-dismiss="modal">&times;</button>
			   <h3>文件导入</h3>
		   </div>
		   <form id="uploadMainForm" action="#"  method="post" enctype="multipart/form-data">
			   <div class="modal-body">
				   <div class="control-group">
					   <div class="controls">
						   <div class="control-group formSep">
							   <div class="input-prepend">
							   <span class="add-on">订单名称：</span><input id="batchOrderName" name="batchOrderName" type="text" class="input-medium" value=""/>
							   </div>
							   <div data-provides="fileupload" class="fileupload fileupload-new input-prepend">
								   <input type="hidden" />
								   <span class="add-on">上传文件 ：</span>
								   <div class="input-append">
									   <div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file"  name="file"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
								   </div>
							   </div>
							   <p>如果文件上传失败，请重新刷新页面！再次上传</p>
						   </div>
					   </div>
				   </div>
				   <div style="text-align: center;">
					   <button class="btn btn-primary " type="button" onclick="listBatchOrder.orderImportCommit();" id="orderImportBtn">导 入  </button>
				   </div>
			   </div>
		   </form>
	   </div>


	   <div id="msg" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="height: 200px;">
		   <div class="modal-header">

			   <h3 id="commodityInfModal_h">温馨提示</h3>
		   </div>
		   <br/><br/><br/>
		   <h3 align="center">信息正在处理中，数据未返回请不要关闭页面...</h3>
	   </div>
</body>
</html>

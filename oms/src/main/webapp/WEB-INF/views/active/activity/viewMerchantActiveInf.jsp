<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<script src="${ctx}/resource/js/module/active/activity/viewMerchantActiveInf.js"></script>
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
		                    <li>活动管理</li>
		                    <li><a href="${ctx }/active/activity/listMerchantActives.do">优惠活动列表</a></li>
		                    <li>查看优惠活动</li>
		                </ul>
		            </div>
		        </nav>
				<div class="row-fluid">
					<div class="span12">
						<form id="myForm" class="form-horizontal form_validation_tip" method="post">
							<h3 class="heading">查看优惠活动</h3>
							<div class="tabbable">
								<div class="tab-content">
									<div class="tab-pane active">
										<div class="control-group formSep">
							             	<input id="activeId" value="${entity.activeId}" type="hidden" />
							             	<label class="control-label">所属商户[商户号]：</label>
							             	<div class="controls">
							               		<input type="text" class="span6" id="activeName" value="${entity.mchntName}[${entity.mchntCode}]" readonly />
				                                <input id="mchntId_h" value="${entity.merchantId}" type="hidden" />
				                                <span class="help-block"></span>
							             	</div>
							     		</div>
										<div class="control-group formSep">
											<label class="control-label">活动名称：</label>
											<div class="controls">
												<input type="text" class="span6" id="activeName" value="${entity.activeName}" readonly />
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group formSep">
											<label class="control-label">活动状态：</label>
											<input id="activeStat_h" value="${entity.activeStat}" type="hidden" />
											<div class="controls">
												<select name="activeStat" id="activeStat" class="span6" value="${entity.activeStat}" disabled>
							                     	<option value="">---请选择---</option>
							                     	<c:forEach var="status" items="${activeStatList}" varStatus="sta">
							                     		<option <c:if test="${status.code==entity.activeStat}">selected</c:if> value="${status.code}">${status.name }</option>
							                     	</c:forEach>
							                    </select>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group date date-time-picker formSep">
			                             	<label class="control-label">活动开始时间：</label>
			                             	<div class="controls">
				                             	<input class="span6" id="startTime" name="startTime" readonly type="text" value="${entity.startTime}" />
				                             	<span class="help-block"></span>
			                             	</div>
			                         	</div> 
										<div class="control-group date date-time-picker formSep">
			                             	<label class="control-label">活动结束时间：</label>
			                             	<div class="controls hidden">
				                             	<input class="span6" id="endTime" name="endTime" readonly type="text" value="${entity.endTime}" />
				                             	<span class="help-block"></span>
				                             </div>	
			                         	</div> 
			                         	<div class="control-group formSep">
								             <label class="control-label">活动说明：</label>
								             <div class="controls">
								                 <textarea rows="6" class="span6" id="activeExplain" readonly>${entity.activeExplain}</textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
			                         	<div class="control-group">
								             <label class="control-label">使用规则：</label>
								             <div class="controls">
								                 <textarea rows="6" class="span6" id="activeRule" readonly>${entity.activeRule}</textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
										<div class="control-group">
											<div class="controls">
												<button class="btn btn-primary btn-add-comm" type="button">商品列表</button>
												<button class="btn btn-inverse btn-return" type="button">返 回</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="commListModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-header">
				<button class="close" data-dismiss="modal">&times;</button>
				<h3>优惠活动商品列表</h3>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
					<thead>
						<tr>
							<th>商品名称</th>
							<th>商品面额</th>
							<th>商品成本</th>
							<th>活动售价</th>
							<th>库存</th>
						</tr>
					</thead>
					<tbody id="commList"></tbody>
				</table>
				<div style="text-align: center;">
					<button class="btn btn-close" data-dismiss="modal">关 闭</button>
				</div>
				<br />
			</div>
		</div>
	</body>
</html>
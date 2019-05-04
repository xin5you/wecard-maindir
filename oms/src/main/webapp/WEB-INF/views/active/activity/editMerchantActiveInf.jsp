<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/views/common/init.jsp"%>
		<%@ include file="/WEB-INF/views/common/head.jsp"%>
		<link rel="stylesheet" href="${ctx}/resource/chosen/chosen.css" />
		<script src="${ctx}/resource/chosen/chosen.jquery.js"></script>
		<link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
		<script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
		<script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="${ctx}/resource/js/module/active/activity/editMerchantActiveInf.js"></script>
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
		                    <li>活动信息</li>
		                    <li><a href="${ctx }/active/activity/listMerchantActives.do">优惠活动列表</a></li>
		                    <li>编辑优惠活动</li>
		                </ul>
		            </div>
		        </nav>
				<div class="row-fluid">
					<div class="span12">
						<form id="myForm" class="form-horizontal form_validation_tip" method="post">
							<h3 class="heading">编辑优惠活动</h3>
							<div class="tabbable">
								<div class="tab-content">
									<div class="tab-pane active">
										<div class="control-group formSep">
							             	<input id="activeId" value="${entity.activeId}" type="hidden" />
							             	<label class="control-label">所属商户[商户号]：</label>
							             	<div class="controls">
							               		<select name="mchntId" id="mchntId" class="span6" <c:if test="${entity.activeStat!=0}">disabled</c:if> >
													<c:forEach var="rs" items="${mchntList}" varStatus="st">
														<option <c:if test="${rs.mchntId==entity.merchantId}">selected</c:if> value="${rs.mchntId}">${rs.mchntName}[${rs.mchntCode}]</option>
													</c:forEach>
				                                </select>
				                                <input id="mchntId_h" value="${entity.merchantId}" type="hidden" />
				                                <span class="help-block"></span>
							             	</div>
							     		</div>
										<div class="control-group formSep">
											<label class="control-label">活动名称<span class="f_req">*</span>：</label>
											<div class="controls">
												<input type="text" class="span6" id="activeName" name="activeName" value="${entity.activeName}" <c:if test="${entity.activeStat==1}">readonly</c:if> />
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group formSep">
											<label class="control-label">活动状态<span class="f_req">*</span>：</label>
											<input id="activeStat_h" value="${entity.activeStat}" type="hidden" />
											<div class="controls">
												<select name="activeStat" id="activeStat" class="span6" value="${entity.activeStat}">
							                     	<option value="">---请选择---</option>
							                     	<c:forEach var="status" items="${activeStatList}" varStatus="sta">
							                     		<option <c:if test="${status.code==entity.activeStat}">selected</c:if> value="${status.code}">${status.name }</option>
							                     	</c:forEach>
							                    </select>
												<span class="help-block"></span>
											</div>
										</div>
										<div class="control-group date date-time-picker formSep">
			                             	<label class="control-label">活动开始时间<span class="f_req">*</span>：</label>
			                             	<div class="controls">
				                             	<input class="span6" id="startTime" name="startTime" readonly="readonly" type="text" value="${entity.startTime}" />
				                             	<span class="add-on data-time-icon" <c:if test="${entity.activeStat==1}">hidden</c:if> >
				                             		<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
				                             	</span>
				                             	<span class="help-block"></span>
			                             	</div>
			                         	</div> 
										<div class="control-group date date-time-picker formSep">
			                             	<label class="control-label">活动结束时间<span class="f_req">*</span>：</label>
			                             	<div class="controls hidden">
				                             	<input class="span6" id="endTime" name="endTime" readonly="readonly" type="text" value="${entity.endTime}" />
				                             	<span class="add-on data-time-icon" <c:if test="${entity.activeStat==1}">hidden</c:if> >
				                             		<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
				                             	</span>
				                             	<span class="help-block"></span>
				                             </div>	
			                         	</div> 
			                         	<div class="control-group formSep">
								             <label class="control-label">活动说明：</label>
								             <div class="controls">
								                 <textarea rows="6" class="span6" id="activeExplain" name="activeExplain">${entity.activeExplain}</textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
			                         	<div class="control-group">
								             <label class="control-label">使用规则：</label>
								             <div class="controls">
								                 <textarea rows="6" class="span6" id="activeRule" name="activeRule">${entity.activeRule}</textarea>
								                 <span class="help-block"></span>
								             </div>
								     	</div>
										<div class="control-group">
											<div class="controls">
												<sec:authorize access="hasRole('ROLE_ACTIVES_EIDTCOMMIT')">
												<button class="btn btn-primary" type="submit">确 定</button>
												</sec:authorize>
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
				<h3>优惠活动商品列表（请逐条操作保存）</h3>
				<div id="comm_opr_info" style="color: red;text-align: center;"></div>
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
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="commList"></tbody>
				</table>
				<input id="commNum" type="hidden" />
				<input id="commId_h" type="hidden" />
				<div style="text-align: center;">
					<c:if test="${entity.activeStat!=1}">
						<button class="btn btn-primary btn-add">添 加</button>
					</c:if>
					<button class="btn btn-close" data-dismiss="modal">关 闭</button>
				</div>
				<br />
			</div>
		</div>
	</body>
</html>
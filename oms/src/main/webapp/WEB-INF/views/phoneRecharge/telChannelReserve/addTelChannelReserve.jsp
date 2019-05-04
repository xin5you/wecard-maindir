<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelReserve/addTelChannelReserve.js"></script>
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
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/channel/reserve/listTelChannelReserve.do">备付金管理</a></li>
			                     <li>新增备付金</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="${ctx }/channel/reserve/addTelChannelReserveCommit.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">新增备付金</h3>
							     		
							     		<div class="control-group">
							             <label class="control-label">分销商名称</label>
							             <div class="controls">
                                                <select name="channelId" id=channelId class="chzn_a span6">
                                                <option value="">--请选择--</option>
                                                 <c:forEach var="channelInf" items="${channelInfList}">
                                                        <option value="${channelInf.channelId}" >${channelInf.channelName}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">备付金</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="reserveAmt" name="reserveAmt" />
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">备付金类型</label>
							             <div class="controls">
                                                <select name="reserveType" id="reserveType" class="chzn_a span6">
                                                <option value="">--请选择--</option>
                                                 <c:forEach var="reserveType" items="${reserveTypeList}">
                                                        <option value="${reserveType.code}" >${reserveType.value}</option>
                                                 </c:forEach>
                                                </select>
                                         </div>
							     		</div>
							     		
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks"  id="remarks" maxlength="256" ></textarea>
                                             </div>
                                        </div>
							     		
							       <div class="control-group ">
				                            <div class="controls">
				                            	<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_RESERVE_ADDCOMMIT')">
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
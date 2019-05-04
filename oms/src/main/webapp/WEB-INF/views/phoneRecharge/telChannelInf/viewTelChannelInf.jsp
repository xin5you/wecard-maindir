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
			                    <li><a href="${ctx }/channel/channelInf/listTelChannelInf.do">分销商管理</a></li>
			                     <li>分销商详情</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" action="" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
								 <h3 class="heading">分销商详情</h3>
							     		<div class="control-group">
							             <label class="control-label">分销商名称</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="channelName" name="channelName" value="${telChannelInf.channelName }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">分销商编号</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="channelCode" name="channelCode" value="${telChannelInf.channelCode }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">分销商KEY</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="channelKey" name="channelKey" value="${telChannelInf.channelKey }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">分销商备付金额(元)</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="channelReserveAmt" name="channelReserveAmt" value="${telChannelInf.channelReserveAmt }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">分销商预警金额(元)</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="channelPrewarningAmt" name="channelPrewarningAmt" value="${telChannelInf.channelPrewarningAmt }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		
							     		<div class="control-group">
							             <label class="control-label">管理员手机号</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="phoneNo" name="phoneNo" value="${telChannelInf.phoneNo }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
							     		
							     		<div class="control-group">
							             <label class="control-label">邮箱</label>
							             <div class="controls">
							                 <input type="text" class="span6" id="email" name="email" value="${telChannelInf.email }" readonly="readonly"/>
							                 <span class="help-block"></span>
							             </div>
							     		</div>
                                        
                                        
                                        <div class="control-group">
                                             <label class="control-label">备注</label>
                                             <div class="controls">
                                                  <textarea  rows="4" class="span6" name="remarks"  id="remarks" readonly="readonly">${telChannelInf.remarks }</textarea>
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
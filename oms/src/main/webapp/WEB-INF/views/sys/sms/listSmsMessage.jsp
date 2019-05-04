<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/sys/user/listUser.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
 		
 		<div class="main_content">
 		<nav>
            <div id="jCrumbs" class="breadCrumb module">
                <ul>
                    <li><a href="#"><i class="icon-home"></i></a></li>
                    <li>系统信息</li>
                    <li><a href="${ctx }/sys/sms/listSmsMessage.do">短信管理</a></li>
                     <li>批量发送短信</li>
                </ul>
            </div>
        </nav>

			<input type="hidden" id="operStatus" />
			<h3 class="heading">批量发送短信</h3>
			<div class="row-fluid" id="h_search">
				<form id="mainForm" action="${ctx }/sys/sms/listSmsMessage.do" class="form-horizontal form_validation_tip" method="post" enctype="multipart/form-data">
					<div class="control-group formSep">
                             <label class="control-label">短信模板</label>
                              <div class="controls">
                           		<div data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden" />
									<div class="input-append">
										<div class="uneditable-input"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">重新选择</span><input type="file"  name="file"/></span><a data-dismiss="fileupload" class="btn fileupload-exists" href="#">删除文件</a>
									</div>
									
									<button class="btn btn-primary" type="submit">批量发送</button>
								</div>
							</div>
                     </div>
				</form>
			</div>
	         </br >       
	         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal">
	             <thead>
	             <tr>
	               <th width="10%">手机号</th>
	               <th>短信内容</th>
	               <th width="10%">状态</th>
	             </tr>
	             </thead>
	             <tbody>
	             <c:forEach var="entity" items="${pageInfo}" varStatus="st">
	                 <tr>
	                    <td>${entity.phone}</td>
	                    <td>${entity.content}</td>
						<td>${entity.flag}</td>
						
	                 </tr>
	             </c:forEach>
	             </tbody>
	         </table>
   </div>
</body>
</html>

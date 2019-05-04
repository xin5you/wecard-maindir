<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script src="${ctx}/resource/ztree/jquery.ztree.core.min.js"></script>
	<script src="${ctx}/resource/ztree/jquery.ztree.excheck.js"></script>
	<script src="${ctx}/resource/js/module/sys/role/roleAuthorization.js"></script>
</head>
	<SCRIPT type="text/javascript">
	$(document).ready(function() {
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		var roleId="${roleId}";
		$.ajax({
               url: Helper.getRootPath() + '/sys/role/getRoleResources.do',
               type: 'post',
               dataType : "json",
               data: {
                   roleId: roleId
               },
               success: function (m) {
					$.fn.zTree.init($("#grantRoleZtreeId"), setting, m.json);
               }
		});
	});
	</SCRIPT>


<BODY>

	<div class="alert alert-block alert-warning fade in right">
		<sec:authorize access="hasRole('ROLE_SYS_ROLE_AUTHCOMMIT')">
        <p><a class="btn btn-primary grant-role-submit" href="#">保存</a></p>
        </sec:authorize>
    </div>
    <input type="hidden" id="roleId" value="${roleId }">
	<div class="zTreeDemoBackground left">
		<ul id="grantRoleZtreeId" class="ztree"></ul>
	</div>
</BODY>
</html>
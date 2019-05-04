<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>  
<sec:authentication property="principal" var="user"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="basePath"
	value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}" />
<script type="text/javascript">
    var appCtx = '${ctx}';
    var appBasePath = '${basePath}';
</script>
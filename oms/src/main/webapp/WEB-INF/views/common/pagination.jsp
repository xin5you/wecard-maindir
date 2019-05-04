<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="p" value="${requestScope.pageInfo}" />
<c:set var="prefixLength" value="2" />
<c:set var="suffixLength" value="2" />
<c:set var="pageBarSize" value="${prefixLength + suffixLength + 1}" />
<div class="page-div row-fluid" style="margin-top:10px;">
	<div class="span6">
		<div id="dt_gal_info" class="dataTables_info">第${p.startRow }到${p.endRow }条  共有 ${p.total} 条数据</div>
	</div>
	<c:if test="${p.pages > 0}">
		<c:set var="beginIndex" value="${(p.pageNum - prefixLength) > 1 ? (p.pageNum - prefixLength) : 1}" />
		<c:set var="endIndex" value="${pageBarSize > (p.pageNum + suffixLength) ? pageBarSize : (p.pageNum + suffixLength)}" />
		<c:if test="${endIndex > p.pages}">
			<c:set var="endIndex" value="${p.pages}" />
		</c:if>
		<c:if test="${pageBarSize > (endIndex - beginIndex) && endIndex >= pageBarSize}">
			<c:set var="beginIndex" value="${endIndex - pageBarSize + 1}" />
		</c:if>
		<div class="span6">
			<div class="dataTables_paginate paging_bootstrap pagination">
				<ul>
					<li class="prev ${p.pageNum > 1 ? '' : 'disabled'}">
						<a href="javascript:void(0)" onclick="first();">首页</a>
					</li>				
					<li class="prev ${p.pageNum > 1 ? '' : 'disabled'}">
						<a href="javascript:void(0)" onclick="pre();">&lt; 前一页</a>
					</li>
					<c:forEach begin="${beginIndex}" end="${p.pageNum-1}" varStatus="st1">
						<li>
							<a href="javascript:void(0)" onclick="skip(${st1.index});">${st1.index}</a>
						</li>
					</c:forEach>
					<li class="active">
						<a href="javascript:void(0)">${p.pageNum}</a>
					</li>
					<c:forEach begin="${p.pageNum + 1}" end="${endIndex}" varStatus="st2">
						<li>
							<a href="javascript:void(0)" onclick="skip(${st2.index});">${st2.index}</a>
						</li>
					</c:forEach>
					<li class="next ${p.pageNum < p.pages ? '' : 'disabled'}">
						<a href="javascript:void(0)" onclick="next();">后一页 &gt; </a>
					</li>
					<li class="next ${p.pageNum < p.pages ? '' : 'disabled'}">
						<a href="javascript:void(0)" onclick="last();">末页 </a>
					</li>					
				</ul>
			</div>
		</div>
	</c:if>
</div>
<input type="hidden" id="hid_pageSize" name="pageSize" value="${p.pageSize}">
<input type="hidden" id="hid_pageNum" name="pageNum" value="${p.pageNum}">
<input type="hidden" id="hid_totalPages" name="totalPages" value="${p.pages}">
<input type="hidden" id="hid_pageTurning" name="pageTurning" value="false">
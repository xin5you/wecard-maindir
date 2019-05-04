<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <script src="${ctx}/resource/js/module/phoneRecharge/telChannelInf/listTelChannelProductRate.js"></script>
</head>
<body>
	   <%@ include file="/WEB-INF/views/common/navbar.jsp"%>
            <div id="contentwrapper">
                <div class="main_content">
                	<nav>
			            <div id="jCrumbs" class="breadCrumb module">
			                <ul>
			                    <li><a href="#"><i class="icon-home"></i></a></li>
			                    <li>手机充值</li>
			                    <li><a href="${ctx }/channel/channelInf/listTelChannelInf.do">分销商管理</a></li>
			                    <li>分销商充值产品信息列表</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="${ctx }/channel/channelInf/intoAddTelChannelRate.do?channelId=${telChannelInf.channelId }" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<input type="hidden" id="channelId" name = "channelId" value="${telChannelInf.channelId }"/>
						<h3 class="heading">${telChannelInf.channelName }充值产品信息列表</h3>
						<div class="row-fluid" id="h_search">
							 <div class="span10">
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">运营商</span>
		           			   	   	<select name="operId" id="operId" class="input-medium">
				                     	<option value="">--全部--</option>
				                     	<c:forEach var="s" items="${operIdList}" varStatus="sta">
				                     		<option value="${s.code}"  <c:if test="${s.code==telCPInf.operId}">selected</c:if> >${s.value }</option>
				                     	</c:forEach>
				                    </select>
		                       	</div>
		                       	<div class="input-prepend">
		           			   	   	<span class="add-on">分销商折扣率</span>
		           			   	   	<input id="channelRate" name=channelRate type="text" class="input-medium" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" maxlength="12" />
		                       	</div>
							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-search"> 查 询 </button>
								<button type="reset" class="btn btn-inverse btn-reset">重 置</button>
								 <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM_LIST_ADDCOMMIT')">
								<button type="button" class="btn btn-primary btn-add">提交</button>
								</sec:authorize>
							</div>
						</div>
						
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				             	<th><input type="checkbox" id="selectAll" title="全选" class="checkbox" /></th>
				                <th>运营商产品名称</th>
				                <th>运营商</th>
				                <th>区分地区标识</th>
				                <th>产品面额（元）</th>
				                <th>产品售价（元）</th>
				                <th>折扣率(百分比)</th>
				                <th>产品类型</th>
				                <th>创建时间</th>
				                <th>修改时间</th>
				                <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${channelProductistCheck}" varStatus="st">
				                 <tr>
				                 	<td>
				                 		<c:if test="${entity.checked == true}">
											<input type="checkbox" name="productId" value="${entity.productId}" class="checkbox" disabled="disabled" readonly="readonly" checked="checked"/>
										</c:if>
										<c:if test="${entity.checked == false}">
											<input type="checkbox" name="productId" value="${entity.productId}" class="checkbox"/>
										</c:if>
									</td>
				                 	<td style="overflow: hidden; text-overflow:ellipsis;white-space: nowrap; ">${entity.operName}</td>
				                 	<td>${entity.operId}</td>
				                 	<td>${entity.areaFlag}</td>
				                 	<td>${entity.productAmt}</td>
				                 	<td>${entity.productPrice}</td>
				                 	<td>${entity.channelRate}</td>
				                 	<td>${entity.productType}</td>
				                 	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${entity.createTime}"/></td>
									<td>
										<c:if test="${entity.checked == true}">
											<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM_LIST_INTOEDIT')">
											<a id="${entity.id}" title="编辑" class="btn-mini btn-edit" href="#"><i class="icon-edit"></i></a>
											</sec:authorize>
										</c:if>
									</td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				      </form>
			   </div>
	    </div>
	    
	    
	    <div id="addAccountModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <form class="form-horizontal">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h3 id="commodityInfModal_h">修改折扣率</h3>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="commodity_id" />
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label">姓名：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="name" name="name"/>
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">手机号码：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="phone"  name = "phone"/>
                                <span class="help-block"></span>
                            </div>  
                        </div>
                        <div class="control-group">
                            <label class="control-label">身份证号码：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="card" name ="card" />
                                <span class="help-block"></span>
                            </div>
                        </div>
                        
                    </fieldset>
                </div>
            </form>
            <div class="modal-footer" style="text-align: center;">
                <button class="btn btn-primary btn-submit">确 定  </button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">取 消</button>
            </div>
        </div>
</body>
</html>

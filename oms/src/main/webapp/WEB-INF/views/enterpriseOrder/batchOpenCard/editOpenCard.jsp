<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchOpenCard/editOpenCard.js"></script>
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
			                    <li><a href="${ctx }/enterpriseOrder/batchOpenCard/listOpenCard.do">批量开卡</a></li>
			                     <li>订单编辑</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">订单编辑</h3>
						<div><button class="btn btn-primary btn-editAddCard" type="button">添加</button></div><br/>
						<div class="row-fluid" >
							 <div class="span12">
		                       	<div style="display: flex;justify-content:left;">
                                    <table cellpadding="5px" style="width: 80%">
                                        <tr>
                                            <td>
                                                <span class="fontBold">订单号:</span>
                                                <span class="fontColor">${order.orderId }</span>
                                            </td>
                                            <td>
                                                <span class="fontBold">订单名称:</span>
                                                <span class="fontColor">${order.orderName }</span>
                                            </td>
                                            <td>
                                                <span class="fontBold">开卡产品:</span>
                                                <span class="fontColor">${order.productName }</span>
                                            </td>
                                            <td>
                                                <span class="fontBold">订单总量:</span>
                                                <span class="fontColor">${order.orderCount }</span>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
						  </div>
						</div>
				         </br >       
				         <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
				             <thead>
				             <tr>
				               <th>序号</th>
				               <th>姓名</th>
				               <th>身份证号码</th>
				               <th>手机号</th>
				               <th>操作</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${st.index +1}</td>
                                    <td>${entity.userName}</td>
                                    <td>${entity.userCardNo}</td>
                                    <td>${entity.phoneNo}</td>
				                    <td>
                                   <sec:authorize access="hasRole('ROLE_BATCH_OPEN_CARD_ORDERLISTDELETE')">
                                    <a orderListId="${entity.orderListId }" title="删除" class="btn-mini btn-delete" href="#"><i class="icon-remove"></i></a>
                                   </sec:authorize>
				                    </td>
				                 </tr>
				             </c:forEach>
				             </tbody>
				         </table>
				         <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
				      </form>
				      <br/>
                      <a href="${ctx }/enterpriseOrder/batchOpenCard/listOpenCard.do"><button class="btn btn-primary" type="button">返回</button></a>
			   </div>
	    </div>
	    
	    <div id="editAddCardModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <form class="form-horizontal">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h3 id="commodityInfModal_h">添加名单</h3>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="orderId" value="${order.orderId }" />
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label">姓名：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="name"/>
                                <span class="help-block"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">手机号码：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="phone" />
                                <span class="help-block"></span>
                            </div>  
                        </div>
                        <div class="control-group">
                            <label class="control-label">身份证号码：</label>
                            <div class="controls">
                                <input type="text" class="span3" id="card" />
                                <span class="help-block"></span>
                            </div>
                        </div>
                        
                    </fieldset>
                </div>
            </form>
            <div class="modal-footer" style="text-align: center;">
            <sec:authorize access="hasRole('ROLE_BATCH_OPEN_CARD_ORDERLISTCOMMIT')">
                <button class="btn btn-primary btn-submit">确 定  </button>
            </sec:authorize>
                <button class="btn" data-dismiss="modal" aria-hidden="true">取 消</button>
            </div>
        </div>   
	    
	    
</body>
</html>

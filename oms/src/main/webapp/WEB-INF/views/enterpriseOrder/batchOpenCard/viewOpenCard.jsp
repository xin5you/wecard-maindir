<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
			                     <li>开卡订单详情</li>
			                </ul>
			            </div>
			        </nav>
					<form id="searchForm" action="" class="form-inline" method="post">
						<input type="hidden" id="operStatus"  value="${operStatus }"/>
						<h3 class="heading">开卡订单详情</h3>
						
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
                                                 <span class="fontBold">订单状态:</span>
                                                 <span class="fontColor">${order.orderStat}</span>
                                             </td>
                                             <td>
                                                <span class="fontBold">开卡产品:</span>
                                                 <span class="fontColor">${order.productName}</span>
                                             </td>
                                         </tr>
                                         <tr>
                                             <td>
                                                 <span class="fontBold">订单总量:</span>
                                                 <span class="fontColor">${order.orderCount }</span>
                                             </td>
                                             <td>
                                                 <span class="fontBold">未 处 理:</span>
                                                 <span class="fontColor">${order.disposeWait }</span>
                                             </td>
                                             <td>
                                                 <span class="fontBold">处理成功:</span>
                                                 <span class="fontColor">${order.disposeSuccess }</span>
                                             </td>
                                             <td>
                                                 <span class="fontBold">处理失败:</span>
                                                 <span class="fontColor">${order.disposeFail }</span>
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
				               <th>状态</th>
				               <th>备注</th>
				             </tr>
				             </thead>
				             <tbody>
				             <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
				                 <tr>
				                 	<td>${st.index+1 }</td>
                                    <td>${entity.userName}</td>
                                    <td>${entity.userCardNo}</td>
                                    <td>${entity.phoneNo}</td>
                                    <td>${entity.orderStat}</td>
                                    <td>${entity.remarks}</td>
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
</body>
</html>

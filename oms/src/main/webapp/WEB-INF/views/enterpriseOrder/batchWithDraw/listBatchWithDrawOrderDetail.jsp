<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
    <link rel="stylesheet" href="${ctx}/resource/datetimepicker/css/bootstrap-datetimepicker.0.0.11.min.css" />
    <script src="${ctx}/resource/datetimepicker/js/bootstrap-datetimepicker.0.0.11.min.js"></script>
    <script src="${ctx}/resource/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/resource/js/module/enterpriseOrder/batchWithDraw/listBatchWithDrawOrderDetail.js?v=1.3"></script>
    <script src="${ctx}/resource/js/jquery/jquery.form.js"></script>
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
                    <li><a href="${ctx }/batchWithdrawOrder/listBatchWithdrawOrder.do">批量代付</a></li>
                    <li>批量代付明细</li>
                </ul>
            </div>
        </nav>
        <form id="searchForm" action="${ctx }/batchWithdrawOrderDetail/listBatchWithDrawOrderDetail.do" class="form-inline" method="post">
            <input type="hidden" id="orderId" name="orderId" value="${order.orderId}">
            <h3 class="heading">批量代付明细</h3>
            <div class="row-fluid" >
                <div class="span12">
                    <div class="input-prepend">
                        <span class="add-on">用户姓名:</span><input id="receiverName" name="receiverName" type="text" class="input-medium" value="${order.receiverName}"  maxlength="22" />
                    </div>
                    <div class="input-prepend">
                        <span class="add-on">银行卡号:</span><input id="receiverCardNo" name="receiverCardNo" type="text" class="input-medium" value="${order.receiverCardNo}"/>
                    </div>

                    <div class="pull-right">
                        <button type="submit" class="btn btn-search"> 查 询 </button>
                        <button type="reset" class="btn btn-inverse btn-reset">重 置</button>
                        <button type="button" class="btn btn-primary btn-synchron">同步状态</button>
                    </div>
                </div>
            </div>
            </br >
            <table class="table table-striped table-bordered dTableR table-hover" id="dt_gal" >
                <thead>
                <tr>
                    <th>序列</th>
                    <th>收款人</th>
                    <th>银行卡</th>
                    <th>银行名称</th>
                    <th>代付金额</th>
                    <th>手续费</th>
                    <th>代付状态</th>
                    <th>代付描述</th>
                    <th>代付时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entity" items="${pageInfo.list}" varStatus="st">
                    <tr>
                        <td>${entity.detailId }</td>
                        <td>${entity.receiverName }</td>
                        <td>${entity.receiverCardNo }</td>
                        <td>${entity.bankName }</td>
                        <td>${entity.amount }</td>
                        <td>${entity.fee }</td>
                        <td>${entity.respCode }</td>
                        <td>${entity.respMsg }</td>
                        <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                        <td></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%@ include file="/WEB-INF/views/common/pagination.jsp"%>
        </form>
    </div>
</div>
</body>
</html>

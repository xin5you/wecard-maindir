<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!-- header -->
 <div id="loading_layer" style="display:none"><img src="${ctx}/resource/gebo/img/ajax_loader.gif" alt="" /></div>
   <header>
             <div class="navbar navbar-fixed-top">
                 <div class="navbar-inner">
                     <div class="container-fluid">
                         <a class="brand" href="#"><i class="icon-home icon-white"></i>薪无忧运营管理平台</a>
                         <ul class="nav user_menu pull-right">
                             <li class="divider-vertical hidden-phone hidden-tablet"></li>
                             <li class="dropdown">
                             	
                                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username } <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<li><a href="${ctx}/login/logout.do">退出登陆</a></li>
                                 	<li><a href="#" id="updatePwdBtn" onclick="PayCommon.loadUpdatePwdModal();" >重置密码</a></li>
                                 </ul>
                             </li>
                         </ul>
                         <ul class="nav" id="mobile-nav">
                         	<sec:authorize access="hasRole('ROLE_CASH_MARGIN')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-tasks icon-white"></i>保证金信息 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<sec:authorize access="hasRole('ROLE_CASH_MARGIN_MANAGE')">
                                    	 <li><a href="${ctx }/margin/mchntCashManage/listMerchantCashManage.do">保证金管理</a></li>
                                     </sec:authorize>
                                      <sec:authorize access="hasRole('ROLE_CASH_MARGIN_APPROVE')">
                                     	<li><a href="${ctx }/margin/mchntCashManage/listMchntMarginApprove.do">保证金审批列表</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_CASH_MARGIN_CONFIRMATION')">
                                     	<li><a href="${ctx }/margin/mchntCashManage/listMerchantCashConfirm.do">保证金押款确认列表</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_MCHNT_MANAGE')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-user icon-white"></i>商户信息 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	 <sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF')">
                                     	<li><a href="${ctx }/merchant/merchantInf/listMerchantInf.do">商户管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_MCHNT_ESHOP')">
                                     	<li><a href="${ctx }/mchnteshop/eShopInf/listMchntEshopInf.do">商城管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_MCHNT_SHOPINF')">
                                     	<li><a href="${ctx }/merchant/shopInf/listShopInf.do">门店管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_MCHNT_PRODUCT')">
                                     	<li><a href="${ctx }/merchant/product/listProduct.do">产品管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_MCHNT_MERCHANTINF')">	
                                     	<li><a href="${ctx }/merchant/managerTmp/listMerchantManagerTmp.do">商户员工管理</a></li>
<%--                                      	<li><a href="${ctx }/merchant/transInf/listTrans.do">交易明细</a></li> --%>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_PERSON_CARD_MANAGER')">
                                     	<li><a href="${ctx }/customer/personInf/getPersonInfList.do">用户会员卡管理</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_ACTIVE_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-gift icon-white"></i>活动信息 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                	 <sec:authorize access="hasRole('ROLE_COMMODITIES_MANAGER')">
                                     	<li><a href="${ctx }/active/commodityInf/listCommodities.do">商品管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_ACTIVES')">
                                     	<li><a href="${ctx }/active/activity/listMerchantActives.do">活动管理</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_ORDER_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-list icon-white"></i>福利管理 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                     <sec:authorize access="hasRole('ROLE_BATCH_OPEN_ACCOUNT')">
                                        <li><a href="${ctx }/enterpriseOrder/batchOpenAccount/listOpenAccount.do">批量开户</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_BATCH_OPEN_CARD')">
                                        <li><a href="${ctx }/enterpriseOrder/batchOpenCard/listOpenCard.do">批量开卡</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_BATCH_RECHARGE')"> 
                                        <li><a href="${ctx }/enterpriseOrder/batchRecharge/listRecharge.do">批量充值</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_CARD_KEYS_PRODUCT')"> 
                                        <li><a href="${ctx }/cardKeys/listCardKeysProduct.do">卡密产品</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_SHOP')"> 
                                     	<li><a href="${ctx }/phone/phoneRecharge/listPhoneRechargeShop.do">手机充值商品</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_WITHDRAW_BLACKLIST_INF')"> 
                                     	<li><a href="${ctx }/enterpriseOrder/batchOpenWBAccount/listOpenWBAccount.do">批量提现黑名单</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_TRANS_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-glass icon-white"></i>交易管理 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                     <sec:authorize access="hasRole('ROLE_TRANS_DETAIL')">
                                        <li><a href="${ctx }/merchant/transInf/listTrans.do">商户交易明细</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_ACC_TRANS_DETAIL')">
                                        <li><a href="${ctx }/trans/cardTransInf/listTrans.do">通卡交易明细</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_PAYCHANNEL_TRANS_DETAIL')">
                                        <li><a href="${ctx }/trans/payChannelTransInf/listTrans.do">通道交易明细</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_CARD_TICKET_TRANS_ORDER')"> 
                                        <li><a href="${ctx }/cardTicketTrans/listCardTicketTransOrder.do">卡券交易明细</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_PHONE_RECHARGE_ORDER')"> 
                                        <li><a href="${ctx }/phone/phoneRecharge/getPhoneRechargeList.do">手机充值交易明细</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_STATEMENT_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-th icon-white"></i>报表管理 <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                     <sec:authorize access="hasRole('ROLE_MERCHANT_STATEMENT')">
                                        <li>
                                            <a href="javascript:void(0)">商户报表 <b class="caret-right"></b></a>
                                            <ul class="dropdown-menu">
                                                <sec:authorize access="hasRole('ROLE_MERCHANT_SUMMARIZING_LIST')">
                                                <li><a href="${ctx}/statement/merchantStatement/listMerchantSummarizing.do">商户数据统计列表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_MERCHANT_DETAIL_LIST')">
                                                <li><a href="${ctx}/statement/merchantStatement/listMerchantDetail.do">门店数据统计列表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_SHOP_DETAIL_LIST')">
                                                <li><a href="${ctx}/statement/merchantStatement/listShopDetail.do">档口交易流水列表</a></li>
                                                </sec:authorize>
                                            </ul>
                                        </li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_OPERATION_STATEMENT')">
                                        <li>
	                                        <a href="javascript:void(0)">运营报表 <b class="caret-right"></b></a>
	                                        <ul class="dropdown-menu">
	                                             <sec:authorize access="hasRole('ROLE_OPERATION_SUMMZRIZING_LIST')">
                                                <li><a href="${ctx }/statement/operationStatement/listOperationSummarizing.do">运营汇总表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_OPERATION_DETAIL_LIST')">
                                                <li><a href="${ctx }/statement/operationStatement/listOperationDetail.do">运营明细表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_CUSTOMERINFO_LIST')">
                                                <li><a href="${ctx }/statement/operationStatement/listCustomerInfo.do">客户数据表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_CUSTOMERINFO_DETAIL_LIST')">
                                                <li><a href="${ctx }/statement/operationStatement/listCustomerInfoDetail.do">客户数据明细表</a></li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_MARKETING_DETAIL_LIST')">
                                                <li><a href="${ctx }/statement/operationStatement/listMarketingDetail.do">营销数据明细表</a></li>
                                                </sec:authorize>
                                            </ul>
                                        </li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_FINANCING_STATEMENT')"> 
                                        <li>
	                                        <a href="javascript:void(0)">财务报表 <b class="caret-right"></b></a>
	                                        <ul class="dropdown-menu">
	                                           <sec:authorize access="hasRole('ROLE_FINANCING_SUMMZRIZING_LIST')"> 
	                                           <li><a href="${ctx }/statement/financingStatement/listFinancingSummarizing.do">财务结算汇总表</a></li>
	                                           </sec:authorize>
	                                           <sec:authorize access="hasRole('ROLE_FINANCING_DETAIL_LIST')"> 
	                                           <li><a href="${ctx }/statement/financingStatement/listFinancingDetail.do">财务结算明细表</a></li>
	                                           </sec:authorize>
	                                        </ul>
                                        </li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_DIY_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-cog icon-white"></i>商户自助平台<b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<sec:authorize access="hasRole('ROLE_DIY_USER')">
                                     	<li><a href="${ctx }/diy/diyUser/listDiyUser.do">商户用户管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_DIY_ROLE')">
                                     	<li><a href="${ctx }/diy/diyRole/listDiyRole.do">商户角色管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_DIY_RESORCE')">
                                     	<li><a href="${ctx }/diy/diyResource/listDiyResource.do">商户资源管理</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_PHONE_RECHARGE')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-cog icon-white"></i>手机充值<b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_INF')">
                                     	<li><a href="${ctx }/channel/channelInf/listTelChannelInf.do">分销商管理</a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_RESERVE')">
                                     	<li><a href="${ctx }/channel/reserve/listTelChannelReserve.do">分销商备付金管理</a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ORDER_INF')">
										<li><a href="${ctx }/channel/channelOrder/listTelChannelOrderInf.do">分销商订单</a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_TEL_CHANNEL_PRODUCT')">
										<li><a href="${ctx }/channel/product/listTelChannelProduct.do">分销商充值产品管理</a></li>
									</sec:authorize>
<!-- 									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_AREA_INF')">	 -->
<%-- 										<li><a href="${ctx }/channel/areaInf/listTelChannelAreaInf.do">分销商话费地区维护</a></li> --%>
<!-- 									</sec:authorize> -->
<!-- 									<sec:authorize access="hasRole('ROLE_TEL_CHANNEL_ITEM')"> -->
<%-- 										<li><a href="${ctx }/channel/item/listTelChannelItem.do">分销商产品折扣率管理</a></li> --%>
<!--                                     </sec:authorize> -->
                                   	<sec:authorize access="hasRole('ROLE_TEL_PROVIDER_INF')">
                                   		<li><a href="${ctx }/provider/providerInf/listTelProviderInf.do">供应商信息管理</a></li>
                                   	</sec:authorize>
                                   	<sec:authorize access="hasRole('ROLE_TEL_PROVIDER_ORDER_INF')">
										<li><a href="${ctx }/provider/providerOrder/listTelProviderOrderInf.do">供应商订单</a></li>
									</sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             <sec:authorize access="hasRole('ROLE_TERMINAL_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-lock icon-white"></i>终端管理<b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<sec:authorize access="hasRole('ROLE_SCAN_BOX_DEVICE_INF')">
                                     	<li><a href="${ctx }/merchant/scanBoxDeviceInf/listScanBoxDeviceInf.do">设备管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_PAYMENT_CHANNELS')">
                                     	<li><a href="${ctx }/channel/paymentChannel/listPaymentChannel.do">支付通道管理</a></li>
                                     </sec:authorize>
                                 </ul>
                             </li>
                             </sec:authorize>
                             
                             <sec:authorize access="hasRole('ROLE_SYS_MANAGER')">
                             <li class="dropdown">
                                 <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i class="icon-wrench icon-white"></i>系统管理<b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                 	<sec:authorize access="hasRole('ROLE_SYS_USER')">
                                     	<li><a href="${ctx }/sys/user/listUser.do">用户管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_SYS_ROLE')">
                                     	<li><a href="${ctx }/sys/role/listRole.do">角色管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_SYS_RESORCE')">
                                     	<li><a href="${ctx }/sys/resource/listResource.do">资源管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_SYS_DICT')">
                                    	 <li><a href="${ctx }/sys/dictionary/listDictionary.do">字典管理</a></li>
                                     </sec:authorize>
                                     <sec:authorize access="hasRole('ROLE_SYS_ORGANIZATION')">
                                     	<li><a href="${ctx }/sys/organization/listOrganization.do">部门管理</a></li>
                                     </sec:authorize>
                                     	<li><a href="${ctx }/sys/sms/listSmsMessage.do">短信管理</a></li>
                                 </ul>
                             </li>
                             </sec:authorize>
                         </ul>
                     </div>
                 </div>
             </div>
  </header>
  
  <div id="updatePWDModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form class="form-horizontal">
			<div class="modal-header">
				<button class="close" data-dismiss="modal">&times;</button>
				<h3>修改密码</h3>
			</div>
			<div class="modal-body">
				<fieldset>
					<div class="control-group">
						<label class="control-label">旧密码：</label>
						<div class="controls">
							<input type="password" class="span3" id="oldPasswrodPage" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">新密码：</label>
						<div class="controls">
							<input type="password" class="span3" id="newPasswordPage"/>
						</div>	
					</div>
					<div class="control-group">
						<label class="control-label">二次确认密码：</label>
						<div class="controls">
							<input type="password" class="span3" id="newPassword2Page"/>
						</div>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="modal-footer" style="text-align: center;">
            <button class="btn btn-primary btn-submit" id="userPWdSubmitBtn">提 交  </button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">关 闭</button>
        </div> 
	</div>

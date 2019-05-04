<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/init.jsp"%>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
     <script src="${ctx}/resource/js/module/sys/dictionary/editDictionary.js"></script>
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
			                    <li>系统信息</li>
			                    <li><a href="${ctx }/sys/dictionary/listDictionary.do">字典管理</a></li>
			                     <li>编辑字典</li>
			                </ul>
			            </div>
			        </nav>
					 <div class="row-fluid">
					 	<div class="span12">
							<form id="mainForm" class="form-horizontal form_validation_tip" method="post">
								<input type="hidden" id="dictId" name="dictId" value="${dict.id }">
							
								 <h3 class="heading">编辑字典</h3>
						          <div class="control-group">
						             <label class="control-label">字典名称</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="name" name="name" value="${dict.name }" />
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">字典编码</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="code" name="code" value="${dict.code }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">字典值</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="value" name="value" value="${dict.value }"/>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">字典类型</label>
						             <div class="controls">
						                 <select class="form-control span6" id="type" name="type">
						                 	 <option value="0" <c:if test="${dict.type=='0' }">selected="selected"</c:if>>类型</option>
						                 	 <option value="1" <c:if test="${dict.type=='1' }">selected="selected"</c:if>>字典值</option>
						                 </select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         <div class="control-group">
						             <label class="control-label">排序</label>
						             <div class="controls">
						                 <input type="text" class="span6" id="seq" name="seq" value="${dict.seq }"/>
						                 <span class="help-block" style="color: red;">请输入整数</span>
						             </div>
						         </div>
						         
						         
						         <div class="control-group">
						             <label class="control-label">状态</label>
						             <div class="controls">
						                 <select class="form-control span6" id="state" name="state">
						                 	 <option value="0" <c:if test="${dict.state=='0' }">selected="selected"</c:if>>正常</option>
						                 	 <option value="1" <c:if test="${dict.state=='1' }">selected="selected"</c:if>>禁用</option>
						                 </select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						         
						         <div class="control-group">
						             <label class="control-label">上级类型</label>
						             <div class="controls">
						                  <select class="form-control span6" id="pid" name="pid">
						                 	 <option value="0" selected="selected">请选择字典类型</option>
											 <c:forEach var="dl" items="${dictList}" varStatus="st">
											 		<option value="${dl.id}" <c:if test="${dict.pid==dl.id }">selected="selected"</c:if>>${dl.name }</option>
											 </c:forEach>
										</select>
						                 <span class="help-block"></span>
						             </div>
						         </div>
						       
						         <div class="control-group ">
				                            <div class="controls">
				                            <sec:authorize access="hasRole('ROLE_SYS_DICT_EDITCOMMIT')">
				                                <button class="btn btn-primary btn-submit" type="submit">提 交</button>
				                             </sec:authorize>   
				                                <button class="btn btn-inverse btn-reset" type="reset">重 置</button>
				                            </div>
				                  </div>
						     </form>
					     </div>
				     </div>
				</div>
		</div>
</body>
</html>
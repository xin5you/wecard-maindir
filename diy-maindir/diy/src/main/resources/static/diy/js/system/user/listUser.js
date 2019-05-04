$(document).ready(function() {
	listUser.init();
})

var listUser = {
	init : function() {
		listUser.initEvent();
	},

	initEvent:function(){
		deleteDialog = new hkbShowDialog({
			'ele' : 'delMember'
		});
		detailDialog = new hkbShowDialog({
			'ele' : 'addMember'
		});
		$('#btn-reset').on('click', listUser.searchReset);
		$('#btn-add').on('click', listUser.intoAddUser);
		$('.btn-detail').on('click', listUser.detailUser);
		$('.btn-edit').on('click', listUser.editUser);
		$('.btn-delete').on('click', listUser.deleteDialogUser);
		$('#cancel').on('click', listUser.cancelUser);
		$('#ok').on('click', listUser.deleteUser);
		
	},
	searchReset : function(){
		Helper.post('/system/user/listUser');
	},
	intoAddUser : function(){
		Helper.post('/system/user/intoAddUser');
	},
	detailUser : function(){
		//成员详情模态框
		detailDialog.show();
		var userId = $(this).attr('userId');
		$.ajax({
			type : 'POST',
			url : Helper.getRootPath() + '/system/user/detailUser',
			data : {
				"id" : userId
			},
			cache : false,
			dataType : 'json',
			success : function(data) {
				$("#userName").html(data.userName);
				$("#roleName").html(data.roleName);
				$("#shopName").html(data.shopName);
				$("#loginPhone").html(data.phoneNo);
				$("#userPhone").html(data.phoneNo);
				$("#mchntName").html(data.mchntName);
			},
			error : function() {
				alert("查看详情出错");
			}
		});
	},
	editUser : function(){
		var userId = $(this).attr('userId');
		Helper.post('/system/user/intoEditUser.do?userId='+userId);
	},
	deleteDialogUser : function(){
		//删除成员模态框
		deleteDialog.show();
		var userId = $(this).attr('userId');
		$("#deleteUserId").val(userId);
	},
	cancelUser : function(){
		$("#deleteUserId").val("");
		deleteDialog.hide();
	},
	deleteUser : function(){
		var id = $("#deleteUserId").val();
		deleteDialog.hide();
		$.ajax({
			type : 'POST',
			url : Helper.getRootPath() + '/system/user/deleteUser',
			data : {
				"id" : id
			},
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == '00'){
					Helper.alert('系统提示',data.msg);
					listUser.searchReset();
				} else {
					Helper.alert('系统提示',data.msg);
					listUser.searchReset();
				}
			},
			error : function() {
				Helper.alert('系统提示',"系统故障，请稍后再试！");
			}
		});
	}
}
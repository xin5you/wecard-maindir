package com.cn.iboot.diy.api.base.constants;

public class ExceptionEnum {

	public static final String SUCCESS_CODE = "00";
	public static final String SUCCESS_MSG = "操作成功";
	
	public static final String ERROR_CODE = "999";
	public static final String ERROR_MSG = "系统故障，请稍后再试";

	public enum loginNews {
		LN01("01", "会话过期，请重新登录"), 
		LN02("02", "验证码不正确，请重新输入"), 
		LN03("03", "用户名或密码错误，请重新输入"), 
		LN04("04", "登录失败，系统出错"),
		LN05("05", "无权限");

		private String code;
		private String msg;

		private loginNews(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public static loginNews findByCode(String code) {
			for (loginNews t : loginNews.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	public enum userNews {
		UN01("01", "新增成员出错"), 
		UN02("02", "新增用户角色信息出错"),
		UN03("03", "新增数据权限信息出错"),
		UN04("04", "新增用户数据权限信息出错"),
		UN05("05", "编辑成员出错"), 
		UN06("06", "删除成员出错"), 
		UN07("07", "删除数据权限信息出错"),
		UN08("08", "删除用户数据权限信息出错"),
		UN09("09", "删除用户角色信息出错"),
		UN10("10", "查询成员列表信息出错"),
		UN11("11", "成员名已存在，请重新输入"),
		UN12("12", "新增成员角色信息出错"),
		UN13("13", "两次密码输入不一致，请重新输入"),
		UN14("14", "请输入正确的旧密码"),
		UN15("15", "修改密码失败，请重新提交"),
		UN16("16", "请至少选中一项"),
		UN17("17", "当前手机号码已存在，请重新输入"),
		UN18("18", "当前商户下成员名已存在，请重新输入"),
		UN19("19", "当前门店下的门店财务已存在，请重新选择职位"),
		UN20("20", "该条数据已被操作，请核实后再操作");
		
		private String code;
		private String msg;

		private userNews(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public static userNews findByCode(String code) {
			for (userNews t : userNews.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	public enum roleNews {
		REN01("01", "新增角色出错"), 
		REN02("02", "编辑角色出错"), 
		REN03("03", "删除角色出错"), 
		REN04("04", "查询角色列表信息出错"),
		REN05("05", "该角色名称已存在，请重新输入"),
		REN06("06", "新增角色资源信息出错");
		

		private String code;
		private String msg;

		private roleNews(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public static roleNews findByCode(String code) {
			for (roleNews t : roleNews.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	public enum resourceNews {
		RN01("01", "新增资源出错"), 
		RN02("02", "编辑资源出错"), 
		RN03("03", "删除资源出错"), 
		RN04("04", "查询资源列表信息出错"),
		RN05("05", "该资源信息已存在，请重新输入");

		private String code;
		private String msg;

		private resourceNews(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public static resourceNews findByCode(String code) {
			for (resourceNews t : resourceNews.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
}

package com.cn.thinkx.oms.module.merchant.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.util.QrcodeUtil;
import com.cn.thinkx.pms.base.utils.DES3Util;
import com.cn.thinkx.pms.base.utils.StringUtil;

/**
 * 商户门店二维码图片下载
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/shopQrcodeDownload"})
public class ShopQrcodeDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(QrcodeUtil.class);
	// 二维码中间LOGO图相对路径
	private static final String LOGO_URL = "/resource/images/hkb_logo.png";
	// 路径分隔符'/'
//	private static final String SPT = File.separator;
//	private static final String KEY = ReadPropertiesFile.getInstance().getProperty("ENCODING_AES_KEY", null);
//	private static final String ADDR = ReadPropertiesFile.getInstance().getProperty("SHOP_QRCODE_ADDR", null);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopQrcodeDownload() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		InputStream in = null;
		OutputStream out = null;
		try {
			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
			ShopInfService shopInfService = (ShopInfService) wac.getBean("shopInfService");
			
			String shopId = request.getParameter("shopId");
			ShopInf shop = shopInfService.getShopInfById(shopId);
			if (shop == null) {
				LOG.info("当前查询门店不存在");
				response.getWriter().print("当前查询门店不存在");
				return;
			}
			if (StringUtil.isNullOrEmpty(shop.getQrCodeUrl())) {
				MerchantInfService merchantfService = (MerchantInfService) wac.getBean("merchantInfService");
				MerchantInf mer = merchantfService.getMerchantInfById(shop.getMchntId());
				if (mer == null) {
					LOG.info("当前查询门店商户不存在");
					response.getWriter().print("当前查询门店商户不存在");
					return;
				}
				String encryptStr = mer.getMchntCode() + "|" + shop.getShopCode();
				encryptStr = URLEncoder.encode(DES3Util.Encrypt3DES(encryptStr, RedisDictProperties.getInstance().getdictValueByCode("ENCODING_AES_KEY")), "UTF-8");
				shop.setQrCodeUrl(RedisDictProperties.getInstance().getdictValueByCode("SHOP_QRCODE_ADDR") + encryptStr);
				shopInfService.updateShopInf(shop);
			}
			String logoUrl = QrcodeUtil.getLogoUrl(request, LOGO_URL);
			String dirUrl = RedisDictProperties.getInstance().getdictValueByCode("SHOP_QRCODE_URL");
			
			File dir = new File(dirUrl);
			if (!dir.exists())
				dir.mkdir();
			
			String qrCodeUrl = dirUrl + File.separator + shopId + ".png";
			File qrCode = new File(qrCodeUrl);
			int res = 0;
			if (!qrCode.exists()) {
				res = QrcodeUtil.createQRCode(shop.getQrCodeUrl(), qrCodeUrl, logoUrl, 10);
				if (res == 0) {
					qrCode = new File(qrCodeUrl);
				} else {
					LOG.info("二维码图片为空，路径：{}", logoUrl);
					response.getWriter().print("二维码图片为空，路径：" + logoUrl);
					return;
				}
			}
			
			// 设置文件MIME类型
			response.setContentType(getServletContext().getMimeType(shopId + ".png"));
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + shopId + ".png");
			// 读取目标文件，通过response将目标文件写到客户端
			in = new FileInputStream(qrCode);
			out = response.getOutputStream();
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
		} catch (Exception e) {
			LOG.error("## 二维码下载异常", e);
		} finally {
			if (in != null) 
				in.close();
			if (out != null)
				out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

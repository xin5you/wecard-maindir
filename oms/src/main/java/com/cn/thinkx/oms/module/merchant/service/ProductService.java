package com.cn.thinkx.oms.module.merchant.service;

import java.util.List;

import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.model.RelInsProduct;
import com.github.pagehelper.PageInfo;

public interface ProductService {


	/**
	 * 查找产品信息
	 * @param productCode
	 * @return
	 */
	public Product getProductByProductCode(String productCode);
	
	/**
	 * 通过商户id查找产品信息
	 * @param mchntId
	 * @return
	 */
	public Product getProductByMerchantId(String mchntId);
	
	/**
	 * 根据产品号获取商户id
	 * @param productCode
	 * @return
	 */
	public String getMerchantIdByProduct(String productCode);
	
	/**
	 * 获取产品列表
	 * @param product
	 * @return
	 */
	public List<Product> findProductList(Product product);
	
	/**
	 * 保存产品信息
	 * @param Product
	 * @return
	 */
	public String insertProduct(Product product);
	
	/**
	 * 保存机构产品关联信息
	 * @param relInfProduct
	 * @return
	 */
	public int insertRelInsProduct(RelInsProduct relInsProduct);
	
	/**
	 * 保存产品信息及产品机构的关联关系及合同明细
	 * @param relInfProduct
	 * @return
	 */
	public int insertProductAndRelInsInf(Product product,InsInf insInf,MerchantInf merchantInf);
	/**
	 * 修改产品信息
	 * @param Product
	 * @return
	 */
	public int updateProduct(Product product);
	
	
	/**
	 * 删除产品信息
	 * @param productCode
	 * @return
	 */
	public int deleteProduct(String productCode); 
	
	/**
	 * 产品分页
	 * @param startNum
	 * @param pageSize
	 * @param Product
	 * @return
	 */
    public PageInfo<Product> getProductPage(int startNum, int pageSize, Product entity);
    
    /**
	 * 获取产品列表
	 * @param product
	 * @return
	 */
	public List<Product> getProductList(Product product);

}

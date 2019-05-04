package com.cn.thinkx.oms.module.merchant.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.model.RelInsProduct;

@Repository("productMapper")
public interface ProductMapper {

	/**
	 * 查找产品信息
	 * @param productCode
	 * @return
	 */
	public Product getProductByProductCode(String productCode);
	/**
	 * 根据商户id查找产品信息
	 * @param mchntId
	 * @return
	 */
	public Product getProductByMerchantId(String mchntId);
	/**
	 * 根据产品号获取商户id
	 * @param productCode
	 * @return
	 */
	public String getMerchantIdByProductCode(String productCode);
	/**
	 * 获取产品列表
	 * @param product
	 * @return
	 */
	public List<Product> getProductList(Product product);
	/**
	 * 插入产品信息
	 * @param product
	 * @return
	 */
	public int insertProduct(Product product);
	/**
	 * 保存机构产品关联信息
	 * @param relInsProduct
	 * @return
	 */
	public int saveRelInfProduct(RelInsProduct relInsProduct);
	/**
	 * 更新产品信息
	 * @param product
	 * @return
	 */
	public int updateProduct(Product product);
	/**
	 * 删除产品信息
	 * @param productCode
	 * @return
	 */
	public int deleteProduct(String productCode);
	

}
package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.ProductMapper;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantContract;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.Product;
import com.cn.thinkx.oms.module.merchant.model.RelInsProduct;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractListService;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractService;
import com.cn.thinkx.oms.module.merchant.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



@Service("productService")
public class ProductSerivceImpl implements ProductService {

	@Autowired
	@Qualifier("productMapper")
	private ProductMapper productMapper;
	
	@Autowired
	@Qualifier("merchantContractService")
	private MerchantContractService merchantContractService;
	
	@Autowired
	@Qualifier("merchantContractListService")
	private MerchantContractListService merchantContractListService;
	
	@Override
	public Product getProductByProductCode(String productCode) {
		return productMapper.getProductByProductCode(productCode);
	}

	@Override
	public Product getProductByMerchantId(String merchantId) {
		return productMapper.getProductByMerchantId(merchantId);
	}
	
	@Override
	public java.lang.String getMerchantIdByProduct(java.lang.String productCode) {
		return productMapper.getMerchantIdByProductCode(productCode);
	}
	
	@Override
	public List<Product> findProductList(Product product) {
		return productMapper.getProductList(product);
	}

	
	@Override
	public String insertProduct(Product product) {
		productMapper.insertProduct(product);
		return product.getProductCode();
	}
	
	@Override
	public int insertRelInsProduct(RelInsProduct relInsProduct) {
		return productMapper.saveRelInfProduct(relInsProduct);
	}

	@Override
	public int updateProduct(Product product) {
		return productMapper.updateProduct(product);
	}

	@Override
	public int deleteProduct(String productCode) {
		return productMapper.deleteProduct(productCode);
	}

	@Override
	public PageInfo<Product> getProductPage(int startNum, int pageSize, Product entity) {
		PageHelper.startPage(startNum, pageSize);
		List<Product> list = findProductList(entity);
		PageInfo<Product> page = new PageInfo<Product>(list);
		return page;
	}
	
	@Override
	public int insertProductAndRelInsInf(Product product,InsInf insInf,MerchantInf merchantInf){
		insertProduct(product);//插入产品信息
		RelInsProduct relInsProduct = new RelInsProduct();//组装机构产品关联信息
		relInsProduct.setInsId(insInf.getInsId());
		relInsProduct.setProductCode(product.getProductCode());
		relInsProduct.setDataStat(product.getDataStat());
		relInsProduct.setCreateUser(product.getCreateUser());
		relInsProduct.setUpdateUser(product.getUpdateUser());
		relInsProduct.setProductCode(product.getProductCode());
		this.insertRelInsProduct(relInsProduct);//插入机构产品关联表
		
		//插入商户合同明细表
		MerchantContract merchantContract = merchantContractService.getMerchantContractByMerchantId(merchantInf.getMchntId());			
		return merchantContractListService.insertDefaultMerchantContractList(merchantContract, product);
	}

	@Override
	public List<Product> getProductList(Product product) {
		return productMapper.getProductList(product);
	}
}

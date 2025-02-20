package com.main.Test9.product;

public class ProductMasterDto {

	private long productId; 
	private String productName;
	public ProductMasterDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductMasterDto(long productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Override
	public String toString() {
		return "ProductMasterDto [productId=" + productId + ", productName=" + productName + "]";
	} 
	
	
	
}

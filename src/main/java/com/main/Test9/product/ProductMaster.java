package com.main.Test9.product;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicVerticalLayoutManager;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Fallback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "product_master", uniqueConstraints = {@UniqueConstraint(columnNames = "fssai-No")})
public class ProductMaster {
	
	public interface AdminView extends BasicView {}
	public interface BasicView{}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	@JsonProperty(access = Access.READ_ONLY)
	private long productId; 
	
	@JsonView(BasicView.class)
	@Size(min = 2,max = 20,message = "Name of Product should be min 2 charactors and max 20 charactores")
	@NotEmpty(message = "Invalid Product Name")
	@Column(name = "product_name",nullable = false,unique = true, length = 150)
 	private String productName;
	
	@Column(name = "fssai_no", nullable =  false, unique = true) 
	private Double fssai_No;
	
	@NotNull(message = "Price Can not be null")
	@Min(value = 0, message = "Price must be greater than or equals to 0")
	@Column(name = "price")
	private Double price;
	
	@JsonView(AdminView.class)
	@Column(name = "active")
	private boolean active;
	public ProductMaster() {
		super();
		// TODO Auto-generated constructor stub
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
	public Double getFssai_No() {
		return fssai_No;
	}
	public void setFssai_No(Double fssai_No) {
		this.fssai_No = fssai_No;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "ProductMaster [productId=" + productId + ", productName=" + productName + ", fssai_No=" + fssai_No
				+ ", price=" + price + ", active=" + active + "]";
	}
	 
	
	
	
	

}

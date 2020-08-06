package com.couponsystem.jay.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.couponsystem.jay.exceptions.NoAccessException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder

public class Coupon {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private int companyID;
	@Column(nullable = false) @Enumerated(EnumType.STRING)
	private Category category;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private Date startDate;
	@Column(nullable = false)
	private Date endDate;
	@Column(nullable = false)
	private int amount;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String image;

	
	
	@Override
	public String toString() {
		return "Coupon #"+ id + ", companyID=" + companyID + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "\n";
	}
	
	public void setId(int id) throws NoAccessException{
		if (this.id == 0) {
			this.id = id;
		}else {
			throw new NoAccessException("Cannot change coupon id!");
		}
		
	}
	
	public void setcompanyID(int companyID) throws NoAccessException{
		if (this.companyID == 0) {
			this.companyID = companyID;
		}else {
			throw new NoAccessException("Cannot change company id!");
		}
		
	}
	
	

}

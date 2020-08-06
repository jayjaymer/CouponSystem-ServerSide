package com.couponsystem.jay.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.couponsystem.jay.exceptions.NoAccessException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "customers")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder

public class Customer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@OneToMany(targetEntity = Coupon.class,cascade = CascadeType.ALL)
	private List<Coupon> coupons;

	@Override
	public String toString() {
		return "Customer #"+ id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons = "+coupons + "\n";
	}
	
	public void setId(int id) throws NoAccessException{
		if (this.id == 0) {
			this.id = id;
		}else {
			throw new NoAccessException("Cannot change customer id!");
		}
		
	}
	
}



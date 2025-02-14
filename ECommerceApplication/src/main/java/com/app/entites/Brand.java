package com.app.entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

	@Id
	private Long brandId;

	@NotBlank
	private String brandName;

	@OneToMany(mappedBy = "brand", cascade =  CascadeType.ALL )
	private List<Product> products;
}
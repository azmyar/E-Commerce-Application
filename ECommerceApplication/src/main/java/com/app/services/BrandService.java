package com.app.services;

import com.app.entites.Brand;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;

public interface BrandService {

	BrandDTO createBrand(Brand category);

	BrandResponse getBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	BrandDTO updateBrand(Brand brand, Long brandId);

	String deleteBrand(Long brandId);
}

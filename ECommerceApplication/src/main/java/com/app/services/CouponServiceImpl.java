package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.entites.Coupon;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;
import com.app.repositories.CouponRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CouponDTO createCoupon(Coupon coupon) {
        Coupon existingCoupon = couponRepo.findByCouponName(coupon.getCouponName());

        if (existingCoupon != null) {
            throw new APIException("Coupon with the name '" + coupon.getCouponName() + "' already exists !!!");
        }

        Coupon savedCoupon = couponRepo.save(coupon);
        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponResponse getCoupons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Coupon> pageCoupons = couponRepo.findAll(pageDetails);

        List<Coupon> coupons = pageCoupons.getContent();

        if (coupons.isEmpty()) {
            throw new APIException("No coupon is created till now");
        }

        List<CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class)).collect(Collectors.toList());

        CouponResponse couponResponse = new CouponResponse();
        couponResponse.setContent(couponDTOs);
        couponResponse.setPageNumber(pageCoupons.getNumber());
        couponResponse.setPageSize(pageCoupons.getSize());
        couponResponse.setTotalElements(pageCoupons.getTotalElements());
        couponResponse.setTotalPages(pageCoupons.getTotalPages());
        couponResponse.setLastPage(pageCoupons.isLast());
        
        return couponResponse;
    }

    @Override
    public CouponDTO updateCoupon(Coupon coupon, Long couponId) {
        Coupon savedCoupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));

        coupon.setCouponId(couponId);
        savedCoupon = couponRepo.save(coupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public String deleteCoupon(Long couponId) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));

        couponRepo.delete(coupon);
        return "Coupon with couponId: " + couponId + " deleted successfully !!!";
    }
}
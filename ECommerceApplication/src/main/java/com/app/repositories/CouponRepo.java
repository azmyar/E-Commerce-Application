package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.entites.Coupon;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {

    Coupon findByCouponName(String couponName);

}

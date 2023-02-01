package com.shop.repository;

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {//엔티티를 제어해주는 것은 레퍼지토리

    //1월 13일
    Cart findByMemberId(Long memberId);

}

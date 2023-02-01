package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    //1월 12일 대표이미지만빼기추가
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}

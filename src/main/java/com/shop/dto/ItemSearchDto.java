package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

//0106
@Getter
@Setter
public class ItemSearchDto {
    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String SearchQuery = "";
    // 조회방법선택 searchBy itemNm 상품명 createdBy상품등록자 아이디 searchDatetype :기간별로나오게하는 것 ppt66
}

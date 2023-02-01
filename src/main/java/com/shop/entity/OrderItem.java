package com.shop.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id") //외래키
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id") //외래키
    private Order order;

    private int orderPrice;
    private int count;
    // private LocalDateTime regTime;
    // private LocalDateTime updateTime;

    //1워11일
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());
        item.removeStock(count);
        return  orderItem;
    }

    public int getTotalPrice(){ return orderPrice*count;}
    //1월 12일 주문취소
    public void cancel(){
        this.getItem().addStock(count);
    }
}

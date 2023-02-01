package com.shop.entity;

import com.shop.dto.MemberFormDto;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
@TestPropertySource(locations ="classpath:application-test.properties")
class CartTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PersistenceContext
    EntityManager em;
    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트") //카트테스트 에러
    public void findCartAndMemberTest(){
        Member member = createMember();
        memberRepository.save(member); //회원가입

        Cart cart = new Cart(); //카트만들기
        cart.setMember(member);
        cartRepository.save(cart); //카트저장

        em.flush();//영속성 컨텍스트에 데이터를 저장후 트렌잭션이 끝날 때flush()를 호출하여 데이터 베이스에반영
        em.clear();//영속성 컨텍스트에 조회후 엔티티가 없을 경우 데이터 베이스를 조회 영속성 컨텍스트를 비워줍니다.

        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);//만약 잘못해서 에러가 발생하면끝냄
        assertEquals(savedCart.getMember().getId(), member.getId());//에러가안나면
    }
}
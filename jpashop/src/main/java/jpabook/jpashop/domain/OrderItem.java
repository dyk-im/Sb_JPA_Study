package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.item.Item;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item; //주문 상품

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order; //주문
	private int orderPrice; //주문 가격
	private int count; //주문 수량

	public static OrderItem createOrderItem(Item item, int orderPrice, int
		count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		item.removeStock(count);
		return orderItem;
	}
	//==비즈니스 로직==//
	/** 주문 취소 */
	public void cancel() {
		getItem().addStock(count);
	}
	//==조회 로직==//
	/** 주문상품 전체 가격 조회 */
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}

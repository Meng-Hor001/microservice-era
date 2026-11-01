package kh.edu.istad.platform.order.domain.entity;

import kh.edu.istad.common.domain.entity.AggregateRoot;
import kh.edu.istad.common.domain.valueobject.*;
import kh.edu.istad.platform.order.domain.exception.OrderDomainException;
import kh.edu.istad.platform.order.domain.valueobject.StreetAddress;
import kh.edu.istad.platform.order.domain.valueobject.TrackingId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final BusinessId businessId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;
    private  TrackingId trackingId;
    private  OrderStatus orderStatus;
    private List<String> failureMessages;
    private Long version;

    //-------- Start domain logic ------------//
    /**
     * Initialize order
     */
    public void initializeOrder(){
        if (super.getId() != null || orderStatus != null){
            throw new OrderDomainException("Order has  already been initialized");
        }

        super.setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        validateOrder();;
    }

    /**
     * Validate order data
     */
    public void validateOrder(){
        // validate order price
        if (price == null && price.isGreaterThanZero()){
            throw new OrderDomainException("Price must be greater than zero");
        }

        // validate product price
        Money orderSubTotal = items.stream()
                .map(orderItem ->{
                    validateItemPrice(orderItem);
                    return orderItem.getSubTotal();
                })
                .reduce(Money.ZERO, Money::add);

        if (!price.equals(orderSubTotal)){
            throw new OrderDomainException("price must be equal to order sub total");
        }
    }

    /**
     * Validate item price
     */
    private void validateItemPrice(OrderItem orderItem){
        if (orderItem.getPrice().equals(orderItem.getProduct().getPrice())){
            throw new OrderDomainException("price must be equal to product price");
        }
    }

    /**
     * Pay price
     */
    public void pay(){
        if (orderStatus != OrderStatus.PENDING){
            throw new OrderDomainException("Order state is not correct for pay operation");
        }
    }

    /**
     * Pay price
     */

   public void approved(){
       if (orderStatus != OrderStatus.PAID){
           throw new OrderDomainException("Order state is not correct for approved operation");
       }
       orderStatus = OrderStatus.APPROVED;
   }

    /**
     * Init cancel order
     */
    public void initCancel(List<String> failureMessages){
        if (orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order state is not correct for cancel operation");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    /**
     * cancel order
     */

    public void cancel(){
        if (orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING){
            throw new OrderDomainException("Order state is not correct for cancel operation");
        }
        orderStatus = OrderStatus.CANCELLED;
    }

    private void updateFailureMessages(List<String> failureMessages){
        if (this.failureMessages != null && failureMessages != null){
            this.failureMessages.addAll(failureMessages.stream().filter(
                    message -> !message.isBlank()
            ).toList());
        }else {
            this.failureMessages = new ArrayList<>();
        }
    }



    //-------- end domain logic ------------//


    public CustomerId getCustomerId() {
        return customerId;
    }

    public BusinessId getBusinessId() {
        return businessId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public Long getVersion() {
        return version;
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        businessId = builder.businessId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.status;
        failureMessages = builder.failureMessages;
        version = builder.version;
    }


    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private BusinessId businessId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus status;
        private List<String> failureMessages;
        private Long version;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder businessId(BusinessId val) {
            businessId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder status(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}

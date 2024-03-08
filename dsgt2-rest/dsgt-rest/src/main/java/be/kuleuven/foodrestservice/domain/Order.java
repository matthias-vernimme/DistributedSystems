package be.kuleuven.foodrestservice.domain;

import java.util.List;
import java.util.Objects;

public class Order {
    private String orderId;
    private String address;
    private List<String> mealIds;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMealIds() {
        return mealIds;
    }

    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(address, order.address) &&
                Objects.equals(mealIds, order.mealIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, address, mealIds);
    }
}

    package ordernow.backend.ordernow_backend.requests.order;

    import java.util.ArrayList;
    import java.util.List;

    import ordernow.backend.ordernow_backend.entities.OrderItem;

    public class CreateNewOrderRequest {
        private String qrToken;
        private List<OrderItem> orderList = new ArrayList<>();

        public List<OrderItem> getOrderList() {
            return orderList;
        }
        
        public void setOrderList(List<OrderItem> orderList) {
            this.orderList = orderList;
        }

        public String getQrToken() {
            return qrToken;
        }

        public void setQrToken(String qrToken) {
            this.qrToken = qrToken;
        }
    }

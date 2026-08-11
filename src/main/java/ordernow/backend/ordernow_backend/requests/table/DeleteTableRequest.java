package ordernow.backend.ordernow_backend.requests.table;

public class DeleteTableRequest {
    private String qrToken;

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public String getQrToken() {
        return qrToken;
    }
}

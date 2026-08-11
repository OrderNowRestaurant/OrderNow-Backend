package ordernow.backend.ordernow_backend.requests.table;

import ordernow.backend.ordernow_backend.enums.StatusTypeEnum;

public class UpdateTableRequest {
    private String qrToken;
    private StatusTypeEnum newStatus;

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public String getQrToken() {
        return qrToken;
    }

    public StatusTypeEnum getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(StatusTypeEnum newStatus) {
        this.newStatus = newStatus;
    }
}
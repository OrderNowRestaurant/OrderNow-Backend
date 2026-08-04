package ordernow.backend.ordernow_backend.responses.table;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class TableResponse extends BaseResponse {
    private List<ServiceTable> tableList;

    public TableResponse(List<ServiceTable> tableList, String message) {
        super(message);
        this.tableList = tableList;
    }

    public List<ServiceTable> getTables() {
        return tableList;
    }

    public void setTables(List<ServiceTable> tableList) {
        this.tableList = tableList;
    }
}

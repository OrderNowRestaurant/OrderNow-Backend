package ordernow.backend.ordernow_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.table.CreateTableRequest;
import ordernow.backend.ordernow_backend.requests.table.DeleteTableRequest;
import ordernow.backend.ordernow_backend.requests.table.UpdateTableRequest;
import ordernow.backend.ordernow_backend.responses.table.TableResponse;
import ordernow.backend.ordernow_backend.services.TableService;

@RestController
@RequestMapping("/api/table")
public class TableServiceController {

     private final TableService tableService;

    public TableServiceController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/get")
    public TableResponse getTables() {
        return this.tableService.getAllTables();
    }

    @PostMapping("/create")
    public TableResponse createTable(@RequestBody CreateTableRequest createTableRequest) {
        return this.tableService.createTable(createTableRequest);
    }

    @PostMapping("/delete")
    public TableResponse deleteTable(@RequestBody DeleteTableRequest deleteTableRequest) {
        return this.tableService.deleteTable(deleteTableRequest);
    }

    @PutMapping("/status/update")
    public TableResponse updateTable(@RequestBody UpdateTableRequest updateTableRequest) {
        return this.tableService.updateTable(updateTableRequest);
    }
}

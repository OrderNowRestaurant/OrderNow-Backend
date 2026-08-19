package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.RestaurantRepository;
import ordernow.backend.ordernow_backend.repositories.TableServiceRepository;
import ordernow.backend.ordernow_backend.requests.table.CreateTableRequest;
import ordernow.backend.ordernow_backend.requests.table.DeleteTableRequest;
import ordernow.backend.ordernow_backend.requests.table.UpdateTableRequest;
import ordernow.backend.ordernow_backend.responses.table.TableResponse;

@Service
public class TableService {

    private TableServiceRepository tableServiceRepository;
    private AuthService authService;
    private RestaurantRepository restaurantRepository;

    public TableService(TableServiceRepository tableServiceRepository, AuthService authService, RestaurantRepository restaurantRepository) {
        this.tableServiceRepository = tableServiceRepository;
        this.authService = authService;
        this.restaurantRepository = restaurantRepository;
    }


    public TableResponse getAllTables() {
        List<ServiceTable> tableList = tableServiceRepository.findByRestaurant(authService.getAuthenticatedUser().getRestaurant(), Sort.by(Sort.Direction.ASC, "createdAt"));

        if (tableList == null || tableList.isEmpty()) {
            throw new ResourceNotFoundException("No se ha encontrado ninguna mesa para este restaurante.");
        }
        
        return new TableResponse(tableList, "Se han encontrado las mesas del restaurante.");
    }

    /**
     * Create a table and returns a list of it
     * @param createTableRequest
     * @return
     */
    public TableResponse createTable(CreateTableRequest createTableRequest) {
        Restaurant restaurant = restaurantRepository.findByUserList_Username(authService.getAuthenticatedUser().getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado al usuario: " + authService.getAuthenticatedUser().getUsername()));

        ServiceTable newTable = new ServiceTable(createTableRequest.getName(), restaurant);
        ServiceTable savedTable = tableServiceRepository.save(newTable);

        List<ServiceTable> serviceTables = List.of(savedTable);

        return new TableResponse(serviceTables, "La mesa se ha creado correctamente.");
    }

    public TableResponse updateTable(UpdateTableRequest updateTableRequest) {
        ServiceTable table = tableServiceRepository.findByQrToken(updateTableRequest.getQrToken());

        if(table == null) {
            throw new ResourceNotFoundException("No se ha encontrado la mesa.");
        }

        table.setStatus(updateTableRequest.getNewStatus());

        ServiceTable savedTable = tableServiceRepository.save(table);

        List<ServiceTable> serviceTables = List.of(savedTable);

        return new TableResponse(serviceTables, "La mesa se ha actualizado correctamente.");
    }

    public TableResponse deleteTable(DeleteTableRequest deleteTableRequest) {
        ServiceTable table = tableServiceRepository.findByQrToken(deleteTableRequest.getQrToken());

        if (table == null) {
            throw new ResourceNotFoundException("La mesa indicada no se ha encontrado.");
        }
        
        tableServiceRepository.delete(table);

        return new TableResponse(null, "La mesa se ha borrado correctamente.");
    }
    
}
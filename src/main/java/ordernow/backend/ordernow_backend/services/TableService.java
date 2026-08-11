package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.RestaurantRepository;
import ordernow.backend.ordernow_backend.repositories.TableServiceRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.table.CreateTableRequest;
import ordernow.backend.ordernow_backend.requests.table.DeleteTableRequest;
import ordernow.backend.ordernow_backend.requests.table.UpdateTableRequest;
import ordernow.backend.ordernow_backend.responses.table.TableResponse;

@Service
public class TableService {

    private TableServiceRepository tableServiceRepository;
    private AuthService authService;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    public TableService(TableServiceRepository tableServiceRepository, AuthService authService, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.tableServiceRepository = tableServiceRepository;
        this.authService = authService;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public TableResponse getAllTables() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        List<ServiceTable> tableList = tableServiceRepository.findByRestaurant(user.getRestaurant(), Sort.by(Sort.Direction.ASC, "createdAt"));

        if (tableList.isEmpty()) {
            return new TableResponse(null, "No se ha encontrado ninguna mesa para este restaurante.");
        } else {
            return new TableResponse(tableList, "Se han encontrado las mesas del restaurante.");
        }
    }

    /**
     * Create a table and returns a list of it
     * @param createTableRequest
     * @return
     */
    public TableResponse createTable(CreateTableRequest createTableRequest) {
        String username = authService.getUsername();

        Restaurant restaurant = restaurantRepository.findByUserList_Username(username)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado para el usuario: " + username));

        ServiceTable newTable = new ServiceTable(createTableRequest.getName(), restaurant);
        ServiceTable savedTable = tableServiceRepository.save(newTable);

        List<ServiceTable> serviceTables = List.of(savedTable);

        return new TableResponse(serviceTables, "La mesa se ha creado correctamente.");
    }

    public TableResponse updateTable(UpdateTableRequest updateTableRequest) {
        ServiceTable table = tableServiceRepository.findByQrToken(updateTableRequest.getQrToken());
        table.setStatus(updateTableRequest.getNewStatus());

        ServiceTable savedTable = tableServiceRepository.save(table);

        List<ServiceTable> serviceTables = List.of(savedTable);

        return new TableResponse(serviceTables, "La mesa se ha actualizado correctamente.");
    }

    public TableResponse deleteTable(DeleteTableRequest deleteTableRequest) {
        ServiceTable table = tableServiceRepository.findByQrToken(deleteTableRequest.getQrToken());

        if (table != null) {
            tableServiceRepository.delete(table);
        } else {
            return new TableResponse(null, "La mesa indicada no se ha encontrado.");
        }
        

        return new TableResponse(null, "La mesa se ha borrado correctamente.");
    }
    
}
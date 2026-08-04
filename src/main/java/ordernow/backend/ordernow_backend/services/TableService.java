package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.ServiceTable;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.RestaurantRepository;
import ordernow.backend.ordernow_backend.repositories.TableServiceRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.restaurant.CreateRestaurantRequest;
import ordernow.backend.ordernow_backend.requests.table.CreateTableRequest;
import ordernow.backend.ordernow_backend.responses.restaurant.RestaurantResponse;
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

        List<ServiceTable> tableList = tableServiceRepository.findByRestaurant(user.getRestaurant());

        if (tableList.isEmpty()) {
            return new TableResponse(null, "No se ha encontrado ninguna mesa para este restaurante.");
        } else {
            return new TableResponse(tableList, "Se han encontrado las mesas del restaurante.");
        }
    }

    public TableResponse createTable(CreateTableRequest createTableRequest) {
        String username = authService.getUsername();

        Restaurant restaurant = restaurantRepository.findByUserList_Username(username)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado para el usuario: " + username));

        ServiceTable newTable = new ServiceTable(createTableRequest.getName(), restaurant);
        ServiceTable savedTable = tableServiceRepository.save(newTable);

        List<ServiceTable> serviceTables = List.of(savedTable);

        return new TableResponse(serviceTables, "La mesa se ha creado correctamente.");
    }
    
}
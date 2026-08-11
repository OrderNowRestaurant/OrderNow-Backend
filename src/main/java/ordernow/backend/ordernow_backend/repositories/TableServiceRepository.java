package ordernow.backend.ordernow_backend.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.ServiceTable;

@Repository
public interface TableServiceRepository extends CrudRepository<ServiceTable, Long> {
    List<ServiceTable> findByRestaurant(Restaurant idRestaurant, Sort sort);

    ServiceTable findByQrToken(String qrToken);
}
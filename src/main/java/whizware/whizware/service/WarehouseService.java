package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;

    private static final String WAREHOUSE_NOT_FOUND_MESSEGE = "Warehouse with ID %d not found";
    private static final String LOCATION_NOT_FOUND_MESSEGE = "Location with ID %d not found";

    public BaseResponse getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new NoContentException("Warehouse is empty");
        }

        List<WarehouseResponse> data = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            data.add(WarehouseResponse.builder()
                    .id(warehouse.getId())
                    .name(warehouse.getName())
                    .locationId(warehouse.getLocation().getId())
                    .build());
        }

        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(WAREHOUSE_NOT_FOUND_MESSEGE, id)));

        WarehouseResponse data = WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .locationId(warehouse.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("Success")
                .data(data)
                .build();
    }

    public BaseResponse saveWarehouse(WarehouseRequest request) {
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, request.getLocationId())));

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setLocation(location);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        WarehouseResponse data = WarehouseResponse.builder()
                .id(savedWarehouse.getId())
                .name(savedWarehouse.getName())
                .locationId(savedWarehouse.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("Warehouse successfully added")
                .data(data)
                .build();
    }

    public BaseResponse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(WAREHOUSE_NOT_FOUND_MESSEGE, id)));
        Location location = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(LOCATION_NOT_FOUND_MESSEGE, request.getLocationId())));

        warehouse.setLocation(location);
        warehouse.setName(request.getName());
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        WarehouseResponse data = WarehouseResponse.builder()
                .id(updatedWarehouse.getId())
                .name(updatedWarehouse.getName())
                .locationId(updatedWarehouse.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("Warehouse successfully updated")
                .data(data)
                .build();
    }

    public BaseResponse deleteWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(WAREHOUSE_NOT_FOUND_MESSEGE, id)));
        warehouseRepository.delete(warehouse);
        return BaseResponse.builder()
                .message("Warehouse successfully deleted")
                .build();
    }

}

package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Warehouse;
import whizware.whizware.repository.LocationRepository;
import whizware.whizware.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    public final WarehouseRepository warehouseRepository;
    public final LocationRepository locationRepository;

    public BaseResponse getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();

        List<WarehouseResponse> data = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            data.add(WarehouseResponse.builder()
                    .id(warehouse.getId())
                    .name(warehouse.getName())
                    .locationId(warehouse.getLocation().getId())
                    .build());
        }

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse getWarehouseById(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("warehouse with ID " + id + " not found")
                    .data(null)
                    .build();
        }

        WarehouseResponse data = WarehouseResponse.builder()
                .id(warehouse.get().getId())
                .name(warehouse.get().getName())
                .locationId(warehouse.get().getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse saveWarehouse(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        Optional<Location> location = locationRepository.findById(request.getLocationId());
        if (location.isEmpty()) {
            return BaseResponse.builder()
                    .message("Location with ID " + request.getLocationId() + " is not found!")
                    .data(null)
                    .build();
        }
        warehouse.setLocation(location.get());

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        WarehouseResponse data = WarehouseResponse.builder()
                .id(savedWarehouse.getId())
                .name(savedWarehouse.getName())
                .locationId(savedWarehouse.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse updateWarehouse(Long id, WarehouseRequest request) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(id);
        if (warehouseOptional.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + id + " not found")
                    .build();
        }

        Warehouse warehouse = warehouseOptional.get();
        Optional<Location> location = locationRepository.findById(request.getLocationId());
        if (location.isEmpty()) {
            return BaseResponse.builder()
                    .message("Location with ID " + id + " not found")
                    .build();
        }
        warehouse.setLocation(location.get());
        warehouse.setName(request.getName());
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        WarehouseResponse data = WarehouseResponse.builder()
                .id(updatedWarehouse.getId())
                .name(updatedWarehouse.getName())
                .locationId(updatedWarehouse.getLocation().getId())
                .build();

        return BaseResponse.builder()
                .message("berhasil")
                .data(data)
                .build();
    }

    public BaseResponse deleteWarehouse(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if (warehouse.isEmpty()) {
            return BaseResponse.builder()
                    .message("Warehouse with ID " + id + " not found")
                    .build();
        }
        warehouseRepository.delete(warehouse.get());
        return BaseResponse.builder()
                .message("berhasil")
                .data(warehouse.get())
                .build();
    }

}

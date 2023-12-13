package whizware.whizware.util;

import whizware.whizware.dto.warehouse.WarehouseRequest;
import whizware.whizware.dto.warehouse.WarehouseResponse;
import whizware.whizware.entity.Location;
import whizware.whizware.entity.Warehouse;

public class TestUtilities {

    public static WarehouseResponse generateWarehouseResponse(Long id, String name, Long locationId) {
        return WarehouseResponse.builder()
                .id(id)
                .name(name)
                .locationId(locationId)
                .build();
    }

    public static WarehouseRequest generateWarehouseRequest(String name, Long locationId) {
        WarehouseRequest request = new WarehouseRequest();
        request.setName(name);
        request.setLocationId(locationId);
        return request;
    }

    public static Warehouse generateWarehouse(Long id, String name, Location location) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setName(name);
        warehouse.setLocation(location);
        return warehouse;
    }

    public static Location generateLocation(Long id, String name) {
        Location location = new Location();
        location.setId(id);
        location.setName(name);
        return location;
    }

}

package whizware.whizware.dto.warehouse;

import lombok.Data;

@Data
public class WarehouseRequest {
    private String name;
    private Long locationId;
}

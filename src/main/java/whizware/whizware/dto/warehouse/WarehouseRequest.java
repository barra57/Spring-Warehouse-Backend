package whizware.whizware.dto.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseRequest {
    @NotBlank(message = "Please enter Warehouse Name")
    private String name;

    @NotNull(message = "Please enter Location ID")
    private Long locationId;
}

package whizware.whizware.dto.transfer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestTransfer {
    @NotNull(message = "Please enter Warehouse ID")
    private Long warehouseId;

    @NotNull(message = "Please enter Warehouse Target ID")
    private Long warehouseTargetId;

    @NotNull(message = "Please enter Goods ID")
    private Long goodsId;

    @NotNull(message = "Please enter Quantity")
    @Min(message = "Quantity must be above 0", value = 0L)
    private Long quantity;
}

package whizware.whizware.dto.receipt;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiptRequest {
    @NotNull(message = "Please enter Warehouse ID")
    private Long warehouseId;

    @NotNull(message = "Please enter Goods ID")
    private Long goodsId;

    @NotNull(message = "Please enter Quantity")
    @Min(message = "Quantity must be above 0", value = 0L)
    private Long quantity;
    
    @NotBlank(message = "Please enter Suplier")
    private String suplier;
}

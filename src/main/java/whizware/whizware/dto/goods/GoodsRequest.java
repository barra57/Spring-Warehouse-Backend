package whizware.whizware.dto.goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsRequest {
    @NotBlank(message = "Please enter Goods Name")
    private String name;

    @NotNull(message = "Please enter the Selling Price")
    @Min(message = "Selling Price must be above 0", value = 0L)
    private BigDecimal sellingPrice;

    @NotNull(message = "Please enter the Purchase Price")
    @Min(message = "Purchase Price must be above 0", value = 0L)
    private BigDecimal purchasePrice;

    @NotBlank(message = "Please enter Goods Description")
    private String description;
}

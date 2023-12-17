package whizware.whizware.dto.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsRequest {

    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;
    private String description;
}

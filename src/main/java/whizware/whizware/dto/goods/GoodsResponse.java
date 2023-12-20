package whizware.whizware.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class GoodsResponse {
    private Long id;
    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;
    private String description;
}

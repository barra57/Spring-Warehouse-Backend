package whizware.whizware.dto.stock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockResponse {

    private Long id;
    private Long warehouseId;
    private Long goodsId;
    private Long quantity;
}

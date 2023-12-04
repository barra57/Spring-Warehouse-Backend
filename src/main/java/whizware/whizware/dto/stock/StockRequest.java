package whizware.whizware.dto.stock;

import lombok.Data;

@Data
public class StockRequest {

    private Long id;
    private Long warehouseId;
    private Long goodsId;
    private Long qty;
}

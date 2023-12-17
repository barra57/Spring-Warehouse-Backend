package whizware.whizware.dto.transfer;

import lombok.Data;

@Data
public class RequestTransfer {
    private Long warehouseId;
    private Long warehouseTargetId;
    private Long goodsId;
    private Integer quantity;
}

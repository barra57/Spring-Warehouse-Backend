package whizware.whizware.dto.receipt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;

@Data
@Builder
public class ReceiptResponse {
    private Long id;
    private Long warehouseId;
    private Long goodsId;
    private Long quantity;
    private BigDecimal totalPrice;
    private String supplier;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}

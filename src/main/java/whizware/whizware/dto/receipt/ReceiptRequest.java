package whizware.whizware.dto.receipt;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ReceiptRequest {
    private Long warehouseId;
    private Long goodsId;
    private Long quantity;
    private BigDecimal totalPrice;
    private String suplier;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}

package whizware.whizware.dto.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
public class DeliveryResponse {

    private Long id;
    private Long warehouseId;
    private Long storeId;
    private Long goodsId;
    private Long qty;
    private BigDecimal totalPrice;
    private String status;
    @JsonFormat(pattern = "DD-MM-yyyy")
    private Date date;
}

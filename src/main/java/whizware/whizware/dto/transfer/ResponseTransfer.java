package whizware.whizware.dto.transfer;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseTransfer {
    private Long id;
    private Integer quantity;
    private String status;
    private Date date;
    private Long warehouse_id;
    private Long warehouse_target_id;
    private Long goods_id;
}

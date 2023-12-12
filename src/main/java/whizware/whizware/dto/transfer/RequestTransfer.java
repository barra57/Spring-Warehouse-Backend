package whizware.whizware.dto.transfer;

import lombok.Data;

import java.util.Date;

@Data
public class RequestTransfer {
    private Integer quantity;
    private String status;
    private Date date;
    private Long warehouse_id;
    private Long warehouse_target_id;
    private Long goods_id;
}

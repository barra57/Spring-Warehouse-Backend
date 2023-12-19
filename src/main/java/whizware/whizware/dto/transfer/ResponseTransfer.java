package whizware.whizware.dto.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseTransfer {
    private Long id;
    private Integer quantity;
    private String status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private Long warehouse_id;
    private Long warehouse_target_id;
    private Long goods_id;
}

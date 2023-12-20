package whizware.whizware.dto.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseTransfer {
    private Long id;
    private Long warehouseId;
    private Long warehouseTargetId;
    private Long goodsId;
    private Long quantity;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}

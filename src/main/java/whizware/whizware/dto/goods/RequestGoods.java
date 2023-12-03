package whizware.whizware.dto.goods;

import lombok.Data;

@Data
public class RequestGoods {

    private Long idGoods;
    private String nameGoods;
    private Long sellingPrice;
    private Long purchasePrice;
    private String description;
}

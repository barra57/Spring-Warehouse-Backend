package whizware.whizware.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idGoods;
    private String nameGoods;
    private Long sellingPrice;
    private Long purchasePrice;
    private String description;

}

package whizware.whizware.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Goods goods;
    private Long qty;
}

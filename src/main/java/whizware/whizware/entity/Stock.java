package whizware.whizware.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Goods goods;
    private Long quantity;
}

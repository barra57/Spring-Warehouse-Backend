package whizware.whizware.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.dto.goods.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;
    @Autowired
    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    private String condition = "Success";

    @SneakyThrows
    public BaseResponse saveGoods(RequestGoods requestGoods) {
        Goods goods = new Goods();
        goods.setNameGoods(requestGoods.getNameGoods());
        goods.setDescription(requestGoods.getDescription());
        goods.setSellingPrice(requestGoods.getSellingPrice());
        goods.setSellingPrice(requestGoods.getSellingPrice());
        goodsRepository.save(goods);

        ResponseGoods data = ResponseGoods.builder()
                .nameGoods(goods.getNameGoods())
                .description(goods.getDescription())
                .sellingPrice(goods.getSellingPrice())
                .purchasePrice(goods.getPurchasePrice())
                .build();

        return BaseResponse.builder()
                .message(condition)
                .data(data)
                .build();
    }
    @SneakyThrows
    public BaseResponse getAllGoods(RequestGoods requestGoods) {
        List<ResponseGoods> responseGoods = new ArrayList<>();
        List<Goods> goods = goodsRepository.findAll();
        for (Goods g : goods) {
            responseGoods.add(ResponseGoods.builder()
                            .idGoods(g.getIdGoods())
                            .nameGoods(g.getNameGoods())
                            .description(g.getDescription())
                            .sellingPrice(g.getSellingPrice())
                            .purchasePrice(g.getPurchasePrice())
                    .build());
        }

        return BaseResponse.builder()
                .message(condition)
                .data(responseGoods)
                .build();
    }
    @SneakyThrows
    public BaseResponse updateGoods(Long id, RequestGoods requestGoods) {
        Optional<Goods> optionalGoods = goodsRepository.findById(id);
        Goods goods = new Goods();
        if (!optionalGoods.isPresent() || !optionalGoods.get().getIdGoods().equals(id)) {
            return BaseResponse.builder()
                    .message("Gagal")
                    .data(null)
                    .build();
        }
        else {
            goods.setNameGoods(requestGoods.getNameGoods());
            goods.setDescription(requestGoods.getDescription());
            goods.setSellingPrice(requestGoods.getSellingPrice());
            goods.setPurchasePrice(requestGoods.getPurchasePrice());
            goodsRepository.save(goods);
        }
        ResponseGoods data = ResponseGoods.builder()
                .nameGoods(requestGoods.getNameGoods())
                .description(requestGoods.getDescription())
                .sellingPrice(requestGoods.getSellingPrice())
                .purchasePrice(requestGoods.getPurchasePrice())
                .build();
        return BaseResponse.builder()
                .message("Berhasil")
                .data(data)
                .build();
    }
    @SneakyThrows
    public BaseResponse deleteIdGoods(Long id) {
        Optional<Goods> optionalGoods = goodsRepository.findById(id);

        if (optionalGoods.isPresent()) {
            goodsRepository.deleteById(id);
            return BaseResponse.builder()
                    .message("Delete Success")
                    .data(null)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("id not found")
                    .data(null)
                    .build();
        }
    }

    @SneakyThrows
    public BaseResponse getGoodsById(Long id, RequestGoods requestGoods) {
        Optional<Goods> responseGoods = goodsRepository.findById(id);
        if (responseGoods.isPresent()) {
            ResponseGoods data = ResponseGoods.builder()
                    .idGoods(requestGoods.getIdGoods())
                    .nameGoods(requestGoods.getNameGoods())
                    .description(requestGoods.getDescription())
                    .sellingPrice(requestGoods.getSellingPrice())
                    .purchasePrice(requestGoods.getPurchasePrice())
                    .build();

            return BaseResponse.builder()
                    .message(condition)
                    .data(data)
                    .build();
        } else {
            return BaseResponse.builder()
                    .message("Gagal")
                    .data(null)
                    .build();
        }
    }
}

package whizware.whizware.service;

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

    public BaseResponse saveGoods(GoodsRequest goodsRequest) {
        Goods goods = new Goods();
        goods.setName(goodsRequest.getName());
        goods.setDescription(goodsRequest.getDescription());
        goods.setSellingPrice(goodsRequest.getSellingPrice());
        goods.setPurchasePrice(goodsRequest.getPurchasePrice());
        goodsRepository.save(goods);

        GoodsResponse data = GoodsResponse.builder()
                .id(goods.getId())
                .name(goods.getName())
                .description(goods.getDescription())
                .sellingPrice(goods.getSellingPrice())
                .purchasePrice(goods.getPurchasePrice())
                .build();

        return BaseResponse.builder()
                .message(condition)
                .data(data)
                .build();
    }

    public BaseResponse getAllGoods() {
        List<GoodsResponse> goodResponses = new ArrayList<>();
        List<Goods> goods = goodsRepository.findAll();
        for (Goods g : goods) {
            goodResponses.add(GoodsResponse.builder()
                    .id(g.getId())
                    .name(g.getName())
                    .description(g.getDescription())
                    .sellingPrice(g.getSellingPrice())
                    .purchasePrice(g.getPurchasePrice())
                    .build());
        }

        return BaseResponse.builder()
                .message(condition)
                .data(goodResponses)
                .build();
    }

    public BaseResponse updateGoods(Long id, GoodsRequest goodsRequest) {
        Optional<Goods> optionalGoods = goodsRepository.findById(id);
        if (!optionalGoods.isPresent()) {
            return BaseResponse.builder()
                    .message("Gagal")
                    .data(null)
                    .build();
        }

        Goods goods = optionalGoods.get();
        goods.setName(goodsRequest.getName());
        goods.setDescription(goodsRequest.getDescription());
        goods.setSellingPrice(goodsRequest.getSellingPrice());
        goods.setPurchasePrice(goodsRequest.getPurchasePrice());
        goodsRepository.save(goods);

        GoodsResponse data = GoodsResponse.builder()
                .id(goods.getId())
                .name(goods.getName())
                .description(goods.getDescription())
                .sellingPrice(goods.getSellingPrice())
                .purchasePrice(goods.getPurchasePrice())
                .build();
        return BaseResponse.builder()
                .message("Berhasil")
                .data(data)
                .build();
    }

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


    public BaseResponse getGoodsById(Long id) {
        Optional<Goods> responseGoods = goodsRepository.findById(id);
        if (responseGoods.isPresent()) {
            GoodsResponse data = GoodsResponse.builder()
                    .id(responseGoods.get().getId())
                    .name(responseGoods.get().getName())
                    .description(responseGoods.get().getDescription())
                    .sellingPrice(responseGoods.get().getSellingPrice())
                    .purchasePrice(responseGoods.get().getPurchasePrice())
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

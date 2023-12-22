package whizware.whizware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;
import whizware.whizware.entity.Goods;
import whizware.whizware.exception.NoContentException;
import whizware.whizware.exception.NotFoundException;
import whizware.whizware.repository.GoodsRepository;
import whizware.whizware.dto.goods.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String GOODS_NOT_FOUND_MESSAGE = "Goods with ID %d not found";

    public BaseResponse getAllGoods() {
        List<Goods> goods = goodsRepository.findAll();
        if (goods.isEmpty())
            throw new NoContentException("Goods is empty");

        List<GoodsResponse> goodResponses = new ArrayList<>();
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
                .message(SUCCESS_MESSAGE)
                .data(goodResponses)
                .build();
    }

    public BaseResponse getGoodsById(Long id) {
        Goods responseGoods = goodsRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(GOODS_NOT_FOUND_MESSAGE, id)));

        GoodsResponse data = GoodsResponse.builder()
                .id(responseGoods.getId())
                .name(responseGoods.getName())
                .description(responseGoods.getDescription())
                .sellingPrice(responseGoods.getSellingPrice())
                .purchasePrice(responseGoods.getPurchasePrice())
                .build();

        return BaseResponse.builder()
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

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
                .message("Goods successfully added")
                .data(data)
                .build();
    }


    public BaseResponse updateGoods(Long id, GoodsRequest goodsRequest) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(GOODS_NOT_FOUND_MESSAGE, id)));
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
                .message("Goods successfully updated")
                .data(data)
                .build();
    }

    public BaseResponse deleteIdGoods(Long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(GOODS_NOT_FOUND_MESSAGE, id)));

        goodsRepository.delete(goods);
        return BaseResponse.builder()
                .message("Goods successfully deleted")
                .build();
    }

}

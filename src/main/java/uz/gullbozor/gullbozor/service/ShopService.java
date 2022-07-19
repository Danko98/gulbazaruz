package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.ShopDto;
import uz.gullbozor.gullbozor.entity.AddressEntity;
import uz.gullbozor.gullbozor.entity.ShopEntity;
import uz.gullbozor.gullbozor.repository.AddressRepo;
import uz.gullbozor.gullbozor.repository.CityRepo;
import uz.gullbozor.gullbozor.repository.RegionRepo;
import uz.gullbozor.gullbozor.repository.ShopRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private RegionRepo regionRepo;

    @Autowired
    private CityRepo cityRepo;


    public ApiResponse createShop(ShopDto shopDto) {

        if (shopRepo.existsByShopName(shopDto.getShopName())) {
            return new ApiResponse("This shop name already exists",false);
        }
        if (!regionRepo.existsById(shopDto.getRegionId())){
            return new ApiResponse("Bunday viloyat topilamdi",false);
        }

        if (!cityRepo.existsById(shopDto.getCityId())){
            return new ApiResponse("Bunday shaxar yo tuman topilmadi",false);
        }

        ShopEntity shop = new ShopEntity();

        shop.setShopName(shopDto.getShopName());
        shop.setCityId(shopDto.getCityId());
        shop.setRegionId(shopDto.getRegionId());
        shop.setSellerId(shopDto.getSellerId());
        shop.setPhoneNumber1(shopDto.getPhoneNumber1());
        shop.setPhoneNumber2(shopDto.getPhoneNumber2());
        shopRepo.save(shop);

        return new ApiResponse("Successfully created",true);

    }

    public ApiResponse editShop(ShopDto shopDto, Long id) {

        if (!shopRepo.existsById(id)) {
            return new ApiResponse("Not found shop",false);
        }

        if (shopRepo.existsByShopName(shopDto.getShopName())) {
            return new ApiResponse("This shop name already exists",false);
        }


        if (!regionRepo.existsById(shopDto.getRegionId())){
            return new ApiResponse("Bunday viloyat topilamdi",false);
        }

        if (!cityRepo.existsById(shopDto.getCityId())){
            return new ApiResponse("Bunday shaxar yo tuman topilmadi",false);
        }

        Optional<ShopEntity> optionalShop = shopRepo.findById(id);
        ShopEntity shop = optionalShop.get();

        shop.setShopName(shopDto.getShopName());
        shop.setCityId(shopDto.getCityId());
        shop.setRegionId(shopDto.getRegionId());
        shop.setSellerId(shopDto.getSellerId());
        shop.setPhoneNumber1(shopDto.getPhoneNumber1());
        shop.setPhoneNumber2(shopDto.getPhoneNumber2());

        return new ApiResponse("Successfully edited",true);

    }

    public ApiResponse getShopById(Long id) {
        if (!shopRepo.existsById(id)) {
            return new ApiResponse("Not found shop",false);
        }

        return new ApiResponse(shopRepo.findById(id).get());

    }

    public List<ShopEntity> getShopList() {
        return shopRepo.findAll();
    }

    public Page<ShopEntity> getShopPage(int page) {
        Pageable pageable = PageRequest.of(page, 30);
        return shopRepo.findAll(pageable);
    }

    public ApiResponse deleteShopById(Long id) {
        if (!shopRepo.existsById(id)) {
            return new ApiResponse("Not found shop",false);
        }
        shopRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }

}

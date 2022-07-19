package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.ShopDto;
import uz.gullbozor.gullbozor.entity.ShopEntity;
import uz.gullbozor.gullbozor.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;


    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createShop(@RequestBody ShopDto shopDto) {
        ApiResponse apiResponse = shopService.createShop(shopDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editShop(@RequestBody ShopDto shopDto, @PathVariable Long id) {
        ApiResponse apiResponse = shopService.editShop(shopDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getShopById(@PathVariable Long id) {
        ApiResponse apiResponse = shopService.getShopById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<ShopEntity>> getShopList() {
        List<ShopEntity> shopList = shopService.getShopList();
        return ResponseEntity.ok(shopList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byPage")
    public ResponseEntity<Page<ShopEntity>> getShopPage(@RequestParam int page) {

        Page<ShopEntity> shopPage = shopService.getShopPage(page);
        return ResponseEntity.ok(shopPage);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteShopById(@PathVariable Long id) {
        ApiResponse apiResponse = shopService.deleteShopById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}

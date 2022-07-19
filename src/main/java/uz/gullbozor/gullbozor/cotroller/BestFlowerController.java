package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.BestFlowerEntity;
import uz.gullbozor.gullbozor.service.BestFlowerService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/bestFlower")
public class BestFlowerController {

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBestFlower(@RequestBody BestFlowerEntity bestFlowerEntity) {
        ApiResponse apiResponse = bestFlowerService.addBestFlower(bestFlowerEntity);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @Autowired
    private BestFlowerService bestFlowerService;

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/getFirstBestFlower")
    public ResponseEntity<List<BestFlowerEntity>> getFirstBestFlower() {
        List<BestFlowerEntity> bestFlowerEntityList = new ArrayList<>();

        BestFlowerEntity firstBestFlower = bestFlowerService.getFirstBestFlower();
        bestFlowerEntityList.add(firstBestFlower);

        BestFlowerEntity secondFlowerEntity = bestFlowerService.getSecondBestFlower(firstBestFlower.getFlowerValue());
        bestFlowerEntityList.add(secondFlowerEntity);

        firstBestFlower.setView(firstBestFlower.getView() + 1 );
        secondFlowerEntity.setView(secondFlowerEntity.getView() + 1);

        return ResponseEntity.ok(bestFlowerEntityList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/edit/{flowerId}")
    public ResponseEntity<ApiResponse> getSecondBestFlower(@RequestBody BestFlowerEntity bestFlowerEntity, @PathVariable Long flowerId) {
        ApiResponse apiResponse = bestFlowerService.editBestFlower(bestFlowerEntity,flowerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}

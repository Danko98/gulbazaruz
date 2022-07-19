package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.Reklama;
import uz.gullbozor.gullbozor.service.ReklamaService;

@RestController
@RequestMapping("/reklama")
public class ReklamaController {

    @Autowired
    private ReklamaService reklamaService;


    @PostMapping
    public ResponseEntity<ApiResponse> addReklama(@RequestBody Reklama reklamaImageDto) {
        ApiResponse apiResponse = reklamaService.addReklama(reklamaImageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReklamaById(@PathVariable Integer id) {
        ApiResponse apiResponse = reklamaService.getReklamaById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byPlaceNumber/{placeNumber}")
    public ResponseEntity<ApiResponse> getReklamaByPlaceNumber(@PathVariable Integer placeNumber) {
        ApiResponse apiResponse = reklamaService.getReklamaByPlaceNumber(placeNumber);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReklamaById(@PathVariable Integer id) {
        ApiResponse apiResponse = reklamaService.deleteReklamaById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409 ).body(apiResponse);
    }






}

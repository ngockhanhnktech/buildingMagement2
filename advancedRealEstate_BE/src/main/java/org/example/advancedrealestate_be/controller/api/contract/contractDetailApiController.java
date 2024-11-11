package org.example.advancedrealestate_be.controller.api.contract;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.service.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/contractDetail")
@Tag(name = "Admin contractDetail", description = "API for admin")
public class contractDetailApiController {
    @Autowired
    private ContractDetailService contractDetailService;

    @PostMapping
    public ResponseEntity<ContractDetailResponse> createContractDetail(@RequestBody ContractDetailRequest request) {
        return ResponseEntity.ok(contractDetailService.createContractDetail(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDetailResponse> getContractDetailById(@PathVariable String id) {
        return ResponseEntity.ok(contractDetailService.getContractDetailById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContractDetailResponse>> getAllContractDetails() {
        return ResponseEntity.ok(contractDetailService.getAllContractDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDetailResponse> updateContractDetail(
            @PathVariable String id,
            @RequestBody ContractDetailRequest request) {
        return ResponseEntity.ok(contractDetailService.updateContractDetail(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContractDetail(@PathVariable String id) {
        contractDetailService.deleteContractDetail(id);
        return ResponseEntity.noContent().build();
    }
}

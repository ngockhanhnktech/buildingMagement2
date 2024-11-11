package org.example.advancedrealestate_be.controller.api.contract;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/contract")
@Tag(name = "Admin contract", description = "API for admin")
public class contractApiController {
    @Autowired
    private ContractService contractsService;

    @PostMapping
    public ResponseEntity<ContractResponse> createContract(@RequestBody ContractRequest request) {
        ContractResponse response = contractsService.createContract(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponse> updateContract(@PathVariable String id, @RequestBody ContractRequest request) {
        ContractResponse response = contractsService.updateContract(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getContractById(@PathVariable String id) {
        ContractResponse response = contractsService.getContractById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAllContracts() {
        List<ContractResponse> responses = contractsService.getAllContracts();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable String id) {
        contractsService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }

}

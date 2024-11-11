package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Contracts;

import java.util.List;

public interface ContractService {

    public ContractResponse createContract(ContractRequest request);
    public ContractResponse updateContract(String id, ContractRequest request);
    public ContractResponse getContractById(String id);
    public List<ContractResponse> getAllContracts();
    public void deleteContract(String id);

    ContractResponse mapContractToResponse(Contracts contract);
    public void mapRequestToContract(ContractRequest request, Contracts contract);


}

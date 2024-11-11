package org.example.advancedrealestate_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractResponse {
    private String id;
    private String contractName;
    private String contractDetails;
    private String customerName;
    private String buildingName;
    private String userName;
    private List<String> transactionIds;
    private List<String> invoiceIds;
}

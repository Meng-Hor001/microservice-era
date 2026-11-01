package kh.edu.istad.platform.customer.restapi.controller;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.usecase.InitiateCustomerUseCase;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final InitiateCustomerUseCase initiateCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;


    public CustomerInitiateResponse initiateCustomer(
            @RequestBody CustomerInitiateRequest customerInitiateRequest
            ){
        InitiateCustomerResult result = initiateCustomerUseCase.execute(customerWebMapper.toCommand(customerInitiateRequest));
        return customerWebMapper.toResponse(result);
    }
}

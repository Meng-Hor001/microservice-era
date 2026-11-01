package kh.edu.istad.platform.customer.restapi.dto;

public record CustomerInitiateResponse(
        String familyName,
        String givenName,
        String email,
        String phoneNumber
){
}

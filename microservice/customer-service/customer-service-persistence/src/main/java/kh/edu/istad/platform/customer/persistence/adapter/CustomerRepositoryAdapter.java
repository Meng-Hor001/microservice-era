package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.entity.Customer;
import kh.edu.istad.platform.customer.persistence.entity.CustomerEntity;
import kh.edu.istad.platform.customer.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;



    @Override
    public Customer save(Customer customer) {
        customerJpaRepository.save(new CustomerEntity());
        return null;
    }
}

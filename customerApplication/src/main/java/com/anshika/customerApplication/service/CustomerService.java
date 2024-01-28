package com.anshika.customerApplication.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anshika.customerApplication.dto.CustomerDto;
import com.anshika.customerApplication.models.Customer;
import com.anshika.customerApplication.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired 
	private  CustomerRepository customerRepository;
	
	
	public List<CustomerDto> getAllCustomers(Pageable pageable, String search) {
        try {
            Page<Customer> customers = customerRepository.findAll(pageable);
            return convertToDTOs(customers.getContent());
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new RuntimeException("Error retrieving customers", e);
        }
    }
	
	public CustomerDto getCustomerById(Long id) {
		
		try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);

            return optionalCustomer.map(this::convertToDTO).orElse(null);
            
        } catch (Exception e) {
        	
            throw new RuntimeException("Error retrieving customer by ID", e);
        }
	}
	
	public CustomerDto createCustomer(CustomerDto customerDTO) {
        try {
            validateCustomerDTO(customerDTO); // Example: Add validation logic

            Customer customer = convertToEntity(customerDTO);
            Customer savedCustomer = customerRepository.save(customer);

            return convertToDTO(savedCustomer);
        } catch (DataIntegrityViolationException e) {
            // Handle specific exceptions, e.g., unique constraint violation
            throw new DuplicateKeyException("Customer with the same data already exists", e);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new RuntimeException("Error creating customer", e);
        }
    }

    private void validateCustomerDTO(CustomerDto customerDTO) {
		// TODO Auto-generated method stub
    	
		
	}

	private Customer convertToEntity(CustomerDto customerDTO) {
		// TODO Auto-generated method stub
		Customer customer = new Customer(customerDTO.getId(),
				customerDTO.getFirstName(), customerDTO.getLastName(),
				customerDTO.getStreet(), customerDTO.getAddress(), customerDTO.getCity(),
				customerDTO.getState(), customerDTO.getEmail(), customerDTO.getPhone());
		
		return customer;
	}
	
	private List<CustomerDto> convertToDTOs(List<Customer> customers) {
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	public CustomerDto updateCustomer(Long id, CustomerDto customerDTO) {
        try {
            validateCustomerDTO(customerDTO); // Example: Add validation logic

            Optional<Customer> optionalCustomer = customerRepository.findById(id);

            if (optionalCustomer.isPresent()) {
                Customer existingCustomer = optionalCustomer.get();
                updateCustomerFields(existingCustomer, customerDTO);
                Customer updatedCustomer = customerRepository.save(existingCustomer);
                return convertToDTO(updatedCustomer);
            } else {
                return null; // Handle not found scenario
            }
        } catch (DataIntegrityViolationException e) {
            // Handle specific exceptions, e.g., unique constraint violation
            throw new DuplicateKeyException("Customer with the same data already exists", e);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new RuntimeException("Error updating customer", e);
        }
    }

    private void updateCustomerFields(Customer existingCustomer, CustomerDto customerDTO) {
		// TODO Auto-generated method stub
    	
    	existingCustomer.setFirstName(customerDTO.getFirstName());
    	existingCustomer.setLastName(customerDTO.getLastName());
    	existingCustomer.setStreet(customerDTO.getStreet());
    	existingCustomer.setCity(customerDTO.getCity());
    	existingCustomer.setAddress(customerDTO.getAddress());
    	existingCustomer.setEmail(customerDTO.getEmail());
    	existingCustomer.setPhone(customerDTO.getPhone());
    	existingCustomer.setState(customerDTO.getState());
		
	}

	public void deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            throw new RuntimeException("Error deleting customer", e);
        }
    }
	
	
	private CustomerDto convertToDTO(Customer c) {
		
		CustomerDto customerDto = new CustomerDto( c.getId(),
				c.getFirstName(), c.getLastName(), c.getStreet(), c.getAddress(),
				c.getCity(), c.getState(), c.getEmail(), c.getPhone());
		return customerDto;
	}

}

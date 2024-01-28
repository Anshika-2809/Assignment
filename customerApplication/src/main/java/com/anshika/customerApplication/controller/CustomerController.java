package com.anshika.customerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anshika.customerApplication.dto.CustomerDto;
import com.anshika.customerApplication.service.CustomerService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "") String search) {
        
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            List<CustomerDto> customers = customerService.getAllCustomers(pageable, search);
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
		try {
            CustomerDto CustomerDto = customerService.getCustomerById(id);

            if (CustomerDto != null) {
                return new ResponseEntity<>(CustomerDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        	
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto createdCustomer = customerService.createCustomer(customerDto);
            return new ResponseEntity<>(createdCustomer, HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            // Handle specific exceptions, e.g., unique constraint violation
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        try {
            CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);

            return (updatedCustomer != null) ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

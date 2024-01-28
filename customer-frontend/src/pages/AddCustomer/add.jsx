import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AddCustomer = ({ onSubmit }) => {

  const [customerData, setCustomerData] = useState({
    id: null,
    firstName: '',
    lastName: '',
    street: '',
    address: '',
    city: '',
    state: '',
    email: '',
    phone: '',
  });

  const navigate = useNavigate();

  const handleSubmit = async(e) => {
    e.preventDefault();
    console.log(customerData);
    
    try {
        const token= localStorage.getItem("token");

        const response = await fetch('http://localhost:8081/api/customers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
              },
            body: JSON.stringify(customerData),
        });

        if (response.ok) {
            // Successful API call
            const createdCustomer = await response.json();
            console.log('Customer created:', createdCustomer);

            // Reset the form after handling the submission
            setCustomerData({
                id: null,
                firstName: '',
                lastName: '',
                street: '',
                address: '',
                city: '',
                state: '',
                email: '',
                phone: '',
            });

            navigate("/home");
            
        } else {
            // Handle errors based on the response status
            console.error('Error:', response.status, response.statusText);
        }
    } catch (error) {
        // Handle other errors, e.g., network issues
        console.error('Error:', error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          id="firstName"
          value={customerData.firstName}
          onChange={(e) => setCustomerData({ ...customerData, firstName: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="lastName">Last Name:</label>
        <input
          type="text"
          id="lastName"
          value={customerData.lastName}
          onChange={(e) => setCustomerData({ ...customerData, lastName: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="street">Street:</label>
        <input
          type="text"
          id="street"
          value={customerData.street}
          onChange={(e) => setCustomerData({ ...customerData, street: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="address">Address:</label>
        <input
          type="text"
          id="address"
          value={customerData.address}
          onChange={(e) => setCustomerData({ ...customerData, address: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="city">City:</label>
        <input
          type="text"
          id="city"
          value={customerData.city}
          onChange={(e) => setCustomerData({ ...customerData, city: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="state">State:</label>
        <input
          type="text"
          id="state"
          value={customerData.state}
          onChange={(e) => setCustomerData({ ...customerData, state: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={customerData.email}
          onChange={(e) => setCustomerData({ ...customerData, email: e.target.value })}
          required
        />
      </div>
      <div>
        <label htmlFor="phone">Phone:</label>
        <input
          type="tel"
          id="phone"
          value={customerData.phone}
          onChange={(e) => setCustomerData({ ...customerData, phone: e.target.value })}
          required
        />
      </div>
      <div>
        <button type="submit">Add Customer</button>
      </div>
    </form>
  );
};

export default AddCustomer;

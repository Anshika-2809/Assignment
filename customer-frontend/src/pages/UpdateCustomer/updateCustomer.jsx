import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import "./update.css";

const UpdateCustomer = () => {
  const navigate = useNavigate();
  const { customerId } = useParams();

  const [customer, setCustomer] = useState({
    id: '',
    firstName: '',
    lastName: '',
    street: '',
    address: '',
    city: '',
    state: '',
    email: '',
    phone: '',
  });

  useEffect(() => {
    // Fetch the customer details based on the customerId
    const fetchData = async () => {
        try {
          const token = localStorage.getItem("token");
          const response = await fetch(`http://localhost:8081/api/customers/${customerId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
    
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
    
          const data = await response.json();
          setCustomer(data);
        } catch (error) {
          console.error('Error fetching customer details:', error);
        }
      };
    fetchData();
  }, [customerId]);

  

  const handleUpdate = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await fetch(`http://localhost:8081/api/customers/${customerId}`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(customer),
      });

      if (response.ok) {
        // Successful update, navigate back to the home page 
        navigate('/home');
      } else {
        console.error('Error updating customer:', response.status, response.statusText);
        // Handle failed update request
      }
    } catch (error) {
      console.error('Error during update request:', error.message);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCustomer((prevCustomer) => ({ ...prevCustomer, [name]: value }));
  };

  return (
    <div>
      <h2>Update Customer</h2>
      <form>
        
        <div className='form-group'>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          id="firstName"
          name="firstName"
          value={customer.firstName}
          onChange={handleInputChange}
        />
        </div>

        <div className="form-group">
          <label htmlFor="lastName">Last Name:</label>
          <input
            type="text"
            id="lastName"
            name="lastName"
            value={customer.lastName}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="street">Street:</label>
          <input
            type="text"
            id="street"
            name="street"
            value={customer.street}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="address">Address:</label>
          <input
            type="text"
            id="address"
            name="address"
            value={customer.address}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="city">City:</label>
          <input
            type="text"
            id="city"
            name="city"
            value={customer.city}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="state">State:</label>
          <input
            type="text"
            id="state"
            name="state"
            value={customer.state}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={customer.email}
            onChange={handleInputChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">Phone:</label>
          <input
            type="tel"
            id="phone"
            name="phone"
            value={customer.phone}
            onChange={handleInputChange}
          />
        </div>
        

        <button type="button" onClick={handleUpdate}>
          Save Update
        </button>
      </form>
    </div>
  );
};

export default UpdateCustomer;

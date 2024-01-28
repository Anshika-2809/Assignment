import React, { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import { FaEdit, FaTrash } from 'react-icons/fa';
import "./home.css";

const Home = () => {

  const navigate = useNavigate();  
  const [customers, setCustomers] = useState([]);

  const [searchText, setSearchText] = useState('');
  const [selectedField, setSelectedField] = useState('');

  const fields = [
    'firstName',
    'lastName',
    'email',
    'phone',
    
  ];

  const handleDeleteCustomer = async (customerId) => {
    try {
      const token = localStorage.getItem("token");

      const response = await fetch(`http://localhost:8081/api/customers/${customerId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        // Successfully deleted, update the customer list
        fetchData();
      } else {
        console.error('Error deleting customer:', response.status, response.statusText);
        // Handle failed delete request
      }
    } catch (error) {
      console.error('Error during delete request:', error.message);
      // Handle other errors
    }
  };


  const handleInputChange = (e) => {
    setSearchText(e.target.value);
  };

  const handleFieldChange = (e) => {
    setSelectedField(e.target.value);
  };


  const handleEnterKey = (e) => {
    if (e.key === 'Enter') {
      // Call the API to get customer data based on the selected country or search text
      // For demonstration purposes, a placeholder API URL is used here.
      const apiUrl = `https://api.example.com/customers?country=${selectedField}&search=${searchText}`;

      // Assuming you are using fetch for API calls
    //   fetch(apiUrl)
    //     .then((response) => response.json())
    //     .then((data) => setCustomerData(data))
    //     .catch((error) => console.error('Error fetching customer data:', error));
    }
  };

  // Use useEffect to simulate API call on component mount for initial data
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const token= localStorage.getItem("token");
      const response = await fetch('http://localhost:8081/api/customers',
      {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },});
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      setCustomers(data);
    } catch (error) {
      console.error('Error fetching customer data:', error);
    }
  };

  const handleAddCustomer = (e)=>{
    navigate('/add');
  };

  const handleUpdateCustomer = (customerId) => {
    navigate(`/update/${customerId}`);
    console.log(`Updating customer with ID ${customerId}`);
  };


  return (
    <div>
      <h2>Customer List</h2>

      <div>
      <div style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
        <button onClick={handleAddCustomer}>Add Customer</button>

        {/* Search Form */}
        <div className="drop-down">
          <div id="Dropdown" className="dropdown-content">
            
            <select value={selectedField} onChange={handleFieldChange}>
              <option value="">Search By</option>
              {fields.map((field) => (
                <option key={field} value={field}>
                  {field}
                </option>
              ))}
            </select>
            <input
              type="text"
              id="userInput"
              value={searchText}
              onChange={handleInputChange}
              onKeyPress={handleEnterKey}
              placeholder="Search"
            />
          </div>
        </div>
      </div>

      
    </div>

      {/* Customer Table */}
      <table className="customer-table">
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.id}>
              <td>{customer.firstName}</td>
              <td>{customer.lastName}</td>
              <td>{customer.street}</td>
              <td>{customer.address}</td>
              <td>{customer.city}</td>
              <td>{customer.state}</td>
              <td>{customer.email}</td>
              <td>{customer.phone}</td>
              <td>
              <FaEdit
                    style={{ marginRight: '10px', cursor: 'pointer' }}
                    onClick={() => handleUpdateCustomer(customer.id)}
                  />
                  <FaTrash
                    style={{ cursor: 'pointer' }}
                    onClick={() => handleDeleteCustomer(customer.id)}
                  />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Home;

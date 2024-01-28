import React, {useState} from "react";
import "./login.css";
import { useNavigate } from "react-router-dom";


const Login= () =>{

    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const navigate= useNavigate();


    const handleLogin = async(event)=>{

        event.preventDefault();
    
        console.log('Login ID:', loginId);
        console.log('Password:', password);

        const apiEndpoint = 'http://localhost:8081/api/customers/token'; 

    try {

        const requestBody = {
            loginId: loginId,
            password: password,
          };

      const response = await fetch(apiEndpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Login successful:', data);
        localStorage.setItem("token", data.token);
        navigate('/home');
      } else {
        console.error('Login failed:', response.status, response.statusText);
      }
    } catch (error) {
      console.error('Error during login:', error.message);
    }

        setLoginId('');
        setPassword('');
    
    }
    
    return (

        <div>
        <h2>Login Page</h2>
        <form onSubmit={handleLogin}>
          <div>
            <label htmlFor="loginId">Login ID:</label>
            <input
              type="text"
              id="loginId"
              value={loginId}
              onChange={(e) => setLoginId(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div>
            <button type="submit">Submit</button>
          </div>
        </form>
      </div>

    )
}
export default Login;
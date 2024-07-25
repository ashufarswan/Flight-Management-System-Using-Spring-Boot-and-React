import React, { useCallback, useContext, useEffect } from 'react';
import "../css/Unauthorized.css"
import { AuthContext } from '../Context/AuthContext';


const UnauthorizedPage = () => {
  const authContext = useContext(AuthContext);
  useEffect( ()=>{
    authContext.setIsLoggedIn(false); 
    authContext.setRole('')
    window.sessionStorage.clear()
    window.sessionStorage.setItem("token",null);
    window.sessionStorage.setItem("isLoggedIn",false);
  })
  return (
    <div className="unauthorized-page bg-dark">
      <h1 className='text-white'>Unauthorized Access</h1>
      <p className='text-white'>You do not have permission to access this page.</p>
      <p className='text-white'>Please login or contact an administrator for assistance.</p>
    </div>
  );
};

export default UnauthorizedPage;
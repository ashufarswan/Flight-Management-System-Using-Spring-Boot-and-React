import React from 'react';
import { createContext, useState,useEffect } from "react";

export const AuthContext = createContext(null);

export const AuthContextProvider = (props) =>{
    const [isLoggedIn, setIsLoggedIn] = useState(() => {
        const storedIsLoggedIn = sessionStorage.getItem("isLoggedIn");
        return storedIsLoggedIn ? JSON.parse(storedIsLoggedIn) : false;
      });
    
      const [role, setRole] = useState(() => {
        const storedRole = localStorage.getItem("role");
        return storedRole ? storedRole : "";
      });
    
      // Update localStorage when state changes
      useEffect(() => {
        localStorage.setItem("isLoggedIn", JSON.stringify(isLoggedIn));
        localStorage.setItem("role", role);
      }, [isLoggedIn, role]);
    return(
    <AuthContext.Provider value={{isLoggedIn, setIsLoggedIn,role, setRole}}>
        {props.children}
    </AuthContext.Provider>
    )
}
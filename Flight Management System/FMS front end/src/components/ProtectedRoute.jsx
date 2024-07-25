import { useContext } from "react"
import { AuthContext } from "../Context/AuthContext"
import { Navigate } from "react-router-dom";

const ProtectedRoute =({allowedRole,children})=>{
    const authContext = useContext(AuthContext)
    if(authContext.isLoggedIn && authContext.role === allowedRole){
        return (
            children
        );
    }
    return <Navigate to = "/unauthorized"/>
};

export default ProtectedRoute;
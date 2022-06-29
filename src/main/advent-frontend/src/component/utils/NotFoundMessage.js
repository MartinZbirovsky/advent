import { Link } from "react-router-dom"

const NotFountMessage = () => {
    return ( 
        <div className="not-found">
        <h2>SORRY</h2>
        <p>page not found</p>
        <Link to="/">Back...</Link>
      </div>
     );
}
 
export default NotFountMessage;
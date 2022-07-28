import { Link } from 'react-router-dom'
export const ObjectToTable = ({ object, routValue }) => {

    let objectAttributes = []
    
    for (const prop in object) {
        if (object[prop] !== null && prop !== "id") {
                objectAttributes.push(<tr key={prop} name={prop}>{String(object[prop])}</tr>)    
        }
    }
    return (
        <Link id={object.id} key={object.id} to={`/${routValue}/${object.id}`}>
            <li className="listItem">{objectAttributes}</li>
        </Link>
    )
}

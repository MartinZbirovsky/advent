import { Link } from 'react-router-dom'
export const ObjectToTable = ({ object, routValue }) => {

    let objectAttributes = []
    console.log(object)
    for (const prop in object) {
        if (object[prop] && prop !== "id") {
            objectAttributes.push(<td key={prop} name={prop}>{object[prop]}</td>)
        }
    }
    return (
        <Link id={object.id} key={object.id} to={`/${routValue}/${object.id}`}>
            <li className="listItem">{objectAttributes}</li>
        </Link>
    )
}
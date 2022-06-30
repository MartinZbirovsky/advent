import React from 'react'
import { Link } from 'react-router-dom';
export const ListObjectToTable = ({ data }) => {
    return (
        <>
            <ul className='adsList'>
                {data.content.map((obj) => 
                <li  key={obj.id} id={obj.id}>
                    <Link to={`/user/${obj.id}`}>
                        <p>{obj.email}</p>
                        <p>{String(obj.enabled)}</p>
                        <p>{String(obj.accountNonLocked)}</p>
                    </Link>
                </li>)}
            </ul>
            <div className="pagination">
                <p>Total pages: {data.totalPages}</p>
            </div>
        </>
    )
}

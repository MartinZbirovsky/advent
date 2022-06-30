import React from 'react'
import { Link } from 'react-router-dom'
export const HomePageCat = ({ data }) => {
    return (
        <>
            <div>Chose Category</div>
            <ul>{data.content.map(category =>
                <Link to={`/ads/${category.id}`}>
                    <li>{category.id} {category.name}</li>
                </Link>)}</ul>
        </>
    )
}
//    <div>{JSON.stringify(data.content)}</div>
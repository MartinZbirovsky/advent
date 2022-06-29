import React from 'react'
import { HomePageCatItem } from './HomePageCatListItem'

export const HomePageCat = ({ data }) => {
    return (
        <>
            <div>Chose Category</div>
            <ul>{data.content.map(category => <HomePageCatItem key = {category.id} category={category}/>)}</ul>
        </>
    )
}
//    <div>{JSON.stringify(data.content)}</div>
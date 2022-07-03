import React from 'react'
import { ObjectToTable } from '../utils/ObjectToTable.js';

export const HomeCatList = ({ data }) => {
    return (
        <div className='categoryList'>
            <ul className='itemList'>
                {data.content.map(category =>      
                    <ObjectToTable object={category} routValue={"ads"}/>
               )}
            </ul>
        </div>
    )
}
//    <div>{JSON.stringify(data.content)}</div>
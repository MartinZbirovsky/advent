import React from 'react'
import { AdsPageListItem } from './AdsPageListItem'

export const AdsPageList = ({ data }) => {
    return (
        <>
            <ul className='adsList'>
                {data.content.map(ads => <AdsPageListItem ads={ads} />)}
            </ul>
            <div class="pagination">
                <p>Total pages: {data.totalPages}</p>
            </div>
        </>
    )
}

import React from 'react'
import { AdsPageListItem } from './AdsPageListItem'

export const AdsPageList = ({ data }) => {
    return (
        <>
            <ul className='adspagelist'>{data.content.map(ads => <AdsPageListItem key = {ads.id} ads={ads}/>)}</ul>
        </>
    )
}

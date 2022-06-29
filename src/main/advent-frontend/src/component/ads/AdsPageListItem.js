import React from 'react'
import { Link } from 'react-router-dom';
import { ObjectToTable } from '../utils/ObjectToTable';

export const AdsPageListItem = ({ ads }) => {
  

  return (
      <li id={`ad-${ads.id}`}>
        <Link to={`/ad/${ads.id}`}>
                <ObjectToTable key={ads.id}  object={ads}/>
        </Link>
      </li>

  )
}

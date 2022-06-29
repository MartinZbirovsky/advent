import React from 'react'
import { Link } from 'react-router-dom'

export const HomePageCatItem = ({ category }) => {
  return (
    <Link to={`/ads/${category.id}`}>
      <li>{category.id} {category.name}</li>
    </Link>
  )
}

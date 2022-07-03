import React from 'react'

export const Pagination = ({ pages, elements }) => {
    return (
        <div className='pagination'>
            <p>Pages:{pages} with {elements} elements.</p>
        </div>
    )
}

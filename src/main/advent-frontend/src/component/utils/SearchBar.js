import React from 'react'

export const SearchBar = ({ searchValue, valueState, handleSubmitMethod }) => {
    const handleEnterSubmit = e => {
        if (e.key === "Enter") {
            handleSubmitMethod(e);
        }
      }

    return (
        <div className='searchBar'>
            <label>Search</label>
            <input onKeyPress={handleEnterSubmit} type="text" value={searchValue} onChange={(e) => valueState(e.target.value)} />
            <button onClick={handleSubmitMethod}>Create</button>
        </div>
    )
}

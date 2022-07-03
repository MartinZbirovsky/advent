import React from 'react'

export const Input = ({ required, idValue, impName, inpType, inpValue, inpState }) => {
    return <div className='input'>
        <label>
            {impName}
        </label>
        <input
            required={required}
            id={idValue}
            type={inpType}
            value={inpValue}
            onChange={e => inpState(e.target.value)}
        />
    </div>
}

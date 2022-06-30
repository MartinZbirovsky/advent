import React from 'react'

export const Input = ({ required, idValue, impName, inpType, inpValue, inpState }) => {
    return (
        <label>
            {impName}
            <input 
                required = {required}
                id={idValue}
                type={inpType}
                value={inpValue}
                onChange={e => inpState(e.target.value)}
            />
        </label>
    )
}

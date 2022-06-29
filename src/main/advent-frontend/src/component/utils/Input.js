import React from 'react'

export const Input = ({ idValue, impName, inpType, inpValue, inpState }) => {
    return (
        <label>
            {impName}
            <input
                id={idValue}
                type={inpType}
                value={inpValue}
                onChange={e => inpState(e.target.value)}
            />
        </label>
    )
}

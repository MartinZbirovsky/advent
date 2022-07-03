import React from 'react'

export const InputSpread = ({ object, setObjState, propertyName, inputType = "text" }) => {
    const handleInputChange = (e) => {
        setObjState({
            ...object,
            [e.target.id]: e.target.value,
        });
    };
    return (
        <input id={propertyName} value={object[propertyName]} onChange={handleInputChange} type={inputType} />
    )
}

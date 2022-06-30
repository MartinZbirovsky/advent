export const ObjectToTable = ({ object }) => {
    let objectAttributes = []
    for (const prop in object) {
        if (object[prop] && prop !== "id") {
            objectAttributes.push(<td key= {prop} name={prop}>{object[prop]}</td>)
        }
    }
    return (
        <table>
            <tbody>
                <tr>
                    {objectAttributes}
                </tr>
            </tbody>
        </table>
    )
}
export const ObjectToTable = ({ key, object }) => {
    let objectAttributes = []
    for (const prop in object) {
        if (object[prop] && prop !== "id") {
            objectAttributes.push(<td name={prop}>{object[prop]}</td>)
        }
    }
    return (
        <table>
            <tbody>
                <tr key={key}>
                    {objectAttributes}
                </tr>
            </tbody>
        </table>
    )
}
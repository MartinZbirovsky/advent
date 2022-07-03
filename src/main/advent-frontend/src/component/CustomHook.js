export async function getData(postUrl, reqMethod = 'GET') {
    console.log(reqMethod)
    const response = await fetch(postUrl, {
        method: reqMethod,
        headers: { 'Content-Type': 'application/json' }
    })
    return await response.json();
}

export async function postData(postUrl, body) {
    const response = await fetch(postUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
    })
    return await response.json();
}
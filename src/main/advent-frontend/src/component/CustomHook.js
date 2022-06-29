import { useState, useEffect } from "react";

export const useFetch = (url) => {
    const [data, setData] = useState(null)
    const [isPending, setIsPending] = useState(true)
    const [error, setError] = useState(null)

    useEffect(() => {
        const abortCont = new AbortController();
        fetch(url, { signal: abortCont.signal })
            .then(res => {
                if (!res.ok) {
                    return res.json().then((text) => {
                        setIsPending(false)
                        setError(text)
                        throw Error(text.message)
                    });
                }
                return res.json()
            })
            .then(data => {
                setData(data)
                setIsPending(false)
                setError(null)
            })
            .catch(err => {
                setIsPending(false)
                setError(err.message)
            })
        return () => abortCont.abort();
    }, [url]);
    return { data, isPending, error }
}

/*
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            return response.text().then((text) => { 
                        setIsPending(false)
                        setError(text)
                throw Error(text) 
            });
        })
        .then((data) => {
                        setData(data)
                        setIsPending(false)
                        setError(null)
        }).catch((error) => {
        
            console.log(error);
        });


.then((response) => {
    if (response.ok) {
        return response.json();
    }
    return response.text().then((text) => { 
                setIsPending(false)
                setError(err.message)
        throw Error(text) 
    });
})
.then((jsonResponse) => {
                setData(data)
                setIsPending(false)
                setError(null)
}).catch((error) => {

    console.log(error);
});*/
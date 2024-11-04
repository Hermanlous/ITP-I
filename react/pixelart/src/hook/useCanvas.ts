import { useEffect, useState } from 'react';

const APIURL = 'http://localhost:8080/canvas';

const useCanvas = () => {
    const [data, setData] = useState<string[][] | null>( null)

    useEffect(() => {
        fetch(APIURL)
            .then((res)=>res.json())
            .then((json)=>setData(json))
            .catch((error)=>console.log(error))
    }, []);
    console.log(data)
    return data

}


export default useCanvas;
import { useEffect, useState } from 'react';

//chatGPT prompt from 3 to 6 & 11 & 16, "CanvasResponse"
interface CanvasResponse {
    canvas: string;
}

const APIURL = 'http://localhost:8080/canvas';

const useCanvas = () => {
    const [data, setData] = useState<CanvasResponse | undefined>(undefined)

    useEffect(() => {
        fetch(APIURL)
            .then((res)=>res.json())
            .then((json: CanvasResponse)=>setData(json))
            .catch((error)=>console.log(error))
    }, []);
    console.log(data)
    return data

}


export default useCanvas;
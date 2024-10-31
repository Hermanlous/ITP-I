import React, {useEffect, useState} from 'react';
import Canvas from './components/Canvas';
import useCanvas from "./hook/useCanvas.ts";


const App: React.FC = () => {
  const [selectedColor, setSelectedColor] = useState<string>('#000000'); // default color is black
  const  canvasData  = useCanvas();


  const [pixelData, setPixelData] = useState<string[][]>([]);

  /* Chatgpt promt from 14 to line 32 */
  useEffect(() => {
    if (canvasData) {
      try {
        const canvasString = canvasData.canvas;
        //console.log( canvasString);

        const parsed = JSON.parse(canvasString);
        console.log( parsed);
        if (Array.isArray(parsed.canvas)) {
          const translatedFromApiData = parsed.canvas.map((row: string[]) =>
              row.map((col: string) => col === 'W' ? '#ffffff' : col === 'B' ? '#000000' : col) //Subject to change, may not need to convert, still need to iterate.
          );
          //console.log(translatedFromApiData)
          setPixelData(translatedFromApiData);
        }
      } catch (error) {
        console.error('Error parsing canvas:', error);
      }
    }
  }, [canvasData]);


  // Function to update a specific pixel's color
  const handlePixelChange = (x: number, y: number, color: string) => {
    //const newPixelData = [...pixelData];
    //newPixelData[y][x] = color;
    //setPixelData(newPixelData);
    if (canvasData) {
      //console.log(canvasData.canvas, "want to return")
      const canvasString = canvasData.canvas; //first parse
      //console.log(canvasString, "middle")
      const parsed = JSON.parse(canvasString);
      //console.log(parsed);
      parsed.canvas[y][x] = 'B'
      //console.log(parsed.canvas)
      const newPixelData = [...pixelData];
      newPixelData[y][x] = color
      //console.log(newPixelData)
      setPixelData(newPixelData)

      canvasData.canvas = JSON.stringify(parsed);
      //console.log(canvasData)

      fetch('http://localhost:8080/canvas',{
        method: "PUT",
        body: canvasData.canvas,
        headers:{
          "Content-type":"application/json; charset=utf-8",
          'Accept': 'application/json'
        },

      }).then(response => response.json())
    }
    //console.log(pixelData)






  };
  // TODO - handlePixelChange, function above may be redundant. We may change 'W' to hex code for color

  // List of colors to choose from + contrast color for text
  const colors = [
    { color: '#000000', onColor: '#ffffff' }, // black bg, white text
    { color: '#ff0000', onColor: '#ffffff' }, // red bg, white text
    { color: '#00ff00', onColor: '#000000' }, // green bg, black text
    { color: '#0000ff', onColor: '#ffffff' }, // blue bg, white text
    { color: '#ffff00', onColor: '#000000' }, // yellow bg, black text
    { color: '#ff00ff', onColor: '#ffffff' }, // magenta bg, white text
    { color: '#00ffff', onColor: '#000000' }, // cyan bg, black text
    { color: '#ffffff', onColor: '#000000' }, // white bg, black text
  ];

  return (
    <main className="w-full max-w-5xl mx-auto p-4">
      <h1 className="text-red-500 text-center p-4">Pixel Art</h1>

      <div className='flex justify-center'>
        {/* Color Palette */}
        <div className="flex flex-col gap-2 m-4 pt-4">
          {colors.map((color) => (
            <div
              key={color.color}
              className={`w-10 h-10 border-2 cursor-pointer ${selectedColor === color.color ? 'border-black' : 'border-transparent'}`}
              style={{ backgroundColor: color.color }}
              onClick={() => setSelectedColor(color.color)}
            >
              {selectedColor === color.color && (
                <div style={{ color: color.onColor }} className={`text-center text-2xl p-0.5`}>✓</div>
              )}
            </div>
          ))}
        </div>

        {/* Canvas */}
        <div>
          <p className='text-xl'>Selected Color: <span style={{ color: selectedColor }}>{selectedColor}</span></p>
          <Canvas

            // TODO: set width, height and pixel size based on pixeldata fetched from api
            width={600}
            height={600}
            pixelSize={12}
            initialData={pixelData}
            selectedColor={selectedColor}
            onPixelChange={handlePixelChange}
          />
        </div>
      </div>
    </main>
  );
};

export default App;

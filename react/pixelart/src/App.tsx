import React, { useEffect, useState } from 'react';
import Canvas from './components/Canvas';
import useCanvas from './hook/useCanvas.ts';
import CheckIcon from './components/CheckIcon.tsx';

type Color = {
  color: string;
  onColor: string;
};

const App: React.FC = () => {
  // List of colors to choose from + contrast color for text
  const colors = [
    { color: '#000000', onColor: '#ffffff' },
    { color: '#ff0000', onColor: '#ffffff' },
    { color: '#00ff00', onColor: '#000000' },
    { color: '#0000ff', onColor: '#ffffff' },
    { color: '#ffff00', onColor: '#000000' },
    { color: '#ff00ff', onColor: '#ffffff' },
    { color: '#00ffff', onColor: '#000000' },
    { color: '#ffffff', onColor: '#000000' },
  ];

  const [selectedColor, setSelectedColor] = useState<Color>(colors[0]);
  const [pixelData, setPixelData] = useState<string[][]>([]);
  const [showGrid, setShowGrid] = useState(false);
  const canvasData = useCanvas();

  useEffect(() => {
    if (canvasData) {
      try {
        if (Array.isArray(canvasData)) {
          setPixelData(canvasData);
        }
      } catch (error) {
        console.error('Error parsing canvas:', error);
      }
    }
  }, [canvasData]);

  const handlePixelChange = (x: number, y: number, color: string) => {
    if (canvasData) {
      canvasData[y][x] = color;
      const newPixelData = [...pixelData];
      newPixelData[y][x] = color;
      setPixelData(newPixelData);

      fetch('http://localhost:8080/canvas', {
        method: 'PUT',
        body: JSON.stringify(canvasData),
        headers: {
          'Content-type': 'application/json; charset=utf-8',
          'Accept': 'application/json'
        },
      })
        .then(response => response.json)
        .then((data) => { console.log(data) })
        .catch(error => console.error(error))
    }
  };

  return (
    <main className='w-full max-w-5xl mx-auto p-4'>
      <h1 className='text-red-500 text-center p-4'>Pixel Art</h1>

      <div className='p-4'>
        <div className='flex justify-center items-center gap-4 mb-4'>
          <p className='text-xl text-center'>
            Selected Color: 
            <span className='p-2 ml-2' style={{ color: selectedColor.onColor, backgroundColor: selectedColor.color }}>
              {selectedColor.color}
            </span>
          </p>
          
          <label className='flex items-center gap-2 cursor-pointer'>
            <input
              type='checkbox'
              checked={showGrid}
              onChange={(e) => setShowGrid(e.target.checked)}
              className='w-6 h-6 cursor-pointer'
            />
            Show Grid
          </label>
        </div>

        <div className='flex justify-center'>
          {/* Color Palette */}
          <div className='flex flex-col gap-2 mx-4'>
            {colors.map((color) => (
              <div
                key={color.color}
                className='w-10 h-10 border-2 cursor-pointer'
                style={{
                  backgroundColor: color.color,
                  borderColor: selectedColor.color === color.color ? selectedColor.onColor : 'transparent',
                }}
                onClick={() => setSelectedColor(color)}
              >
                {selectedColor.color === color.color && (
                  <CheckIcon color={color.onColor} />
                )}
              </div>
            ))}
          </div>

          {/* Canvas */}
          <Canvas
            initialData={pixelData}
            selectedColor={selectedColor.color}
            onPixelChange={handlePixelChange}
            maxWidth={1000}
            maxHeight={800}
            showGrid={showGrid}
            gridGap={1}
          />
        </div>
      </div>
    </main>
  );
};

export default App;

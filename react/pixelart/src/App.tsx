import React, { useState } from 'react';
import Canvas from './components/Canvas';


const App: React.FC = () => {

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

  const [selectedColor, setSelectedColor] = useState<typeof colors[0]>(colors[0]); // default to the first color object

  // TODO: get pixelData from api
  const [pixelData, setPixelData] = useState<string[][]>(Array.from({ length: 50 }, () => Array(50).fill('#ffffff')));

  // Function to update a specific pixel's color
  const handlePixelChange = (x: number, y: number, color: string) => {
    const newPixelData = [...pixelData];
    newPixelData[y][x] = color;
    setPixelData(newPixelData);
  };

  return (
    <main className="w-full max-w-5xl mx-auto p-4">
      <h1 className="text-red-500 text-center p-4">Pixel Art</h1>

      {/* TODO: create color selection panel component */}
      <div className='flex justify-center'>
        <div className="flex flex-col gap-2 m-4 pt-4">
          {colors.map((color) => (
            <div
              key={color.color}
              className={`w-10 h-10 border-2 cursor-pointer`}
              style={{
                backgroundColor: color.color,
                borderColor: selectedColor.color === color.color ? color.onColor : 'transparent'
              }}
              onClick={() => setSelectedColor(color)}
            >
              {selectedColor.color === color.color && (
                <div
                  style={{ color: color.onColor }}
                  className={`text-center text-2xl p-0.5`}>
                    ✓
                </div>
              )}
            </div>
          ))}
        </div>

        <div>
          <p className='text-2xl'>
            Selected Color:{' '}
            <span style={{ color: selectedColor.onColor, backgroundColor: selectedColor.color }}>
              {selectedColor.color}
            </span>
          </p>

          <Canvas
            // TODO: set width, height and pixel size based on pixeldata fetched from api
            width={600}
            height={600}
            pixelSize={12}
            initialData={pixelData}
            selectedColor={selectedColor.color}
            onPixelChange={handlePixelChange}
          />
        </div>
      </div>
    </main>
  );
};

export default App;

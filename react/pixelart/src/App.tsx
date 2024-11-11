import { useState } from 'react';

import Canvas from './components/Canvas';
import ColorPanel from './components/ColorPanel';

import useCanvasData from './hooks/useCanvasData';
import { Color } from './types/color.types';

const App: React.FC = () => {
  const { pixelData, setPixelData, saveCanvasData, loading, error } = useCanvasData();

  const colors: Color[] = [
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
  const [showGrid, setShowGrid] = useState(false);

  const handlePixelChange = (x: number, y: number, color: string) => {
    const newPixelData = [...pixelData];
    newPixelData[y][x] = color;
    setPixelData(newPixelData);
    saveCanvasData(newPixelData);
    setPixelData(newPixelData);
    saveCanvasData(newPixelData);
  };

  if (loading) {
    return <div className='flex justify-center pt-10'>Loading...</div>;
  }

  if (error) {
    return <div className='flex justify-center pt-10'>Error: {error}</div>;
  }

  return (
    <main className="w-full max-w-5xl mx-auto">
      <h1 className="text-red-500 text-center p-2">Pixel Art</h1>
      <div className="p-4">
        <div className="flex justify-center items-center gap-20 pb-2">
          <p className="text-xl text-center">
            Selected Color:
            <span
              className="p-1 ml-2"
              style={{
                color: selectedColor.onColor,
                backgroundColor: selectedColor.color,
              }}
            >
              {selectedColor.color}
            </span>
          </p>
          <label className="flex items-center gap-2 cursor-pointer">
            <input
              type="checkbox"
              checked={showGrid}
              onChange={(e) => setShowGrid(e.target.checked)}
              className="w-6 h-6 cursor-pointer"
            />
            Show Grid
          </label>
        </div>

        <div className="flex justify-center">
          <ColorPanel
            colors={colors}
            selectedColor={selectedColor}
            onColorSelect={setSelectedColor}
          />
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

import React, { useState } from 'react';

import Canvas from './components/Canvas';
import ColorPanel from './components/ColorPanel';
import ConfirmationDialog from './components/ConfirmationDialog';
import CheckIcon from './components/CheckIcon';

import useCanvasData from './hooks/useCanvasData';
import useCanvasDataUpdater from './hooks/useCanvasDataUpdater';

import { Color } from './types/color.types';


const App: React.FC = () => {

  /**
   * useCanvasData is fetching from restAPI.
   * useCanvasUpdater sends a PUT request for each updated pixel.
   * useState for showing either confirm of cancel to clear canvas.
   **/
  const { pixelData, setPixelData, loading, error } = useCanvasData();
  const { debouncedSaveCanvasData } = useCanvasDataUpdater();
  const [showClearConfirmation, setShowClearConfirmation] = useState(false);

  /**
   * Colors to pick and choose from
   **/
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

  /**
   * useState for selected color. Standard is black.
   * useState to show or not to show grid.
   **/
  const [selectedColor, setSelectedColor] = useState<Color>(colors[0]);
  const [showGrid, setShowGrid] = useState(false);

  /**
   * Sets new pixeldata, which then updates the rest API which use useCanvasUpdater
   * @param x position of x coordinate of pixel
   * @param y position of y coordinate of pixel
   * @param color the color string to be updated
   **/
  const handlePixelChange = (x: number, y: number, color: string) => {
    const newPixelData = [...pixelData];
    newPixelData[y][x] = color;
    setPixelData(newPixelData);
    debouncedSaveCanvasData(newPixelData);
  };

  /**
   * Sets clearing information true, can then cancel or confirm
   **/
  const handleClearCanvas = () => {
    setShowClearConfirmation(true);
  };

  /**
   * Confirms handleClearCanvas function
   **/
  const handleClearConfirmation = () => {
    const newPixelData = pixelData.map((row) => row.map(() => '#ffffff'));
    setPixelData(newPixelData);
    debouncedSaveCanvasData(newPixelData);
    setShowClearConfirmation(false);
  };

  /**
   * Cancels handleClearCanvas function
   **/
  const handleClearCancel = () => {
    setShowClearConfirmation(false);
  };

  
  if (loading) {
    return <div className='flex justify-center pt-10'>Loading...</div>;
  }

  if (error) {
    return <div className='flex justify-center pt-10'>Error: {error}</div>;
  }

  return (
    <main className="mx-auto w-[1014px]">
      <h1 className="text-red-500 text-center p-2">Pixel Art</h1>
      <div className="flex justify-between align-middle py-2 pl-14">
        <p className="text-xl leading-[40px] flex">
          Color:
          <span
            className="ml-2 px-2 h-full block"
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
              className="hidden peer"
            />
            <div className="h-10 w-10 bg-gray-400 hover:bg-gray-500 border-2 border-black peer-checked:bg-blue-600 peer-checked:border-white flex items-center justify-center">
              {showGrid && <CheckIcon color="#FFFFFF" />}
            </div>
            Show Grid
          </label>
          <button
            className="bg-gray-400 hover:bg-gray-500 text-black font-bold p-2"
            onClick={handleClearCanvas}
            data-testid='clear-canvas-button'
          >
            Clear Canvas
          </button>
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

        <ConfirmationDialog
          open={showClearConfirmation}
          onConfirm={handleClearConfirmation}
          onCancel={handleClearCancel}
          title="Clear Canvas"
          message="Are you sure you want to clear the entire canvas?"
          confirmBtn="Yes, delete it"
          cancelBtn="Cancel"
        />
    </main>
  );
};

export default App;
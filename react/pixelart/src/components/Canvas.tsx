import React, { useEffect, useRef, useState } from 'react';

interface CanvasProps {
  width: number;
  height: number;
  pixelSize: number;
  initialData: string[][];
  selectedColor: string;
  onPixelChange: (x: number, y: number, color: string) => void;
}



const Canvas: React.FC<CanvasProps> = ({ width, height, pixelSize, initialData, selectedColor, onPixelChange }) => {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [hoveredPixel, setHoveredPixel] = useState<{ x: number; y: number } | null>(null);

  // draw the entire canvas based on pixel data
  const drawCanvas = (ctx: CanvasRenderingContext2D, pixelData: string[][]) => {
    for (let y = 0; y < pixelData.length; y++) {
      for (let x = 0; x < pixelData[y].length; x++) {
        ctx.fillStyle = pixelData[y][x];
        ctx.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
      }
    }
  };

  // draw a border around the hovered pixel
  const drawHoveredPixelBorder = (ctx: CanvasRenderingContext2D, pixelX: number, pixelY: number) => {
    ctx.strokeStyle = '#333333';
    ctx.lineWidth = 1;
    ctx.strokeRect(
      pixelX * pixelSize + 1, // X-coordinate
      pixelY * pixelSize + 1, // Y-coordinate
      pixelSize - 2,          // Width of the inner rectangle
      pixelSize - 2           // Height of the inner rectangle
    );
  };

  // track mouse position
  const handleMouseMove = (e: React.MouseEvent<HTMLCanvasElement, MouseEvent>) => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const rect = canvas.getBoundingClientRect();
    const x = Math.floor((e.clientX - rect.left) / pixelSize);
    const y = Math.floor((e.clientY - rect.top) / pixelSize);

    // check if the hovered pixel is within bounds
    if (x >= 0 && y >= 0 && y < initialData.length && x < initialData[0].length) {
      setHoveredPixel({ x, y });
    } else {
      setHoveredPixel(null);
    }
  };

  // handle pixel click to change color
  const handleClick = () => {
    if (hoveredPixel) {
      onPixelChange(hoveredPixel.x, hoveredPixel.y, selectedColor);
    }
  };

  // reset hover when mouse leaves the canvas
  const handleMouseLeave = () => {
    setHoveredPixel(null);
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // initial drawing of the canvas
    drawCanvas(ctx, initialData);

    // if a pixel is hovered, draw a border around it
    if (hoveredPixel) {
      drawHoveredPixelBorder(ctx, hoveredPixel.x, hoveredPixel.y);
    }
  }, [hoveredPixel, initialData]);



  return (
    <canvas
      ref={canvasRef}
      width={width}
      height={height}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
      onClick={handleClick}
      className='cursor-crosshair border-2 border-black mx-auto'
    />
  );
};

export default Canvas;

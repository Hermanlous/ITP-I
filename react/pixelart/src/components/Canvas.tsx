import { useEffect, useRef, useState } from 'react';
import { CanvasProps } from '../types/canvas.types';

const Canvas: React.FC<CanvasProps> = ({
  initialData,
  selectedColor,
  onPixelChange,
  maxWidth = 1000,
  maxHeight = 800,
  showGrid = false,
  gridGap = 1
}) => {

  const calculateCanvasDimensions = (pixelData: string[][]) => {
    if (!pixelData || pixelData.length === 0) {
      return { width: 0, height: 0, pixelSize: 0 };
    }

    const numRows = pixelData.length;
    const numCols = pixelData[0].length;

    const availableWidth = maxWidth - (showGrid ? (numCols - 1) * gridGap : 0);
    const availableHeight = maxHeight - (showGrid ? (numRows - 1) * gridGap : 0);

    const pixelSizeFromWidth = Math.floor(availableWidth / numCols);
    const pixelSizeFromHeight = Math.floor(availableHeight / numRows);

    const pixelSize = Math.max(1, Math.min(pixelSizeFromWidth, pixelSizeFromHeight));

    const width = pixelSize * numCols + (showGrid ? (numCols - 1) * gridGap : 0);
    const height = pixelSize * numRows + (showGrid ? (numRows - 1) * gridGap : 0);

    return { width, height, pixelSize };
  };


  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [hoveredPixel, setHoveredPixel] = useState<{ x: number; y: number } | null>(null);
  const [isDrawing, setIsDrawing] = useState(false);
  const lastPixelRef = useRef<{ x: number; y: number } | null>(null);
  const { width, height, pixelSize } = calculateCanvasDimensions(initialData);

  const getPixelPosition = (x: number, y: number) => {
    const xPos = x * (pixelSize + (showGrid ? gridGap : 0));
    const yPos = y * (pixelSize + (showGrid ? gridGap : 0));
    return { x: xPos, y: yPos };
  };

  const drawCanvas = (ctx: CanvasRenderingContext2D, pixelData: string[][]) => {
    for (let y = 0; y < pixelData.length; y++) {
      for (let x = 0; x < pixelData[y].length; x++) {
        const { x: xPos, y: yPos } = getPixelPosition(x, y);
        ctx.fillStyle = pixelData[y][x];
        ctx.fillRect(xPos, yPos, pixelSize, pixelSize);
      }
    }
  };

  const drawHoveredPixelBorder = (ctx: CanvasRenderingContext2D, pixelX: number, pixelY: number) => {
    const { x: xPos, y: yPos } = getPixelPosition(pixelX, pixelY);
    ctx.strokeStyle = '#202020';
    ctx.lineWidth = 1;
    ctx.strokeRect(
      xPos + 1,
      yPos + 1,
      pixelSize - 2,
      pixelSize - 2
    );
  };

  const getPixelFromMouseEvent = (e: React.MouseEvent<HTMLCanvasElement, MouseEvent>) => {
    const canvas = canvasRef.current;
    if (!canvas) return null;

    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    const x = Math.floor(mouseX / (pixelSize + (showGrid ? gridGap : 0)));
    const y = Math.floor(mouseY / (pixelSize + (showGrid ? gridGap : 0)));

    if (x >= 0 && y >= 0 && y < initialData.length && x < initialData[0].length) {
      return { x, y };
    }
    return null;
  };

  const paintPixel = (pixel: { x: number; y: number }) => {
    if (!lastPixelRef.current ||
        lastPixelRef.current.x !== pixel.x ||
        lastPixelRef.current.y !== pixel.y) {
      onPixelChange(pixel.x, pixel.y, selectedColor);
      lastPixelRef.current = pixel;
    }
  };

  const handleMouseMove = (e: React.MouseEvent<HTMLCanvasElement, MouseEvent>) => {
    const pixel = getPixelFromMouseEvent(e);
    setHoveredPixel(pixel);

    if (isDrawing && pixel) {
      paintPixel(pixel);
    }
  };

  const handleMouseDown = (e: React.MouseEvent<HTMLCanvasElement, MouseEvent>) => {
    if (e.button !== 0) return; // Only draw if left click
    setIsDrawing(true);
    const pixel = getPixelFromMouseEvent(e);
    if (pixel) {
      paintPixel(pixel);
    }
  };

  const handleMouseUp = () => {
    setIsDrawing(false);
    lastPixelRef.current = null;
  };

  const handleMouseLeave = () => {
    setHoveredPixel(null);
    setIsDrawing(false);
    lastPixelRef.current = null;
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    drawCanvas(ctx, initialData);

    // if a pixel is hovered, draw a border around it to highlight the pixel
    if (hoveredPixel) {
      drawHoveredPixelBorder(ctx, hoveredPixel.x, hoveredPixel.y);
    }
  }, [hoveredPixel, initialData, showGrid, gridGap]);

  return (
    <canvas
      ref={canvasRef}
      width={width}
      height={height}
      onMouseMove={handleMouseMove}
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
      onMouseLeave={handleMouseLeave}
      className='cursor-crosshair'
      data-testid="pixel-canvas"
    />
  );
};

export default Canvas;

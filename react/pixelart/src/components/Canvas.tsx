import { useEffect, useRef, useState } from 'react';
import { CanvasProps } from '../types/canvas.types';

const Canvas: React.FC<CanvasProps> = ({
  initialData,
  selectedColor,
  onPixelChange,
  maxWidth = 1000,
  maxHeight = 800,
  showGrid = false,
  gridGap = 1,
}) => {
  /**
   * Calculates Canvas dimensions and pixelSize based on maxWidth, maxHeight and gridGap.
   * @param pixelData renders the canvas.
   * @returns {width, height, pixelSize}, calculated dimensions and pixel size.
   **/
  const calculateCanvasDimensions = (pixelData: string[][]) => {
    if (!pixelData || pixelData.length === 0) {
      return { width: 0, height: 0, pixelSize: 0 };
    }

    const numRows = pixelData.length;
    const numCols = pixelData[0].length;

    const availableWidth = maxWidth - (showGrid ? (numCols - 1) * gridGap : 0);
    const availableHeight =
      maxHeight - (showGrid ? (numRows - 1) * gridGap : 0);

    const pixelSizeFromWidth = Math.floor(availableWidth / numCols);
    const pixelSizeFromHeight = Math.floor(availableHeight / numRows);

    const pixelSize = Math.max(
      1,
      Math.min(pixelSizeFromWidth, pixelSizeFromHeight)
    );

    const width =
      pixelSize * numCols + (showGrid ? (numCols - 1) * gridGap : 0);
    const height =
      pixelSize * numRows + (showGrid ? (numRows - 1) * gridGap : 0);

    return { width, height, pixelSize };
  };

  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [hoveredPixel, setHoveredPixel] = useState<{
    x: number;
    y: number;
  } | null>(null);
  const [isDrawing, setIsDrawing] = useState(false);
  const lastPixelRef = useRef<{ x: number; y: number } | null>(null);
  const { width, height, pixelSize } = calculateCanvasDimensions(initialData);

  /**
   * Calculates the pixel position based on x and y, so that it can be applied to Canvas.
   * @param x is the x dimension for the given position.
   * @param y is the y dimension for the given position.
   * @returns calculated positions for x and y.
   **/
  const getPixelPosition = (x: number, y: number) => {
    const xPos = x * (pixelSize + (showGrid ? gridGap : 0));
    const yPos = y * (pixelSize + (showGrid ? gridGap : 0));
    return { x: xPos, y: yPos };
  };

  /**
   * Draws the canvas based on rendered pixelData
   * @param ctx is CanvasRenderingContext2D, which is the 2d context of the canvas.
   * @param pixelData is the rendered canvas data.
   **/
  const drawCanvas = (ctx: CanvasRenderingContext2D, pixelData: string[][]) => {
    for (let y = 0; y < pixelData.length; y++) {
      for (let x = 0; x < pixelData[y].length; x++) {
        const { x: xPos, y: yPos } = getPixelPosition(x, y);
        ctx.fillStyle = pixelData[y][x];
        ctx.fillRect(xPos, yPos, pixelSize, pixelSize);
      }
    }
  };
  /**
   * Draws an outline of the hovered position of the pixel
   * @param ctx is CanvasRenderingContext2D, which is the 2d context of the canvas.
   * @param pixelX is the calculated positions of x so that it can be applied to the canvas.
   * @param pixelY is the calculated positions of x so that it can be applied to the canvas.
   **/
  const drawHoveredPixelBorder = (
    ctx: CanvasRenderingContext2D,
    pixelX: number,
    pixelY: number
  ) => {
    const { x: xPos, y: yPos } = getPixelPosition(pixelX, pixelY);
    ctx.strokeStyle = '#202020';
    ctx.lineWidth = 1;
    ctx.strokeRect(xPos + 1, yPos + 1, pixelSize - 2, pixelSize - 2);
  };

  /**
   * Gets current pixel from mouse event.
   * @param e which is a mouse event this recalculates the position of x and y on the canvas back to usable coordinates.
   * @return x and y coordinates, otherwise null.
   **/
  const getPixelFromMouseEvent = (
    e: React.MouseEvent<HTMLCanvasElement, MouseEvent>
  ) => {
    const canvas = canvasRef.current;
    if (!canvas) return null;

    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    const x = Math.floor(mouseX / (pixelSize + (showGrid ? gridGap : 0)));
    const y = Math.floor(mouseY / (pixelSize + (showGrid ? gridGap : 0)));

    if (
      x >= 0 &&
      y >= 0 &&
      y < initialData.length &&
      x < initialData[0].length
    ) {
      return { x, y };
    }
    return null;
  };
  /** Paints selected pixel, to selected color.
   * @param pixel pixel at given coordinates
   **/
  const paintPixel = (pixel: { x: number; y: number }) => {
    if (
      !lastPixelRef.current ||
      lastPixelRef.current.x !== pixel.x ||
      lastPixelRef.current.y !== pixel.y
    ) {
      onPixelChange(pixel.x, pixel.y, selectedColor);
      lastPixelRef.current = pixel;
    }
  };

  /**
   * Handles mouse movement, tracks the pixel and if it's drawing or not.
   * @param e mouse event.
   **/
  const handleMouseMove = (
    e: React.MouseEvent<HTMLCanvasElement, MouseEvent>
  ) => {
    const pixel = getPixelFromMouseEvent(e);
    setHoveredPixel(pixel);

    if (isDrawing && pixel) {
      paintPixel(pixel);
    }
  };

  /**
   * Handles mouse movement down, starts drawing, when left mouse is clicked.
   * @param e mouse event.
   **/
  const handleMouseDown = (
    e: React.MouseEvent<HTMLCanvasElement, MouseEvent>
  ) => {
    if (e.button !== 0) return; // Only draw if left click
    setIsDrawing(true);
    const pixel = getPixelFromMouseEvent(e);
    if (pixel) {
      paintPixel(pixel);
    }
  };

  /**
   * Handles mouse movement, stops drawing, .
   **/
  const handleMouseUp = () => {
    setIsDrawing(false);
    lastPixelRef.current = null;
  };

  /**
   * Handles mouse movement, stops drawing and removes hovered border.
   **/
  const handleMouseLeave = () => {
    setHoveredPixel(null);
    setIsDrawing(false);
    lastPixelRef.current = null;
  };
  /**
   * Handling state and drawing the canvas
   **/
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    drawCanvas(ctx, initialData);

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
      className="cursor-crosshair"
      data-testid="pixel-canvas"
    />
  );
};

export default Canvas;

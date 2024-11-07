import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Canvas from '../../src/components/Canvas';

describe('Canvas Component', () => {
  const mockPixelData = [
    ['#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'],
  ];

  const selectedColor = '#000000'; //It Has no function

  it('should render canvas with initial pixel data', () => {
    render(
      <Canvas
        width={250}
        height={250}
        pixelSize={50}
        initialData={mockPixelData}
        selectedColor={selectedColor}
        onPixelChange={() => {}}
      />
    );

    const canvas = screen.getByTestId('pixel-canvas');
    expect(canvas).toBeInTheDocument();
  });

  it('should initialize the canvas with the correct white color for all pixels', () => {
    render(
      <Canvas
        width={250}
        height={250}
        pixelSize={12}
        initialData={mockPixelData}
        selectedColor={selectedColor}
        onPixelChange={() => {}}
      />
    );

    const canvas = screen.getByTestId('pixel-canvas') as HTMLCanvasElement;
    const ctx = canvas.getContext('2d');

    expect(ctx).toBeTruthy();
    if (ctx) {
      // Check the color of multiple pixels to make sure the canvas is correctly initialized
      const pixelsToCheck = [
        { x: 0, y: 0 },
        { x: 1, y: 0 },
        { x: 0, y: 1 },
        { x: 4, y: 4 },
      ];

      pixelsToCheck.forEach(({ x, y }) => {
        const imageData = ctx.getImageData(x * 12, y * 12, 1, 1);
        const pixelData = imageData.data;

        expect(pixelData[0]).toBe(255);
        expect(pixelData[1]).toBe(255);
        expect(pixelData[2]).toBe(255);
        expect(pixelData[3]).toBe(255);
      });
    }
  });

  it('should not call onPixelChange when clicking outside of the canvas', async () => {
    const mockOnPixelChange = vi.fn();
    render(
      <Canvas
        width={250}
        height={250}
        pixelSize={50}
        initialData={mockPixelData}
        selectedColor={selectedColor}
        onPixelChange={mockOnPixelChange}
      />
    );

    fireEvent.click(document.body);

    await waitFor(() => {
      expect(mockOnPixelChange).not.toHaveBeenCalled();
    });
  });

});

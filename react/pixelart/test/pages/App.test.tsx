import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import App from '../../src/App';
import { vi } from 'vitest';
import useCanvasData from '../../src/hooks/useCanvasData';
import useCanvasDataUpdater from '../../src/hooks/useCanvasDataUpdater';
import Canvas from '../../src/components/Canvas';

// Mock both hooks
vi.mock('../../src/hooks/useCanvasData', () => ({
  __esModule: true,
  default: vi.fn(),
}));

vi.mock('../../src/hooks/useCanvasDataUpdater', () => ({
  __esModule: true,
  default: vi.fn(),
}));

// Mock Canvas component to capture props
vi.mock('../../src/components/Canvas', () => ({
  default: vi.fn((props) => {
    // Store props for testing
    vi.mocked(Canvas).mock.calls[vi.mocked(Canvas).mock.calls.length - 1][0] = props;
    return <div data-testid="pixel-canvas" />;
  })
}));

describe('App Component', () => {
  const mockSetPixelData = vi.fn();
  const mockDebouncedSaveCanvasData = vi.fn();
  
  beforeEach(() => {
    // Reset all mocks
    vi.clearAllMocks();
    
    // Default mock implementations
    vi.mocked(useCanvasData).mockReturnValue({
      pixelData: [
        ['#ffffff', '#ffffff'],
        ['#ffffff', '#ffffff']
      ],
      setPixelData: mockSetPixelData,
      fetchCanvasData: vi.fn(),
      loading: false,
      error: null,
    });

    vi.mocked(useCanvasDataUpdater).mockReturnValue({
      debouncedSaveCanvasData: mockDebouncedSaveCanvasData,
      isSaving: false,
      error: null,
    });
  });

  it('renders the loading state when loading', () => {
    // Mock the hook to simulate loading state
    vi.mocked(useCanvasData).mockReturnValue({
      pixelData: [],
      setPixelData: vi.fn(),
      fetchCanvasData: vi.fn(),
      loading: true,
      error: null,
    });

    render(<App />);
    expect(screen.getByText(/Loading.../i)).toBeTruthy();
  });

  it('renders an error message on error', () => {
    // Mock the hook to simulate an error state
    vi.mocked(useCanvasData).mockReturnValue({
      pixelData: [],
      setPixelData: vi.fn(),
      fetchCanvasData: vi.fn(),
      loading: false,
      error: 'you have got an error buddy',
    });

    render(<App />);
    expect(screen.getByText(/Error: you have got an error buddy/i)).toBeTruthy();
  });

  it('renders the canvas when pixel data is available', () => {
    // Mock the hook to simulate available pixel data
    const mockPixelData = [
      ['#ffffff', '#000000'],
      ['#ff0000', '#00ff00'],
    ];

    vi.mocked(useCanvasData).mockReturnValue({
      pixelData: mockPixelData,
      setPixelData: vi.fn(),
      fetchCanvasData: vi.fn(),
      loading: false,
      error: null,
    });

    render(<App />);

    // Verify that the Canvas component renders
    const canvas = screen.getByTestId('pixel-canvas');
    expect(canvas).toBeTruthy();
  });

  describe('Clear Canvas Functionality', () => {
    const initialColoredPixels = [
      ['#ff0000', '#000000'],
      ['#0000ff', '#00ff00']
    ];

    it('should clear all pixels to white when clearing canvas', async () => {
      // Setup initial canvas with some colored pixels
      vi.mocked(useCanvasData).mockReturnValue({
        pixelData: initialColoredPixels,
        setPixelData: mockSetPixelData,
        fetchCanvasData: vi.fn(),
        loading: false,
        error: null,
      });
      
      render(<App />);
      
      // Click the "clear canvas" button
      const clearButton = screen.getByTestId('clear-canvas-button');
      await userEvent.click(clearButton);
      
      // Click the confirm "Yes, delete it" button
      const confirmButton = screen.getByText('Yes, delete it');
      await userEvent.click(confirmButton);
      
      // All white canvas matrix
      const expectedWhiteMatrix = [
        ['#ffffff', '#ffffff'],
        ['#ffffff', '#ffffff']
      ];
      
      // Get the first argument of the first call to setPixelData
      const newPixelData = mockSetPixelData.mock.calls[0][0];
      
      // Check if every pixel is white
      expect(newPixelData).toEqual(expectedWhiteMatrix);
    });

    it('should not change canvas when clear operation is cancelled', async () => {
      // Setup initial canvas with some colored pixels
      vi.mocked(useCanvasData).mockReturnValue({
        pixelData: initialColoredPixels,
        setPixelData: mockSetPixelData,
        fetchCanvasData: vi.fn(),
        loading: false,
        error: null,
      });
      
      render(<App />);
      
      // Click the clear button
      const clearButton = screen.getByTestId('clear-canvas-button');
      await userEvent.click(clearButton);
      
      // Click the "Cancel" button
      const cancelButton = screen.getByText('Cancel');
      await userEvent.click(cancelButton);
      
      // Verify setPixelData was never called
      expect(mockSetPixelData).not.toHaveBeenCalled();
      
      // Verify debouncedSaveCanvasData was never called
      expect(mockDebouncedSaveCanvasData).not.toHaveBeenCalled();
    });
  });

  describe('Pixel Change Functionality', () => {
    it('should update pixel data when handlePixelChange is called', () => {
      render(<App />);
      
      // Get the onPixelChange function from the Canvas props
      const { onPixelChange } = vi.mocked(Canvas).mock.calls[0][0];
      
      // Call handlePixelChange through the Canvas prop
      onPixelChange(0, 1, '#ff0000');
  
      // Check if setPixelData was called with the correct updated matrix
      const expectedPixelData = [
        ['#ffffff', '#ffffff'],
        ['#ff0000', '#ffffff']
      ];
      
      expect(mockSetPixelData).toHaveBeenCalledWith(expectedPixelData);
      expect(mockDebouncedSaveCanvasData).toHaveBeenCalledWith(expectedPixelData);
    });
  
    it('should not affect other pixels when updating a single pixel', () => {
      // Setup initial pixel data with some colors
      const initialPixelData = [
        ['#ff0000', '#00ff00'],
        ['#0000ff', '#ffffff']
      ];
  
      vi.mocked(useCanvasData).mockReturnValue({
        pixelData: initialPixelData,
        setPixelData: mockSetPixelData,
        fetchCanvasData: vi.fn(),
        loading: false,
        error: null,
      });
  
      render(<App />);
      
      // Get the onPixelChange function from the Canvas props
      const { onPixelChange } = vi.mocked(Canvas).mock.calls[0][0];
      
      // Change a single pixel
      onPixelChange(1, 1, '#000000');
  
      // Check if setPixelData was called with the correct matrix
      // where only the target pixel was changed
      const expectedPixelData = [
        ['#ff0000', '#00ff00'],
        ['#0000ff', '#000000']
      ];
      
      expect(mockSetPixelData).toHaveBeenCalledWith(expectedPixelData);
      expect(mockDebouncedSaveCanvasData).toHaveBeenCalledWith(expectedPixelData);
    });
  });
});
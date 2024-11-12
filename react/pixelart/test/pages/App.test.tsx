import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import App from '../../src/App';
import { vi } from 'vitest';
import useCanvasData from '../../src/hooks/useCanvasData';
import useCanvasDataUpdater from '../../src/hooks/useCanvasDataUpdater';
import Canvas from '../../src/components/Canvas';

/**
 * Mocks both hooks for use.
 **/
vi.mock('../../src/hooks/useCanvasData', () => ({
  __esModule: true,
  default: vi.fn(),
}));

vi.mock('../../src/hooks/useCanvasDataUpdater', () => ({
  __esModule: true,
  default: vi.fn(),
}));


/**
 * Mocks Canvas component, and stores props for testing
 **/
vi.mock('../../src/components/Canvas', () => ({
  default: vi.fn((props) => {

    vi.mocked(Canvas).mock.calls[vi.mocked(Canvas).mock.calls.length - 1][0] = props;
    return <div data-testid="pixel-canvas" />;
  })
}));

/**
 * Establishes beforeEach to clear all mocks.
 * Mocks both hooks and initializes a white 2 by 2 gird
 **/
describe('App Component', () => {
  const mockSetPixelData = vi.fn();
  const mockDebouncedSaveCanvasData = vi.fn();
  
  beforeEach(() => {
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
  /**
   * Renders loading state using getByText, while useCanvasData is mocked.
   **/

  it('renders the loading state when loading', () => {
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
  /**
   * Renders error message, while hook is mocked as an error.
   **/

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
  /**
   * Renders the canvas when the mocked pixel data is available.
   * This is verifyed by the Canvas test id.
   **/
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
/**
 * initialises a multicolored canvas.
 **/
  describe('Clear Canvas Functionality', () => {
    const initialColoredPixels = [
      ['#ff0000', '#000000'],
      ['#0000ff', '#00ff00']
    ];

    /**
     * Turns all the pixels white.
     * Using two click mouse events.
     **/

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
  /**
   * Does not clear the mulitcolored canvas when clear is canceled.
   * Two click functions.
   **/
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
    /**
     * Updated pixel data when handlePixelChange is called.
     * Changes a single pixel in the specific coordinate.
     **/
    it('should update pixel data when handlePixelChange is called', () => {
      render(<App />);

      const { onPixelChange } = vi.mocked(Canvas).mock.calls[0][0];

      onPixelChange(0, 1, '#ff0000');

      const expectedPixelData = [
        ['#ffffff', '#ffffff'],
        ['#ff0000', '#ffffff']
      ];
      
      expect(mockSetPixelData).toHaveBeenCalledWith(expectedPixelData);
      expect(mockDebouncedSaveCanvasData).toHaveBeenCalledWith(expectedPixelData);
    });
    /**
     * Shpuld update a single pixel.
     * And should not affect other pixels.
     **/
    it('should not affect other pixels when updating a single pixel', () => {
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

      const { onPixelChange } = vi.mocked(Canvas).mock.calls[0][0];

      onPixelChange(1, 1, '#000000');

      const expectedPixelData = [
        ['#ff0000', '#00ff00'],
        ['#0000ff', '#000000']
      ];
      
      expect(mockSetPixelData).toHaveBeenCalledWith(expectedPixelData);
      expect(mockDebouncedSaveCanvasData).toHaveBeenCalledWith(expectedPixelData);
    });
  });
});
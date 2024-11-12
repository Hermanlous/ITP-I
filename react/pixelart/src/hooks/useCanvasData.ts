import { useState, useEffect, useCallback } from 'react';
import { getCanvas } from '../utils/api';

/**
 * Hook for fetching pixel data for the canvas.
 * Manages and provides loading, error and current pixeldata.
 * Fetches data from restAPI
 *
 * @return {
 * {pixelData},
 * {setPixelData},
 * {loading},
 * {setLoading},
 * {error},
 * {setError}
 * }
 * These are states to hold handling from the hook.
 * **/

const useCanvasData = () => {
  const [pixelData, setPixelData] = useState<string[][]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  /**
   * Async function for fetching data from the rest API. Using getCanvas()
   * It updates loading, error and sets the pixel data for further use.
   **/
  const fetchCanvasData = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getCanvas();
      console.log('gettin canvas');
      if (data) {
        setPixelData(data);
      }
    } catch (err) {
      console.error('Failed to fetch canvas data:', err);
      setError('Failed to load canvas data.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchCanvasData();
  }, [fetchCanvasData]);

  return { pixelData, setPixelData, fetchCanvasData, loading, error };
};

export default useCanvasData;

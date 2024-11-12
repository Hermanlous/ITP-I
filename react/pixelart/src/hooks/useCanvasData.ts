import { useState, useEffect, useCallback } from 'react';
import { getCanvas } from '../utils/api';

const useCanvasData = () => {
  const [pixelData, setPixelData] = useState<string[][]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch the canvas data from the backend
  const fetchCanvasData = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getCanvas(); console.log("gettin canvas")
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
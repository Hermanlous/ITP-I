import { useState, useEffect } from 'react';
import { getCanvas, updateCanvas } from '../utils/api';

const useCanvasData = () => {
  const [pixelData, setPixelData] = useState<string[][]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch the canvas data from the backend
  const fetchCanvasData = async () => {
    setLoading(true);
    try {
      const data = await getCanvas();
      if (data) {
        setPixelData(data);
      }
    } catch (err) {
      console.error('Failed to fetch canvas data:', err);
      setError('Failed to load canvas data.');
    } finally {
      setLoading(false);
    }
  };

  // Update the canvas data on the backend
  const saveCanvasData = async (updatedData: string[][]) => {
    try {
      await updateCanvas(updatedData);
      setPixelData(updatedData); // Optionally update state after saving
    } catch (err) {
      console.error('Failed to save canvas data:', err);
      setError('Failed to save canvas data.');
    }
  };

  useEffect(() => {
    fetchCanvasData();
  }, []);

  return { pixelData, setPixelData, fetchCanvasData, saveCanvasData, loading, error };
};

export default useCanvasData;

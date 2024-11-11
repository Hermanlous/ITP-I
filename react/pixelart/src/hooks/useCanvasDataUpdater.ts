import { useState, useCallback, useRef } from 'react';
import { updateCanvas } from '../utils/api';

const useCanvasDataUpdater = () => {
  const [isSaving, setIsSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const timeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const debouncedSaveCanvasData = useCallback(
    (updatedData: string[][]) => {
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
      }
      timeoutRef.current = setTimeout(async () => {
        setIsSaving(true);
        try {
          await updateCanvas(updatedData); console.log("updating canvas");
        } catch (err) {
          console.error('Failed to save canvas data:', err);
          setError('Failed to save canvas data.');
        } finally {
          setIsSaving(false);
        }
      }, 5000); // Debounce delay in milliseconds
    },
    [setIsSaving, setError]
  );

  return { debouncedSaveCanvasData, isSaving, error };
};

export default useCanvasDataUpdater;
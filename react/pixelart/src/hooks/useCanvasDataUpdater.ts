import { useState, useCallback, useRef } from 'react';
import { updateCanvas } from '../utils/api';
/**
 * Hook for updating pixel data for the canvas rest API.
 * Manages and provides loading, error and current pixeldata.
 * Updates data from canvas to restAPI, it monitors the saving state & errors.
 *
 * */
const useCanvasDataUpdater = () => {
  const [isSaving, setIsSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const timeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  /**
   * Debounce function to restrict the amount of API calls, implemented for sustainability.
   * It updates after 5 seconds of inactivity.
   * More calls within the 5 seconds will reset the inactivity state.
   **/
  const debouncedSaveCanvasData = useCallback(
    (updatedData: string[][]) => {
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
      }
      timeoutRef.current = setTimeout(async () => {
        setIsSaving(true);
        try {
          await updateCanvas(updatedData);
          console.log('updating canvas');
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

/**
 * Localhost hosting our restAPI
 **/
const API_BASE_URL = 'http://localhost:8080/canvas';

/**
 * Fetches the data from the rest API.
 * The data is expected to be a 2-dimensional array of strings: string[][], displaying the canvasgrid.
 * Each string is a hexcode depicting it's color.
 * This is done by a GET request.
 **/

export const getCanvas = async (): Promise<string[][] | null> => {
  try {
    const response = await fetch(API_BASE_URL, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
      },
    });
    if (!response.ok) {
      console.error('Failed to fetch canvas data:', response.statusText);
      return null;
    }
    return await response.json();
  } catch (error) {
    console.error('Error fetching canvas data:', error);
    return null;
  }
};

/**
 * Updates the rest API with updated data as a 2d-array of strings: string[][],
 * displaying the new pixel grid data.
 * This is done by using a PUT request.
 **/

export const updateCanvas = async (canvasData: string[][]): Promise<void> => {
  try {
    const response = await fetch(API_BASE_URL, {
      method: 'PUT',
      body: JSON.stringify(canvasData),
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (!response.ok) {
      console.error('Failed to update canvas:', response.statusText);
    }
  } catch (error) {
    console.error('Error updating canvas:', error);
  }
};

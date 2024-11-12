const API_BASE_URL = 'http://localhost:8080/canvas';

export const getCanvas = async (): Promise<string[][] | null> => {
  try {
    const response = await fetch(API_BASE_URL, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
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


/**
 * Canvas props, used in Canvas.tsx.
 * @param {CanvasProps}, props for the Canvas component.
 * @param {string[][]}, is a 2-dimensional array the initial data.
 * @param {string}, is the hexcode of the selected color, for drawing.
 * @param {(x: number, y: number, color: string) => void}, function for resolving mouse events.
 * @param {number}, defines max width of the Canvas component.
 * @param {number}, defines max height of the Canvas component.
 * @param {boolean}, either displays grid or does not display grid.
 * @param {number}, establishes grid gap.
 **/

export interface CanvasProps {
    initialData: string[][];
    selectedColor: string;
    onPixelChange: (x: number, y: number, color: string) => void;
    maxWidth?: number;
    maxHeight?: number;
    showGrid?: boolean;
    gridGap?: number;
}

export interface Pixel {
    x: number;
    y: number;
    color: string;
}

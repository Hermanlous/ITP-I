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

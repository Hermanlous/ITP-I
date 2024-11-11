import React from 'react';
import { Color } from '../types/color.types';
import CheckIcon from './CheckIcon';

interface ColorPanelProps {
  colors: Color[];
  selectedColor: Color;
  onColorSelect: (color: Color) => void;
}

const ColorPanel: React.FC<ColorPanelProps> = ({ colors, selectedColor, onColorSelect }) => {
  return (
    <div className="flex flex-col gap-2 pr-4">
      {colors.map((color) => (
        <div
          key={color.color}
          className="w-10 h-10 border-2 cursor-pointer"
          style={{
            backgroundColor: color.color,
            borderColor: selectedColor.color === color.color ? selectedColor.onColor : 'transparent',
          }}
          onClick={() => onColorSelect(color)}
        >
          {selectedColor.color === color.color && <CheckIcon color={color.onColor} />}
        </div>
      ))}
    </div>
  );
};

export default ColorPanel;

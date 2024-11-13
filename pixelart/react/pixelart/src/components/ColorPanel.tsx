import React from 'react';
import { Color } from '../types/color.types';
import CheckIcon from './CheckIcon';

/**
 * ColorPanelProps is props for the color panel, here the user can select color.
 * @param {Color[]}, an array of colors, each mapped to a button.
 * @param {Color}, selected color.
 * @param {(color: Color) => void}, function for selecting color.
 **/

interface ColorPanelProps {
  colors: Color[];
  selectedColor: Color;
  onColorSelect: (color: Color) => void;
}

const ColorPanel: React.FC<ColorPanelProps> = ({
  colors,
  selectedColor,
  onColorSelect,
}) => {
  return (
    <div className="flex flex-col gap-2 pr-4">
      {colors.map((color) => (
        <button
          key={color.color}
          data-testid={'color-panel-' + color.color}
          className="w-10 h-10 border-2 cursor-pointer"
          style={{
            backgroundColor: color.color,
            borderColor:
              selectedColor.color === color.color
                ? selectedColor.onColor
                : 'transparent',
          }}
          onClick={() => onColorSelect(color)}
        >
          {selectedColor.color === color.color && (
            <CheckIcon color={color.onColor} />
          )}
        </button>
      ))}
    </div>
  );
};

export default ColorPanel;

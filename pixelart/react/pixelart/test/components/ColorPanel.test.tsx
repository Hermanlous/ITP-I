import { render, screen, fireEvent } from '@testing-library/react';
import ColorPanel from '../../src/components/ColorPanel';

const mockColors = [
  { color: '#000000', onColor: '#ffffff' },
  { color: '#ff0000', onColor: '#ffffff' },
];
/**
 * Test case to ensure that the ColorPanel component renders the correct number of color options.
 * It checks that buttons representing the colors are rendered and the correct number of buttons are present.
 */
test('renders the color panel', () => {
  render(
    <ColorPanel
      colors={mockColors}
      selectedColor={mockColors[0]}
      onColorSelect={vi.fn()}
    />
  );
  const colorDivs = screen.getAllByRole('button');
  expect(colorDivs).toHaveLength(2);
});
/**
 * Test case to verify that when a user selects a color, the `onColorSelect`.
 * It simulates a click on a color button and checks if the callback receives the correct color object.
 */
test('selects a color', () => {
  const onColorSelectMock = vi.fn();
  render(
    <ColorPanel
      colors={mockColors}
      selectedColor={mockColors[0]}
      onColorSelect={onColorSelectMock}
    />
  );
  const redColorDiv = screen.getByTestId('color-panel-#ff0000');
  fireEvent.click(redColorDiv);
  expect(onColorSelectMock).toHaveBeenCalledWith(mockColors[1]);
});

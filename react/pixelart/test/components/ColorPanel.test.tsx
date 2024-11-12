import { render, screen, fireEvent } from '@testing-library/react';
import ColorPanel from '../../src/components/ColorPanel';

const mockColors = [
  { color: '#000000', onColor: '#ffffff' },
  { color: '#ff0000', onColor: '#ffffff' },
];

test('renders the color panel', () => {
  render(<ColorPanel colors={mockColors} selectedColor={mockColors[0]} onColorSelect={vi.fn()} />);
  const colorDivs = screen.getAllByRole('button');
  expect(colorDivs).toHaveLength(2);
});

test('selects a color', () => {
  const onColorSelectMock = vi.fn();
  render(<ColorPanel colors={mockColors} selectedColor={mockColors[0]} onColorSelect={onColorSelectMock} />);
  const redColorDiv = screen.getByTestId('color-panel-#ff0000');
  fireEvent.click(redColorDiv);
  expect(onColorSelectMock).toHaveBeenCalledWith(mockColors[1]);
});

import '@testing-library/jest-dom';
import App from '../../src/App.tsx';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';

vi.stubGlobal('fetch', vi.fn().mockResolvedValue({
  json: vi.fn().mockResolvedValue({ success: true }),
}));

describe('App Component', () => {
  it('should render color palette correctly and allow color selection', async () => {
    render(<App />);
    const blackColorDiv = screen.getByTestId('000000-color-div');
    const redColorDiv = screen.getByTestId('ff0000-color-div');


    expect(blackColorDiv).toHaveStyle('background-color: #000000');
    expect(redColorDiv).toHaveStyle('background-color: #ff0000');

    expect(screen.getByText('Selected Color:')).toHaveTextContent('#000000');


    fireEvent.click(redColorDiv);

    await waitFor(() => {
      expect(screen.getByText('Selected Color:')).toHaveTextContent('#ff0000');
    });

    const checkMark = screen.getByText('✓');
    expect(checkMark).toBeInTheDocument();

    expect(checkMark.closest('[data-testid="ff0000-color-div"]')).toBeInTheDocument();
  });
});
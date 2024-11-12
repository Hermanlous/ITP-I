import { render, screen } from '@testing-library/react';
import App from '../../src/App';

// vi.stubGlobal('fetch', vi.fn().mockResolvedValue({
//   json: vi.fn().mockResolvedValue({ success: true }),
// }));

describe('App Component', () => {
  it('renders a loading state initially', () => {
    render(<App />);
    expect(screen.getByText(/Loading.../i)).toBeTruthy();
  });
});
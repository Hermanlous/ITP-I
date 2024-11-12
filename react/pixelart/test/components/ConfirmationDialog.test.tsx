import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import ConfirmationDialog from '../../src/components/ConfirmationDialog';

describe('ConfirmationDialog Component', () => {
  it('renders nothing when "open" is false', () => {
    render(
      <ConfirmationDialog
        open={false}
        onConfirm={() => {}}
        onCancel={() => {}}
        title="Test Title"
        message="Test Message"
        confirmBtn="Confirm"
        cancelBtn="Cancel"
      />
    );
    expect(screen.queryByText('Test Title')).toBeNull();
  });

  it('calls onConfirm when the confirm button is clicked', () => {
    const onConfirm = vi.fn();
    render(
      <ConfirmationDialog
        open={true}
        onConfirm={onConfirm}
        onCancel={() => {}}
        title="Test Title"
        message="Test Message"
        confirmBtn="Confirm"
        cancelBtn="Cancel"
      />
    );

    fireEvent.click(screen.getByText('Confirm'));
    expect(onConfirm).toHaveBeenCalled();
  });

  it('calls onCancel when the cancel button is clicked', () => {
    const onCancel = vi.fn();
    render(
      <ConfirmationDialog
        open={true}
        onConfirm={() => {}}
        onCancel={onCancel}
        title="Test Title"
        message="Test Message"
        confirmBtn="Confirm"
        cancelBtn="Cancel"
      />
    );

    fireEvent.click(screen.getByText('Cancel'));
    expect(onCancel).toHaveBeenCalled();
  });
});

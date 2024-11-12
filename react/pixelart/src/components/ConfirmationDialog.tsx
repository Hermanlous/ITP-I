import React from 'react';

interface ConfirmationDialogProps {
  open: boolean;
  onConfirm: () => void;
  onCancel: () => void;
  title: string;
  message: string;
  confirmBtn: string;
  cancelBtn: string;
}

const ConfirmationDialog: React.FC<ConfirmationDialogProps> = ({
  open,
  onConfirm,
  onCancel,
  title,
  message,
  confirmBtn,
  cancelBtn,
}) => {
  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-700 bg-opacity-80">
        <div className="bg-gray-800 w-full max-w-md p-6">
            <h2 className='text-3xl'>{title}</h2>
            <p className="mt-4">{message}</p>
            <div className="mt-8 flex justify-center space-x-2">
                <button
                    className="px-4 bg-red-600 text-white hover:bg-red-700"
                    onClick={onConfirm}
                >
                    {confirmBtn}
                </button>
                <button
                    className="px-4 py-2 bg-gray-400 text-gray-900 hover:bg-gray-500"
                    onClick={onCancel}
                >
                    {cancelBtn}
                </button>
            </div>
        </div>
    </div>

  );
};

export default ConfirmationDialog;
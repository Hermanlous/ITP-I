import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { render, screen, fireEvent, act } from '@testing-library/react';
import Canvas from '../../src/components/Canvas.tsx';

/* Chatgpt was implemented here for templates for testing as well as logic for mouseclick and timers.
Mousedown logic is all credited to ChatGPT,
and the prompt contained multiple iteration and reviewing processes. */

/**
 * Initialises canvas and establishes beforeEach
 * and afterEach restoring og clearing mocks.
 **/
describe('Canvas', () => {
  const initialData = [
    ['#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff'],
    ['#ffffff', '#ffffff', '#ffffff']
  ];

  const onPixelChange = vi.fn();

  beforeEach(() => {
    vi.useFakeTimers();
    vi.clearAllMocks();
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });
  /**
   * Simulates mouse click, changing the color from white to black.
   * Checks to see if onPixelChange is called.
   **/
  it('Mouse click', () => {
    render(
        <Canvas
            initialData={initialData}
            selectedColor="#000000"
            onPixelChange={onPixelChange}
            maxWidth={500}
            maxHeight={500}
        />
    );


    const canvasElement = screen.getByTestId('pixel-canvas');
    fireEvent.mouseDown(canvasElement, {
      button: 0,
      clientX: 100,
      clientY: 100,
    });

    act(() => {
      vi.advanceTimersByTime(100);
    });

    expect(onPixelChange).toHaveBeenCalled();
    expect(onPixelChange).toHaveBeenCalledWith(expect.any(Number), expect.any(Number), "#000000");
  });

  /**
   * Mouse click and mouse movement test. Changes color from white to blue.
   * Mouse draws and stops drawing.
   **/

  it('Move mouse and vlick', () => {
    render(
        <Canvas
            initialData={initialData}
            selectedColor="#0000ff"
            onPixelChange={onPixelChange}
            maxWidth={500}
            maxHeight={500}
        />
    );
    const canvasElement = screen.getByTestId('pixel-canvas');

    fireEvent.mouseDown(canvasElement,{
      button: 0,
      clientX: 50,
      clientY: 50,
    });
    fireEvent.mouseUp(canvasElement);
    expect(onPixelChange).toHaveBeenCalledTimes(1);
    expect(onPixelChange).toHaveBeenCalledWith(expect.any(Number), expect.any(Number), "#0000ff");
  });
/**
 * Test case to ensure that drawing stops after mouse up.
 * Checks if it only clicks after mouse is down and not after it is up.
 **/
  it('should stop drawing after mouse up', () => {
    render(
        <Canvas
            initialData={initialData}
            selectedColor="#0000ff"
            onPixelChange={onPixelChange}
            maxWidth={500}
            maxHeight={500}
        />
    );
    const canvasElement = screen.getByTestId('pixel-canvas');
    fireEvent.mouseDown(canvasElement, {
      button: 0,
      clientX: 200,
      clientY: 200,
    });

    fireEvent.mouseMove(canvasElement, {
      clientX: 210,
      clientY: 210,
    });
    fireEvent.mouseUp(canvasElement);
    expect(onPixelChange).toHaveBeenCalled();

    fireEvent.mouseMove(canvasElement, {
      clientX: 220,
      clientY: 220,
    });
    expect(onPixelChange).toHaveBeenCalledTimes(1);
  });
});

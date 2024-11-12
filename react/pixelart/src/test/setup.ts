import '@testing-library/jest-dom'
import {cleanup} from '@testing-library/react';
import {afterEach} from 'vitest';
/**
 * Runs after each test to clean up the DOM.
 * This ensures that no leftover elements from previous tests remain in the DOM.
 */
afterEach(()=>{
  cleanup()
})
import { test, expect } from '@playwright/test';

test.describe('Tomatoma Frontend E2E Tests', () => {
  test('should load homepage and display main UI components', async ({ page }) => {
    // Navigate to the application
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    // Check page title
    const title = await page.title();
    console.log(`Page title: ${title}`);
    
    // Take screenshot of initial page
    await page.screenshot({ path: 'screenshot-initial.png' });
    console.log('Screenshot saved: screenshot-initial.png');
    
    // Check for main page elements
    const body = await page.locator('body');
    await expect(body).toBeVisible();
    console.log('✓ Body element visible');
    
    // Try to find header elements
    const headers = await page.locator('header, nav, [role="banner"]');
    const headerCount = await headers.count();
    console.log(`Found ${headerCount} header/nav elements`);
    
    // Check for main content area
    const mainContent = await page.locator('main, [role="main"], .main, #app');
    const mainCount = await mainContent.count();
    console.log(`Found ${mainCount} main content elements`);
    
    // Look for any buttons that might be category filters
    const buttons = await page.locator('button');
    const buttonCount = await buttons.count();
    console.log(`Found ${buttonCount} buttons`);
    
    // List all visible buttons
    if (buttonCount > 0) {
      for (let i = 0; i < Math.min(5, buttonCount); i++) {
        const text = await buttons.nth(i).textContent();
        console.log(`  Button ${i + 1}: "${text}"`);
      }
    }
    
    // Look for search input
    const searchInputs = await page.locator('input[type="text"], input[placeholder*="search" i]');
    const searchCount = await searchInputs.count();
    console.log(`Found ${searchCount} search input elements`);
    
    // Check for any iframes (Google Maps might be in iframe)
    const iframes = await page.locator('iframe');
    const iframeCount = await iframes.count();
    console.log(`Found ${iframeCount} iframe elements (likely Google Maps)`);
    
    // Check for canvas elements (map might be canvas)
    const canvas = await page.locator('canvas');
    const canvasCount = await canvas.count();
    console.log(`Found ${canvasCount} canvas elements`);
    
    // Check console for errors
    const errors = [];
    page.on('console', (msg) => {
      if (msg.type() === 'error') {
        errors.push(msg.text());
        console.log(`🔴 Console error: ${msg.text()}`);
      } else if (msg.type() === 'warn') {
        console.log(`🟡 Console warn: ${msg.text()}`);
      }
    });
    
    page.on('pageerror', (err) => {
      errors.push(err.message);
      console.log(`🔴 Page error: ${err.message}`);
    });
    
    // Wait a moment for any async operations
    await page.waitForTimeout(2000);
    
    // Check for API responses
    const apiResponses = [];
    page.on('response', async (response) => {
      if (response.url().includes('localhost:8080')) {
        apiResponses.push({
          url: response.url(),
          status: response.status()
        });
        console.log(`API Response: ${response.status()} ${response.url()}`);
      }
    });
    
    // Trigger API calls by waiting for network
    await page.waitForLoadState('networkidle');
    
    console.log(`\nTotal console errors detected: ${errors.length}`);
    console.log(`Total API responses detected: ${apiResponses.length}`);
  });
});

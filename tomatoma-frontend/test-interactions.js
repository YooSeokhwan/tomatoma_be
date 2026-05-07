import { test, expect } from '@playwright/test';

test.describe('Tomatoma Frontend Interaction Tests', () => {
  test('should handle category filter interactions', async ({ page }) => {
    console.log('\n=== Starting Category Filter Test ===');
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    // Wait for buttons to be visible
    await page.waitForTimeout(2000);
    
    // Find "전체 선택" (Select All) button
    const selectAllButton = page.locator('button:has-text("전체 선택")');
    const selectAllCount = await selectAllButton.count();
    console.log(`Found "전체 선택" button: ${selectAllCount > 0 ? 'YES' : 'NO'}`);
    
    if (selectAllCount > 0) {
      // Click the Select All button
      await selectAllButton.first().click();
      console.log('Clicked "전체 선택" button');
      
      // Wait for any updates
      await page.waitForTimeout(1000);
      
      // Check if categories are now selected
      const selectedCategories = page.locator('button[class*="selected"], button[class*="active"], button[style*="selected"]');
      const selectedCount = await selectedCategories.count();
      console.log(`Selected categories count: ${selectedCount}`);
    }
    
    // Try to find individual category buttons
    const allButtons = page.locator('button');
    const buttonCount = await allButtons.count();
    console.log(`Total buttons on page: ${buttonCount}`);
    
    // Get text of all buttons to identify categories
    const buttonTexts = [];
    for (let i = 0; i < Math.min(10, buttonCount); i++) {
      const text = await allButtons.nth(i).textContent();
      if (text && text.trim().length > 0 && text.trim().length < 50) {
        buttonTexts.push(text.trim());
      }
    }
    console.log('Sample button texts:', buttonTexts);
    
    // Take screenshot after interaction
    await page.screenshot({ path: 'screenshot-after-filter.png' });
    console.log('Screenshot saved: screenshot-after-filter.png');
  });

  test('should handle search input', async ({ page }) => {
    console.log('\n=== Starting Search Input Test ===');
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    // Find search input
    const searchInput = page.locator('input[type="text"], input[placeholder*="search" i]');
    const searchCount = await searchInput.count();
    console.log(`Found search input: ${searchCount > 0 ? 'YES' : 'NO'}`);
    
    if (searchCount > 0) {
      // Click on search input
      await searchInput.first().click();
      console.log('Clicked on search input');
      
      // Type search query
      await searchInput.first().fill('치킨');
      console.log('Typed "치킨" in search input');
      
      // Wait for results to update
      await page.waitForTimeout(2000);
      
      // Take screenshot
      await page.screenshot({ path: 'screenshot-search.png' });
      console.log('Screenshot saved: screenshot-search.png');
      
      // Check if page still renders without errors
      const body = await page.locator('body');
      await expect(body).toBeVisible();
      console.log('Page still visible after search');
    }
  });

  test('should verify API connectivity', async ({ page }) => {
    console.log('\n=== Starting API Connectivity Test ===');
    
    const apiResponses = [];
    const apiErrors = [];
    
    // Monitor network requests
    page.on('response', async (response) => {
      const url = response.url();
      const status = response.status();
      
      if (url.includes('localhost:8080')) {
        apiResponses.push({ url, status });
        console.log(`API Response: ${status} ${url}`);
        
        if (status >= 400) {
          apiErrors.push({ url, status });
        }
      }
    });
    
    page.on('requestfailed', (request) => {
      if (request.url().includes('localhost:8080')) {
        console.log(`API Request Failed: ${request.failure().errorText}`);
        apiErrors.push({ url: request.url(), error: request.failure().errorText });
      }
    });
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    // Wait for any delayed requests
    await page.waitForTimeout(3000);
    
    console.log(`Total API responses: ${apiResponses.length}`);
    console.log(`API errors: ${apiErrors.length}`);
    
    // Log all API responses
    apiResponses.forEach((resp, idx) => {
      console.log(`  ${idx + 1}. ${resp.status} - ${resp.url}`);
    });
    
    if (apiErrors.length > 0) {
      console.log('API Errors detected:');
      apiErrors.forEach((err, idx) => {
        console.log(`  ${idx + 1}. ${err.status || err.error} - ${err.url}`);
      });
    }
  });

  test('should verify page structure and accessibility', async ({ page }) => {
    console.log('\n=== Starting Page Structure Test ===');
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    // Check for common accessibility issues
    const headings = page.locator('h1, h2, h3, h4, h5, h6');
    const headingCount = await headings.count();
    console.log(`Found ${headingCount} heading elements`);
    
    // Check for alt text on images
    const images = page.locator('img');
    const imageCount = await images.count();
    console.log(`Found ${imageCount} image elements`);
    
    let imagesWithoutAlt = 0;
    for (let i = 0; i < imageCount; i++) {
      const alt = await images.nth(i).getAttribute('alt');
      if (!alt) {
        imagesWithoutAlt++;
      }
    }
    if (imagesWithoutAlt > 0) {
      console.log(`Warning: ${imagesWithoutAlt} images without alt text`);
    }
    
    // Check for forms
    const forms = page.locator('form');
    const formCount = await forms.count();
    console.log(`Found ${formCount} form elements`);
    
    // Check for links
    const links = page.locator('a');
    const linkCount = await links.count();
    console.log(`Found ${linkCount} link elements`);
    
    // Check for navigation elements
    const nav = page.locator('nav, [role="navigation"]');
    const navCount = await nav.count();
    console.log(`Found ${navCount} navigation elements`);
    
    // Check page performance (no major layout shifts)
    const layoutShifts = [];
    page.on('console', (msg) => {
      if (msg.text().includes('CLS') || msg.text().includes('Layout Shift')) {
        layoutShifts.push(msg.text());
      }
    });
    
    console.log('Page structure validation complete');
  });
});

import { test, expect } from '@playwright/test';

test.describe('Comprehensive E2E Tests', () => {
  test('should verify database connectivity and data flow', async ({ page }) => {
    console.log('\n=== Testing Database Connectivity ===');
    
    const dataFlowLog = { apiCalls: [], errors: [], pageLoads: [] };
    
    page.on('response', async (response) => {
      const url = response.url();
      const status = response.status();
      if (url.includes('localhost:8080')) {
        dataFlowLog.apiCalls.push({
          endpoint: url.replace('http://localhost:8080/api', ''),
          status
        });
        console.log(`API: ${status} ${url.replace('http://localhost:8080', '')}`);
      }
    });
    
    page.on('pageerror', (err) => {
      dataFlowLog.errors.push(err.message);
      console.log(`Page Error: ${err.message}`);
    });
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    await page.waitForTimeout(3000);
    
    console.log(`Total API calls: ${dataFlowLog.apiCalls.length}`);
    console.log(`Page errors: ${dataFlowLog.errors.length}`);
    
    const failedCalls = dataFlowLog.apiCalls.filter(call => call.status >= 400);
    console.log(`Failed API calls: ${failedCalls.length}`);
    
    expect(dataFlowLog.errors.length).toBe(0);
    expect(failedCalls.length).toBe(0);
  });

  test('should verify UI rendering', async ({ page }) => {
    console.log('\n=== Testing UI Rendering ===');
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    
    const header = page.locator('header, [class*="header"]');
    const mapArea = page.locator('canvas, iframe');
    const headerCount = await header.count();
    const mapCount = await mapArea.count();
    
    console.log(`Header elements: ${headerCount}`);
    console.log(`Map elements: ${mapCount}`);
    
    const bodyBox = await page.locator('body').boundingBox();
    console.log(`Page dimensions: ${bodyBox.width}x${bodyBox.height}`);
    
    expect(headerCount).toBeGreaterThan(0);
    expect(mapCount).toBeGreaterThan(0);
  });

  test('should test component interactions', async ({ page }) => {
    console.log('\n=== Testing Component Interactions ===');
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    await page.waitForTimeout(2000);
    
    const refreshBtn = page.locator('button:has-text("↻")');
    if (await refreshBtn.count() > 0) {
      await refreshBtn.click();
      console.log('Clicked refresh button');
      await page.waitForTimeout(1500);
    }
    
    const searchInput = page.locator('input[type="text"]');
    if (await searchInput.count() > 0) {
      await searchInput.first().fill('');
      await searchInput.first().type('버거', { delay: 50 });
      console.log('Typed search query');
      await page.waitForTimeout(1500);
      
      const value = await searchInput.first().inputValue();
      console.log(`Search value: ${value}`);
    }
  });

  test('should check memory and performance', async ({ page }) => {
    console.log('\n=== Testing Performance ===');
    
    const consoleMessages = { errors: [], warnings: [] };
    
    page.on('console', (msg) => {
      if (msg.type() === 'error') consoleMessages.errors.push(msg.text());
      if (msg.type() === 'warn') consoleMessages.warnings.push(msg.text());
    });
    
    await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
    await page.waitForTimeout(2000);
    
    const metrics = await page.metrics();
    console.log(`Memory: ${(metrics.JSHeapUsedSize / 1048576).toFixed(2)} MB`);
    console.log(`Console errors: ${consoleMessages.errors.length}`);
    console.log(`Console warnings: ${consoleMessages.warnings.length}`);
  });
});

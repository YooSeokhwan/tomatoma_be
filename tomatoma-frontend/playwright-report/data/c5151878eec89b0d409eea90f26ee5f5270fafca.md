# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: test-comprehensive.js >> Comprehensive E2E Tests >> should check memory and performance
- Location: test-comprehensive.js:84:3

# Error details

```
TypeError: page.metrics is not a function
```

# Page snapshot

```yaml
- generic [ref=e4]:
  - banner [ref=e5]:
    - generic [ref=e6]:
      - generic [ref=e7]:
        - generic [ref=e8]: 🍅
        - heading "토마토마" [level=1] [ref=e9]
      - heading "트렌드 음식 판매처 찾기" [level=2] [ref=e10]
      - generic [ref=e11]:
        - button "Refresh trending foods" [ref=e12] [cursor=pointer]: ↻
        - button "Help" [ref=e13] [cursor=pointer]: "?"
        - button "Settings" [ref=e14] [cursor=pointer]: ⚙️
  - generic [ref=e15]:
    - generic [ref=e16]:
      - textbox "Search foods" [ref=e18]:
        - /placeholder: 🔍 음식명을 입력하세요...
      - generic [ref=e19]:
        - heading "필터" [level=3] [ref=e20]
        - button "Select all" [ref=e23] [cursor=pointer]: 전체 선택
      - generic [ref=e24]:
        - generic [ref=e25]: "정렬:"
        - combobox "Sort by" [ref=e26] [cursor=pointer]:
          - option "인기순" [selected]
          - option "신규순"
          - option "최신순"
      - generic [ref=e27]:
        - button "부대찌개, 한식, 90 searches" [ref=e28] [cursor=pointer]:
          - generic [ref=e29]:
            - generic [ref=e30]: 한식
            - generic [ref=e31]: "#15"
          - heading "부대찌개" [level=4] [ref=e32]
          - generic [ref=e33]:
            - generic [ref=e34]: 검색 빈도
            - text: "90"
          - generic [ref=e37]:
            - generic [ref=e38]: "판매처: 미로딩"
            - button "상세보기" [ref=e39]
        - button "라면, 분식, 88 searches" [ref=e40] [cursor=pointer]:
          - generic [ref=e41]:
            - generic [ref=e42]: 분식
            - generic [ref=e43]: "#6"
          - heading "라면" [level=4] [ref=e44]
          - generic [ref=e45]:
            - generic [ref=e46]: 검색 빈도
            - text: "88"
          - generic [ref=e49]:
            - generic [ref=e50]: "판매처: 미로딩"
            - button "상세보기" [ref=e51]
        - button "떡볶이, 한식, 88 searches" [ref=e52] [cursor=pointer]:
          - generic [ref=e53]:
            - generic [ref=e54]: 한식
            - generic [ref=e55]: "#1"
          - heading "떡볶이" [level=4] [ref=e56]
          - generic [ref=e57]:
            - generic [ref=e58]: 검색 빈도
            - text: "88"
          - generic [ref=e61]:
            - generic [ref=e62]: "판매처: 미로딩"
            - button "상세보기" [ref=e63]
        - button "마라탕, 중식, 84 searches" [ref=e64] [cursor=pointer]:
          - generic [ref=e65]:
            - generic [ref=e66]: 중식
            - generic [ref=e67]: "#20"
          - heading "마라탕" [level=4] [ref=e68]
          - generic [ref=e69]:
            - generic [ref=e70]: 검색 빈도
            - text: "84"
          - generic [ref=e73]:
            - generic [ref=e74]: "판매처: 미로딩"
            - button "상세보기" [ref=e75]
        - button "감자탕, 기타, 81 searches" [ref=e76] [cursor=pointer]:
          - generic [ref=e77]:
            - generic [ref=e78]: 기타
            - generic [ref=e79]: "#17"
          - heading "감자탕" [level=4] [ref=e80]
          - generic [ref=e81]:
            - generic [ref=e82]: 검색 빈도
            - text: "81"
          - generic [ref=e85]:
            - generic [ref=e86]: "판매처: 미로딩"
            - button "상세보기" [ref=e87]
        - button "곱창, 기타, 80 searches" [ref=e88] [cursor=pointer]:
          - generic [ref=e89]:
            - generic [ref=e90]: 기타
            - generic [ref=e91]: "#12"
          - heading "곱창" [level=4] [ref=e92]
          - generic [ref=e93]:
            - generic [ref=e94]: 검색 빈도
            - text: "80"
          - generic [ref=e97]:
            - generic [ref=e98]: "판매처: 미로딩"
            - button "상세보기" [ref=e99]
        - button "순대국, 한식, 77 searches" [ref=e100] [cursor=pointer]:
          - generic [ref=e101]:
            - generic [ref=e102]: 한식
            - generic [ref=e103]: "#2"
          - heading "순대국" [level=4] [ref=e104]
          - generic [ref=e105]:
            - generic [ref=e106]: 검색 빈도
            - text: "77"
          - generic [ref=e109]:
            - generic [ref=e110]: "판매처: 미로딩"
            - button "상세보기" [ref=e111]
        - button "초밥, 한식, 72 searches" [ref=e112] [cursor=pointer]:
          - generic [ref=e113]:
            - generic [ref=e114]: 한식
            - generic [ref=e115]: "#5"
          - heading "초밥" [level=4] [ref=e116]
          - generic [ref=e117]:
            - generic [ref=e118]: 검색 빈도
            - text: "72"
          - generic [ref=e121]:
            - generic [ref=e122]: "판매처: 미로딩"
            - button "상세보기" [ref=e123]
        - button "칼국수, 한식, 67 searches" [ref=e124] [cursor=pointer]:
          - generic [ref=e125]:
            - generic [ref=e126]: 한식
            - generic [ref=e127]: "#11"
          - heading "칼국수" [level=4] [ref=e128]
          - generic [ref=e129]:
            - generic [ref=e130]: 검색 빈도
            - text: "67"
          - generic [ref=e133]:
            - generic [ref=e134]: "판매처: 미로딩"
            - button "상세보기" [ref=e135]
        - button "돈까스, 일식, 49 searches" [ref=e136] [cursor=pointer]:
          - generic [ref=e137]:
            - generic [ref=e138]: 일식
            - generic [ref=e139]: "#19"
          - heading "돈까스" [level=4] [ref=e140]
          - generic [ref=e141]:
            - generic [ref=e142]: 검색 빈도
            - text: "49"
          - generic [ref=e145]:
            - generic [ref=e146]: "판매처: 미로딩"
            - button "상세보기" [ref=e147]
        - button "치킨, 치킨, 46 searches" [ref=e148] [cursor=pointer]:
          - generic [ref=e149]:
            - generic [ref=e150]: 치킨
            - generic [ref=e151]: "#4"
          - heading "치킨" [level=4] [ref=e152]
          - generic [ref=e153]:
            - generic [ref=e154]: 검색 빈도
            - text: "46"
          - generic [ref=e157]:
            - generic [ref=e158]: "판매처: 미로딩"
            - button "상세보기" [ref=e159]
        - button "짬뽕, 중식, 46 searches" [ref=e160] [cursor=pointer]:
          - generic [ref=e161]:
            - generic [ref=e162]: 중식
            - generic [ref=e163]: "#10"
          - heading "짬뽕" [level=4] [ref=e164]
          - generic [ref=e165]:
            - generic [ref=e166]: 검색 빈도
            - text: "46"
          - generic [ref=e169]:
            - generic [ref=e170]: "판매처: 미로딩"
            - button "상세보기" [ref=e171]
        - button "김치찌개, 한식, 39 searches" [ref=e172] [cursor=pointer]:
          - generic [ref=e173]:
            - generic [ref=e174]: 한식
            - generic [ref=e175]: "#7"
          - heading "김치찌개" [level=4] [ref=e176]
          - generic [ref=e177]:
            - generic [ref=e178]: 검색 빈도
            - text: "39"
          - generic [ref=e181]:
            - generic [ref=e182]: "판매처: 미로딩"
            - button "상세보기" [ref=e183]
        - button "된장찌개, 한식, 34 searches" [ref=e184] [cursor=pointer]:
          - generic [ref=e185]:
            - generic [ref=e186]: 한식
            - generic [ref=e187]: "#18"
          - heading "된장찌개" [level=4] [ref=e188]
          - generic [ref=e189]:
            - generic [ref=e190]: 검색 빈도
            - text: "34"
          - generic [ref=e193]:
            - generic [ref=e194]: "판매처: 미로딩"
            - button "상세보기" [ref=e195]
        - button "양념치킨, 치킨, 34 searches" [ref=e196] [cursor=pointer]:
          - generic [ref=e197]:
            - generic [ref=e198]: 치킨
            - generic [ref=e199]: "#16"
          - heading "양념치킨" [level=4] [ref=e200]
          - generic [ref=e201]:
            - generic [ref=e202]: 검색 빈도
            - text: "34"
          - generic [ref=e205]:
            - generic [ref=e206]: "판매처: 미로딩"
            - button "상세보기" [ref=e207]
        - button "삼겹살, 한식, 32 searches" [ref=e208] [cursor=pointer]:
          - generic [ref=e209]:
            - generic [ref=e210]: 한식
            - generic [ref=e211]: "#3"
          - heading "삼겹살" [level=4] [ref=e212]
          - generic [ref=e213]:
            - generic [ref=e214]: 검색 빈도
            - text: "32"
          - generic [ref=e217]:
            - generic [ref=e218]: "판매처: 미로딩"
            - button "상세보기" [ref=e219]
        - button "짜장면, 중식, 32 searches" [ref=e220] [cursor=pointer]:
          - generic [ref=e221]:
            - generic [ref=e222]: 중식
            - generic [ref=e223]: "#9"
          - heading "짜장면" [level=4] [ref=e224]
          - generic [ref=e225]:
            - generic [ref=e226]: 검색 빈도
            - text: "32"
          - generic [ref=e229]:
            - generic [ref=e230]: "판매처: 미로딩"
            - button "상세보기" [ref=e231]
        - button "냉면, 기타, 27 searches" [ref=e232] [cursor=pointer]:
          - generic [ref=e233]:
            - generic [ref=e234]: 기타
            - generic [ref=e235]: "#14"
          - heading "냉면" [level=4] [ref=e236]
          - generic [ref=e237]:
            - generic [ref=e238]: 검색 빈도
            - text: "27"
          - generic [ref=e241]:
            - generic [ref=e242]: "판매처: 미로딩"
            - button "상세보기" [ref=e243]
        - button "족발, 한식, 22 searches" [ref=e244] [cursor=pointer]:
          - generic [ref=e245]:
            - generic [ref=e246]: 한식
            - generic [ref=e247]: "#8"
          - heading "족발" [level=4] [ref=e248]
          - generic [ref=e249]:
            - generic [ref=e250]: 검색 빈도
            - text: "22"
          - generic [ref=e253]:
            - generic [ref=e254]: "판매처: 미로딩"
            - button "상세보기" [ref=e255]
        - button "갈비탕, 한식, 14 searches" [ref=e256] [cursor=pointer]:
          - generic [ref=e257]:
            - generic [ref=e258]: 한식
            - generic [ref=e259]: "#13"
          - heading "갈비탕" [level=4] [ref=e260]
          - generic [ref=e261]:
            - generic [ref=e262]: 검색 빈도
            - text: "14"
          - generic [ref=e265]:
            - generic [ref=e266]: "판매처: 미로딩"
            - button "상세보기" [ref=e267]
    - generic [ref=e268]:
      - generic [ref=e270]:
        - generic:
          - button "Keyboard shortcuts"
        - region "Map" [ref=e271]
        - generic [ref=e272]:
          - iframe [ref=e321]:
            
          - menubar [ref=e322] [cursor=pointer]:
            - menuitemradio "Show street map" [checked] [ref=e324]: Map
            - menuitemradio "Show satellite imagery" [ref=e326]: Satellite
          - button "Map camera controls" [ref=e328] [cursor=pointer]
          - link "Open this area in Google Maps (opens a new window)" [ref=e330] [cursor=pointer]:
            - /url: https://maps.google.com/maps?ll=37.5665,126.978&z=13&t=m&hl=en&gl=US&mapclient=apiv3
            - img "Google" [ref=e332]
          - generic [ref=e333]:
            - button "Keyboard shortcuts" [ref=e339] [cursor=pointer]
            - generic [ref=e344]: Map data ©2026 TMap Mobility
            - link "Terms (opens in new tab)" [ref=e349] [cursor=pointer]:
              - /url: https://www.google.com/intl/en_US/help/terms_maps.html
              - text: Terms
      - generic:
        - paragraph: 좌측에서 음식을 선택하여 판매처를 확인하세요.
  - contentinfo [ref=e350]:
    - paragraph [ref=e352]: "© 2026 토마토마 | 트렌드 업데이트: 2026. 05. 05. 오후 09:04"
```

# Test source

```ts
  1   | import { test, expect } from '@playwright/test';
  2   | 
  3   | test.describe('Comprehensive E2E Tests', () => {
  4   |   test('should verify database connectivity and data flow', async ({ page }) => {
  5   |     console.log('\n=== Testing Database Connectivity ===');
  6   |     
  7   |     const dataFlowLog = { apiCalls: [], errors: [], pageLoads: [] };
  8   |     
  9   |     page.on('response', async (response) => {
  10  |       const url = response.url();
  11  |       const status = response.status();
  12  |       if (url.includes('localhost:8080')) {
  13  |         dataFlowLog.apiCalls.push({
  14  |           endpoint: url.replace('http://localhost:8080/api', ''),
  15  |           status
  16  |         });
  17  |         console.log(`API: ${status} ${url.replace('http://localhost:8080', '')}`);
  18  |       }
  19  |     });
  20  |     
  21  |     page.on('pageerror', (err) => {
  22  |       dataFlowLog.errors.push(err.message);
  23  |       console.log(`Page Error: ${err.message}`);
  24  |     });
  25  |     
  26  |     await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
  27  |     await page.waitForTimeout(3000);
  28  |     
  29  |     console.log(`Total API calls: ${dataFlowLog.apiCalls.length}`);
  30  |     console.log(`Page errors: ${dataFlowLog.errors.length}`);
  31  |     
  32  |     const failedCalls = dataFlowLog.apiCalls.filter(call => call.status >= 400);
  33  |     console.log(`Failed API calls: ${failedCalls.length}`);
  34  |     
  35  |     expect(dataFlowLog.errors.length).toBe(0);
  36  |     expect(failedCalls.length).toBe(0);
  37  |   });
  38  | 
  39  |   test('should verify UI rendering', async ({ page }) => {
  40  |     console.log('\n=== Testing UI Rendering ===');
  41  |     
  42  |     await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
  43  |     
  44  |     const header = page.locator('header, [class*="header"]');
  45  |     const mapArea = page.locator('canvas, iframe');
  46  |     const headerCount = await header.count();
  47  |     const mapCount = await mapArea.count();
  48  |     
  49  |     console.log(`Header elements: ${headerCount}`);
  50  |     console.log(`Map elements: ${mapCount}`);
  51  |     
  52  |     const bodyBox = await page.locator('body').boundingBox();
  53  |     console.log(`Page dimensions: ${bodyBox.width}x${bodyBox.height}`);
  54  |     
  55  |     expect(headerCount).toBeGreaterThan(0);
  56  |     expect(mapCount).toBeGreaterThan(0);
  57  |   });
  58  | 
  59  |   test('should test component interactions', async ({ page }) => {
  60  |     console.log('\n=== Testing Component Interactions ===');
  61  |     
  62  |     await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
  63  |     await page.waitForTimeout(2000);
  64  |     
  65  |     const refreshBtn = page.locator('button:has-text("↻")');
  66  |     if (await refreshBtn.count() > 0) {
  67  |       await refreshBtn.click();
  68  |       console.log('Clicked refresh button');
  69  |       await page.waitForTimeout(1500);
  70  |     }
  71  |     
  72  |     const searchInput = page.locator('input[type="text"]');
  73  |     if (await searchInput.count() > 0) {
  74  |       await searchInput.first().fill('');
  75  |       await searchInput.first().type('버거', { delay: 50 });
  76  |       console.log('Typed search query');
  77  |       await page.waitForTimeout(1500);
  78  |       
  79  |       const value = await searchInput.first().inputValue();
  80  |       console.log(`Search value: ${value}`);
  81  |     }
  82  |   });
  83  | 
  84  |   test('should check memory and performance', async ({ page }) => {
  85  |     console.log('\n=== Testing Performance ===');
  86  |     
  87  |     const consoleMessages = { errors: [], warnings: [] };
  88  |     
  89  |     page.on('console', (msg) => {
  90  |       if (msg.type() === 'error') consoleMessages.errors.push(msg.text());
  91  |       if (msg.type() === 'warn') consoleMessages.warnings.push(msg.text());
  92  |     });
  93  |     
  94  |     await page.goto('http://localhost:5174', { waitUntil: 'networkidle' });
  95  |     await page.waitForTimeout(2000);
  96  |     
> 97  |     const metrics = await page.metrics();
      |                                ^ TypeError: page.metrics is not a function
  98  |     console.log(`Memory: ${(metrics.JSHeapUsedSize / 1048576).toFixed(2)} MB`);
  99  |     console.log(`Console errors: ${consoleMessages.errors.length}`);
  100 |     console.log(`Console warnings: ${consoleMessages.warnings.length}`);
  101 |   });
  102 | });
  103 | 
```
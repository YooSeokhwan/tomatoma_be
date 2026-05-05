-- Initial Categories Seed Data

INSERT INTO categories (name, color, icon_emoji) VALUES ('한식', '#FF5252', '🍱');
INSERT INTO categories (name, color, icon_emoji) VALUES ('양식', '#AB47BC', '🍝');
INSERT INTO categories (name, color, icon_emoji) VALUES ('중식', '#FF9800', '🥡');
INSERT INTO categories (name, color, icon_emoji) VALUES ('카페', '#FFEB3B', '☕');
INSERT INTO categories (name, color, icon_emoji) VALUES ('일식', '#4CAF50', '🍣');
INSERT INTO categories (name, color, icon_emoji) VALUES ('치킨', '#FF6F00', '🍗');
INSERT INTO categories (name, color, icon_emoji) VALUES ('피자', '#D32F2F', '🍕');
INSERT INTO categories (name, color, icon_emoji) VALUES ('버거', '#8D6E63', '🍔');
INSERT INTO categories (name, color, icon_emoji) VALUES ('분식', '#9C27B0', '🌮');
INSERT INTO categories (name, color, icon_emoji) VALUES ('디저트', '#E91E63', '🍰');

-- Sample Trending Foods
INSERT INTO trend_foods (name, category, search_frequency, trend_rank, source, created_at, updated_at)
VALUES ('버터 계란 밥', '한식', 8500, 1, 'google_trends', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trend_foods (name, category, search_frequency, trend_rank, source, created_at, updated_at)
VALUES ('계란 마니아', '한식', 7200, 2, 'google_trends', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trend_foods (name, category, search_frequency, trend_rank, source, created_at, updated_at)
VALUES ('미역국 부자', '한식', 5800, 3, 'google_trends', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trend_foods (name, category, search_frequency, trend_rank, source, created_at, updated_at)
VALUES ('카라멜 마끼아또', '카페', 6500, 4, 'google_trends', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trend_foods (name, category, search_frequency, trend_rank, source, created_at, updated_at)
VALUES ('매콤한 우동', '중식', 5200, 5, 'google_trends', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sample Food Places
INSERT INTO food_places (trend_food_id, name, address, latitude, longitude, phone, rating, price_approx, operating_hours, google_place_id, created_at, updated_at)
VALUES (1, '명동 계란밥', '서울시 중구 명동 111', 37.5642, 126.9847, '02-1234-5678', 4.5, 7500, '10:00 - 22:00', 'place_id_001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO food_places (trend_food_id, name, address, latitude, longitude, phone, rating, price_approx, operating_hours, google_place_id, created_at, updated_at)
VALUES (1, '강남 버터 밥', '서울시 강남구 강남대로 222', 37.4979, 127.0276, '02-2222-3333', 4.3, 8000, '11:00 - 23:00', 'place_id_002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO food_places (trend_food_id, name, address, latitude, longitude, phone, rating, price_approx, operating_hours, google_place_id, created_at, updated_at)
VALUES (4, '역삼 카페', '서울시 강남구 역삼로 333', 37.4850, 127.0352, '02-3333-4444', 4.7, 6000, '09:00 - 21:00', 'place_id_003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

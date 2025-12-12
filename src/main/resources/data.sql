-- 1. SEED ONBOARDING ITEMS
-- (The ID is auto-generated, so we only insert title, description, and image_url)

INSERT INTO onboarding_items (title, description, image_url)
VALUES ('Welcome to FoRest!', 'Natural beuty ,powered by nature. Take care of yourself and the planet with the FoRest.', 'https://res.cloudinary.com/dorc5p2jg/image/upload/v1764387619/forestonboarding_1_besms2.png');

INSERT INTO onboarding_items (title, description, image_url)
VALUES ('Conscious Beuty', 'No parabens , no sillicones , no harmful additives . Just what truly works', 'https://res.cloudinary.com/dorc5p2jg/image/upload/v1764395080/forest_onboarding_two_w7xetb.png');

INSERT INTO onboarding_items (title, description, image_url)
VALUES ('Pure Ingridients Only', 'we use 100% natural ingridients that are safe for your skin and the envoriment. ', 'https://res.cloudinary.com/dorc5p2jg/image/upload/v1764386768/forest_onboarding_three_pcozgt.png');

INSERT INTO onboarding_items (title, description, image_url)
VALUES ('Personalised for You', 'Find the perfect products for your skin with our smart reccomendation system.', 'https://res.cloudinary.com/dorc5p2jg/image/upload/v1764386768/forest_onboarding_four_gfazsp.png');

-- 2. SEED CATEGORIES (Optional, but recommended)
--INSERT INTO categories (name, image_url) VALUES ('Indoor', 'https://link-to-indoor.jpg');
--INSERT INTO categories (name, image_url) VALUES ('Outdoor', 'https://link-to-outdoor.jpg');
--INSERT INTO categories (name, image_url) VALUES ('Succulents', 'https://link-to-succulents.jpg');
--INSERT INTO categories (name, image_url) VALUES ('Outdoor', 'https://link-to-outdoor.jpg');

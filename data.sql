-- =====================================================
-- MEAL MATE - DATOS DE PRUEBA
-- 3 Usuarios, 10 Recetas, 2 Planes, etc.
-- =====================================================

-- === 1. ROLES ===
INSERT INTO roles (name) VALUES 
    ('ADMIN'),
    ('USER');

-- === 2. USUARIOS ===
INSERT INTO users (username, email, password, avatar, bio, role_id) VALUES 
    ('admin', 'admin@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin.jpg', 'Administrador del sistema', 1),
    ('maria', 'maria@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'maria.jpg', 'Amante de la cocina saludable', 2),
    ('juan', 'juan@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'juan.jpg', 'Chef profesional', 2);

-- === 3. DIETAS ===
INSERT INTO diets (name) VALUES 
    ('Mediterránea'),
    ('Keto'),
    ('Vegetariana'),
    ('Vegana');

-- === 4. PREFERENCIAS USUARIO ===
INSERT INTO user_preferences (user_id, daily_calories_goal, daily_carbs_goal, daily_protein_goal, daily_fat_goal, diet_id) VALUES 
    (1, 2500, 300.00, 150.00, 80.00, 1),
    (2, 2000, 250.00, 100.00, 60.00, 3),
    (3, 3000, 350.00, 200.00, 100.00, 2);

-- === 5. ALÉRGENOS ===
INSERT INTO allergens (name) VALUES 
    ('Lactosa'),
    ('Gluten'),
    ('Mariscos'),
    ('Cacahuetes'),
    ('Huevos');

-- === 6. ALÉRGENOS USUARIO ===
INSERT INTO user_allergens (user_id, allergen_id) VALUES 
    (2, 1), -- Maria es alérgica a lactosa
    (2, 2); -- Maria es alérgica a gluten

-- === 7. TIPOS DE COMIDA ===
INSERT INTO meal_types (name) VALUES 
    ('Desayuno'),
    ('Comida'),
    ('Cena'),
    ('Aperitivo'),
    ('Merienda');

-- === 8. RECETAS (con JSONB y Nutrition) ===
INSERT INTO recipes (title, description, instructions, author_id, is_public, meal_type_id, ingredients) VALUES 
    ('Tortilla Española', 'Clásica tortilla de patatas', '1. Pelar patatas... 2. Freír...', 3, true, 2, '[{"name":"Patata","quantity":4,"unit":"unidades"},{"name":"Huevos","quantity":6,"unit":"unidades"},{"name":"Cebolla","quantity":1,"unit":"unidad"}]'),
    ('Ensalada César', 'Ensalada fresca con pollo', '1. Mezclar lechuga... 2. Añadir pollo...', 2, true, 2, '[{"name":"Lechuga","quantity":200,"unit":"g"},{"name":"Pollo","quantity":150,"unit":"g"},{"name":"Pan crujiente","quantity":50,"unit":"g"}]'),
    ('Batido Verde', 'Desayuno saludable', '1. Licuar espinacas... 2. Añadir manzana...', 2, true, 1, '[{"name":"Espinacas","quantity":100,"unit":"g"},{"name":"Manzana","quantity":1,"unit":"unidad"},{"name":"Plátano","quantity":1,"unit":"unidad"}]'),
    ('Salmón al Horno', 'Cena ligera y saludable', '1. Sazonar salmón... 2. Hornear 15 min...', 3, true, 3, '[{"name":"Salmón","quantity":200,"unit":"g"},{"name":"Limón","quantity":1,"unit":"unidad"},{"name":"Eneldo","quantity":10,"unit":"g"}]'),
    ('Yogur con Frutas', 'Merienda rápida', '1. Poner yogur... 2. Añadir frutas...', 2, true, 5, '[{"name":"Yogur natural","quantity":150,"unit":"g"},{"name":"Fresas","quantity":100,"unit":"g"},{"name":"Granola","quantity":30,"unit":"g"}]'),
    ('Pasta Bolognaise', 'Pasta casera italiana', '1. Sofreír carne... 2. Cocer pasta...', 3, true, 2, '[{"name":"Pasta","quantity":200,"unit":"g"},{"name":"Carne picada","quantity":150,"unit":"g"},{"name":"Tomate","quantity":200,"unit":"g"}]'),
    ('Smoothie Bowl', 'Desayuno colorido', '1. Licuar frutas... 2. Decorar con toppings...', 2, true, 1, '[{"name":"Mango","quantity":1,"unit":"unidad"},{"name":"Piña","quantity":100,"unit":"g"},{"name":"Coco rallado","quantity":20,"unit":"g"}]'),
    ('Pollo al Curry', 'Cena exótica', '1. Marinar pollo... 2. Cocinar con curry...', 3, true, 3, '[{"name":"Pollo","quantity":300,"unit":"g"},{"name":"Curry","quantity":20,"unit":"g"},{"name":"Coco","quantity":200,"unit":"ml"}]'),
    ('Galletas Veganas', 'Aperitivo dulce', '1. Mezclar ingredientes... 2. Hornear 12 min...', 2, true, 4, '[{"name":"Harina almendra","quantity":150,"unit":"g"},{"name":"Dátiles","quantity":100,"unit":"g"},{"name":"Chocolate","quantity":50,"unit":"g"}]'),
    ('Sopa de Lentejas', 'Comida reconfortante', '1. Sofreír verduras... 2. Añadir lentejas...', 3, true, 2, '[{"name":"Lentejas","quantity":200,"unit":"g"},{"name":"Zanahoria","quantity":2,"unit":"unidades"},{"name":"Cebolla","quantity":1,"unit":"unidad"}]');

-- === 9. NUTRICIÓN (para las 10 recetas) ===
INSERT INTO nutrition_info (recipe_id, calories, protein, carbs, fat, portion_size) VALUES 
    (1, 350.50, 15.20, 40.00, 18.50, 250.00), -- Tortilla
    (2, 280.00, 25.00, 15.50, 12.00, 300.00), -- Ensalada
    (3, 180.75, 5.00, 35.00, 2.50, 350.00),  -- Batido
    (4, 420.25, 35.00, 2.00, 28.75, 200.00), -- Salmón
    (5, 220.00, 8.50, 30.00, 6.00, 200.00),  -- Yogur
    (6, 550.00, 28.00, 70.00, 15.00, 400.00),-- Pasta
    (7, 250.50, 4.00, 55.00, 3.50, 300.00),  -- Smoothie
    (8, 480.75, 40.00, 10.00, 32.00, 350.00),-- Curry
    (9, 320.00, 6.00, 45.00, 14.00, 100.00), -- Galletas
    (10,260.25, 12.00, 40.00, 5.50, 300.00); -- Sopa

-- === 10. PLANES DE COMIDAS ===
INSERT INTO meal_plans (user_id, start_date, end_date) VALUES 
    (2, '2025-10-13', '2025-10-19'), -- Maria: Plan semanal
    (3, '2025-10-14', '2025-10-20'); -- Juan: Plan semanal

-- === 11. ITEMS DEL PLAN (Ejemplos) ===
INSERT INTO meal_plan_items (meal_plan_id, recipe_id, meal_type_id, date) VALUES 
    -- Maria - Lunes
    (1, 3, 1, '2025-10-13'),  -- Batido Verde (Desayuno)
    (1, 1, 2, '2025-10-13'),  -- Tortilla (Comida)
    (1, 4, 3, '2025-10-13'),  -- Salmón (Cena)
    -- Maria - Martes
    (1, 7, 1, '2025-10-14'),  -- Smoothie Bowl (Desayuno)
    (1, 2, 2, '2025-10-14'),  -- Ensalada (Comida)
    (1, 8, 3, '2025-10-14'),  -- Curry (Cena)
    -- Juan - Lunes
    (2, 6, 2, '2025-10-14'),  -- Pasta (Comida)
    (2, 10,2, '2025-10-14');  -- Sopa (Comida)

-- === 12. VALORACIONES ===
INSERT INTO ratings (user_id, recipe_id, rating) VALUES 
    (2, 1, 5.0), -- Maria ama la tortilla
    (2, 2, 4.5), -- Maria ama ensalada
    (3, 1, 4.0), -- Juan valora tortilla
    (3, 4, 5.0); -- Juan ama salmón

-- === 13. FAVORITOS ===
INSERT INTO favorites (user_id, recipe_id) VALUES 
    (2, 3), -- Maria guarda Batido Verde
    (2, 5), -- Maria guarda Yogur
    (3, 4), -- Juan guarda Salmón
    (3, 8); -- Juan guarda Curry

-- === 14. GRUPOS ===
INSERT INTO groups (name, description, creator_id, is_public) VALUES 
    ('Cocina Saludable', 'Recetas para vida saludable', 2, true),
    ('Chefs Profesionales', 'Recetas avanzadas', 3, false);

-- === 15. MIEMBROS DE GRUPOS ===
INSERT INTO group_members (group_id, user_id, role) VALUES 
    (1, 2, 'CREATOR'),
    (1, 3, 'MEMBER'),
    (2, 3, 'CREATOR'),
    (2, 1, 'ADMIN');

-- === 16. SEGUIDORES ===
INSERT INTO follows (follower_id, followed_id) VALUES 
    (2, 3), -- Maria sigue a Juan
    (3, 2); -- Juan sigue a Maria

-- === 17. RECETAS DE GRUPOS ===
INSERT INTO group_recipes (group_id, recipe_id, added_by) VALUES 
    (1, 3, 2), -- Batido Verde en Cocina Saludable
    (1, 2, 2), -- Ensalada en Cocina Saludable
    (2, 6, 3), -- Pasta en Chefs Profesionales
    (2, 8, 3); -- Curry en Chefs Profesionales

-- === 18. LISTAS DE COMPRA ===
INSERT INTO shopping_lists (meal_plan_id, user_id, items) VALUES 
    (1, 2, '[{"name":"Patata","quantity":4,"unit":"unidades","isChecked":false},{"name":"Huevos","quantity":6,"unit":"unidades","isChecked":true},{"name":"Lechuga","quantity":200,"unit":"g","isChecked":false}]'),
    (2, 3, '[{"name":"Pasta","quantity":200,"unit":"g","isChecked":false},{"name":"Carne picada","quantity":150,"unit":"g","isChecked":false},{"name":"Lentejas","quantity":200,"unit":"g","isChecked":true}]');

-- === ACTUALIZAR AVG_RATING EN RECETAS ===
UPDATE recipes 
SET avg_rating = (
    SELECT AVG(rating) 
    FROM ratings r 
    WHERE r.recipe_id = recipes.id
), rating_count = (
    SELECT COUNT(*) 
    FROM ratings r 
    WHERE r.recipe_id = recipes.id
)
WHERE EXISTS (SELECT 1 FROM ratings r WHERE r.recipe_id = recipes.id);
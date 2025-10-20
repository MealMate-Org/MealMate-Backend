-- =====================================================
-- MEAL MATE - DATOS DE PRUEBA COMPLETOS
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
-- === 5. ALÉRGENOS (6 COMPLETOS) ===
INSERT INTO allergens (name) VALUES
('Lactosa'),      -- ID 1
('Gluten'),       -- ID 2
('Frutas'),       -- ID 3
('Mariscos'),     -- ID 4
('Cacahuetes'),   -- ID 5
('Huevos');       -- ID 6
-- === 6. ALÉRGENOS USUARIO ===
INSERT INTO user_allergens (user_id, allergen_id) VALUES
(2, 1), -- Maria es alérgica a lactosa
(2, 2); -- Maria es alérgica a gluten
-- === 7. TIPOS DE COMIDA ===
INSERT INTO meal_types (name) VALUES
('Desayuno'),     -- ID 1
('Comida'),       -- ID 2
('Cena'),         -- ID 3
('Aperitivo'),    -- ID 4
('Merienda');     -- ID 5
-- === 8. RECETAS (10 COMPLETAS) ===
INSERT INTO recipes (title, description, instructions, author_id, is_public, meal_type_id, avg_rating, rating_count, ingredients, created_at) VALUES
('Ensalada César', 'Ensalada fresca con pollo', '1. Mezclar lechuga... 2. Añadir pollo...', 1, true, 2, 0.0, 0, '[{"name":"Lechuga","quantity":200,"unit":"g"},{"name":"Pollo","quantity":150,"unit":"g"}]', CURRENT_TIMESTAMP),
('Sopa de Lentejas', 'Sopa nutritiva', '1. Cocinar lentejas... 2. Añadir verduras...', 2, true, 2, 0.0, 0, '[{"name":"Lentejas","quantity":100,"unit":"g"},{"name":"Zanahoria","quantity":50,"unit":"g"}]', CURRENT_TIMESTAMP),
('Tortilla Española', 'Clásico español', '1. Batir huevos... 2. Añadir patata...', 1, true, 3, 0.0, 0, '[{"name":"Huevos","quantity":3,"unit":"unidades"},{"name":"Patata","quantity":200,"unit":"g"}]', CURRENT_TIMESTAMP),
('Brownie de Chocolate', 'Postre irresistible', '1. Mezclar harina... 2. Añadir chocolate...', 2, true, 3, 0.0, 0, '[{"name":"Harina","quantity":150,"unit":"g"},{"name":"Chocolate","quantity":100,"unit":"g"}]', CURRENT_TIMESTAMP),
('Yogur con Frutas', 'Merienda rápida', '1. Poner yogur... 2. Añadir frutas...', 2, true, 1, 0.0, 0, '[{"name":"Yogur natural","quantity":150,"unit":"g"},{"name":"Fresas","quantity":100,"unit":"g"}]', CURRENT_TIMESTAMP),
('Pasta Boloñesa', 'Pasta casera', '1. Cocinar pasta... 2. Añadir salsa...', 3, true, 2, 0.0, 0, '[{"name":"Pasta","quantity":200,"unit":"g"},{"name":"Carne","quantity":150,"unit":"g"}]', CURRENT_TIMESTAMP),
('Batido Verde', 'Batido saludable', '1. Licuar espinacas... 2. Añadir manzana...', 2, true, 1, 0.0, 0, '[{"name":"Espinacas","quantity":100,"unit":"g"},{"name":"Manzana","quantity":1,"unit":"unidades"}]', CURRENT_TIMESTAMP),
('Curry de Pollo', 'Curry exótico', '1. Dorar pollo... 2. Añadir especias...', 3, true, 3, 0.0, 0, '[{"name":"Pollo","quantity":200,"unit":"g"},{"name":"Arroz","quantity":150,"unit":"g"}]', CURRENT_TIMESTAMP),
('Galletas Veganas', 'Galletas sin huevo', '1. Mezclar harina... 2. Hornear...', 2, true, 5, 0.0, 0, '[{"name":"Harina","quantity":200,"unit":"g"},{"name":"Azúcar","quantity":100,"unit":"g"}]', CURRENT_TIMESTAMP),
('Sopa de Verduras', 'Sopa ligera', '1. Cortar verduras... 2. Cocinar...', 1, true, 2, 0.0, 0, '[{"name":"Calabacín","quantity":150,"unit":"g"},{"name":"Tomate","quantity":100,"unit":"g"}]', CURRENT_TIMESTAMP);
-- === 9. RELACIONAR RECETAS CON ALERGENOS ===
INSERT INTO recipe_allergens (recipe_id, allergen_id) VALUES
(1, 6), -- Ensalada César: Huevos (6)
(3, 6), -- Tortilla Española: Huevos (6)
(4, 1), -- Brownie: Lactosa (1)
(4, 2), -- Brownie: Gluten (2)
(5, 1), -- Yogur: Lactosa (1)
(5, 3), -- Yogur: Frutas (3)
(8, 4); -- Curry: Mariscos (4)
-- === 10. NUTRICIÓN (para las 10 recetas) ===
INSERT INTO nutrition_info (recipe_id, calories, protein, carbs, fat, portion_size) VALUES
(1, 350.50, 25.00, 10.00, 18.50, 250.00), -- Ensalada César
(2, 280.00, 18.00, 35.00, 8.00, 300.00),  -- Sopa Lentejas
(3, 420.00, 20.00, 25.00, 28.00, 200.00), -- Tortilla
(4, 550.00, 5.00, 70.00, 25.00, 150.00),  -- Brownie
(5, 220.00, 8.50, 30.00, 6.00, 250.00),   -- Yogur
(6, 650.00, 35.00, 80.00, 20.00, 350.00),  -- Pasta
(7, 180.00, 3.00, 40.00, 1.00, 300.00),   -- Batido
(8, 480.00, 40.00, 15.00, 32.00, 350.00),  -- Curry
(9, 320.00, 6.00, 45.00, 14.00, 100.00),   -- Galletas
(10,260.00, 12.00, 40.00, 5.50, 300.00);   -- Sopa Verduras
-- === 11. PLANES DE COMIDAS ===
INSERT INTO meal_plans (user_id, start_date, end_date) VALUES
(2, '2025-10-13', '2025-10-19'), -- Maria: Plan semanal
(3, '2025-10-14', '2025-10-20'); -- Juan: Plan semanal
-- === 12. ITEMS DEL PLAN (Ejemplos) ===
INSERT INTO meal_plan_items (meal_plan_id, recipe_id, meal_type_id, date) VALUES
-- Maria - Lunes
(1, 7, 1, '2025-10-13'),  -- Batido Verde (Desayuno)
(1, 1, 2, '2025-10-13'),  -- Ensalada César (Comida)
(1, 8, 3, '2025-10-13'),  -- Curry (Cena)
-- Maria - Martes
(1, 5, 1, '2025-10-14'),  -- Yogur (Desayuno)
(1, 2, 2, '2025-10-14'),  -- Sopa Lentejas (Comida)
(1, 3, 3, '2025-10-14'),  -- Tortilla (Cena)
-- Juan - Lunes
(2, 6, 2, '2025-10-14'),  -- Pasta (Comida)
(2, 10,2, '2025-10-14');  -- Sopa Verduras (Comida)
-- === 13. VALORACIONES ===
INSERT INTO ratings (user_id, recipe_id, rating) VALUES
(2, 1, 5.0), -- Maria ama Ensalada César
(2, 2, 4.5), -- Maria ama Sopa Lentejas
(3, 1, 4.0), -- Juan valora Ensalada César
(3, 4, 5.0); -- Juan ama Brownie
-- === 14. FAVORITOS ===
INSERT INTO favorites (user_id, recipe_id) VALUES
(2, 7), -- Maria guarda Batido Verde
(2, 5), -- Maria guarda Yogur
(3, 4), -- Juan guarda Brownie
(3, 8); -- Juan guarda Curry
-- === 15. GRUPOS ===
INSERT INTO groups (name, description, creator_id, is_public) VALUES
('Cocina Saludable', 'Recetas para vida saludable', 2, true),
('Chefs Profesionales', 'Recetas avanzadas', 3, false);
-- === 16. MIEMBROS DE GRUPOS ===
INSERT INTO group_members (group_id, user_id, role) VALUES
(1, 2, 'CREATOR'),
(1, 3, 'MEMBER'),
(2, 3, 'CREATOR'),
(2, 1, 'ADMIN');
-- === 17. SEGUIDORES ===
INSERT INTO follows (follower_id, followed_id) VALUES
(2, 3), -- Maria sigue a Juan
(3, 2); -- Juan sigue a Maria
-- === 18. RECETAS DE GRUPOS ===
INSERT INTO group_recipes (group_id, recipe_id, added_by) VALUES
(1, 7, 2), -- Batido Verde en Cocina Saludable
(1, 2, 2), -- Sopa Lentejas en Cocina Saludable
(2, 6, 3), -- Pasta en Chefs Profesionales
(2, 8, 3); -- Curry en Chefs Profesionales
-- === 19. LISTAS DE COMPRA ===
INSERT INTO shopping_lists (meal_plan_id, user_id, items) VALUES
(1, 2, '[{"name":"Patata","quantity":4,"unit":"unidades","checked":false},{"name":"Huevos","quantity":6,"unit":"unidades","checked":true},{"name":"Lechuga","quantity":200,"unit":"g","checked":false}]'),
(2, 3, '[{"name":"Pasta","quantity":200,"unit":"g","checked":false},{"name":"Carne picada","quantity":150,"unit":"g","checked":false},{"name":"Lentejas","quantity":200,"unit":"g","checked":true}]');
-- === 20. ACTUALIZAR AVG_RATING EN RECETAS ===
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
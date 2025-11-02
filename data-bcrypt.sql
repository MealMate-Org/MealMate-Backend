-- === 1. ROLES (ESENCIAL) ===
INSERT INTO roles (name) VALUES
('ADMIN'),
('USER');

-- === 2. USUARIOS (con contraseñas bcrypt) ===
-- Contraseña: "password" (hash bcrypt)
INSERT INTO users (username, email, password, avatar, bio, role_id) VALUES
('admin', 'admin@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Administrador del sistema', 1),
('usuario1', 'usuario1@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Usuario de prueba', 2);

-- === 3. ALÉRGENOS (CATÁLOGO) ===
INSERT INTO allergens (name) VALUES
('Lactosa'),      -- ID 1
('Gluten'),       -- ID 2
('Frutas'),       -- ID 3
('Mariscos'),     -- ID 4
('Cacahuetes'),   -- ID 5
('Huevos'),       -- ID 6
('Frutos secos'), -- ID 7
('Soja');         -- ID 8

-- === 4. TIPOS DE COMIDA (CATÁLOGO) ===
INSERT INTO meal_types (name) VALUES
('Desayuno'),     -- ID 1
('Comida'),       -- ID 2
('Cena'),         -- ID 3
('Aperitivo');    -- ID 4

-- === 5. DIETAS (CATÁLOGO) ===
INSERT INTO diets (name) VALUES
('Mediterránea'),
('Keto'),
('Vegetariana'),
('Vegana'),
('Sin gluten'),
('Paleo');

-- === 6. RECETAS DEL ADMIN ===
INSERT INTO recipes (title, description, instructions, author_id, is_public, ingredients, meal_type_id) VALUES
(
    'Ensalada Mediterránea',
    'Ensalada fresca con ingredientes típicos del mediterráneo',
    '1. Lavar y cortar las verduras
2. Mezclar la lechuga, tomate, pepino y cebolla
3. Añadir aceitunas y queso feta
4. Aliñar con aceite de oliva y vinagre
5. Servir fresco',
    1,
    true,
    '[{"name": "lechuga", "quantity": 200.0, "unit": "g"}, {"name": "tomate", "quantity": 2.0, "unit": "unidades"}, {"name": "pepino", "quantity": 1.0, "unit": "unidad"}, {"name": "cebolla", "quantity": 0.5, "unit": "unidad"}, {"name": "aceitunas", "quantity": 50.0, "unit": "g"}, {"name": "queso feta", "quantity": 100.0, "unit": "g"}, {"name": "aceite de oliva", "quantity": 2.0, "unit": "cucharadas"}, {"name": "vinagre", "quantity": 1.0, "unit": "cucharada"}]',
    2
),
(
    'Pollo a la Plancha',
    'Pechuga de pollo marinada y cocinada a la plancha',
    '1. Salpimentar el pollo
2. Marinarlo con hierbas por 30 minutos
3. Calentar la plancha
4. Cocinar 5 minutos por cada lado
5. Dejar reposar antes de servir',
    1,
    true,
    '[{"name": "pechuga de pollo", "quantity": 2.0, "unit": "unidades"}, {"name": "sal", "quantity": 1.0, "unit": "pizca"}, {"name": "pimienta", "quantity": 1.0, "unit": "pizca"}, {"name": "romero", "quantity": 1.0, "unit": "cucharadita"}, {"name": "tomillo", "quantity": 1.0, "unit": "cucharadita"}, {"name": "aceite de oliva", "quantity": 1.0, "unit": "cucharada"}]',
    2
),
(
    'Avena con Frutas',
    'Desayuno saludable con avena y frutas de temporada',
    '1. Cocinar la avena con leche o agua
2. Cortar las frutas frescas
3. Mezclar la avena cocida con las frutas
4. Añadir miel o endulzante al gusto
5. Servir caliente o frío',
    1,
    true,
    '[{"name": "avena", "quantity": 100.0, "unit": "g"}, {"name": "leche", "quantity": 250.0, "unit": "ml"}, {"name": "plátano", "quantity": 1.0, "unit": "unidad"}, {"name": "fresas", "quantity": 100.0, "unit": "g"}, {"name": "miel", "quantity": 1.0, "unit": "cucharada"}, {"name": "nueces", "quantity": 30.0, "unit": "g"}]',
    1
),
(
    'Sopa de Verduras',
    'Sopa caliente perfecta para días fríos',
    '1. Picar todas las verduras
2. Sofreír en una olla grande
3. Añadir caldo de verduras
4. Cocinar a fuego lento 25 minutos
5. Servir caliente con perejil fresco',
    1,
    true,
    '[{"name": "zanahoria", "quantity": 2.0, "unit": "unidades"}, {"name": "apio", "quantity": 2.0, "unit": "tallos"}, {"name": "cebolla", "quantity": 1.0, "unit": "unidad"}, {"name": "puerro", "quantity": 1.0, "unit": "unidad"}, {"name": "calabacín", "quantity": 1.0, "unit": "unidad"}, {"name": "caldo de verduras", "quantity": 1.0, "unit": "litro"}, {"name": "perejil", "quantity": 1.0, "unit": "pizca"}]',
    3
),
(
    'Hummus Casero',
    'Dip de garbanzos cremoso y sabroso',
    '1. Escurrir y lavar los garbanzos
2. Triturar con tahini, ajo y limón
3. Añadir agua hasta conseguir textura deseada
4. Rectificar de sal
5. Servir con aceite de oliva y pimentón',
    1,
    true,
    '[{"name": "garbanzos", "quantity": 400.0, "unit": "g"}, {"name": "tahini", "quantity": 2.0, "unit": "cucharadas"}, {"name": "ajo", "quantity": 1.0, "unit": "diente"}, {"name": "limón", "quantity": 1.0, "unit": "unidad"}, {"name": "comino", "quantity": 1.0, "unit": "cucharadita"}, {"name": "aceite de oliva", "quantity": 3.0, "unit": "cucharadas"}, {"name": "pimentón", "quantity": 1.0, "unit": "pizca"}]',
    4
);

-- === 7. INFORMACIÓN NUTRICIONAL PARA LAS RECETAS ===
INSERT INTO nutrition_info (recipe_id, calories, protein, carbs, fat, portion_size) VALUES
(1, 320.50, 8.20, 25.75, 22.10, 350.00),
(2, 450.25, 35.80, 5.50, 30.75, 200.00),
(3, 380.00, 12.50, 65.25, 10.80, 300.00),
(4, 180.75, 6.30, 30.45, 5.20, 400.00),
(5, 285.50, 9.80, 30.25, 15.60, 150.00);

-- === 8. PREFERENCIAS DEL USUARIO ===
INSERT INTO user_preferences (user_id, daily_calories_goal, daily_carbs_goal, daily_protein_goal, daily_fat_goal, diet_id, use_automatic_calculation, gender, age, weight, height, activity_level, goal) VALUES
(1, 2000, 250.00, 120.00, 70.00, 1, false, 'male', 35, 75.50, 180.00, 'moderate', 'maintenance'),
(2, 1800, 200.00, 100.00, 60.00, 3, true, 'female', 28, 62.00, 165.00, 'light', 'deficit');
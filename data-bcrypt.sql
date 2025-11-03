-- === 1. ROLES (ESENCIAL) ===
INSERT INTO roles (name) VALUES
('ADMIN'),
('USER');

-- === 2. USUARIOS (con contraseñas bcrypt) ===
-- Contraseña: "password" (hash bcrypt)
INSERT INTO users (username, email, password, avatar, bio, role_id) VALUES
('admin', 'admin@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Administrador del sistema', 1),
('usuario1', 'usuario1@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Usuario de prueba', 2),
('usuario2', 'usuario2@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Amante de la cocina saludable', 2),
('usuario3', 'usuario3@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Chef aficionado', 2),
('usuario4', 'usuario4@mealmate.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '', 'Fan de la comida mediterránea', 2);

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
INSERT INTO recipes (title, description, instructions, image_path, author_id, is_public, ingredients, meal_type_id) VALUES
(
    'Ensalada Mediterránea',
    'Ensalada fresca con ingredientes típicos del mediterráneo',
    '1. Lavar y cortar las verduras
2. Mezclar la lechuga, tomate, pepino y cebolla
3. Añadir aceitunas y queso feta
4. Aliñar con aceite de oliva y vinagre
5. Servir fresco',
    'https://images.pexels.com/photos/8963467/pexels-photo-8963467.jpeg',
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
    'https://images.pexels.com/photos/262945/pexels-photo-262945.jpeg',
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
    'https://images.pexels.com/photos/128865/pexels-photo-128865.jpeg',
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
    'https://images.pexels.com/photos/19063283/pexels-photo-19063283.jpeg',
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
    'https://images.pexels.com/photos/1618898/pexels-photo-1618898.jpeg',
    1,
    true,
    '[{"name": "garbanzos", "quantity": 400.0, "unit": "g"}, {"name": "tahini", "quantity": 2.0, "unit": "cucharadas"}, {"name": "ajo", "quantity": 1.0, "unit": "diente"}, {"name": "limón", "quantity": 1.0, "unit": "unidad"}, {"name": "comino", "quantity": 1.0, "unit": "cucharadita"}, {"name": "aceite de oliva", "quantity": 3.0, "unit": "cucharadas"}, {"name": "pimentón", "quantity": 1.0, "unit": "pizca"}]',
    4
),
(
    'Tortilla de Patatas',
    'Clásica tortilla española con patatas y cebolla',
    '1. Pelar y cortar las patatas en rodajas finas
2. Cortar la cebolla en juliana
3. Freír patatas y cebolla en aceite de oliva
4. Batir los huevos y mezclar con las patatas
5. Cuajar la tortilla en sartén por ambos lados
6. Servir caliente o fría',
    'https://images.pexels.com/photos/14941246/pexels-photo-14941246.jpeg',
    2,
    true,
    '[{"name": "patatas", "quantity": 4.0, "unit": "unidades"}, {"name": "huevos", "quantity": 6.0, "unit": "unidades"}, {"name": "cebolla", "quantity": 1.0, "unit": "unidad"}, {"name": "aceite de oliva", "quantity": 100.0, "unit": "ml"}, {"name": "sal", "quantity": 1.0, "unit": "pizca"}]',
    2
),
(
    'Pasta con Pesto',
    'Pasta con salsa pesto casera y piñones',
    '1. Cocer la pasta en agua con sal
2. Preparar el pesto: triturar albahaca, ajo, piñones y queso
3. Añadir aceite de oliva lentamente
4. Escurrir la pasta y mezclar con el pesto
5. Decorar con piñones tostados y queso rallado',
    'https://images.pexels.com/photos/34485646/pexels-photo-34485646.jpeg',
    2,
    true,
    '[{"name": "pasta", "quantity": 250.0, "unit": "g"}, {"name": "albahaca fresca", "quantity": 50.0, "unit": "g"}, {"name": "piñones", "quantity": 30.0, "unit": "g"}, {"name": "queso parmesano", "quantity": 50.0, "unit": "g"}, {"name": "aceite de oliva", "quantity": 80.0, "unit": "ml"}, {"name": "ajo", "quantity": 2.0, "unit": "dientes"}]',
    2
),
(
    'Smoothie Verde',
    'Batido saludable con espinacas y frutas',
    '1. Lavar las espinacas y la fruta
2. Pelar el plátano y la manzana
3. Poner todos los ingredientes en la licuadora
4. Licuar hasta obtener textura suave
5. Servir inmediatamente con hielo',
    'https://images.pexels.com/photos/12050010/pexels-photo-12050010.jpeg',
    2,
    true,
    '[{"name": "espinacas", "quantity": 50.0, "unit": "g"}, {"name": "plátano", "quantity": 1.0, "unit": "unidad"}, {"name": "manzana", "quantity": 1.0, "unit": "unidad"}, {"name": "yogur natural", "quantity": 150.0, "unit": "ml"}, {"name": "miel", "quantity": 1.0, "unit": "cucharada"}, {"name": "hielo", "quantity": 4.0, "unit": "cubitos"}]',
    1
),
(
    'Salmón al Horno',
    'Salmón con hierbas al horno y limón',
    '1. Precalentar el horno a 180°C
2. Colocar el salmón en una bandeja
3. Aliñar con aceite, sal, pimienta y eneldo
4. Añadir rodajas de limón
5. Hornear durante 15-20 minutos
6. Servir con verduras al vapor',
    'https://images.pexels.com/photos/12077942/pexels-photo-12077942.jpeg',
    2,
    true,
    '[{"name": "salmón", "quantity": 2.0, "unit": "filetes"}, {"name": "limón", "quantity": 1.0, "unit": "unidad"}, {"name": "aceite de oliva", "quantity": 2.0, "unit": "cucharadas"}, {"name": "eneldo fresco", "quantity": 1.0, "unit": "cucharada"}, {"name": "sal", "quantity": 1.0, "unit": "pizca"}, {"name": "pimienta", "quantity": 1.0, "unit": "pizca"}]',
    3
),
(
    'Brownies de Chocolate',
    'Brownies densos y chocolatosos',
    '1. Precalentar horno a 180°C y engrasar molde
2. Derretir chocolate y mantequilla
3. Mezclar con azúcar y huevos
4. Incorporar harina y nueces
5. Hornear 25-30 minutos
6. Dejar enfriar antes de cortar',
    'https://images.pexels.com/photos/3026804/pexels-photo-3026804.jpeg',
    2,
    true,
    '[{"name": "chocolate negro", "quantity": 200.0, "unit": "g"}, {"name": "mantequilla", "quantity": 150.0, "unit": "g"}, {"name": "azúcar", "quantity": 200.0, "unit": "g"}, {"name": "huevos", "quantity": 3.0, "unit": "unidades"}, {"name": "harina", "quantity": 100.0, "unit": "g"}, {"name": "nueces", "quantity": 80.0, "unit": "g"}]',
    4
);

-- === 7. INFORMACIÓN NUTRICIONAL PARA LAS RECETAS ===
INSERT INTO nutrition_info (recipe_id, calories, protein, carbs, fat, portion_size) VALUES
(1, 520.50, 8.20, 25.75, 22.10, 350.00),
(2, 650.25, 35.80, 5.50, 30.75, 200.00),
(3, 580.00, 12.50, 65.25, 10.80, 300.00),
(4, 380.75, 6.30, 30.45, 5.20, 400.00),
(5, 485.50, 9.80, 30.25, 15.60, 150.00),
(6, 650.75, 18.20, 45.30, 22.50, 250.00),
(7, 720.30, 15.80, 70.45, 18.90, 300.00),
(8, 480.50, 8.60, 45.20, 8.75, 350.00),
(9, 580.25, 25.40, 5.80, 28.90, 180.00),
(10, 620.80, 6.50, 55.75, 20.30, 100.00);

-- === 8. PREFERENCIAS DEL USUARIO ===
INSERT INTO user_preferences (user_id, daily_calories_goal, daily_carbs_goal, daily_protein_goal, daily_fat_goal, diet_id, use_automatic_calculation, gender, age, weight, height, activity_level, goal) VALUES
(1, 2000, 250.00, 120.00, 70.00, 1, false, 'male', 35, 75.50, 180.00, 'moderate', 'maintenance'),
(2, 1800, 200.00, 100.00, 60.00, 3, true, 'female', 28, 62.00, 165.00, 'light', 'deficit'),
(3, 2200, 280.00, 130.00, 80.00, 2, false, 'male', 32, 80.00, 175.00, 'active', 'maintenance'),
(4, 1900, 210.00, 95.00, 65.00, 1, true, 'female', 25, 58.00, 160.00, 'moderate', 'deficit'),
(5, 2400, 300.00, 140.00, 85.00, 4, false, 'male', 40, 85.00, 182.00, 'very_active', 'surplus');

-- === 9. RATINGS DE RECETAS ===
INSERT INTO ratings (user_id, recipe_id, score) VALUES
-- Receta 1: Ensalada Mediterránea
(2, 1, 4), (3, 1, 5), (4, 1, 4), (5, 1, 3),
-- Receta 2: Pollo a la Plancha
(2, 2, 5), (3, 2, 5), (4, 2, 5), (5, 2, 5),
-- Receta 3: Avena con Frutas
(2, 3, 4), (3, 3, 4), (4, 3, 5), (5, 3, 3),
-- Receta 4: Sopa de Verduras
(2, 4, 3), (3, 4, 3), (4, 4, 4), (5, 4, 2),
-- Receta 5: Hummus Casero
(2, 5, 5), (3, 5, 5), (4, 5, 5), (5, 5, 5),
-- Receta 6: Tortilla de Patatas
(1, 6, 4), (3, 6, 5), (4, 6, 4), (5, 6, 3),
-- Receta 7: Pasta con Pesto
(1, 7, 4), (3, 7, 4), (4, 7, 5), (5, 7, 3),
-- Receta 8: Smoothie Verde
(1, 8, 4), (3, 8, 3), (4, 8, 5), (5, 8, 4);
-- Recetas 9 y 10: Sin ratings (Salmón al Horno y Brownies de Chocolate)
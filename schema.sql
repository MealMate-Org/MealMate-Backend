-- MEAL MATE - SCHEMA ACTUALIZADO
-- PostgreSQL 16 - Con persistencia completa de planner y macros automáticos
-- =====================================================

-- === 1. USERS (Usuarios) ===
CREATE TABLE roles (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
avatar VARCHAR(255),
bio TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP,
deleted_at TIMESTAMP,
role_id INTEGER NOT NULL REFERENCES roles(id)
);

-- === 2. USER PREFERENCES (Preferencias con cálculo automático) ===
CREATE TABLE diets (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_preferences (
user_id BIGINT PRIMARY KEY REFERENCES users(id),
-- Objetivos manuales
daily_calories_goal INTEGER,
daily_carbs_goal NUMERIC(6,2),
daily_protein_goal NUMERIC(6,2),
daily_fat_goal NUMERIC(6,2),
diet_id INTEGER REFERENCES diets(id),
-- Campos para cálculo automático
use_automatic_calculation BOOLEAN DEFAULT FALSE,
gender VARCHAR(10) CHECK (gender IN ('male', 'female')),
age INTEGER CHECK (age >= 15 AND age <= 100),
weight NUMERIC(6,2) CHECK (weight >= 30 AND weight <= 300),
height NUMERIC(6,2) CHECK (height >= 100 AND height <= 250),
activity_level VARCHAR(20) CHECK (activity_level IN ('sedentary', 'light', 'moderate', 'active', 'very_active')),
goal VARCHAR(20) CHECK (goal IN ('deficit', 'maintenance', 'surplus'))
);

-- === 3. ALLERGENS (Alérgenos) ===
CREATE TABLE allergens (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_allergens (
id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(id),
allergen_id INTEGER NOT NULL REFERENCES allergens(id),
UNIQUE(user_id, allergen_id)
);

-- === 4. RECIPES (Recetas) ===
CREATE TABLE recipes (
id BIGSERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description TEXT,
instructions TEXT NOT NULL,
image_path VARCHAR(255),
author_id BIGINT NOT NULL REFERENCES users(id),
is_public BOOLEAN DEFAULT FALSE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP,
deleted_at TIMESTAMP,
avg_rating NUMERIC(3,2) DEFAULT 0.0,
rating_count INTEGER DEFAULT 0,
ingredients JSONB,
meal_type_id INTEGER
);

-- === 5. NUTRITION INFO ===
CREATE TABLE nutrition_info (
recipe_id BIGINT PRIMARY KEY REFERENCES recipes(id),
calories NUMERIC(6,2),
protein NUMERIC(6,2),
carbs NUMERIC(6,2),
fat NUMERIC(6,2),
portion_size NUMERIC(10,2)
);

-- === 6. PLANNER (Planificador mejorado) ===
CREATE TABLE meal_types (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE meal_plans (
id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(id),
start_date DATE NOT NULL,
end_date DATE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
is_active BOOLEAN DEFAULT TRUE,
UNIQUE(user_id, start_date, end_date)
);

CREATE TABLE meal_plan_items (
id BIGSERIAL PRIMARY KEY,
meal_plan_id BIGINT NOT NULL REFERENCES meal_plans(id) ON DELETE CASCADE,
recipe_id BIGINT NOT NULL REFERENCES recipes(id),
meal_type_id INTEGER NOT NULL REFERENCES meal_types(id),
date DATE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE(meal_plan_id, recipe_id, meal_type_id, date)
);

-- === 7. RATINGS & FAVORITES ===
CREATE TABLE ratings (
user_id BIGINT NOT NULL REFERENCES users(id),
recipe_id BIGINT NOT NULL REFERENCES recipes(id),
score NUMERIC(2,1) NOT NULL CHECK (score >= 0 AND score <= 5),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id, recipe_id)
);

CREATE TABLE favorites (
user_id BIGINT NOT NULL REFERENCES users(id),
recipe_id BIGINT NOT NULL REFERENCES recipes(id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id, recipe_id)
);

-- === 8. SOCIAL ===
CREATE TABLE groups (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description TEXT,
creator_id BIGINT NOT NULL REFERENCES users(id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
is_public BOOLEAN DEFAULT TRUE
);

CREATE TABLE group_members (
group_id BIGINT NOT NULL REFERENCES groups(id),
user_id BIGINT NOT NULL REFERENCES users(id),
joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
role VARCHAR(20) DEFAULT 'MEMBER',
PRIMARY KEY (group_id, user_id)
);

CREATE TABLE follows (
follower_id BIGINT NOT NULL REFERENCES users(id),
followed_id BIGINT NOT NULL REFERENCES users(id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (follower_id, followed_id),
CHECK (follower_id != followed_id)
);

CREATE TABLE group_recipes (
group_id BIGINT NOT NULL REFERENCES groups(id),
recipe_id BIGINT NOT NULL REFERENCES recipes(id),
added_by BIGINT NOT NULL REFERENCES users(id),
added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (group_id, recipe_id)
);

-- === 9. SHOPPING (Lista de compra mejorada) ===
CREATE TABLE shopping_lists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    meal_plan_id BIGINT REFERENCES meal_plans(id),
    group_id BIGINT REFERENCES groups(id),
    week_start_date DATE,
    week_end_date DATE,
    title VARCHAR(255), -- NUEVO: Título de la lista
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- NUEVO: Campo de actualización
    deleted_at TIMESTAMP,
    items JSONB NOT NULL DEFAULT '[]'::jsonb
);

-- === 10. RECIPE_ALLERGENS ===
CREATE TABLE recipe_allergens (
recipe_id BIGINT NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
allergen_id INTEGER NOT NULL REFERENCES allergens(id),
PRIMARY KEY (recipe_id, allergen_id)
);

-- === ÍNDICES PARA PERFORMANCE ===
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_recipes_author_id ON recipes(author_id);
CREATE INDEX idx_recipes_is_public ON recipes(is_public);
CREATE INDEX idx_recipes_title ON recipes(title);
CREATE INDEX idx_meal_plans_user_id ON meal_plans(user_id);
CREATE INDEX idx_meal_plans_dates ON meal_plans(start_date, end_date);
CREATE INDEX idx_meal_plan_items_date ON meal_plan_items(date);
CREATE INDEX idx_meal_plan_items_meal_plan ON meal_plan_items(meal_plan_id);
CREATE INDEX idx_meal_plan_items_recipe ON meal_plan_items(recipe_id);
CREATE INDEX idx_shopping_lists_user ON shopping_lists(user_id);
CREATE INDEX idx_shopping_lists_meal_plan ON shopping_lists(meal_plan_id);
CREATE INDEX idx_shopping_lists_week ON shopping_lists(week_start_date, week_end_date);

-- === TRIGGERS PARA ACTUALIZACIÓN AUTOMÁTICA ===
-- Trigger para actualizar updated_at en shopping_lists
CREATE OR REPLACE FUNCTION update_shopping_lists_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_shopping_lists_updated_at
    BEFORE UPDATE ON shopping_lists
    FOR EACH ROW
    EXECUTE FUNCTION update_shopping_lists_updated_at();

-- Trigger para actualizar el rating promedio de recetas
CREATE OR REPLACE FUNCTION update_recipe_rating()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE recipes 
    SET avg_rating = (
        SELECT COALESCE(AVG(score), 0) 
        FROM ratings 
        WHERE recipe_id = NEW.recipe_id
    ),
    rating_count = (
        SELECT COUNT(*) 
        FROM ratings 
        WHERE recipe_id = NEW.recipe_id
    )
    WHERE id = NEW.recipe_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_recipe_rating
    AFTER INSERT OR UPDATE OR DELETE ON ratings
    FOR EACH ROW
    EXECUTE FUNCTION update_recipe_rating();

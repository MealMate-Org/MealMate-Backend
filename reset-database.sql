-- =====================================================
-- RESET COMPLETO DE LA BASE DE DATOS MEALMATE
-- =====================================================
-- EJECUTA ESTE SCRIPT CUANDO QUIERAS EMPEZAR DE CERO

-- 1. ELIMINAR TODAS LAS TABLAS EN ORDEN (por dependencias)
DROP TABLE IF EXISTS shopping_lists CASCADE;
DROP TABLE IF EXISTS group_recipes CASCADE;
DROP TABLE IF EXISTS group_members CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS follows CASCADE;
DROP TABLE IF EXISTS recipe_permissions CASCADE;
DROP TABLE IF EXISTS meal_plan_items CASCADE;
DROP TABLE IF EXISTS meal_plans CASCADE;
DROP TABLE IF EXISTS ratings CASCADE;
DROP TABLE IF EXISTS favorites CASCADE;
DROP TABLE IF EXISTS recipe_allergens CASCADE;
DROP TABLE IF EXISTS nutrition_info CASCADE;
DROP TABLE IF EXISTS recipes CASCADE;
DROP TABLE IF EXISTS meal_types CASCADE;
DROP TABLE IF EXISTS user_allergens CASCADE;
DROP TABLE IF EXISTS user_preferences CASCADE;
DROP TABLE IF EXISTS allergens CASCADE;
DROP TABLE IF EXISTS diets CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- 2. RESETEAR SECUENCIAS (esto es CRÍTICO)
DROP SEQUENCE IF EXISTS roles_id_seq CASCADE;
DROP SEQUENCE IF EXISTS users_id_seq CASCADE;
DROP SEQUENCE IF EXISTS diets_id_seq CASCADE;
DROP SEQUENCE IF EXISTS allergens_id_seq CASCADE;
DROP SEQUENCE IF EXISTS meal_types_id_seq CASCADE;
DROP SEQUENCE IF EXISTS recipes_id_seq CASCADE;
DROP SEQUENCE IF EXISTS meal_plans_id_seq CASCADE;
DROP SEQUENCE IF EXISTS meal_plan_items_id_seq CASCADE;
DROP SEQUENCE IF EXISTS shopping_lists_id_seq CASCADE;
DROP SEQUENCE IF EXISTS groups_id_seq CASCADE;
DROP SEQUENCE IF EXISTS user_allergens_id_seq CASCADE;
DROP SEQUENCE IF EXISTS recipe_permissions_id_seq CASCADE;

-- =====================================================
-- AHORA VUELVE A EJECUTAR schema.sql Y data.sql
-- =====================================================
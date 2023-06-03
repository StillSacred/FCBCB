/* 7. В подключенном MySQL репозитории создать базу данных “Друзья человека”*/
DROP DATABASE IF EXISTS mans_friends;
CREATE DATABASE mans_friends;
USE mans_friends;


-- 8. Создать таблицы с иерархией из диаграммы в БД
DROP TABLE IF EXISTS animals;
CREATE TABLE animals
(
	id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
    animal_type VARCHAR(20) NOT NULL
);

INSERT INTO animals(animal_type)
VALUES
	('Собака'),
    ('Кот'),
    ('Кошка'),
    ('Хомяк'),
    ('Лошадь'),
    ('Конь'),
    ('Осел'),
    ('Верблюд');
    
DROP TABLE IF EXISTS pets;
CREATE TABLE pets
(
	animal_type_id INT,
    pet_id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    pet_type VARCHAR(20) NOT NULL,
    pet_name VARCHAR(20) NOT NULL,
    pet_birthday DATE NOT NULL,
    FOREIGN KEY (animal_type_id) REFERENCES animals (id)
);

DROP TABLE IF EXISTS pack_animals;
CREATE TABLE pack_animals
(
	pack_animal_type_id INT,
    pack_animal_id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    pack_animal_type VARCHAR(20) NOT NULL,
    pack_animal_name VARCHAR(20) NOT NULL,
    pack_animal_birthday DATE NOT NULL,
    FOREIGN KEY (pack_animal_type_id) REFERENCES animals (id)
);

DROP TABLE IF EXISTS commands;
CREATE TABLE commands
(
	pet_id INT,
    pack_animal_id INT,
    command_name VARCHAR(20) NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets (pet_id) ON DELETE CASCADE,
    FOREIGN KEY (pack_animal_id) REFERENCES pack_animals (pack_animal_id) ON DELETE CASCADE
);


-- 9. Заполнить низкоуровневые таблицы именами(животных), командами которые они выполняют и датами рождения
INSERT INTO pets(animal_type_id, pet_type, pet_name, pet_birthday)
VALUES
(1, 'собака', 'Джесси', '2021-06-05'),
(2, 'кот', 'Бегемот', '2020-02-12'),
(3, 'кошка', 'Мурка', '2022-04-02'),
(4, 'хомяк', 'Хома', '2023-05-06');

INSERT INTO pack_animals(pack_animal_type_id, pack_animal_type, pack_animal_name, pack_animal_birthday)
VALUES
(5, 'лошадь', 'Плотва', '2022-01-02'),
(6, 'конь', 'Конь', '2021-03-17'),
(7, 'осел', 'Ишак', '2021-02-03'),
(8, 'верблюд', 'Вася', '2023-05-06');

INSERT INTO commands(pet_id, pack_animal_id, command_name)
VALUES
(1, null, 'сидеть'),
(2, null, 'лежать'),
(3, null, 'ко мне'),
(4, null, 'бежать'),
(null, 1, 'галоп'),
(null, 2, 'рысью'),
(null, 3, 'стоять'),
(null, 4, 'вперед');


-- 10. Удалить из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу
DELETE FROM pack_animals WHERE pack_animal_type LIKE 'верблюд';


/* 11.Создать новую таблицу “молодые животные” в которую попадут все животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
до месяца подсчитать возраст животных в новой таблице */
CREATE TABLE young_animals
  SELECT * FROM pets where YEAR(CURRENT_TIMESTAMP) - YEAR(pet_birthday) - (RIGHT(CURRENT_TIMESTAMP, 5) < RIGHT(pet_birthday, 5))>1 and YEAR(CURRENT_TIMESTAMP) - YEAR(pet_birthday) - (RIGHT(CURRENT_TIMESTAMP, 5) < RIGHT(pet_birthday, 5))<3  
    UNION
  SELECT * FROM pack_animals WHERE YEAR(CURRENT_TIMESTAMP) - YEAR(pack_animal_birthday) - (RIGHT(CURRENT_TIMESTAMP, 5) < RIGHT(pack_animal_birthday, 5))>1 and YEAR(CURRENT_TIMESTAMP) - YEAR(pack_animal_birthday) - (RIGHT(CURRENT_TIMESTAMP, 5) < RIGHT(pack_animal_birthday, 5))<3;

SELECT *, (TIMESTAMPDIFF(MONTH, pet_birthday, curdate())) as month_age  FROM young_animals;


-- 12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам.
SELECT DISTINCT * FROM pets JOIN pack_animals JOIN young_animals;
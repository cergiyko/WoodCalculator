-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3307
-- Время создания: Окт 28 2022 г., 12:46
-- Версия сервера: 5.6.51
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `wood_calculator`
--

-- --------------------------------------------------------

--
-- Структура таблицы `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(10);

-- --------------------------------------------------------

--
-- Структура таблицы `manufacturers`
--

CREATE TABLE `manufacturers` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `manufacturers`
--

INSERT INTO `manufacturers` (`id`, `name`) VALUES
(1, 'WoodLine'),
(2, 'PapaKarlo'),
(3, 'LesHoz'),
(4, 'WoodFactory'),
(5, 'WoodLine1');

-- --------------------------------------------------------

--
-- Структура таблицы `price`
--

CREATE TABLE `price` (
  `id` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `manufacturer_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `price`
--

INSERT INTO `price` (`id`, `price`, `manufacturer_id`, `type_id`) VALUES
(2, 3600, 1, 2),
(3, 3700, 1, 3),
(4, 4000, 1, 4),
(5, 4200, 1, 5),
(6, 6500, 2, 2),
(7, 7200, 2, 3),
(8, 7050, 2, 4),
(9, 8000, 2, 5),
(10, 5000, 3, 2),
(11, 5500, 3, 3),
(12, 6300, 3, 5),
(13, 4800, 3, 4),
(14, 7550, 4, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `type`
--

CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `info` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `type`
--

INSERT INTO `type` (`id`, `info`, `type`) VALUES
(2, 'Древесина дуба обладает красивой текстурой и цветом, высокой прочностью против гниения, а также хорошей способностью к изгибу.', 'Дуб'),
(3, 'Древесина прочная и вязкая, с красивой текстурой, хорошо обрабатывается, а при сушке почти не растрескивается.', 'Ясень'),
(4, 'Древесина тяжелая, прочная, твердая, с красивой текстурой на радиальном разрезе, хорошо гнется, но не стойкая к гниению.', 'Бук'),
(5, 'Отличается высокой прочностью, особенно при ударных нагрузках, но малостойкая и во влажной среде быстро загнивает. Хорошо обтачивается.', 'Береза'),
(8, 'Древесина мягкая, легкая. Легко поддается лущению, хорошо гнется и имитируется под красное дерево.', 'Ольха');

-- --------------------------------------------------------

--
-- Структура таблицы `wood`
--

CREATE TABLE `wood` (
  `id` int(11) NOT NULL,
  `cost` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `length` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `quantity_val` double DEFAULT NULL,
  `valume` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  `manufacturer_id` int(11) DEFAULT NULL,
  `price_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `wood`
--

INSERT INTO `wood` (`id`, `cost`, `height`, `length`, `quantity`, `quantity_val`, `valume`, `width`, `manufacturer_id`, `price_id`, `type_id`) VALUES
(2, 3420, 45, 5, 100, 105.82, 0.95, 42, 1, 2, 2),
(3, 9000, 100, 5, 50, 20, 2.5, 100, 1, 2, 2),
(4, 840, 20, 10, 10, 50, 0.2, 100, 1, 5, 5),
(5, 4464, 50, 2, 123, 200, 0.62, 50, 2, 7, 3),
(6, 22715, 125, 3, 55, 13.33, 4.13, 200, 3, 11, 3),
(7, 4032, 40, 6, 58, 69.44, 0.84, 60, 3, 13, 4);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `manufacturers`
--
ALTER TABLE `manufacturers`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsd3cyaunprgj4pv2qakroo1oa` (`manufacturer_id`),
  ADD KEY `FKqsyislgi96wf3a4riof6lwkh2` (`type_id`);

--
-- Индексы таблицы `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5uj1b5sieanedt31v8on15ey` (`type`);

--
-- Индексы таблицы `wood`
--
ALTER TABLE `wood`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrw4wuc0d8lxpiyaauyh3js8bv` (`manufacturer_id`),
  ADD KEY `FK6bgnydu9l3y56c3hh5nf1v9y3` (`price_id`),
  ADD KEY `FKg5ttm789pupxdl2m38d76vd6g` (`type_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `manufacturers`
--
ALTER TABLE `manufacturers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `price`
--
ALTER TABLE `price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT для таблицы `wood`
--
ALTER TABLE `wood`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `price`
--
ALTER TABLE `price`
  ADD CONSTRAINT `FKqsyislgi96wf3a4riof6lwkh2` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `FKsd3cyaunprgj4pv2qakroo1oa` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`);

--
-- Ограничения внешнего ключа таблицы `wood`
--
ALTER TABLE `wood`
  ADD CONSTRAINT `FK6bgnydu9l3y56c3hh5nf1v9y3` FOREIGN KEY (`price_id`) REFERENCES `price` (`id`),
  ADD CONSTRAINT `FKg5ttm789pupxdl2m38d76vd6g` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `FKrw4wuc0d8lxpiyaauyh3js8bv` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

CREATE TABLE `pokemon_has_type`(
    `pokemon_id` int NOT NULL,
    `type_id` int NOT NULL,
    FOREIGN KEY (`pokemon_id`) REFERENCES `pokemon`(`id`), 
    FOREIGN KEY (`type_id`) REFERENCES `type`(`id`),
    UNIQUE (`pokemon_id`, `type_id`)
);
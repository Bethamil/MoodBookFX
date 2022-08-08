CREATE SCHEMA MoodBookFX;

CREATE TABLE MoodBookFX.moods(
`mood` varchar(32) NOT NULL PRIMARY KEY);

CREATE TABLE MoodBookFX.allMoods(
`mood_id` int AUTO_INCREMENT PRIMARY KEY,
 `date` DATE NOT NULL unique,
 `mood` varchar(32) NOT NULL,
 `extra_info` TEXT,
 CONSTRAINT `fk_moods_allmoods`
    FOREIGN KEY (`mood`)
    REFERENCES `MoodbookFX`.`moods` (`mood`));

INSERT INTO MoodBookFX.moods (`mood`) VALUES ('Happy'), ('Sad'), ('Anxious'), ('Disgusted'), ('Angry'), ('Surprised');

CREATE USER 'MoodAdmin'@'localhost' IDENTIFIED BY 'MoodPW';
GRANT ALL PRIVILEGES ON moodbookFX.* TO 'MoodAdmin'@'localhost';
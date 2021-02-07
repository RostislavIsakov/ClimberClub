CREATE DEFINER=`root`@`localhost` TRIGGER `climbers_BEFORE_UPDATE` BEFORE UPDATE ON `climbers` FOR EACH ROW BEGIN
	IF ((SELECT Category FROM climbers WHERE ID_Climber = NEW.ID_Climber) < NEW.Category) 
    THEN
		SIGNAL SQLSTATE '45000';
    END IF;
END
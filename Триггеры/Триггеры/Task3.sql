CREATE DEFINER=`root`@`localhost` TRIGGER `climbersingroups_BEFORE_INSERT` BEFORE INSERT ON `climbersingroups` FOR EACH ROW BEGIN
	IF ((SELECT COUNT(ID_Climber) FROM climbersingroups WHERE ID_Group = NEW.ID_Group) < 8)
    THEN
		SIGNAL SQLSTATE '45000';
    END IF;
END
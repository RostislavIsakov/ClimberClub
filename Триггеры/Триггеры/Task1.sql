CREATE DEFINER=`root`@`localhost` TRIGGER `climbersingroups_AFTER_DELETE` AFTER DELETE ON `climbersingroups` FOR EACH ROW BEGIN
	DELETE FROM climbers WHERE ID_Climber = OLD.ID_Climber;
    IF ((SELECT COUNT(ID_Climber) FROM climbersingroups WHERE ID_Group = OLD.ID_Group) = 0) 
    THEN
		DELETE FROM teams WHERE ID_Group = OLD.ID_Group;
		DELETE FROM climbing WHERE ID_Group = OLD.ID_Group;
    END IF;
END
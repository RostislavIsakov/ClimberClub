use climblingclubdb;

CREATE VIEW Group_info
AS SELECT  ID_Group, FIO, Address, Phone, Category, Sex
FROM climbers_in_groups
INNER JOIN climbers ON climbers.ID_Climber = climbers_in_groups.ID_Climber


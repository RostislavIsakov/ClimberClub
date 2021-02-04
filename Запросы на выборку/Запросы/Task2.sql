use climblingclubdb;

SELECT climbers_in_groups.ID_Group, FIO, Date_S, Date_E, Leader FROM climbers_in_groups
INNER JOIN climbers ON climbers.ID_Climber = climbers_in_groups.ID_Climber 
INNER JOIN team ON team.ID_Group = climbers_in_groups.ID_Group;
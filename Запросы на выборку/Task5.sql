use climblingclubdb;

SELECT * FROM climbers 
WHERE ID_Climber IN (SELECT climbers_in_groups.ID_Climber FROM climbers_in_groups 
WHERE climbers_in_groups.ID_Group IN (SELECT climbing.ID_Group FROM climbing
INNER JOIN mountains ON mountains.ID_Mountain = climbing.ID_Mountain 
WHERE mountains.Country LIKE 'Россия'));
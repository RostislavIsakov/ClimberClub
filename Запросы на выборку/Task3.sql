use climblingclubdb;

SELECT Name, (SELECT COUNT(ID_Climber) FROM climbers_in_groups WHERE climbers_in_groups.ID_Group = climbing.ID_Group) AS Количество FROM climbing 
INNER JOIN mountains ON mountains.ID_Mountain = climbing.ID_Mountain ORDER BY Name;
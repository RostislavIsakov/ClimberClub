use climblingclubdb;

DELIMITER //
CREATE PROCEDURE ProcTask1 
(
	proc_date_a DATE,
	proc_id_group INT,
	proc_id_mountain INT,
	proc_successful VARCHAR(10),
	proc_duration INT,
	proc_amount INT
)
BEGIN
	IF (proc_date_a BETWEEN (SELECT Date_S FROM climbers_in_groups WHERE ID_Group = proc_id_group LIMIT 1) AND (SELECT Date_E FROM climbers_in_groups WHERE ID_Group = proc_id_group LIMIT 1)) 
	THEN
		IF (DATE_ADD(proc_date_a, INTERVAL proc_duration HOUR) <= (SELECT Date_E FROM climbers_in_groups WHERE ID_Group = proc_id_group LIMIT 1))
		THEN
			INSERT INTO climbing (Date_A, ID_Group, ID_Mountain, Successful, Duration, Amount) 
			VALUES (proc_date_A, proc_id_group, proc_id_mountain, proc_successful, proc_duration, proc_amount);
		END IF;
	END IF;
END //
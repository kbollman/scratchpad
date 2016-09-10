CREATE OR REPLACE FUNCTION public.is_db_change_required (in p_tracked_item varchar, in p_change_id varchar) RETURNS bool AS
$BODY$
DECLARE
    v_returnvalue boolean;

BEGIN
	if (select 1 from db_change_tracker where tracked_item = p_tracked_item and change_id = p_change_id) = 1 then
		v_returnvalue := false;
	else
		v_returnvalue := true;
    end if;

    RETURN v_returnvalue;
END;
$BODY$
LANGUAGE 'plpgsql';

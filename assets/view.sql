-- create or replace view etat_offre as
-- select row_number() over () as id, offre_siege_avion_vol.id_offre_siege_avion_vol,coalesce(count(reservation.id_offre_siege_avion_vol) , 0) as nombre from offre_siege_avion_vol
--    left join (select * from reservation where reservation.date_annulation IS NULL) as reservation
--     on reservation.id_offre_siege_avion_vol = offre_siege_avion_vol.id_offre_siege_avion_vol
-- group by public.offre_siege_avion_vol.id_offre_siege_avion_vol;

create or replace view etat_offre as
select row_number() over () as id, offre_siege_avion_vol.id_offre_siege_avion_vol,coalesce(count(reservation.id_offre_siege_avion_vol) , 0) as nombre from offre_siege_avion_vol
       left join (select * from reservation_fille where reservation_fille.id_reservation_mere in (select id_reservation from reservation where reservation.date_annulation IS NULL)) as reservation
                 on reservation.id_offre_siege_avion_vol = offre_siege_avion_vol.id_offre_siege_avion_vol
group by public.offre_siege_avion_vol.id_offre_siege_avion_vol;
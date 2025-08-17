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

-- offre_siege_avion_vol_reel
create or replace view offre_siege_avion_vol_promotion as
select
    os.id_offre_siege_avion_vol,
    os.prix as prix_normal,
    COALESCE(p.valeur_pourcentage, 0) as reduction,
    p.nombre_siege - eo.nombre as prom_restant,
    sa.nombre - eo.nombre as billet_restant,
    CASE
        WHEN p.nombre_siege > eo.nombre THEN (os.prix * (100 - p.valeur_pourcentage))/100
        WHEN p.nombre_siege <= eo.nombre THEN os.prix
        WHEN p.nombre_siege is null THEN os.prix
        END AS prix_reel,
    ts.libelle
from offre_siege_avion_vol os
         LEFT JOIN promotion p on os.id_offre_siege_avion_vol = p.id_offre_siege_avion_vol
         JOIN etat_offre eo on eo.id_offre_siege_avion_vol = os.id_offre_siege_avion_vol
         JOIN siege_avion sa on os.id_siege_avion = sa.id_siege_avion
         JOIN type_siege ts on sa.id_siege_avion = ts.id_type_siege;


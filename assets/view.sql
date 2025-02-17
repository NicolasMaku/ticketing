create or replace view etat_offre as
select  offre_siege_avion_vol.id_offre_siege_avion_vol,coalesce(count(reservation.id_offre_siege_avion_vol) , 0) as nombre from offre_siege_avion_vol
    left join reservation
              on reservation.id_offre_siege_avion_vol = offre_siege_avion_vol.id_offre_siege_avion_vol
group by public.offre_siege_avion_vol.id_offre_siege_avion_vol;
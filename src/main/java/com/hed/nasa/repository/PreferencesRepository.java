package com.hed.nasa.repository;


import com.hed.nasa.dto.HomeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
Definování databázové repository pro objekt HomeDto s klíčem typu Long
 */

public interface PreferencesRepository extends JpaRepository<HomeDto,Long>
{
    /*
    Existuje plno způsobů jak dostat data z databáze ven.
    První query je jpa upravená
    Druhá query je nativní
    Třetí je nejjednoduší s využitím názvu metody jako výrazu pro to co chceme dělat.
    Všechny tři výrazy tak dole říkají to samé
     */
   // @Query("select dto from HomeDto dto where userId = :userId")
   // @Query(value="Select * from mars_api_preferences from where user_id = :userId",nativeQuery = true)
    HomeDto findByUserId(Long userId);
}

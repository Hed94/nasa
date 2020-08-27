package com.hed.nasa.repository;


import com.hed.nasa.dto.HomeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreferencesRepository extends JpaRepository<HomeDto,Long>
{
   // @Query("select dto from HomeDto dto where userId = :userId")
   // @Query(value="Select * from mars_api_preferences from where user_id = :userId",nativeQuery = true)
    HomeDto findByUserId(Long userId);
}

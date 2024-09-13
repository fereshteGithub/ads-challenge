package com.example.database.repository;

import com.example.database.entity.Impression;

import com.example.dto.GetAdvertiserInfoDto;
import com.example.dto.GetInfoByCountryCodeAndAppId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, UUID> {
    @Query(value = """
            SELECT  new com.example.dto.GetInfoByCountryCodeAndAppId(
             i.appId,
             i.countryCode,
             count (distinct  i.id),
             count (distinct c.impression.id),
             sum (c.revenue)
            )
            FROM Impression i
            join Click  c on c.impression=i
            group by  i.appId,i.countryCode
            having i.appId=:appId and i.countryCode=:countryCode
            """)
    List<GetInfoByCountryCodeAndAppId> getInfoByCountryCodeAndAppId(@Param("countryCode") String countryCode, @Param("appId") Integer appId);

    @Query("SELECT new com.example.dto.GetAdvertiserInfoDto(i.appId, i.countryCode, SUM(c.revenue) ,i.advertiserId) " +
            "FROM Click c " +
            "JOIN c.impression i " +
            "GROUP BY i.appId, i.countryCode, i.advertiserId " +
            "ORDER BY (SUM(c.revenue)) DESC")
    List<GetAdvertiserInfoDto> findTopAdvertisers();


}

package com.example.mapper;

import com.example.database.entity.Impression;
import com.example.dto.ImpressionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImpressionMapper {
   List <Impression> toImpression(List<ImpressionDto> impressionDto);

}

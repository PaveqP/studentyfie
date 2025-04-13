package com.ludoed.university.mapper;

import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.model.UniversityGeographic;
import com.ludoed.university.model.UniversityInfo;
import com.ludoed.university.model.UniversitySocials;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UniversityMapper {

    public UniversityInfo toUniversityInfo(UniversityFullDto universityDto) {
        UniversityInfo universityInfo = new UniversityInfo();
        universityInfo.setName(universityDto.getName());
        universityInfo.setDescription(universityDto.getDescription());
        universityInfo.setAvatar(universityDto.getAvatar());
        universityInfo.setRating(universityDto.getRating());
        return universityInfo;
    }

    public UniversityFullDto toUniversityFullDto(UniversityInfo universityInfo, UniversityGeographic geographic, List<UniversitySocials> socials) {
        return new UniversityFullDto(
                universityInfo.getId(),
                universityInfo.getName(),
                universityInfo.getDescription(),
                universityInfo.getAvatar(),
                universityInfo.getRating(),
                geographic,
                socials
        );
    }

    public List<UniversityFullDto> toUniversityFullDtoList(
            List<UniversityInfo> universitiesInfo,
            List<UniversityGeographic> geographicList,
            List<UniversitySocials> socialsList) {

        // Группируем географические данные по ID университета
        Map<Long, UniversityGeographic> geographicByUniId = geographicList.stream()
                .collect(Collectors.toMap(
                        geo -> geo.getUniversityInfo().getId(),
                        Function.identity()));

        // Группируем социальные сети по ID университета
        Map<Long, List<UniversitySocials>> socialsByUniId = socialsList.stream()
                .collect(Collectors.groupingBy(
                        social -> social.getUniversityInfo().getId()));

        return universitiesInfo.stream()
                .map(universityInfo -> {
                    UniversityGeographic geographic = geographicByUniId.get(universityInfo.getId());
                    List<UniversitySocials> socials = socialsByUniId.getOrDefault(
                            universityInfo.getId(), Collections.emptyList());

                    return toUniversityFullDto(universityInfo, geographic, socials);
                })
                .collect(Collectors.toList());
    }
}

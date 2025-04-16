package com.ludoed.university.mapper;

import com.ludoed.agent.model.AgentFullDto;
import com.ludoed.university.dto.ExchangeProgramDto;
import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.model.ExchangeProgram;
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

    public ExchangeProgramDto toExchangeProgramDto(ExchangeProgram program, AgentFullDto agentFullDto) {
        ExchangeProgramDto programDto = new ExchangeProgramDto();
        programDto.setName(program.getName());
        programDto.setDescription(program.getDescription());
        programDto.setRating(program.getRating());
        programDto.setAgent(agentFullDto);
        programDto.setProgramCondition(program.getProgramCondition());
        programDto.setUniversityInfo(program.getUniversityInfo());
        return programDto;
    }

    public UniversityFullDto toUniversityFullDto(UniversityInfo universityInfo,
                                                 UniversityGeographic geographic, List<UniversitySocials> socials,
                                                 List<ExchangeProgram> programs) {
        return new UniversityFullDto(
                universityInfo.getId(),
                universityInfo.getName(),
                universityInfo.getDescription(),
                universityInfo.getAvatar(),
                universityInfo.getRating(),
                programs,
                geographic,
                socials
        );
    }

    public List<UniversityFullDto> toUniversityFullDtoList(
            List<UniversityInfo> universitiesInfo,
            List<UniversityGeographic> geographicList,
            List<ExchangeProgram> programsList,
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

        Map<Long, List<ExchangeProgram>> programsByUniId = programsList.stream()
                .collect(Collectors.groupingBy(
                        program -> program.getUniversityInfo().getId()));

        return universitiesInfo.stream()
                .map(universityInfo -> {
                    UniversityGeographic geographic = geographicByUniId.get(universityInfo.getId());
                    List<UniversitySocials> socials = socialsByUniId.getOrDefault(
                            universityInfo.getId(), Collections.emptyList());
                    List<ExchangeProgram> programs = programsByUniId.getOrDefault(
                            universityInfo.getId(), Collections.emptyList());

                    return toUniversityFullDto(universityInfo, geographic, socials, programs);
                })
                .collect(Collectors.toList());
    }
}

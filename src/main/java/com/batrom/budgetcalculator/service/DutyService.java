package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.DutyDTO;
import com.batrom.budgetcalculator.model.Duty;
import com.batrom.budgetcalculator.repository.DutyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToFunction;

@Service
public class DutyService {

    public DutyDTO save(final DutyDTO dto) {
        return wrapToFunction(this::dtoToEntity).andThen(dutyRepository::save)
                                                .andThen(this::entityToDTO)
                                                .apply(dto);
    }

    public DutyDTO update(final DutyDTO dto) {
        return save(dto);
    }

    public Long delete(final DutyDTO dto) {
        dutyRepository.deleteById(dto.getId());
        return dto.getId();
    }

    public List<DutyDTO> getAll() {
        return dutyRepository.findAll()
                             .stream()
                             .map(this::entityToDTO)
                             .collect(Collectors.toList());
    }

    private DutyDTO entityToDTO(final Duty duty) {
        final DutyDTO dutyDTO = new DutyDTO();
        dutyDTO.setDescription(duty.getDescription());
        dutyDTO.setOrderer(duty.getOrderer().getName());
        dutyDTO.setExecutor(duty.getExecutor().getName());
        dutyDTO.setDate(duty.getDate().toString());
        dutyDTO.setPoints(duty.getPoints());
        dutyDTO.setDone(duty.getDone());
        return dutyDTO;
    }

    private Duty dtoToEntity(final DutyDTO dto) {
        final Duty duty = new Duty();
        duty.setDescription(dto.getDescription());
        duty.setOrderer(memberService.findByName(dto.getOrderer()));
        duty.setExecutor(memberService.findByName(dto.getExecutor()));
        duty.setDate(Objects.isNull(dto.getDate()) ? LocalDate.now() : LocalDate.parse(dto.getDate()));
        duty.setPoints(calculatePoints(duty));
        duty.setDone(Objects.isNull(dto.getDone()) ? false : dto.getDone());
        return duty;
    }

    private Long calculatePoints(final Duty duty) {
        final long pointsForDate = duty.getDate().isBefore(LocalDate.now()) ? 1 : 0;
        final long pointsForMember = !duty.getExecutor().equals(duty.getOrderer()) ? 1 : 0;
        return 1 + pointsForDate + pointsForMember;
    }

    private final MemberService memberService;

    private final DutyRepository dutyRepository;

    @Autowired
    public DutyService(final MemberService memberService, final DutyRepository dutyRepository) {
        this.memberService = memberService;
        this.dutyRepository = dutyRepository;
    }
}

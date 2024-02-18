package myhome.accountbook.entity;


import myhome.accountbook.dto.CalculateDetailDto;

import java.util.List;

public interface CalculateRepository {

    List<CalculateDetailDto> findContentsList(String type, int period);


}

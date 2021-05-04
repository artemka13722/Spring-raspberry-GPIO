package com.raspberry.raspberry.utils.dtoTransform;

import com.raspberry.raspberry.dto.SetSettingsDto;
import com.raspberry.raspberry.model.GPIOSettings;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GPIOSettingsDtoTransformService {

    private final ModelMapper mapper;

    public SetSettingsDto getSetSettingsDto(GPIOSettings gpioSettings) {
        return mapper.map(gpioSettings, SetSettingsDto.class);
    }
}

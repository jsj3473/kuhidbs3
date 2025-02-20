package com.example.kuhidbs.dto.Fund;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RFundNameDTO {
    private String fundId;
    private String fundName;
}

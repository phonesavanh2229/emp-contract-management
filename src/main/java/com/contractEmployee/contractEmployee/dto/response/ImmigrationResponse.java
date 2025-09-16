
package com.contractEmployee.contractEmployee.dto.response;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImmigrationResponse {
    private List<EmployeeDto> data;
}

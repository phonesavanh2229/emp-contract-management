package com.contractEmployee.contractEmployee.search;

public enum FilterState {
    ANY,       // ไม่กรอง
    ACTIVE,    // ยังไม่หมดอายุ และสถานะ ACTIVE
    EXPIRING,  // จะหมดภายใน N วัน
    EXPIRED    // หมดอายุแล้ว
}

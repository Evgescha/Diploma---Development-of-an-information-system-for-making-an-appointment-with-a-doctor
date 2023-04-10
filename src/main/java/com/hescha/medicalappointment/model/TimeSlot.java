package com.hescha.medicalappointment.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TimeSlot {
    H09_M00_TO_H09_M30 ("9:00 - 9:30"),
    H09_M30_TO_H10_M00 ("9:30 - 10:00"),
    H10_M00_TO_H10_M30 ("10:00 - 10:30"),
    H10_M30_TO_H11_M00 ("10:30 - 11:00"),
    H11_M00_TO_H11_M30 ("11:00 - 11:30"),
    H11_M30_TO_H12_M00 ("11:30 - 12:00"),
    H12_M00_TO_H12_M30 ("12:00 - 12:30"),
    H12_M30_TO_H13_M00 ("12:30 - 13:00"),
    H13_M00_TO_H13_M30 ("13:00 - 13:30"),
    H13_M30_TO_H14_M00 ("13:30 - 14:00"),
    H14_M00_TO_H14_M30 ("14:00 - 14:30"),
    H14_M30_TO_H15_M00 ("14:30 - 15:00"),
    H15_M00_TO_H15_M30 ("15:00 - 15:30"),
    H15_M30_TO_H16_M00 ("15:30 - 16:00"),
    H16_M00_TO_H16_M30 ("16:00 - 16:30"),
    H16_M30_TO_H17_M00 ("16:30 - 17:00"),
    H17_M00_TO_H17_M30 ("17:00 - 17:30"),
    H17_M30_TO_H18_M00 ("17:30 - 18:00");

    final String name;
}

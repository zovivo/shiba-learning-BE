package com.shibalearning.entity.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemException extends Exception {

    private ExceptionCode exceptionCode;

}

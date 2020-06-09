package com.shibalearning.entity.response;

import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseData {

    private Status status;
    private ExceptionCode code;
    private String description;
    private Object data;

}

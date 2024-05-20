package com.ctey.ikunfanscommon.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {
    private String account;
    private String password;
}

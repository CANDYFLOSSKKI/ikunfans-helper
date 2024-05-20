package com.ctey.ikunfansweb.Service;

import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Util.AccountTokenUtil;
import com.ctey.ikunfanscommon.Util.DataParamParseUtil;
import com.ctey.ikunfansweb.Config.DockingInterfaceConfig.Docking;
import org.springframework.stereotype.Component;

@Component
public class TokenWebService {
    @Docking
    public TokenClaimEntity handleAccountAuth(String auth) {
        String token = DataParamParseUtil.parseAuthToToken(auth);
        if (!AccountTokenUtil.checkAccountToken(token)) { return null; }
        return AccountTokenUtil.parseAccountToken(token);
    }

}

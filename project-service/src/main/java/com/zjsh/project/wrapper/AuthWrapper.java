package com.zjsh.project.wrapper;

import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;
import com.zjsh.auth.sdk.dto.response.UserInfoResp;
import com.zjsh.auth.sdk.feign.AuthServiceApi;
import com.zjsh.common.exception.BusinessException;
import com.zjsh.common.exception.BusinessExceptionEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@Slf4j
public class AuthWrapper {
    @Resource
    private AuthServiceApi authService;

    //todo:这里返回不能是dto，必须做防腐
        public AuthInfoDto test() {
            UserInfoReq userInfoReq = new UserInfoReq();
            userInfoReq.setId(1L);
            try{
                UserInfoResp resp = authService.getUser(userInfoReq);
                if(resp == null){
                    log.error("xxxxx");
                    return null;
                }
                if(resp.getCode() != 0){
                    log.error("xxxxx");
                    throw new RuntimeException("xxxxx" + resp.getCode());
                }
                return resp.getData();
            }
            catch(Exception e){
                log.error("调用auth服务异常 userId:{}", userInfoReq.getId(), e);
                throw new BusinessException(BusinessExceptionEnum.SYSTEM_ERROR.getCode(),BusinessExceptionEnum.SYSTEM_ERROR.getMsg());
            }

    }
}

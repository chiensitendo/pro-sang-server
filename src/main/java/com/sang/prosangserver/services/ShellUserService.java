package com.sang.prosangserver.services;

import com.sang.prosangserver.dto.response.ShellUserInfoResponse;
import com.sang.prosangserver.entities.account.ShellUser;
import com.sang.prosangserver.repositories.ShellUserRepository;
import com.sang.prosangserver.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ShellUserService {

    final ShellUserRepository shellUserRepository;

    @Autowired
    public ShellUserService(final ShellUserRepository shellUserRepository) {
        this.shellUserRepository = shellUserRepository;
    }

    public ShellUserInfoResponse getShellUserInfo(String key, String name) {
        Optional<ShellUser> opt = shellUserRepository.findShellUserByKeyAndAndIsDeletedFalse(key);
        if (opt.isPresent()) {
            ShellUser shellUser = opt.get();
            if (name != null && name != "" && !shellUser.getName().equals(name)) {
                shellUser.setName(name);
            }
            shellUser.setUpdatedDate(LocalDateTime.now());
            shellUserRepository.saveAndFlush(shellUser);
            return new ShellUserInfoResponse(shellUser.getName());
        }
        ShellUser shellUser = new ShellUser();
        shellUser.setName(StringUtils.generateShellUserName());
        shellUser.setKey(key);
        shellUserRepository.saveAndFlush(shellUser);
        return new ShellUserInfoResponse(shellUser.getName());
    }
}

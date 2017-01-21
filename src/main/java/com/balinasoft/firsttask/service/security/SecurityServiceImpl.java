package com.balinasoft.firsttask.service.security;


import com.balinasoft.firsttask.domain.User;
import com.balinasoft.firsttask.domain.security.SecurityRole;
import com.balinasoft.firsttask.domain.security.SecurityToken;
import com.balinasoft.firsttask.domain.security.SecurityUser;
import com.balinasoft.firsttask.dto.SignUserDtoIn;
import com.balinasoft.firsttask.dto.SignUserOutDto;
import com.balinasoft.firsttask.repository.UserRepository;
import com.balinasoft.firsttask.repository.security.SecurityRoleRepository;
import com.balinasoft.firsttask.repository.security.SecurityTokenRepository;
import com.balinasoft.firsttask.repository.security.SecurityUserRepository;
import com.balinasoft.firsttask.system.error.exception.SecurityUserException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final SecurityUserRepository securityUserRepository;

    private final SecurityRoleRepository securityRoleRepository;

    private final SecurityTokenRepository securityTokenRepository;

    private final TokenGenerator tokenGenerator;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository,
                               SecurityUserRepository securityUserRepository,
                               SecurityRoleRepository securityRoleRepository,
                               SecurityTokenRepository securityTokenRepository,
                               TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.securityUserRepository = securityUserRepository;
        this.securityRoleRepository = securityRoleRepository;
        this.securityTokenRepository = securityTokenRepository;
        this.tokenGenerator = tokenGenerator;
    }

    //    @Value("${project.security.access-token.max-count:}")
//    private int accessTokeMaxCount;
//
//    @Value("${project.security.sms.max-restore-count}")
//    private int smsMaxRestoreCount;
//
//    @Value("${project.security.sms.max-life-time-min}")
//    private int smsCodeMaxLifeTimeMin;
//
//    private SmsService smsService;
//
//    @Autowired
//    public SecurityServiceImpl(SecurityUserRepository securityUserRepository, UserRepository userRepository,
//                               SecurityRoleRepository securityRoleRepository, TokenGenerator tokenGenerator,
//                               SecurityTokenRepository securityTokenRepository, PatientRepository patientRepository,
//                               DoctorRepository doctorRepository, ClinicRepository clinicRepository,
//                               LocalizationService localizationService, SipUserService sipUserService,
//                               UserMoneyService userMoneyService, SmsService smsService) {
//        this.securityUserRepository = securityUserRepository;
//        this.userRepository = userRepository;
//        this.securityRoleRepository = securityRoleRepository;
//        this.tokenGenerator = tokenGenerator;
//        this.securityTokenRepository = securityTokenRepository;
//        this.patientRepository = patientRepository;
//        this.doctorRepository = doctorRepository;
//        this.clinicRepository = clinicRepository;
//        this.localizationService = localizationService;
//        this.sipUserService = sipUserService;
//        this.userMoneyService = userMoneyService;
//        this.smsService = smsService;
//    }

    @Override
    public SignUserOutDto signup(SignUserDtoIn userDto) {
        if (userRepository.findByLogin(userDto.getLogin()) != null) {
            throw new SecurityUserException("security.signup.login-already-use");
        }

        User user = new User();
        user.setLogin(userDto.getLogin());
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
            if (cause.getConstraintName().equals("login_unique_index")) {
                throw new SecurityUserException("security.signup.login-already-use");
            } else {
                throw e;
            }
        }

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId(user.getId());
        securityUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        securityUser.setRegDate(new Date());

        securityUserRepository.save(securityUser);

        SecurityRole.ROLE role = SecurityRole.ROLE.USER;
        securityRoleRepository.save(new SecurityRole(role, user));

        String token = addNewTokenForUser(securityUser);

        return new SignUserOutDto(user.getId(), user.getLogin(), token);
    }

    @Override
    public SignUserOutDto signin(SignUserDtoIn userDto) {
        SecurityUser securityUser = securityUserRepository.findByLogin(userDto.getLogin());
        if (securityUser == null ||
                !bCryptPasswordEncoder.matches(userDto.getPassword(), securityUser.getPassword())) {
            throw new SecurityUserException("security.signin.incorrect");
        }

        String accessToken = addNewTokenForUser(securityUser);

        User user = securityUser.getUser();

        return new SignUserOutDto(user.getId(), user.getLogin(), accessToken);
    }

    //    private void checkDisabledType(SecurityUser securityUser) {
//        DisabledType disabledType = securityUser.getDisabledType();
//        if (disabledType != null) {
//            switch (disabledType) {
//                case NOT_CONFIRMED_BY_ADMIN:
//                    throw new SecurityUserException("security.wait-admin");
//                case NOT_CONFIRMED_BY_SMS:
//                    throw new SecurityUserException("security.confirmation-required");
//                case BLOCKED:
//                    throw new SecurityUserException("security.blocked");
//                case DELETED:
//                    throw new SecurityUserException("security.deleted");
//            }
//        }
//    }
//
//    @Override
//    public SignInUserOutDto changePassword(ChangePasswordDto changePasswordDto, long userId) {
//        SecurityUser securityUser = securityUserRepository.findOne(userId);
//        if (!bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), securityUser.getPassword())) {
//            throw new SecurityUserException("security.change-password.incorrect-old-password");
//        }
//
//        //delete all tokens
//        if (changePasswordDto.isResetOld()) {
//            securityTokenRepository.deleteByUserId(securityUser.getUserId());
//        }
//
//        securityTokenRepository.flush();
//
//        //generate new token
//        String accessToken = addNewTokenForUser(securityUser);
//
//        //set new password
//        securityUser.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
//        securityUserRepository.save(securityUser);
//
//        //return new token and login and user
//        return new SignInUserOutDto(accessToken);
//    }
//
//    @Override
//    public void delete(long userId) {
//        SecurityUser securityUser = securityUserRepository.findOne(userId);
//
//        //mark as deleted
//        securityUser.setDisabledType(DELETED);
//        securityUser.setChangeStatusDate(new Date());
//        securityUserRepository.save(securityUser);
//
//        User user = securityUser.getUser();
//        user.setEnabled(false);
//        userRepository.save(user);
//
//        //delete all tokens
//        securityTokenRepository.deleteByUserId(securityUser.getUserId());
//    }
//
//    @Override
//    public SignInUserOutDto confirmBySms(ConfirmBySmsDto confirmBySmsDto) {
//        SecurityUser securityUser = securityUserRepository.findByLogin(confirmBySmsDto.getLogin());
//
//        //If user not exists or not require confirm send not required com.balinasoft.firsttask.system.error
//        if (securityUser == null || securityUser.getDisabledType() != NOT_CONFIRMED_BY_SMS) {
//            throw new SecurityUserException("security.confirm-account.not-required");
//        }
//
//        //if code expired
//        if (DateUtils.addMinutes(securityUser.getSmsSendDate(), smsCodeMaxLifeTimeMin).before(new Date())) {
//            throw new SecurityUserException("security.confirm-account.incorrect-code");
//        }
//
//        //if max count reached
//        if (securityUser.getSmsRestoreCount() >= smsMaxRestoreCount) {
//            throw new SecurityUserException("security.confirm-account.incorrect-code");
//        }
//
//        //if sms code not null and correct generate new token and send
//        if (securityUser.getSmsCode() != null && securityUser.getSmsCode().equals(confirmBySmsDto.getCode())) {
//            securityUser.setSmsCode(null);
//            securityUser.setDisabledType(null);
//            securityUser.setChangeStatusDate(new Date());
//            String accessToken = addNewTokenForUser(securityUser);
//            securityUserRepository.save(securityUser);
//
//            //mark user as enabled
//            User user = securityUser.getUser();
//            user.setEnabled(true);
//            userRepository.save(user);
//
//            if (user.getUserType() != UserType.CLINIC) {
//                userMoneyService.createMoneyAccountForUser(user);
//            }
//
//            //add sip account
//            sipUserService.addUser(user.getId());
//
//            return new SignInUserOutDto(accessToken);
//        }
//
//        //increase restore count
//        securityUserRepository.increaseRestoreCount(securityUser.getUserId());
//
//        throw new SecurityUserException("security.confirm-account.incorrect-code");
//    }
//
//    @Override
//    public void resendSms(SmsByLoginDto smsByLoginDto) {
//        SecurityUser securityUser = securityUserRepository.findByLogin(smsByLoginDto.getLogin());
//        if (securityUser != null && securityUser.getDisabledType() == NOT_CONFIRMED_BY_SMS) {
//            //no more than min-sending-delay-min
//            if (DateUtils.addMinutes(securityUser.getSmsSendDate(), MIN_SENDING_DELAY_MIN).after(new Date())) {
//                throw new SecurityUserException("security.sms.wait");
//            }
//            String smsCode = tokenGenerator.generateSmsToken();
//
//            securityUser.setSmsRestoreCount(0);
//            securityUser.setSmsSendDate(new Date());
//            securityUser.setSmsCode(smsCode);
//            securityUserRepository.save(securityUser);
//
//            smsService.send(smsByLoginDto.getLogin(), generateMessageForConfirm(smsCode));
//        } else {
//            throw new SecurityUserException("security.confirm-account.not-required");
//        }
//    }
//
//    @Override
//    public void restoreForgottenPassword(SmsByLoginDto smsByLoginDto) {
//        SecurityUser securityUser = securityUserRepository.findByLogin(smsByLoginDto.getLogin());
//        if (securityUser == null) {
//            throw new SecurityUserException("security.restore-password.phone-not-used");
//        }
//
//        if (securityUser.getDisabledType() == null ||
//                securityUser.getDisabledType() == NOT_CONFIRMED_BY_SMS) {
//            //no more than min-sending-delay-min
//            if (DateUtils.addMinutes(securityUser.getSmsSendDate(), MIN_SENDING_DELAY_MIN).after(new Date())) {
//                throw new SecurityUserException("security.sms.wait");
//            }
//
//            String smsCode = tokenGenerator.generateSmsToken();
//
//            securityUser.setSmsRestoreCount(0);
//            securityUser.setSmsSendDate(new Date());
//            securityUser.setSmsCode(smsCode);
//            securityUserRepository.save(securityUser);
//
//            smsService.send(smsByLoginDto.getLogin(), generateMessageForRestore(smsCode));
//        } else {
//            checkDisabledType(securityUser);
//        }
//    }
//
//    @Override
//    public SignInUserOutDto setNewPassword(NewPasswordDto newPasswordDto) {
//        SecurityUser securityUser = securityUserRepository.findByLogin(newPasswordDto.getLogin());
//
//        if (securityUser == null) {
//            throw new SecurityUserException("security.restore-password.incorrect-code");
//        }
//
//        if (securityUser.getDisabledType() == null ||
//                securityUser.getDisabledType() == NOT_CONFIRMED_BY_SMS) {
//
//            //if code expired
//            if (DateUtils.addMinutes(securityUser.getSmsSendDate(), smsCodeMaxLifeTimeMin).before(new Date())) {
//                throw new SecurityUserException("security.restore-password.incorrect-code");
//            }
//
//            //if max count reached
//            if (securityUser.getSmsRestoreCount() >= smsMaxRestoreCount) {
//                throw new SecurityUserException("security.restore-password.incorrect-code");
//            }
//
//            //if sms code not null and correct set new password, generate new token and send
//            if (securityUser.getSmsCode() != null && securityUser.getSmsCode().equals(newPasswordDto.getCode())) {
//                //delete all tokens
//                if (newPasswordDto.isResetOld()) {
//                    securityTokenRepository.deleteByUserId(securityUser.getUserId());
//                }
//
//                securityTokenRepository.flush();
//
//                securityUser.setPassword(bCryptPasswordEncoder.encode(newPasswordDto.getPassword()));
//                securityUser.setSmsCode(null);
//                securityUser.setDisabledType(null);
//                securityUser.setChangeStatusDate(new Date());
//                String accessToken = addNewTokenForUser(securityUser);
//                securityUserRepository.save(securityUser);
//
//                //mark user as enabled
//                User user = securityUser.getUser();
//                user.setEnabled(true);
//                userRepository.save(user);
//                userMoneyService.createMoneyAccountForUser(user);
//
//                return new SignInUserOutDto(accessToken);
//            }
//
//            //increase restore count
//            securityUserRepository.increaseRestoreCount(securityUser.getUserId());
//        }
//
//        throw new SecurityUserException("security.confirm-account.not-required");
//
//    }
//
//    @Override
//    public void logout(String accessToken) {
//        if (accessToken != null && !accessToken.isEmpty()) {
//            securityTokenRepository.deleteToken(accessToken);
//        }
//    }
//
//    @Override
//    public void changeTypeByAdmin(long userId, DisabledType newType) {
//        //find securityUser
//        SecurityUser securityUser = securityUserRepository.findOne(userId);
//        ApiAssert.notFound(securityUser == null);
//
//        User user = securityUser.getUser();
//
//        DisabledType oldType = securityUser.getDisabledType();
//
//        if (newType == oldType) {
//            return;
//        }
//
//        //enable account
//        if (newType == null) {
//            user.setEnabled(true);
//            sipUserService.addUser(user.getId());
//            if (user.getUserType() != UserType.CLINIC) {
//                userMoneyService.createMoneyAccountForUser(user);
//            }
//        }
//        //disable account
//        else {
//            ApiAssert.unprocessable(newType == NOT_CONFIRMED_BY_ADMIN || newType == NOT_CONFIRMED_BY_SMS);
//            //disable sip account
//            user.setEnabled(false);
//            sipUserService.deleteUser(user.getId());
//        }
//
//        securityUser.setDisabledType(newType);
//        securityUser.setChangeStatusDate(new Date());
//    }
//
    private String addNewTokenForUser(SecurityUser user) {
        String accessToken = tokenGenerator.generateAccessToken();
        SecurityToken securityToken = new SecurityToken();
        securityToken.setSecurityUser(user);
        securityToken.setToken(accessToken);
        securityTokenRepository.save(securityToken);
        return accessToken;
    }

//    private String generateMessageForConfirm(String smsCode) {
//        return localizationService.getMessage("message-confirm-phone", smsCode);
//    }
//
//    private String generateMessageForRestore(String smsCode) {
//        return localizationService.getMessage("message-forgot-password", smsCode);
//    }
}

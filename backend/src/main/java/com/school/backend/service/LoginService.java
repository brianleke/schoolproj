package com.school.backend.service;

import com.school.backend.entities.School;
import com.school.backend.entities.SchoolAdministrators;
import com.school.backend.entities.SuperAdmin;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.model.LoginResponse;
import com.school.backend.repository.SchoolAdministratorRepository;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.repository.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;

import static com.school.backend.util.Constants.*;

@Service
public class LoginService {

    private static String INVALID_LOGIN = "Invalid Login";
//    private static String PRIVATE_KEY = System.getenv("ENV_PRIVATE_KEY");
    private static String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n" +
        "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMYmV9TbmLsb93mS\n" +
        "bLQoNwM6MlOkoSX7wFUKCchiRYWE/M2moB20Z6QmlmUb0GJfbAsWKLrqOdY+C75i\n" +
        "SEegCkLa//QmP90v+z3JxqiUesugSYRw/00JUthN9mnpsHs5Vpm43rXT6YcPMGnk\n" +
        "ePVS4mGHaCTvGZVCMxkOBU/G/LwnAgMBAAECgYAae49rlHniMAewSOCcZ2+Kz+Fg\n" +
        "CnW7NN1swC20iWXPRYngFm/56F2ds8aP9tqv+x7ekHSbHqjiOGKrbElKwVn9fzgC\n" +
        "8P/M3Q43IFYtBLvba97NHl5T0tR0w7VNQ8Pl2ncV4oFk7XJSDZHl+XzX1627acQ9\n" +
        "NayqAIQFnGUFTKYzyQJBAOlAwH0rwjQVEKcBQf0iZpxlowPSp6Tu4fEXesowvpLk\n" +
        "zbf7lIxP8EbUAp2FSRGWMs11xVrrWhDysyXP01ylAdMCQQDZeTo8em71JTa8Q0z2\n" +
        "MNiHUV2UARAYesV2CI3h8SYttZBzXI5NJVaaatrb5fwli1btoWLEYTZzzQj7ZIV0\n" +
        "ypPdAkBGTKW3FAEsAU1LiC2DRWL3sAo5lLvCEd029ISle5t0WlFGKiTTRSR9tFg1\n" +
        "cX5cNpCJK/2XMWerw/oKY2Cog3LRAkA9pQhl9kqXgLn4OZeKPaRTDzRYbMi0N7Db\n" +
        "WWdmgWXSFSJiIP1RTuEymlF5fcKwncPBTjrh8bBuuNLv97mAtp+dAkBTiPuGQM1u\n" +
        "cLrLsIqXs1VtqPoxL60cmOM8VUcjA+3Ifbp7qo/zp9T+RbYM+3McWKkXxZjjMicR\n" +
        "eqbA+MCpj2N1\n" +
        "-----END PRIVATE KEY-----";

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private SchoolAdministratorRepository schoolAdministratorRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public SuperAdmin saveNewSuperAdmin(String username, String encryptedPassword, String token, String firstName, String lastName){
        if(validateToken(token)) {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setUsername(username);
            superAdmin.setFirstName(firstName);
            superAdmin.setLastName(lastName);

            superAdmin.setEncryptedPassword(decryptPasswordWithKey(encryptedPassword));

            return superAdminRepository.save(superAdmin);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }


    private PrivateKey getPrivateKeyFromString(String key) {
        String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----\n", "")
                .replace("\n-----END PRIVATE KEY-----", "");


        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(privateKeyPEM));

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);
            return privKey;
        }
        catch(Exception exception){
            throw new GenericNotFoundException("Unable to decrypt password!");
        }
    }

    public LoginResponse getLoginResponseForUser(String username, String encryptedPassword){
        byte[] encodedSuccess = Base64.encodeBase64(SUCCESSFUL_LOGIN.getBytes());

        String token = PROJECT_ID + new String(encodedSuccess);
        LoginResponse loginResponse = new LoginResponse();

        String decryptedPassword = decryptPasswordWithKey(encryptedPassword);

        SuperAdmin superAdmin = superAdminRepository.findByUsernameAndPassword(username, decryptedPassword);
        if(superAdmin != null) {
            loginResponse.setToken(token);
        }
        else{
            SchoolAdministrators schoolAdmin = schoolAdministratorRepository.findByUsernameAndPassword(username, decryptedPassword);
            if(schoolAdmin != null){
                loginResponse.setToken(token);
                School school = schoolAdmin.getSchool();
                loginResponse.setSchoolId(school.getId().toString());
                loginResponse.setSchoolName(school.getName());
            }
            else{
                loginResponse.setErrorResponse(INVALID_LOGIN);
            }
        }
        return loginResponse;
    }

    private String decryptPasswordWithKey(String encryptedPassword) {
        String decryptedPassword = "";
        try {
            Cipher decrypt = Cipher.getInstance("RSA");

            PrivateKey privateKey = getPrivateKeyFromString(PRIVATE_KEY);

            decrypt.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessage=decrypt.doFinal(Base64.decodeBase64(encryptedPassword));
            decryptedPassword = convertPasswordToBase64(decryptedMessage);

        }
        catch (Exception exception){
            throw new GenericNotFoundException("Password cannot be decrypted");
        }
        return decryptedPassword;
    }

    public boolean validateToken(String token){
        byte[] encodedSuccess = Base64.encodeBase64(SUCCESSFUL_LOGIN.getBytes());
        String validToken = PROJECT_ID + new String(encodedSuccess);
        return validToken.equals(token);
    }

    private String convertPasswordToBase64(byte[] decryptedMessage){
        try {
            String decryptedPasswordString = new String(decryptedMessage, "UTF-8");
            return new String(Base64.encodeBase64(decryptedPasswordString.getBytes()));
        }
        catch (Exception exception){
            throw new GenericNotFoundException(exception.getMessage());
        }
    }

    public SchoolAdministrators saveNewSchoolAdmin(String username, String encryptedPassword, String token, String firstName, String lastName, Long schoolId) {
        if(validateToken(token)) {
            SchoolAdministrators schoolAdministrators = new SchoolAdministrators();
            schoolAdministrators.setUsername(username);
            schoolAdministrators.setPassword(decryptPasswordWithKey(encryptedPassword));
            schoolAdministrators.setFirstName(firstName);
            schoolAdministrators.setLastName(lastName);

            Optional<School> schoolById = schoolRepository.findById(schoolId);
            schoolById.ifPresent(schoolAdministrators::setSchool);

            return schoolAdministratorRepository.save(schoolAdministrators);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }
}

package vincenzomanfredi.EsameBE6.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.UnauthorizedException;
import vincenzomanfredi.EsameBE6.payloads.UtenteLoginDTO;
import vincenzomanfredi.EsameBE6.security.JWTTools;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtenteService utenteService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(UtenteLoginDTO body) {

        Utente found = this.utenteService.findByEmail(body.email());

        if (this.bcrypt.matches(body.password(), found.getPassword())) {
            return this.jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Sbagliate");
        }
    }
}

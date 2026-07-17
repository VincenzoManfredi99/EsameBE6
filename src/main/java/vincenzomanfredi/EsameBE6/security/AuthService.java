package vincenzomanfredi.EsameBE6.security;

import org.springframework.stereotype.Service;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.UnauthorizedException;
import vincenzomanfredi.EsameBE6.payloads.UtenteLoginDTO;
import vincenzomanfredi.EsameBE6.services.UtenteService;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final JWTTools jwtTools;

    public AuthService(UtenteService utenteService, JWTTools jwtTools) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
    }

    public String checkCredentialsAndGenerateToken(UtenteLoginDTO body) {

        Utente found = this.utenteService.findByEmail(body.email());

        if (found.getPassword().equals(body.password())) {
            return this.jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Sbagliate");
        }
    }
}

package vincenzomanfredi.EsameBE6.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.exceptions.BadRequestException; // o la tua ValidationException / BadRequestException
import vincenzomanfredi.EsameBE6.payloads.UtenteDTO;
import vincenzomanfredi.EsameBE6.payloads.UtenteLoginDTO;
import vincenzomanfredi.EsameBE6.payloads.UtenteLoginResponseDTO;
import vincenzomanfredi.EsameBE6.payloads.UtenteResponseDTO;
import vincenzomanfredi.EsameBE6.security.AuthService;
import vincenzomanfredi.EsameBE6.services.UtenteService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtenteService utenteService;

    public AuthController(AuthService authService, UtenteService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body) {
        return new UtenteLoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public UtenteResponseDTO saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new BadRequestException(errorsList.toString());
        }

        Utente saved = this.utenteService.save(body);
        return new UtenteResponseDTO(saved.getId());
    }
}

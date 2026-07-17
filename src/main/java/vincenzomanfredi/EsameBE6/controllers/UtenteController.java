package vincenzomanfredi.EsameBE6.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vincenzomanfredi.EsameBE6.entities.Utente;
import vincenzomanfredi.EsameBE6.payloads.PasswordChangeDTO;
import vincenzomanfredi.EsameBE6.payloads.UtenteUpdateDTO;
import vincenzomanfredi.EsameBE6.services.UtenteService;

@RestController
@RequestMapping("/users")
public class UtenteController {
    private final UtenteService usersService;

    public UtenteController(UtenteService usersService) {
        this.usersService = usersService;
    }

    //GET
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "cognome") String orderBy) {
        return this.usersService.getAll(page, size, orderBy);
    }

    @GetMapping("/me")
    public Utente getOwnProfile(@AuthenticationPrincipal Utente authenticatedUser) {
        return authenticatedUser;
    }

    @PutMapping("/me")
    public Utente updateOwnProfile(@AuthenticationPrincipal Utente authenticatedUser, @RequestBody UtenteUpdateDTO body) {
        return this.usersService.findByIdAndUpdate(authenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnProfile(@AuthenticationPrincipal Utente authenticatedUser) {
        this.usersService.findByIdAndDelete(authenticatedUser.getId());
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@AuthenticationPrincipal Utente authenticatedUser, @RequestBody PasswordChangeDTO body) {
        this.usersService.updatePassword(authenticatedUser.getId(), body);
    }

    //GET
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public Utente getById(@PathVariable long userId) {
        return this.usersService.findById(userId);
    }

    // PUT
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public Utente getByIdAndUpdate(@PathVariable long userId, @RequestBody UtenteUpdateDTO body) {
        return this.usersService.findByIdAndUpdate(userId, body);
    }

    // DELETE
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable long userId) {
        this.usersService.findByIdAndDelete(userId);
    }
}
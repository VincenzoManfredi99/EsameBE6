package vincenzomanfredi.EsameBE6.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    public Utente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.UTENTE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Il metodo mi impone di restituire una Collection di Authorities cioè di RUOLI (al plurale perché in altre applicazioni
        // potrebbe anche succedere che un utente abbia più di un ruolo)
        // SimpleGrantedAuthority è una classe che implementa GrantedAuthority, cioè l'interfaccia "ufficiale" per i ruoli in Spring Security
        // a noi quindi basta passare il nostro enum al suo costruttore e metterlo nella lista
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

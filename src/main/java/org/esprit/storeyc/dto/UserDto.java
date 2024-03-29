package org.esprit.storeyc.dto;

import lombok.*;
import org.esprit.storeyc.entities.Command;
import org.esprit.storeyc.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    private Integer id;
    private String nom;
    private String prenom;
    private String photo;
    private String email;
    private String motDePasse;
    private List<Integer> commandIds;


    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }
        List<Integer> commandIds = user.getCommands()
                .stream()
                .map(Command::getCommandeNumber)
                .collect(Collectors.toList());
        return UserDto.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .photo(user.getPhoto())
                .email(user.getEmail())
                .motDePasse(user.getMotDePasse())
                .commandIds(commandIds)
                .build();
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setNom(userDto.getNom());
        user.setPrenom(userDto.getPrenom());
        user.setPhoto(userDto.getPhoto());
        user.setEmail(userDto.getEmail());
        user.setMotDePasse(userDto.getMotDePasse());
        return user;
    }

}

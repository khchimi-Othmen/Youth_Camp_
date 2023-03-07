//package org.esprit.storeyc.dto;
//
//import lombok.*;
//import org.esprit.storeyc.entities.Command;
//import org.esprit.storeyc.entities.Delivery;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class DeliveryDto {
//
//    private Integer id;
//    private String address;
//    private LocalDate deliveryDate;
//    private List<Integer> commandIds;
///*L'erreur se produit car la méthode getCommands() de l'entité Delivery renvoie une liste de Command et non une liste d'identifiants d'entité Command. Pour résoudre cette erreur, nous devons mapper les identifiants de commandes de l'entité Delivery en une liste d'entiers dans la méthode fromEntity de la classe DeliveryDto et faire l'inverse dans la méthode toEntity. Voici un exemple de code qui effectue cette opération:*/
//    public static DeliveryDto fromEntity(Delivery delivery) {
//        if (delivery == null) {
//            return null;
//        }
//        List<Integer> commandIds = delivery.getCommands()
//                .stream()
//                .map(Command::getCommandeNumber)
//                .collect(Collectors.toList());
//        return DeliveryDto.builder()
//                .id(delivery.getId())
//                .address(delivery.getAddress())
//                .deliveryDate(delivery.getDeliveryDate())
//                .commandIds(commandIds)
//                .build();
//    }
//    public static Delivery toEntity(DeliveryDto deliveryDto){
//        if(deliveryDto == null){
//            return null;
//        }
//
//        Delivery delivery = new Delivery();
//
//        delivery.setId(deliveryDto.getId());
//        delivery.setAddress(deliveryDto.getAddress());
//        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
//        List<Command> commands = deliveryDto.getCommandIds()
//                .stream()
//                .map(id -> {
//                    Command command = new Command();
//                    command.setCommandeNumber(id);
//                    return command;
//                })
//                .collect(Collectors.toList());
//        delivery.setCommands(commands);
//        return delivery;
//    }
//}

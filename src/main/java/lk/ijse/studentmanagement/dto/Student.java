package lk.ijse.studentmanagement.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class Student implements Serializable {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}

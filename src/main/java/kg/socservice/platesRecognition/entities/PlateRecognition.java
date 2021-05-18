package kg.socservice.platesRecognition.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plate_recognition")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PlateRecognition {

    @Id
    @GeneratedValue
    @Column(name = "plate_recognition_id")
    Long id;

    @Column(name = "plate")
    String plate;

    @Column(name = "cam_ip")
    String camIp;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "recognition_date")
    Date recognitionDate;

    @Column(name = "count")
    Integer count;
}

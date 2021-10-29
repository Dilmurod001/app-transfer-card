package uz.pdp.apptransfercard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.apptransfercard.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private Date expiredDate;
    @Column(nullable = false)
    private boolean active = true;
}
